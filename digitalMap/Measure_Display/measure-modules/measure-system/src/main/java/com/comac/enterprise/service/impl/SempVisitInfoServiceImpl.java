package com.comac.enterprise.service.impl;

import java.util.List;

import com.comac.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.comac.enterprise.mapper.SempVisitInfoMapper;
import com.comac.enterprise.domain.SempVisitInfo;
import com.comac.enterprise.service.ISempVisitInfoService;

/**
 * 企业走访信息Service业务层处理
 *
 * @author comac
 * @date 2023-03-29
 */
@Service
public class SempVisitInfoServiceImpl implements ISempVisitInfoService {
    @Autowired
    private SempVisitInfoMapper sempVisitInfoMapper;

    /**
     * 查询企业走访信息
     *
     * @param id 企业走访信息主键
     * @return 企业走访信息
     */
    @Override
    public SempVisitInfo selectSempVisitInfoById(Long id) {
        return sempVisitInfoMapper.selectSempVisitInfoById(id);
    }

    /**
     * 查询企业走访信息列表
     *
     * @param sempVisitInfo 企业走访信息
     * @return 企业走访信息
     */
    @Override
    public List<SempVisitInfo> selectSempVisitInfoList(SempVisitInfo sempVisitInfo) {
        return sempVisitInfoMapper.selectSempVisitInfoList(sempVisitInfo);
    }

    /**
     * 新增企业走访信息
     *
     * @param sempVisitInfo 企业走访信息
     * @return 结果
     */
    @Override
    public int insertSempVisitInfo(SempVisitInfo sempVisitInfo) {
        sempVisitInfo.setCreateTime(DateUtils.getNowDate());
        return sempVisitInfoMapper.insertSempVisitInfo(sempVisitInfo);
    }

    /**
     * 修改企业走访信息
     *
     * @param sempVisitInfo 企业走访信息
     * @return 结果
     */
    @Override
    public int updateSempVisitInfo(SempVisitInfo sempVisitInfo) {
        sempVisitInfo.setUpdateTime(DateUtils.getNowDate());
        return sempVisitInfoMapper.updateSempVisitInfo(sempVisitInfo);
    }

    /**
     * 批量删除企业走访信息
     *
     * @param ids 需要删除的企业走访信息主键
     * @return 结果
     */
    @Override
    public int deleteSempVisitInfoByIds(Long[] ids) {
        return sempVisitInfoMapper.deleteSempVisitInfoByIds(ids);
    }

    /**
     * 删除企业走访信息信息
     *
     * @param id 企业走访信息主键
     * @return 结果
     */
    @Override
    public int deleteSempVisitInfoById(Long id) {
        return sempVisitInfoMapper.deleteSempVisitInfoById(id);
    }

    @Override
    public int deleteSempVisitInfoByEnterpriseId(Long id) {
        return sempVisitInfoMapper.deleteSempVisitInfoByEnterpriseId(id);
    }
}
