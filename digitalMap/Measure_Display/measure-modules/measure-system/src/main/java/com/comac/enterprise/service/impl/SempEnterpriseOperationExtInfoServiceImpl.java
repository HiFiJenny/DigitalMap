package com.comac.enterprise.service.impl;

import java.util.List;
import com.comac.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.comac.enterprise.mapper.SempEnterpriseOperationExtInfoMapper;
import com.comac.enterprise.domain.SempEnterpriseOperationExtInfo;
import com.comac.enterprise.service.ISempEnterpriseOperationExtInfoService;

/**
 * 软件和信息技术服务业运营情况拓展Service业务层处理
 * 
 * @author comac
 * @date 2023-03-29
 */
@Service
public class SempEnterpriseOperationExtInfoServiceImpl implements ISempEnterpriseOperationExtInfoService 
{
    @Autowired
    private SempEnterpriseOperationExtInfoMapper sempEnterpriseOperationExtInfoMapper;

    /**
     * 查询软件和信息技术服务业运营情况拓展
     * 
     * @param id 软件和信息技术服务业运营情况拓展主键
     * @return 软件和信息技术服务业运营情况拓展
     */
    @Override
    public SempEnterpriseOperationExtInfo selectSempEnterpriseOperationExtInfoById(Long id)
    {
        return sempEnterpriseOperationExtInfoMapper.selectSempEnterpriseOperationExtInfoById(id);
    }

    /**
     * 查询软件和信息技术服务业运营情况拓展列表
     * 
     * @param sempEnterpriseOperationExtInfo 软件和信息技术服务业运营情况拓展
     * @return 软件和信息技术服务业运营情况拓展
     */
    @Override
    public List<SempEnterpriseOperationExtInfo> selectSempEnterpriseOperationExtInfoList(SempEnterpriseOperationExtInfo sempEnterpriseOperationExtInfo)
    {
        return sempEnterpriseOperationExtInfoMapper.selectSempEnterpriseOperationExtInfoList(sempEnterpriseOperationExtInfo);
    }

    /**
     * 新增软件和信息技术服务业运营情况拓展
     * 
     * @param sempEnterpriseOperationExtInfo 软件和信息技术服务业运营情况拓展
     * @return 结果
     */
    @Override
    public int insertSempEnterpriseOperationExtInfo(SempEnterpriseOperationExtInfo sempEnterpriseOperationExtInfo)
    {
        sempEnterpriseOperationExtInfo.setCreateTime(DateUtils.getNowDate());
        return sempEnterpriseOperationExtInfoMapper.insertSempEnterpriseOperationExtInfo(sempEnterpriseOperationExtInfo);
    }

    @Override
    public int batchInsertSempEnterpriseOperationExtInfo(List<SempEnterpriseOperationExtInfo> list) {
        return sempEnterpriseOperationExtInfoMapper.batchInsertSempEnterpriseOperationExtInfo(list);
    }

    /**
     * 修改软件和信息技术服务业运营情况拓展
     * 
     * @param sempEnterpriseOperationExtInfo 软件和信息技术服务业运营情况拓展
     * @return 结果
     */
    @Override
    public int updateSempEnterpriseOperationExtInfo(SempEnterpriseOperationExtInfo sempEnterpriseOperationExtInfo)
    {
        sempEnterpriseOperationExtInfo.setUpdateTime(DateUtils.getNowDate());
        return sempEnterpriseOperationExtInfoMapper.updateSempEnterpriseOperationExtInfo(sempEnterpriseOperationExtInfo);
    }

    /**
     * 批量删除软件和信息技术服务业运营情况拓展
     * 
     * @param ids 需要删除的软件和信息技术服务业运营情况拓展主键
     * @return 结果
     */
    @Override
    public int deleteSempEnterpriseOperationExtInfoByIds(Long[] ids)
    {
        return sempEnterpriseOperationExtInfoMapper.deleteSempEnterpriseOperationExtInfoByIds(ids);
    }

    /**
     * 删除软件和信息技术服务业运营情况拓展信息
     * 
     * @param id 软件和信息技术服务业运营情况拓展主键
     * @return 结果
     */
    @Override
    public int deleteSempEnterpriseOperationExtInfoById(Long id)
    {
        return sempEnterpriseOperationExtInfoMapper.deleteSempEnterpriseOperationExtInfoById(id);
    }

    @Override
    public int deleteSempEnterpriseOperationExtInfoByEnterpriseId(Long id) {
        return sempEnterpriseOperationExtInfoMapper.deleteSempEnterpriseOperationExtInfoByEnterpriseId(id);
    }
}
