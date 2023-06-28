package com.comac.enterprise.service.impl;

import java.util.List;
import com.comac.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.comac.enterprise.mapper.SempClaimsSolutionsInfoMapper;
import com.comac.enterprise.domain.SempClaimsSolutionsInfo;
import com.comac.enterprise.service.ISempClaimsSolutionsInfoService;

/**
 * 企业诉求信息Service业务层处理
 * 
 * @author comac
 * @date 2023-03-29
 */
@Service
public class SempClaimsSolutionsInfoServiceImpl implements ISempClaimsSolutionsInfoService 
{
    @Autowired
    private SempClaimsSolutionsInfoMapper sempClaimsSolutionsInfoMapper;

    /**
     * 查询企业诉求信息
     * 
     * @param id 企业诉求信息主键
     * @return 企业诉求信息
     */
    @Override
    public SempClaimsSolutionsInfo selectSempClaimsSolutionsInfoById(Long id)
    {
        return sempClaimsSolutionsInfoMapper.selectSempClaimsSolutionsInfoById(id);
    }

    /**
     * 查询企业诉求信息列表
     * 
     * @param sempClaimsSolutionsInfo 企业诉求信息
     * @return 企业诉求信息
     */
    @Override
    public List<SempClaimsSolutionsInfo> selectSempClaimsSolutionsInfoList(SempClaimsSolutionsInfo sempClaimsSolutionsInfo)
    {
        return sempClaimsSolutionsInfoMapper.selectSempClaimsSolutionsInfoList(sempClaimsSolutionsInfo);
    }

    /**
     * 新增企业诉求信息
     * 
     * @param sempClaimsSolutionsInfo 企业诉求信息
     * @return 结果
     */
    @Override
    public int insertSempClaimsSolutionsInfo(SempClaimsSolutionsInfo sempClaimsSolutionsInfo)
    {
        sempClaimsSolutionsInfo.setCreateTime(DateUtils.getNowDate());
        return sempClaimsSolutionsInfoMapper.insertSempClaimsSolutionsInfo(sempClaimsSolutionsInfo);
    }

    /**
     * 修改企业诉求信息
     * 
     * @param sempClaimsSolutionsInfo 企业诉求信息
     * @return 结果
     */
    @Override
    public int updateSempClaimsSolutionsInfo(SempClaimsSolutionsInfo sempClaimsSolutionsInfo)
    {
        sempClaimsSolutionsInfo.setUpdateTime(DateUtils.getNowDate());
        return sempClaimsSolutionsInfoMapper.updateSempClaimsSolutionsInfo(sempClaimsSolutionsInfo);
    }

    /**
     * 批量删除企业诉求信息
     * 
     * @param ids 需要删除的企业诉求信息主键
     * @return 结果
     */
    @Override
    public int deleteSempClaimsSolutionsInfoByIds(Long[] ids)
    {
        return sempClaimsSolutionsInfoMapper.deleteSempClaimsSolutionsInfoByIds(ids);
    }

    /**
     * 删除企业诉求信息信息
     * 
     * @param id 企业诉求信息主键
     * @return 结果
     */
    @Override
    public int deleteSempClaimsSolutionsInfoById(Long id)
    {
        return sempClaimsSolutionsInfoMapper.deleteSempClaimsSolutionsInfoById(id);
    }

    @Override
    public int deleteSempClaimsSolutionsInfoByEnterpriseId(Long id) {
        return sempClaimsSolutionsInfoMapper.deleteSempClaimsSolutionsInfoByEnterpriseId(id);
    }

}
