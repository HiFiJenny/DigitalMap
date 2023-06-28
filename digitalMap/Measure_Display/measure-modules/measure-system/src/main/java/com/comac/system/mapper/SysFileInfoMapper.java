package com.comac.system.mapper;

import java.util.List;
import com.comac.system.domain.SysFileInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 文件记录Mapper接口
 * 
 * @author comac
 * @date 2023-03-31
 */
public interface SysFileInfoMapper 
{
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

    void updateFileByAgent(@Param("agentId") Long id, @Param("agentType") String type);
}
