package com.comac.enterprise.mapper;

import java.util.List;
import com.comac.enterprise.domain.SempOtherFundingDisbursedInfo;

/**
 * 企业其他资金拨付信息Mapper接口
 * 
 * @author comac
 * @date 2023-03-29
 */
public interface SempOtherFundingDisbursedInfoMapper 
{
    /**
     * 查询企业其他资金拨付信息
     * 
     * @param id 企业其他资金拨付信息主键
     * @return 企业其他资金拨付信息
     */
    public SempOtherFundingDisbursedInfo selectSempOtherFundingDisbursedInfoById(Long id);

    /**
     * 查询企业其他资金拨付信息列表
     * 
     * @param sempOtherFundingDisbursedInfo 企业其他资金拨付信息
     * @return 企业其他资金拨付信息集合
     */
    public List<SempOtherFundingDisbursedInfo> selectSempOtherFundingDisbursedInfoList(SempOtherFundingDisbursedInfo sempOtherFundingDisbursedInfo);

    /**
     * 新增企业其他资金拨付信息
     * 
     * @param sempOtherFundingDisbursedInfo 企业其他资金拨付信息
     * @return 结果
     */
    public int insertSempOtherFundingDisbursedInfo(SempOtherFundingDisbursedInfo sempOtherFundingDisbursedInfo);

    /**
     * 修改企业其他资金拨付信息
     * 
     * @param sempOtherFundingDisbursedInfo 企业其他资金拨付信息
     * @return 结果
     */
    public int updateSempOtherFundingDisbursedInfo(SempOtherFundingDisbursedInfo sempOtherFundingDisbursedInfo);

    /**
     * 删除企业其他资金拨付信息
     * 
     * @param id 企业其他资金拨付信息主键
     * @return 结果
     */
    public int deleteSempOtherFundingDisbursedInfoById(Long id);

    public int deleteSempOtherFundingDisbursedInfoByEnterpriseId(Long id);

    /**
     * 批量删除企业其他资金拨付信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSempOtherFundingDisbursedInfoByIds(Long[] ids);
}
