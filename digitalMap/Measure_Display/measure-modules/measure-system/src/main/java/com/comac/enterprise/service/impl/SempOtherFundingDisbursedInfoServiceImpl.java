package com.comac.enterprise.service.impl;

import java.util.List;
import com.comac.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.comac.enterprise.mapper.SempOtherFundingDisbursedInfoMapper;
import com.comac.enterprise.domain.SempOtherFundingDisbursedInfo;
import com.comac.enterprise.service.ISempOtherFundingDisbursedInfoService;

/**
 * 企业其他资金拨付信息Service业务层处理
 * 
 * @author comac
 * @date 2023-03-29
 */
@Service
public class SempOtherFundingDisbursedInfoServiceImpl implements ISempOtherFundingDisbursedInfoService 
{
    @Autowired
    private SempOtherFundingDisbursedInfoMapper sempOtherFundingDisbursedInfoMapper;

    /**
     * 查询企业其他资金拨付信息
     * 
     * @param id 企业其他资金拨付信息主键
     * @return 企业其他资金拨付信息
     */
    @Override
    public SempOtherFundingDisbursedInfo selectSempOtherFundingDisbursedInfoById(Long id)
    {
        return sempOtherFundingDisbursedInfoMapper.selectSempOtherFundingDisbursedInfoById(id);
    }

    /**
     * 查询企业其他资金拨付信息列表
     * 
     * @param sempOtherFundingDisbursedInfo 企业其他资金拨付信息
     * @return 企业其他资金拨付信息
     */
    @Override
    public List<SempOtherFundingDisbursedInfo> selectSempOtherFundingDisbursedInfoList(SempOtherFundingDisbursedInfo sempOtherFundingDisbursedInfo)
    {
        return sempOtherFundingDisbursedInfoMapper.selectSempOtherFundingDisbursedInfoList(sempOtherFundingDisbursedInfo);
    }

    /**
     * 新增企业其他资金拨付信息
     * 
     * @param sempOtherFundingDisbursedInfo 企业其他资金拨付信息
     * @return 结果
     */
    @Override
    public int insertSempOtherFundingDisbursedInfo(SempOtherFundingDisbursedInfo sempOtherFundingDisbursedInfo)
    {
        sempOtherFundingDisbursedInfo.setCreateTime(DateUtils.getNowDate());
        return sempOtherFundingDisbursedInfoMapper.insertSempOtherFundingDisbursedInfo(sempOtherFundingDisbursedInfo);
    }

    /**
     * 修改企业其他资金拨付信息
     * 
     * @param sempOtherFundingDisbursedInfo 企业其他资金拨付信息
     * @return 结果
     */
    @Override
    public int updateSempOtherFundingDisbursedInfo(SempOtherFundingDisbursedInfo sempOtherFundingDisbursedInfo)
    {
        sempOtherFundingDisbursedInfo.setUpdateTime(DateUtils.getNowDate());
        return sempOtherFundingDisbursedInfoMapper.updateSempOtherFundingDisbursedInfo(sempOtherFundingDisbursedInfo);
    }

    /**
     * 批量删除企业其他资金拨付信息
     * 
     * @param ids 需要删除的企业其他资金拨付信息主键
     * @return 结果
     */
    @Override
    public int deleteSempOtherFundingDisbursedInfoByIds(Long[] ids)
    {
        return sempOtherFundingDisbursedInfoMapper.deleteSempOtherFundingDisbursedInfoByIds(ids);
    }

    /**
     * 删除企业其他资金拨付信息信息
     * 
     * @param id 企业其他资金拨付信息主键
     * @return 结果
     */
    @Override
    public int deleteSempOtherFundingDisbursedInfoById(Long id)
    {
        return sempOtherFundingDisbursedInfoMapper.deleteSempOtherFundingDisbursedInfoById(id);
    }

    @Override
    public int deleteSempOtherFundingDisbursedInfoByEnterpriseId(Long id) {
        return sempOtherFundingDisbursedInfoMapper.deleteSempOtherFundingDisbursedInfoByEnterpriseId(id);
    }
}
