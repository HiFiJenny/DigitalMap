package com.comac.system.controller;

import cn.hutool.core.util.ZipUtil;
import com.comac.common.core.web.controller.BaseController;
import com.comac.common.core.web.domain.AjaxResult;
import com.comac.common.log.annotation.Log;
import com.comac.common.log.enums.BusinessType;
import com.comac.common.minio.service.MinioUtil;
import com.comac.common.security.annotation.RequiresPermissions;
import com.comac.system.domain.SysFileInfo;
import com.comac.system.mapper.SysFileInfoMapper;
import com.comac.system.service.IFileInfoService;
import io.minio.MinioClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 文件信息请求控制层
 *
 * @author hyy
 * @date 2023-01-16 14:08:14
 */
@Api(tags = "文件信息")
@RestController
@RequestMapping("/file")
public class FileInfoController extends BaseController {

    @Resource
    private IFileInfoService fileInfoService;
    @Resource
    private MinioUtil minioUtil;
    @Resource
    private SysFileInfoMapper fileInfoMapper;

    @Log(title = "上传单个文件", businessType = BusinessType.INSERT)
    @ApiOperation(value = "上传单个文件")
    @RequiresPermissions("personal:todo:upload")
    @PostMapping(value = "/upload")
    public AjaxResult uploadFile(@RequestParam("file") MultipartFile file) {
        return AjaxResult.success(fileInfoService.uploadFile(file));
    }


    @Log(title = "上传多个文件", businessType = BusinessType.INSERT)
    @ApiOperation(value = "上传多个文件")
    @PostMapping(value = "/upload/multiple")
    @RequiresPermissions("resource:release:upload")
    public AjaxResult uploadFile(@RequestParam("fileArr") MultipartFile[] fileArr) {
        return AjaxResult.success(fileInfoService.uploadFileMultiple(fileArr));
    }

    @Log(title = "上传单个文件指定桶名", businessType = BusinessType.INSERT)
    @ApiOperation(value = "上传文件单个文件指定桶名")
    @RequiresPermissions("personal:todo:upload")
    @PostMapping(value = "/upload/bucketName")
    public AjaxResult uploadFile(@RequestParam("bucketName") String bucketName, @RequestParam("file") MultipartFile file) {
        return AjaxResult.success(fileInfoService.uploadFileWithBucketName(bucketName, file));
    }

    @Log(title = "将文件写入数据表", businessType = BusinessType.INSERT)
    @ApiOperation(value = "将文件写入数据表")
    @PostMapping(value = "/upload/insertFile")
    public AjaxResult insertFileInfo(@RequestParam("bucketName") String bucketName, @RequestParam("fileName") String fileName,
                                     @RequestParam("filePath") String filePath, @RequestParam(name = "size", required = false) Long size) {
        return AjaxResult.success(fileInfoService.insertFileInfo(bucketName, fileName, filePath, size));
    }

    @Log(title = "上传多个个文件指定桶名", businessType = BusinessType.INSERT)
    @ApiOperation(value = "上传多个个文件指定桶名")
    @RequiresPermissions("personal:todo:upload")
    @PostMapping(value = "/upload/bucketName/multiple")
    public AjaxResult uploadFileMultiple(@RequestParam("bucketName") String bucketName, @RequestParam("fileArr") MultipartFile[] fileArr) {
        return AjaxResult.success(fileInfoService.uploadFileMultipleWithBucketName(bucketName, fileArr));
    }

    @Log(title = "下载文件", businessType = BusinessType.INSERT)
    @ApiOperation(value = "下载文件")
    @GetMapping(value = "/download")
    public void downloadFile(HttpServletResponse response, SysFileInfo SysFileInfo) {
        fileInfoService.downloadFile(response, SysFileInfo);
    }

    @Log(title = "预览文件", businessType = BusinessType.INSERT)
    @ApiOperation(value = "预览文件")
    @GetMapping(value = "/preview-download")
    public void previewDownloadFile(HttpServletResponse response, SysFileInfo SysFileInfo) throws Exception {
        fileInfoService.previewDownloadFile(response, SysFileInfo);
    }


    @ApiOperation("导出多个文件zip")
    @RequestMapping(path = "/exportMore", method = RequestMethod.GET)
    @RequiresPermissions("reference:download")
    public void downloadFileZip(HttpServletResponse response, @RequestParam(value = "fileId", required = false) List<Long> fileId) throws Exception, IOException {
        List<String> paths = new ArrayList<>();
        for (int i = 0; i < fileId.size(); i++) {
            Long id = fileId.get(i);
            SysFileInfo SysFileInfo = fileInfoMapper.selectSysFileInfoByFileId(id);
            paths.add(SysFileInfo.getFilePath());
        }
        //被压缩文件InputStream
        InputStream[] srcFiles = new InputStream[paths.size()];
        //被压缩文件名称
        String[] srcFileNames = new String[paths.size()];
        for (int i = 0; i < paths.size(); i++) {
            String fileUrl = paths.get(i);
            InputStream inputStream = minioUtil.getInputStream(fileUrl);
            if (inputStream == null) {
                continue;
            }
            srcFiles[i] = inputStream;
            String[] splitFileUrl = fileUrl.split("/");
            srcFileNames[i] = splitFileUrl[splitFileUrl.length - 1];
        }
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("softwarePackageInfo.zip", "UTF-8"));
        //多个文件压缩成压缩包返回
        ZipUtil.zip(response.getOutputStream(), srcFileNames, srcFiles);
    }


    @Log(title = "新增或修改文件信息", businessType = BusinessType.INSERT)
    @ApiOperation(value = "新增或修改文件信息")
    @PostMapping(value = "")
    public AjaxResult saveOrUpdate(@RequestBody SysFileInfo body) {
        return AjaxResult.success(fileInfoService.saveOrUpdateFileInfo(body));
    }

    @ApiOperation(value = "根据主键查询文件信息")
    @GetMapping("/get/fileId")
    public AjaxResult getById(@RequestParam("fileId") Long fileId) {
        return AjaxResult.success(fileInfoService.selectSysFileInfoByFileId(fileId));
    }

    @ApiOperation(value = "根据路径查询文件信息")
    @GetMapping("/get/path")
    public AjaxResult getByPath(@RequestParam("filePath") String filePath) {
        return AjaxResult.success(fileInfoService.getByFilePath(filePath));
    }

    @ApiOperation(value = "查询文件信息")
    @GetMapping("/get")
    public AjaxResult get(@RequestBody SysFileInfo SysFileInfo) {
        return AjaxResult.success();
    }

    @Log(title = "删除文件信息", businessType = BusinessType.DELETE)
    @ApiOperation(value = "删除文件信息")
    @PostMapping("/delete")
    public AjaxResult delete(@RequestParam("fileId") Long fileId) throws Exception {
        return AjaxResult.success(fileInfoService.deleteById(fileId));
    }

    @ApiOperation(value = "获取mino文件地址通用前缀")
    @GetMapping("/prefix")
    public AjaxResult getFilePrefix() {
        return AjaxResult.success(fileInfoService.getFilePrefix());
    }

    @ApiOperation(value = "获取文件流")
    @GetMapping(value = "/getFileStream")
    public InputStream getFileStream(@RequestParam("filePath") String filePath) {
        return fileInfoService.getFileStream(filePath);
    }
}
