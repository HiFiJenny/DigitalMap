package com.comac.system.service;

import com.comac.system.domain.SysFileInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

/**
 * 文件对象服务层接口
 *
 * @author hyy
 * @since 2023-01-16 14:08:14
 */
public interface IFileInfoService {

    /**
     * 上传文件
     *
     * @param file 文件信息列表
     * @return 文件信息
     */
    SysFileInfo uploadFile(MultipartFile file);

    /**
     * 上传文件
     *
     * @param fileArr 文件信息列表
     * @return 文件信息
     */
    List<SysFileInfo> uploadFileMultiple(MultipartFile[] fileArr);

    /**
     * 上传文件
     *
     * @param bucketName 桶名
     * @return 文件信息
     */
    SysFileInfo uploadFileWithBucketName(String bucketName, MultipartFile file);

    /**
     * 将文件信息写入表
     *
     * @return
     */
    SysFileInfo insertFileInfo(String bucketName, String originalFileName, String filePath, Long size);

    /**
     * 上传文件
     *
     * @param bucketName 桶名
     * @return 文件信息
     */
    List<SysFileInfo> uploadFileMultipleWithBucketName(String bucketName, MultipartFile[] fileArr);

    /**
     * 下载文件
     *
     * @param SysFileInfo 文件信息
     * @return 文件信息
     */
    void downloadFile(HttpServletResponse response, SysFileInfo SysFileInfo);

    void previewDownloadFile(HttpServletResponse response, SysFileInfo SysFileInfo) throws Exception;

    /**
     * 保存或更新文件信息
     *
     * @param SysFileInfo 文件信息
     * @return 文件信息
     */
    SysFileInfo saveOrUpdateFileInfo(SysFileInfo SysFileInfo);

    /**
     * 删除文件信息
     *
     * @param id 文件ID
     * @return 文件信息
     */
    Integer deleteById(Long id);

    /**
     * 根据路径查询文件信息
     *
     * @param filePath 文件路径
     * @return 文件信息
     */
    SysFileInfo getByFilePath(String filePath);

    /**
     * 多个文件打包压缩下载方法
     *
     * @param outputStream
     */
    void compressZip(List<String> paths, HttpServletResponse outputStream);


    /**
     * 获取文件地址通用前缀
     *
     * @return 前缀地址(nacos配置获取)
     */
    String getFilePrefix();

    /**
     * 获取文件流
     *
     * @param filePath
     * @return java.io.InputStream
     **/
    InputStream getFileStream(String filePath);


    /**
     * 查询文件记录
     *
     * @param fileId 文件记录主键
     * @return 文件记录
     */
    public SysFileInfo selectSysFileInfoByFileId(Long fileId);

    /**
     * 查询文件记录列表
     *
     * @param sysFileInfo 文件记录
     * @return 文件记录集合
     */
    public List<SysFileInfo> selectSysFileInfoList(SysFileInfo sysFileInfo);

    /**
     * 新增文件记录
     *
     * @param sysFileInfo 文件记录
     * @return 结果
     */
    public int insertSysFileInfo(SysFileInfo sysFileInfo);

    /**
     * 修改文件记录
     *
     * @param sysFileInfo 文件记录
     * @return 结果
     */
    public int updateSysFileInfo(SysFileInfo sysFileInfo);

    /**
     * 删除文件记录
     *
     * @param fileId 文件记录主键
     * @return 结果
     */
    public int deleteSysFileInfoByFileId(Long fileId);

    /**
     * 批量删除文件记录
     *
     * @param fileIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysFileInfoByFileIds(Long[] fileIds);

    void updateFileByAgent(Long id, String type);

}
