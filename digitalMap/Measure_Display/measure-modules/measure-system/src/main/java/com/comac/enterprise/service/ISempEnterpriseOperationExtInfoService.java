package com.comac.enterprise.service;

import java.util.List;
import com.comac.enterprise.domain.SempEnterpriseOperationExtInfo;

/**
 * 软件和信息技术服务业运营情况拓展Service接口
 * 
 * @author comac
 * @date 2023-03-29
 */
public interface ISempEnterpriseOperationExtInfoService 
{
    /**
     * 查询软件和信息技术服务业运营情况拓展
     * 
     * @param id 软件和信息技术服务业运营情况拓展主键
     * @return 软件和信息技术服务业运营情况拓展
     */
    public SempEnterpriseOperationExtInfo selectSempEnterpriseOperationExtInfoById(Long id);

    /**
     * 查询软件和信息技术服务业运营情况拓展列表
     * 
     * @param sempEnterpriseOperationExtInfo 软件和信息技术服务业运营情况拓展
     * @return 软件和信息技术服务业运营情况拓展集合
     */
    public List<SempEnterpriseOperationExtInfo> selectSempEnterpriseOperationExtInfoList(SempEnterpriseOperationExtInfo sempEnterpriseOperationExtInfo);

    /**
     * 新增软件和信息技术服务业运营情况拓展
     * 
     * @param sempEnterpriseOperationExtInfo 软件和信息技术服务业运营情况拓展
     * @return 结果
     */
    public int insertSempEnterpriseOperationExtInfo(SempEnterpriseOperationExtInfo sempEnterpriseOperationExtInfo);

    public int batchInsertSempEnterpriseOperationExtInfo(List<SempEnterpriseOperationExtInfo> list);

    /**
     * 修改软件和信息技术服务业运营情况拓展
     * 
     * @param sempEnterpriseOperationExtInfo 软件和信息技术服务业运营情况拓展
     * @return 结果
     */
    public int updateSempEnterpriseOperationExtInfo(SempEnterpriseOperationExtInfo sempEnterpriseOperationExtInfo);

    /**
     * 批量删除软件和信息技术服务业运营情况拓展
     * 
     * @param ids 需要删除的软件和信息技术服务业运营情况拓展主键集合
     * @return 结果
     */
    public int deleteSempEnterpriseOperationExtInfoByIds(Long[] ids);

    /**
     * 删除软件和信息技术服务业运营情况拓展信息
     * 
     * @param id 软件和信息技术服务业运营情况拓展主键
     * @return 结果
     */
    public int deleteSempEnterpriseOperationExtInfoById(Long id);

    public int deleteSempEnterpriseOperationExtInfoByEnterpriseId(Long id);

}
