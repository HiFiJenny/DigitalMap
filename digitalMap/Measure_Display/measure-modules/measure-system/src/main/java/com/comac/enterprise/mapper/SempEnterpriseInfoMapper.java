package com.comac.enterprise.mapper;

import java.util.List;
import com.comac.enterprise.domain.SempEnterpriseInfo;

/**
 * 企业信息Mapper接口
 * 
 * @author comac
 * @date 2023-03-29
 */
public interface SempEnterpriseInfoMapper 
{
    /**
     * 查询企业信息
     * 
     * @param enterpriseId 企业信息主键
     * @return 企业信息
     */
    public SempEnterpriseInfo selectSempEnterpriseInfoByEnterpriseId(Long enterpriseId);

    /**
     * 查询企业信息列表
     * 
     * @param sempEnterpriseInfo 企业信息
     * @return 企业信息集合
     */
    public List<SempEnterpriseInfo> selectSempEnterpriseInfoList(SempEnterpriseInfo sempEnterpriseInfo);

    /**
     * 新增企业信息
     * 
     * @param sempEnterpriseInfo 企业信息
     * @return 结果
     */
    public int insertSempEnterpriseInfo(SempEnterpriseInfo sempEnterpriseInfo);

    /**
     * 修改企业信息
     * 
     * @param sempEnterpriseInfo 企业信息
     * @return 结果
     */
    public int updateSempEnterpriseInfo(SempEnterpriseInfo sempEnterpriseInfo);

    /**
     * 删除企业信息
     * 
     * @param enterpriseId 企业信息主键
     * @return 结果
     */
    public int deleteSempEnterpriseInfoByEnterpriseId(Long enterpriseId);

    /**
     * 批量删除企业信息
     * 
     * @param enterpriseIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSempEnterpriseInfoByEnterpriseIds(Long[] enterpriseIds);
}
