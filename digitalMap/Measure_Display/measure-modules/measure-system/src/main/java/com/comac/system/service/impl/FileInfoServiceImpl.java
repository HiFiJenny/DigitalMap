package com.comac.system.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;

import com.comac.common.core.exception.CustomException;
import com.comac.common.minio.service.MinioUtil;
import com.comac.common.security.utils.SecurityUtils;
import com.comac.system.domain.SysFileInfo;
import com.comac.system.mapper.SysFileInfoMapper;
import com.comac.system.service.IFileInfoService;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;


/**
 * 文件对象服务层实现类
 *
 * @author hyy
 * @since 2023-01-16 14:08:14
 */
@Service
public class FileInfoServiceImpl  implements IFileInfoService {

    @Resource
    private SysFileInfoMapper fileInfoMapper;

    @Resource
    private MinioUtil minioUtil;

    @Value("${minio.file.prefix:http://172.16.40.195:9001/browser}")
    private String fileLocationPrefix;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysFileInfo uploadFile(MultipartFile file) {
        String bucketName = DateUtil.format(new Date(), "yyyyMMdd");
        return this.uploadFileWithBucketName(bucketName, file);
    }

    @Override
    public List<SysFileInfo> uploadFileMultiple(MultipartFile[] fileArr) {
        List<SysFileInfo> fileInfoList = new ArrayList<>();
        for (MultipartFile multipartFile : fileArr) {
            fileInfoList.add(this.uploadFile(multipartFile));
        }
        return fileInfoList;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysFileInfo uploadFileWithBucketName(String bucketName, MultipartFile file) {
        String filePath = minioUtil.uploadFile(file, bucketName);
        String objectURL = minioUtil.getObjectURL(bucketName, filePath.split("/")[1], 3600);
        SysFileInfo sysFileInfo = SysFileInfo.builder()
                .filePath(filePath)
                .fileSize(file.getSize())
                .fileName(file.getOriginalFilename())
                .fileSuffix(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")))
                .createUserName(SecurityUtils.getUsername())
                .createTime(DateUtil.date())
                .objectUrl(objectURL)
                .build();
       /* SysFileInfo.setAgentId(SecurityUtils.getAgentId());
        fileInfoMapper.insertSysFileInfo(SysFileInfo);*/
        return sysFileInfo;
    }

    @Override
    public SysFileInfo insertFileInfo(String bucketName, String originalFileName, String filePath, Long size) {
        SysFileInfo sysFileInfo = SysFileInfo.builder()
                .filePath(filePath)
                .fileSize(size)
                .fileName(originalFileName)
                .fileSuffix(originalFileName.substring(originalFileName.lastIndexOf(".")))
                .delFlag("0")
                .createUserName(SecurityUtils.getUsername())
                .createTime(DateUtil.date())
                .build();
//        sysFileInfo.setAgentId(SecurityUtils.getAgentId());
        fileInfoMapper.insertSysFileInfo(sysFileInfo);
        return sysFileInfo;
    }

    @Override
    public List<SysFileInfo> uploadFileMultipleWithBucketName(String bucketName, MultipartFile[] fileArr) {
        List<SysFileInfo> fileInfoList = new ArrayList<>();
        for (MultipartFile multipartFile : fileArr) {
            fileInfoList.add(this.uploadFileWithBucketName(bucketName, multipartFile));
        }
        return fileInfoList;
    }

    @Override
    public void downloadFile(HttpServletResponse response, SysFileInfo SysFileInfo) {
        if (ObjectUtil.isNull(SysFileInfo.getFilePath())) {
            SysFileInfo = fileInfoMapper.selectSysFileInfoByFileId(SysFileInfo.getFileId());
            if (ObjectUtil.isNull(SysFileInfo)) {
                throw new CustomException("文件信息不存在，请确认文件是否已删除");
            }
        }
        minioUtil.getFileInputStream(response, SysFileInfo.getFilePath());
    }

    @Override
    public void previewDownloadFile(HttpServletResponse response, SysFileInfo sysFileInfo) throws Exception {
        if ( ".pdf".equalsIgnoreCase(sysFileInfo.getFileSuffix()) ) {
            response.setContentType("application/pdf");
        } else if (".xml".equalsIgnoreCase(sysFileInfo.getFileSuffix())) {
            response.setContentType("application/xml");
        }
        if (ObjectUtil.isNull(sysFileInfo.getFilePath())) {
            sysFileInfo = fileInfoMapper.selectSysFileInfoByFileId(sysFileInfo.getFileId());

        } else {
            sysFileInfo = getByFilePath(sysFileInfo.getFilePath());
        }
        if (ObjectUtil.isNull(sysFileInfo)) {
            throw new CustomException("文件信息不存在，请确认文件是否已删除");
        }
//        downloadFile(response, SysFileInfo);
        response.setHeader("Content-Disposition", "inline; filename=" + sysFileInfo.getFileName());

        InputStream inputStream = minioUtil.getInputStream(sysFileInfo.getFilePath());
        OutputStream outputStream = response.getOutputStream();
        IOUtils.copy(inputStream, outputStream);

    }

    @Override
    public void compressZip(List<String> pathList, HttpServletResponse response) {
        InputStream inputStream = null;
        for (String path : pathList) {
            inputStream = minioUtil.getInputStream(path);
        }
            byte[] buffer = new byte[4096];
            try {
                OutputStream os = response.getOutputStream();
                ZipOutputStream zos = new ZipOutputStream(os);
                ZipEntry entry = new ZipEntry("softwarePackageInfo");
                zos.putNextEntry(entry);
                //写入压缩文件
                int size = 0;
                //设置读取数据缓存大小
                while ((size = inputStream.read(buffer)) > 0) {
                    zos.write(buffer, 0, size);
                }
                //关闭输入输出流
                inputStream.close();
                zos.closeEntry();
                zos.close();
            } catch (IOException e) {
//                log.error("download zipOutputStream error");
                throw new RuntimeException(e);
            }


        /*try {
            zipOutStream = new ZipOutputStream(new BufferedOutputStream(response.getOutputStream()));
            zipOutStream.setMethod(ZipOutputStream.DEFLATED);
            for (File file : fileList) {
//                byte[] data = Files.readAllBytes(file.toPath());
                zipOutStream.putNextEntry(new ZipEntry(file.getName()));
                int len;
                byte[] buf = new byte[1024];
                FileInputStream in = new FileInputStream(file);
                while ((len = in.read(buf)) > 0) {
                    zipOutStream.write(buf, 0, len);
                }
//                zipOutStream.write(data);
                zipOutStream.closeEntry();
                in.close();
                zipOutStream.flush();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            IOUtils.closeQuietly(zipOutStream);
        }*/
    }

    @Override
    public SysFileInfo saveOrUpdateFileInfo(SysFileInfo sysFileInfo) {
        SysFileInfo.builder()
                .delFlag("0")
              /*  .agentId(SecurityUtils.getAgentId())
                .updateNickName(SecurityUtils.getNickName())*/
                .updateUserName(SecurityUtils.getUsername())
                .updateTime(DateUtil.date())
                .build();
        if (ObjectUtil.isNotNull(sysFileInfo.getFileId())) {
//            SysFileInfo.setAgentId(SecurityUtils.getAgentId());
            fileInfoMapper.insertSysFileInfo(sysFileInfo);
        } else {
            fileInfoMapper.updateSysFileInfo(sysFileInfo);
        }
        return sysFileInfo;
    }

    @Override
    public Integer deleteById(Long id) {
        SysFileInfo sysFileInfo = SysFileInfo.builder()
                .fileId(id)
                .delFlag("1")
                .updateUserName(SecurityUtils.getUsername())
                .updateTime(DateUtil.date())
                .build();
        return fileInfoMapper.updateSysFileInfo(sysFileInfo);
    }

    @Override
    public SysFileInfo getByFilePath(String filePath) {

        return null;
    }


    @Override
    public String getFilePrefix() {
        return this.fileLocationPrefix;
    }

    @Override
    public InputStream getFileStream(String filePath) {
        return minioUtil.getInputStream(filePath);
    }

    @Override
    public SysFileInfo selectSysFileInfoByFileId(Long fileId) {

        return fileInfoMapper.selectSysFileInfoByFileId(fileId);
    }

    @Override
    public List<SysFileInfo> selectSysFileInfoList(SysFileInfo sysFileInfo) {
        return fileInfoMapper.selectSysFileInfoList(sysFileInfo);
    }

    @Override
    public int insertSysFileInfo(SysFileInfo sysFileInfo) {
        return fileInfoMapper.insertSysFileInfo(sysFileInfo);
    }

    @Override
    public int updateSysFileInfo(SysFileInfo sysFileInfo) {
        return 0;
    }

    @Override
    public int deleteSysFileInfoByFileId(Long fileId) {
        return 0;
    }

    @Override
    public int deleteSysFileInfoByFileIds(Long[] fileIds) {
        return 0;
    }

    @Override
    public void updateFileByAgent(Long id, String type) {
        fileInfoMapper.updateFileByAgent(id, type);
    }
}
