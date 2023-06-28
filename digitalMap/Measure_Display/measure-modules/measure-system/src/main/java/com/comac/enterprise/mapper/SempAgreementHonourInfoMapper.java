package com.comac.enterprise.mapper;

import java.util.List;
import com.comac.enterprise.domain.SempAgreementHonourInfo;

/**
 * 企业协议兑现信息Mapper接口
 * 
 * @author comac
 * @date 2023-03-29
 */
public interface SempAgreementHonourInfoMapper 
{
    /**
     * 查询企业协议兑现信息
     * 
     * @param id 企业协议兑现信息主键
     * @return 企业协议兑现信息
     */
    public SempAgreementHonourInfo selectSempAgreementHonourInfoById(Long id);

    /**
     * 查询企业协议兑现信息列表
     * 
     * @param sempAgreementHonourInfo 企业协议兑现信息
     * @return 企业协议兑现信息集合
     */
    public List<SempAgreementHonourInfo> selectSempAgreementHonourInfoList(SempAgreementHonourInfo sempAgreementHonourInfo);

    /**
     * 新增企业协议兑现信息
     * 
     * @param sempAgreementHonourInfo 企业协议兑现信息
     * @return 结果
     */
    public int insertSempAgreementHonourInfo(SempAgreementHonourInfo sempAgreementHonourInfo);

    /**
     * 修改企业协议兑现信息
     * 
     * @param sempAgreementHonourInfo 企业协议兑现信息
     * @return 结果
     */
    public int updateSempAgreementHonourInfo(SempAgreementHonourInfo sempAgreementHonourInfo);

    /**
     * 删除企业协议兑现信息
     * 
     * @param id 企业协议兑现信息主键
     * @return 结果
     */
    public int deleteSempAgreementHonourInfoById(Long id);
    public int deleteSempAgreementHonourInfoByEnterpriseId(Long id);

    /**
     * 批量删除企业协议兑现信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSempAgreementHonourInfoByIds(Long[] ids);
}
