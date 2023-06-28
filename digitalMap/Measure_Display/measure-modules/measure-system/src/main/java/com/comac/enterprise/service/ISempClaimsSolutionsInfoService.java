package com.comac.enterprise.service;

import java.util.List;
import com.comac.enterprise.domain.SempClaimsSolutionsInfo;

/**
 * 企业诉求信息Service接口
 * 
 * @author comac
 * @date 2023-03-29
 */
public interface ISempClaimsSolutionsInfoService 
{
    /**
     * 查询企业诉求信息
     * 
     * @param id 企业诉求信息主键
     * @return 企业诉求信息
     */
    public SempClaimsSolutionsInfo selectSempClaimsSolutionsInfoById(Long id);

    /**
     * 查询企业诉求信息列表
     * 
     * @param sempClaimsSolutionsInfo 企业诉求信息
     * @return 企业诉求信息集合
     */
    public List<SempClaimsSolutionsInfo> selectSempClaimsSolutionsInfoList(SempClaimsSolutionsInfo sempClaimsSolutionsInfo);

    /**
     * 新增企业诉求信息
     * 
     * @param sempClaimsSolutionsInfo 企业诉求信息
     * @return 结果
     */
    public int insertSempClaimsSolutionsInfo(SempClaimsSolutionsInfo sempClaimsSolutionsInfo);

    /**
     * 修改企业诉求信息
     * 
     * @param sempClaimsSolutionsInfo 企业诉求信息
     * @return 结果
     */
    public int updateSempClaimsSolutionsInfo(SempClaimsSolutionsInfo sempClaimsSolutionsInfo);

    /**
     * 批量删除企业诉求信息
     * 
     * @param ids 需要删除的企业诉求信息主键集合
     * @return 结果
     */
    public int deleteSempClaimsSolutionsInfoByIds(Long[] ids);

    /**
     * 删除企业诉求信息信息
     * 
     * @param id 企业诉求信息主键
     * @return 结果
     */
    public int deleteSempClaimsSolutionsInfoById(Long id);

    public int deleteSempClaimsSolutionsInfoByEnterpriseId(Long id);

}
