package com.comac.enterprise.mapper;

import java.util.List;
import com.comac.enterprise.domain.SempEnterpriseOperationExtInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 软件和信息技术服务业运营情况拓展Mapper接口
 * 
 * @author comac
 * @date 2023-03-29
 */
public interface SempEnterpriseOperationExtInfoMapper 
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
    public int batchInsertSempEnterpriseOperationExtInfo(@Param("list") List<SempEnterpriseOperationExtInfo> list);

    /**
     * 修改软件和信息技术服务业运营情况拓展
     * 
     * @param sempEnterpriseOperationExtInfo 软件和信息技术服务业运营情况拓展
     * @return 结果
     */
    public int updateSempEnterpriseOperationExtInfo(SempEnterpriseOperationExtInfo sempEnterpriseOperationExtInfo);

    /**
     * 删除软件和信息技术服务业运营情况拓展
     * 
     * @param id 软件和信息技术服务业运营情况拓展主键
     * @return 结果
     */
    public int deleteSempEnterpriseOperationExtInfoById(Long id);

    public int deleteSempEnterpriseOperationExtInfoByEnterpriseId(Long id);

    /**
     * 批量删除软件和信息技术服务业运营情况拓展
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSempEnterpriseOperationExtInfoByIds(Long[] ids);
}
