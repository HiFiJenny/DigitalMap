package com.comac.common.minio.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import com.comac.common.core.exception.CustomException;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Upload;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Descripiton minio工具类
 * @Version 1.0.0
 */
@Slf4j
@Service
public class MinioUtil {
    /**
     * 默认块大小
     * 上传的时候必须设置分块大小。对象大小直接-1，分块大小[5m,5G]，参数的单位是B，
     * 所以最小单位是：5 * 1024 * 1024 = 5242880。minio支持分块传输
     */
    private final long defaultPartSize = 5242880;

    @Autowired
    private MinioClient minioClient;

    /**
     * 创建bucket
     *
     * @param bucketName bucket名称
     */
    public boolean bucketExists(String bucketName) {
        try {
            bucketName = bucketName.toLowerCase();
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            throw new CustomException("创建BUCKET失败");
        }
    }

    /**
     * 创建bucket
     *
     * @param bucketName bucket名称
     */
    public void createBucket(String bucketName) {
        try {
            bucketName = bucketName.toLowerCase();
            if (!bucketExists(bucketName)) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            throw new CustomException("创建BUCKET失败");
        }
    }

    /**
     * 获取全部bucket
     */
    public List<Bucket> getAllBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            throw new CustomException("获取BUCKET列表失败");
        }
    }

    /**
     * 根据bucketName获取信息
     *
     * @param bucketName bucket名称
     */
    @SneakyThrows
    public Optional<Bucket> getBucket(String bucketName) {
        try {
            return minioClient.listBuckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
        } catch (Exception e) {
            throw new CustomException("获取BUCKET信息失败");
        }
    }

    /**
     * 根据bucketName删除信息
     *
     * @param bucketName bucket名称
     */
    @SneakyThrows
    public void removeBucket(String bucketName) {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            throw new CustomException("删除BUCKET信息失败");
        }
    }

    /**
     * 获取文件外链
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param expires    (单位秒) 过期时间 <=7
     * @return url
     */
    public String getObjectURL(String bucketName, String objectName, Integer expires) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .method(Method.GET)
                    .build());
        } catch (Exception e) {
            throw new CustomException("获取文件外链失败");
        }
    }



    /**
     * 上传文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param stream     文件流
     */
    public void uploadFile(String bucketName, String objectName, InputStream stream) {
        try {
            this.createBucket(bucketName);
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(stream, -1, defaultPartSize).build());
        } catch (Exception e) {
            throw new CustomException("文件上传失败", e);
        }
    }

    /**
     * 上传文件
     *
     * @param file       文件
     * @param bucketName 存储桶
     * @return
     */
    public String uploadFile(MultipartFile file, String bucketName) {
        try {
            bucketName = bucketName.toLowerCase();
            String filePath = bucketName + "/";
            // 文件名
            String originalFilename = file.getOriginalFilename();
            // 新的文件名
            String fileName = System.currentTimeMillis() + "_" + originalFilename;
            // 判断存储桶是否存在
            this.createBucket(bucketName);
            // 开始上传
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(file.getInputStream(), -1, defaultPartSize).contentType(file.getContentType()).build());
            filePath += fileName;
            return filePath;
        } catch (Exception e) {
            log.error("文件上传失败 MinioUtil:uploadFile()", e);
            throw new CustomException("文件上传失败");
        }
    }

    /**
     * 上传文件
     *
     * @param file 文件
     * @return
     */
    public String uploadFile(MultipartFile file) {
        try {
            String bucketName = DateUtil.format(new Date(), "yyyyMMdd");
            return uploadFile(file, bucketName);
        } catch (Exception e) {
            log.error("文件上传失败 MinioUtil:uploadFile()", e.getMessage());
            throw new CustomException("文件上传失败");
        }

    }


    /**
     * 上传文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param fileName   上传文件路径
     */
    public void uploadFile(String bucketName, String objectName, String fileName) {
        try {
            // 判断存储桶是否存在
            this.createBucket(bucketName);
            minioClient.uploadObject(UploadObjectArgs.builder().bucket(bucketName).object(objectName).filename(fileName).build());
        } catch (Exception e) {
            throw new CustomException("文件上传失败");
        }
    }

    /**
     * 上传文件
     *
     * @param bucketName  bucket名称
     * @param objectName  文件名称
     * @param stream      文件流
     * @param size        大小
     * @param contextType 类型
     */
    public void uploadFile(String bucketName, String objectName, InputStream stream, long size, String contextType) {
        try {
            // 判断存储桶是否存在
            this.createBucket(bucketName);
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(stream, size, defaultPartSize).contentType(contextType).build());
        } catch (Exception e) {
            throw new CustomException("文件上传失败");
        }
    }

    /**
     * 获取文件信息
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     */
    public StatObjectResponse getObjectInfo(String bucketName, String objectName) {
        try {
            return minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            throw new CustomException("获取MINIO服务器文件失败");
        }
    }

    public InputStream getInputStream(String filePath) {
        String[] split = filePath.split("/");
        if (split.length < 2) {
            throw new CustomException("文件路径有误");
        }
        String bucketName = split[0];
        String objectName = filePath.substring(filePath.indexOf("/") + 1);
        return getObjectInputStream(bucketName, objectName);
    }

    /**
     * 获取文件流
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @return 二进制流
     */
    public InputStream getObjectInputStream(String bucketName, String objectName) {
        try {
            return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            log.error("getObjectInputStream error bucketName:{} objectName:{}", bucketName, objectName);
            throw new CustomException("获取文件流失败");
        }
    }

    /**
     * 获取文件流
     *
     * @param response 响应体
     * @param filePath 文件路径
     * @return 二进制流
     */
    public void getFileInputStream(HttpServletResponse response, String filePath) {
        String[] split = filePath.split("/");
        if (split.length < 2) {
            throw new CustomException("文件路径有误");
        }
        String bucketName = split[0];
        String objectName = filePath.substring(filePath.indexOf("/") + 1);
        this.getFileInputStream(response, bucketName, objectName);
    }

    /**
     * 获取mino文件流
     *
     * @param response
     * @param filePath
     */
    public void getPDFFileInputStream(HttpServletResponse response, String filePath) {
        String[] split = filePath.split("/");
        if (split.length < 2) {
            throw new CustomException("文件路径有误");
        }
        String bucketName = split[0];
        String objectName = filePath.substring(filePath.indexOf("/") + 1);
        this.getFileInputStream(response, bucketName, objectName);
    }

    /**
     * 获取文件流
     *
     * @param response   响应体
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @return 二进制流
     */
    public void getFileInputStream(HttpServletResponse response, String bucketName, String objectName) {
        if (!bucketExists(bucketName)) {
            throw new CustomException("文件BUCKET不存在");
        }
        try {
            InputStream inputStream = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
            response.reset();
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(objectName, "UTF-8"));
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("UTF-8");
            OutputStream outputStream = response.getOutputStream();
            IoUtil.copy(inputStream, outputStream);
        } catch (Exception e) {
            throw new CustomException("获取文件流失败");
        }
    }

    /**
     * 获取文件
     *
     * @param fileName 文件路径
     * @return
     */
    public File getObjectFile(String filePath, String fileName) {
        String[] split = filePath.split("/");
        if (split.length < 2) {
            throw new CustomException("文件路径有误");
        }
        String bucketName = split[0];
        String objectName = filePath.substring(filePath.indexOf("/") + 1);
        return getObjectFile(bucketName, objectName, fileName);
    }

    /**
     * 获取文件
     *
     * @param bucketName
     * @param objName
     * @return
     */
    public File getObjectFile(String bucketName, String objName, String fileName) {
        InputStream stream = getObjectInputStream(bucketName, objName);
        if (ObjectUtil.isNull(stream)) {
            return null;
        }
        File file = new File(fileName);
        FileUtil.writeFromStream(stream, file);
        return file;
    }

    /**
     * 删除文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     */
    public void removeObject(String bucketName, String objectName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            throw new CustomException("删除文件失败");
        }
    }

    /**
     * 查询policy
     *
     * @param bucketName
     * @return
     */
    public String getBucketPolicy(String bucketName) {
        try {
            return minioClient.getBucketPolicy(GetBucketPolicyArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            throw new CustomException("查询policy失败");
        }
    }

    /**
     * 设置policy
     *
     * @param bucketName
     * @return
     * @throws Exception
     */
//    public void setBucketPolicy(String bucketName) throws Exception {
//        StringBuilder builder = new StringBuilder();
//        builder.append("{\n");
//        builder.append("    \"Statement\": [\n");
//        builder.append("        {\n");
//        builder.append("            \"Action\": [\n");
//        builder.append("                \"s3:GetBucketLocation\",\n");
//        builder.append("                \"s3:ListBucket\"\n");
//        builder.append("            ],\n");
//        builder.append("            \"Effect\": \"Allow\",\n");
//        builder.append("            \"Principal\": \"*\",\n");
//        builder.append("            \"Resource\": \"arn:aws:s3:::my-bucketname\"\n");
//        builder.append("        },\n");
//        builder.append("        {\n");
//        builder.append("            \"Action\": \"s3:GetObject\",\n");
//        builder.append("            \"Effect\": \"Allow\",\n");
//        builder.append("            \"Principal\": \"*\",\n");
//        builder.append("            \"Resource\": \"arn:aws:s3:::my-bucketname/myobject*\"\n");
//        builder.append("        }\n");
//        builder.append("    ],\n");
//        builder.append("    \"Version\": \"2012-10-17\"\n");
//        builder.append("}\n");
//        minioClient.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucketName).config(builder.toString()).build());
//    }

}
