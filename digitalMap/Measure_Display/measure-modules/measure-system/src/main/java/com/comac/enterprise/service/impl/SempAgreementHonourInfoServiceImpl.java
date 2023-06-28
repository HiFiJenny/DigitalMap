package com.comac.enterprise.service.impl;

import java.util.List;
import com.comac.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.comac.enterprise.mapper.SempAgreementHonourInfoMapper;
import com.comac.enterprise.domain.SempAgreementHonourInfo;
import com.comac.enterprise.service.ISempAgreementHonourInfoService;

/**
 * 企业协议兑现信息Service业务层处理
 * 
 * @author comac
 * @date 2023-03-29
 */
@Service
public class SempAgreementHonourInfoServiceImpl implements ISempAgreementHonourInfoService 
{
    @Autowired
    private SempAgreementHonourInfoMapper sempAgreementHonourInfoMapper;

    /**
     * 查询企业协议兑现信息
     * 
     * @param id 企业协议兑现信息主键
     * @return 企业协议兑现信息
     */
    @Override
    public SempAgreementHonourInfo selectSempAgreementHonourInfoById(Long id)
    {
        return sempAgreementHonourInfoMapper.selectSempAgreementHonourInfoById(id);
    }

    /**
     * 查询企业协议兑现信息列表
     * 
     * @param sempAgreementHonourInfo 企业协议兑现信息
     * @return 企业协议兑现信息
     */
    @Override
    public List<SempAgreementHonourInfo> selectSempAgreementHonourInfoList(SempAgreementHonourInfo sempAgreementHonourInfo)
    {
        return sempAgreementHonourInfoMapper.selectSempAgreementHonourInfoList(sempAgreementHonourInfo);
    }

    /**
     * 新增企业协议兑现信息
     * 
     * @param sempAgreementHonourInfo 企业协议兑现信息
     * @return 结果
     */
    @Override
    public int insertSempAgreementHonourInfo(SempAgreementHonourInfo sempAgreementHonourInfo)
    {
        sempAgreementHonourInfo.setCreateTime(DateUtils.getNowDate());
        return sempAgreementHonourInfoMapper.insertSempAgreementHonourInfo(sempAgreementHonourInfo);
    }

    /**
     * 修改企业协议兑现信息
     * 
     * @param sempAgreementHonourInfo 企业协议兑现信息
     * @return 结果
     */
    @Override
    public int updateSempAgreementHonourInfo(SempAgreementHonourInfo sempAgreementHonourInfo)
    {
        sempAgreementHonourInfo.setUpdateTime(DateUtils.getNowDate());
        return sempAgreementHonourInfoMapper.updateSempAgreementHonourInfo(sempAgreementHonourInfo);
    }

    /**
     * 批量删除企业协议兑现信息
     * 
     * @param ids 需要删除的企业协议兑现信息主键
     * @return 结果
     */
    @Override
    public int deleteSempAgreementHonourInfoByIds(Long[] ids)
    {
        return sempAgreementHonourInfoMapper.deleteSempAgreementHonourInfoByIds(ids);
    }

    /**
     * 删除企业协议兑现信息信息
     * 
     * @param id 企业协议兑现信息主键
     * @return 结果
     */
    @Override
    public int deleteSempAgreementHonourInfoById(Long id)
    {
        return sempAgreementHonourInfoMapper.deleteSempAgreementHonourInfoById(id);
    }

    @Override
    public int deleteSempAgreementHonourInfoByEnterpriseId(Long id) {
        return sempAgreementHonourInfoMapper.deleteSempAgreementHonourInfoByEnterpriseId(id);
    }
}
