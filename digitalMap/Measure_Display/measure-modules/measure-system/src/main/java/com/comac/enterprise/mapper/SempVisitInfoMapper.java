package com.comac.enterprise.mapper;

import java.util.List;
import com.comac.enterprise.domain.SempVisitInfo;

/**
 * 企业走访信息Mapper接口
 * 
 * @author comac
 * @date 2023-03-29
 */
public interface SempVisitInfoMapper 
{
    /**
     * 查询企业走访信息
     * 
     * @param id 企业走访信息主键
     * @return 企业走访信息
     */
    public SempVisitInfo selectSempVisitInfoById(Long id);

    /**
     * 查询企业走访信息列表
     * 
     * @param sempVisitInfo 企业走访信息
     * @return 企业走访信息集合
     */
    public List<SempVisitInfo> selectSempVisitInfoList(SempVisitInfo sempVisitInfo);

    /**
     * 新增企业走访信息
     * 
     * @param sempVisitInfo 企业走访信息
     * @return 结果
     */
    public int insertSempVisitInfo(SempVisitInfo sempVisitInfo);

    /**
     * 修改企业走访信息
     * 
     * @param sempVisitInfo 企业走访信息
     * @return 结果
     */
    public int updateSempVisitInfo(SempVisitInfo sempVisitInfo);

    /**
     * 删除企业走访信息
     *
     * @param id 企业走访信息主键
     * @return 结果
     */
    public int deleteSempVisitInfoById(Long id);

    public int deleteSempVisitInfoByEnterpriseId(Long id);

    /**
     * 批量删除企业走访信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSempVisitInfoByIds(Long[] ids);
}
