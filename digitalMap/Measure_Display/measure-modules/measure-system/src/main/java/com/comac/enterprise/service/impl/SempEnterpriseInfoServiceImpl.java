package com.comac.enterprise.service.impl;

import java.util.List;

import com.comac.common.core.utils.DateUtils;
import com.comac.enterprise.domain.*;
import com.comac.enterprise.service.*;
import com.comac.system.domain.SysFileInfo;
import com.comac.system.service.IFileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.comac.enterprise.mapper.SempEnterpriseInfoMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * 企业信息Service业务层处理
 *
 * @author comac
 * @date 2023-03-29
 */
@Service
public class SempEnterpriseInfoServiceImpl implements ISempEnterpriseInfoService {
    @Autowired
    private SempEnterpriseInfoMapper sempEnterpriseInfoMapper;

    @Autowired
    private ISempEnterpriseOperationExtInfoService iSempEnterpriseOperationExtInfoService;

    @Autowired
    private ISempOtherFundingDisbursedInfoService iSempOtherFundingDisbursedInfoService;

    @Autowired
    private ISempAgreementHonourInfoService iSempAgreementHonourInfoService;

    @Autowired
    private ISempVisitInfoService iSempVisitInfoService;

    @Autowired
    private ISempClaimsSolutionsInfoService iSempClaimsSolutionsInfoService;

    @Autowired
    private IFileInfoService iFileInfoService;

    /**
     * 查询企业信息
     *
     * @param enterpriseId 企业信息主键
     * @return 企业信息
     */
    @Override
    public SempEnterpriseInfo selectSempEnterpriseInfoByEnterpriseId(Long enterpriseId) {
        SempEnterpriseInfo sempEnterpriseInfo = sempEnterpriseInfoMapper.selectSempEnterpriseInfoByEnterpriseId(enterpriseId);

        SempEnterpriseOperationExtInfo sempEnterpriseOperationExtInfo = new SempEnterpriseOperationExtInfo();
        sempEnterpriseOperationExtInfo.setEnterpriseId(enterpriseId);
        List<SempEnterpriseOperationExtInfo> sempEnterpriseOperationExtInfos = iSempEnterpriseOperationExtInfoService.selectSempEnterpriseOperationExtInfoList(sempEnterpriseOperationExtInfo);
        sempEnterpriseInfo.setSempEnterpriseOperationExtInfos(sempEnterpriseOperationExtInfos);

        SempOtherFundingDisbursedInfo sempOtherFundingDisbursedInfo = new SempOtherFundingDisbursedInfo();
        sempOtherFundingDisbursedInfo.setEnterpriseId(enterpriseId);
        List<SempOtherFundingDisbursedInfo> sempOtherFundingDisbursedInfos = iSempOtherFundingDisbursedInfoService.selectSempOtherFundingDisbursedInfoList(sempOtherFundingDisbursedInfo);
        sempEnterpriseInfo.setSempOtherFundingDisbursedInfos(sempOtherFundingDisbursedInfos);

        SempAgreementHonourInfo sempAgreementHonourInfo = new SempAgreementHonourInfo();
        sempAgreementHonourInfo.setEnterpriseId(enterpriseId);
        List<SempAgreementHonourInfo> sempAgreementHonourInfos = iSempAgreementHonourInfoService.selectSempAgreementHonourInfoList(sempAgreementHonourInfo);
        sempEnterpriseInfo.setSempAgreementHonourInfos(sempAgreementHonourInfos);

        SempVisitInfo sempVisitInfo = new SempVisitInfo();
        sempVisitInfo.setEnterpriseId(enterpriseId);
        List<SempVisitInfo> sempVisitInfos = iSempVisitInfoService.selectSempVisitInfoList(sempVisitInfo);
        sempEnterpriseInfo.setSempVisitInfos(sempVisitInfos);
        if (!CollectionUtils.isEmpty(sempVisitInfos)) {
            for (SempVisitInfo visitInfo : sempVisitInfos) {
                SysFileInfo sysFileInfo = new SysFileInfo();
                sysFileInfo.setAgentId(visitInfo.getId());
                sysFileInfo.setAgentType("visit");
                List<SysFileInfo> sysFileInfos = iFileInfoService.selectSysFileInfoList(sysFileInfo);
                visitInfo.setFileList(sysFileInfos);
            }
        }

        SempClaimsSolutionsInfo sempClaimsSolutionsInfo = new SempClaimsSolutionsInfo();
        sempClaimsSolutionsInfo.setEnterpriseId(enterpriseId);
        List<SempClaimsSolutionsInfo> sempClaimsSolutionsInfos = iSempClaimsSolutionsInfoService.selectSempClaimsSolutionsInfoList(sempClaimsSolutionsInfo);
        sempEnterpriseInfo.setSempClaimsSolutionsInfos(sempClaimsSolutionsInfos);

        return sempEnterpriseInfo;
    }

    /**
     * 查询企业信息列表
     *
     * @param sempEnterpriseInfo 企业信息
     * @return 企业信息
     */
    @Override
    public List<SempEnterpriseInfo> selectSempEnterpriseInfoList(SempEnterpriseInfo sempEnterpriseInfo) {
        return sempEnterpriseInfoMapper.selectSempEnterpriseInfoList(sempEnterpriseInfo);
    }

    /**
     * 新增企业信息
     *
     * @param sempEnterpriseInfo 企业信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertSempEnterpriseInfo(SempEnterpriseInfo sempEnterpriseInfo) {
        int result = sempEnterpriseInfoMapper.insertSempEnterpriseInfo(sempEnterpriseInfo);
        Long enterpriseId = sempEnterpriseInfo.getEnterpriseId();
        //先删除在新增
        //TODO  批量修改效率后面改 目前先做demo
        iSempEnterpriseOperationExtInfoService.deleteSempEnterpriseOperationExtInfoByEnterpriseId(enterpriseId);
        if (!CollectionUtils.isEmpty(sempEnterpriseInfo.getSempEnterpriseOperationExtInfos())) {
            for (SempEnterpriseOperationExtInfo sempEnterpriseOperationExtInfo : sempEnterpriseInfo.getSempEnterpriseOperationExtInfos()) {
                sempEnterpriseOperationExtInfo.setEnterpriseId(enterpriseId);
                iSempEnterpriseOperationExtInfoService.insertSempEnterpriseOperationExtInfo(sempEnterpriseOperationExtInfo);

            }
        }

        iSempOtherFundingDisbursedInfoService.deleteSempOtherFundingDisbursedInfoByEnterpriseId(enterpriseId);
        if (!CollectionUtils.isEmpty(sempEnterpriseInfo.getSempOtherFundingDisbursedInfos())) {
            for (SempOtherFundingDisbursedInfo sempOtherFundingDisbursedInfo : sempEnterpriseInfo.getSempOtherFundingDisbursedInfos()) {
                sempOtherFundingDisbursedInfo.setEnterpriseId(enterpriseId);
                iSempOtherFundingDisbursedInfoService.insertSempOtherFundingDisbursedInfo(sempOtherFundingDisbursedInfo);

            }
        }

        iSempAgreementHonourInfoService.deleteSempAgreementHonourInfoByEnterpriseId(enterpriseId);
        if (!CollectionUtils.isEmpty(sempEnterpriseInfo.getSempAgreementHonourInfos())) {
            for (SempAgreementHonourInfo sempAgreementHonourInfo : sempEnterpriseInfo.getSempAgreementHonourInfos()) {
                sempAgreementHonourInfo.setEnterpriseId(enterpriseId);
                iSempAgreementHonourInfoService.insertSempAgreementHonourInfo(sempAgreementHonourInfo);

            }
        }

        iSempVisitInfoService.deleteSempVisitInfoByEnterpriseId(enterpriseId);
        if (!CollectionUtils.isEmpty(sempEnterpriseInfo.getSempVisitInfos())) {
            for (SempVisitInfo sempVisitInfo : sempEnterpriseInfo.getSempVisitInfos()) {
                sempVisitInfo.setEnterpriseId(enterpriseId);
                iSempVisitInfoService.insertSempVisitInfo(sempVisitInfo);
                //TODO
                if (!CollectionUtils.isEmpty(sempVisitInfo.getFileList())) {
                    for (SysFileInfo sysFileInfo : sempVisitInfo.getFileList()) {
                        sysFileInfo.setAgentId(sempVisitInfo.getId());
                        sysFileInfo.setAgentType("visit");
                        iFileInfoService.insertSysFileInfo(sysFileInfo);

                    }
                }
            }
        }


        iSempClaimsSolutionsInfoService.deleteSempClaimsSolutionsInfoByEnterpriseId(enterpriseId);
        if (!CollectionUtils.isEmpty(sempEnterpriseInfo.getSempClaimsSolutionsInfos())) {
            for (SempClaimsSolutionsInfo sempClaimsSolutionsInfo : sempEnterpriseInfo.getSempClaimsSolutionsInfos()) {
                sempClaimsSolutionsInfo.setEnterpriseId(enterpriseId);
                iSempClaimsSolutionsInfoService.insertSempClaimsSolutionsInfo(sempClaimsSolutionsInfo);

            }
        }

        return result;
    }

    /**
     * 修改企业信息
     *
     * @param sempEnterpriseInfo 企业信息
     * @return 结果
     */
    @Override
    public int updateSempEnterpriseInfo(SempEnterpriseInfo sempEnterpriseInfo) {
        sempEnterpriseInfo.setUpdateTime(DateUtils.getNowDate());
        int result = sempEnterpriseInfoMapper.updateSempEnterpriseInfo(sempEnterpriseInfo);

        Long enterpriseId = sempEnterpriseInfo.getEnterpriseId();
        //先删除在新增
        iSempEnterpriseOperationExtInfoService.deleteSempEnterpriseOperationExtInfoByEnterpriseId(enterpriseId);
        if (!CollectionUtils.isEmpty(sempEnterpriseInfo.getSempEnterpriseOperationExtInfos())) {
            for (SempEnterpriseOperationExtInfo sempEnterpriseOperationExtInfo : sempEnterpriseInfo.getSempEnterpriseOperationExtInfos()) {
                sempEnterpriseOperationExtInfo.setEnterpriseId(enterpriseId);
                iSempEnterpriseOperationExtInfoService.insertSempEnterpriseOperationExtInfo(sempEnterpriseOperationExtInfo);

            }
        }

        iSempOtherFundingDisbursedInfoService.deleteSempOtherFundingDisbursedInfoByEnterpriseId(enterpriseId);
        if (!CollectionUtils.isEmpty(sempEnterpriseInfo.getSempOtherFundingDisbursedInfos())) {
            for (SempOtherFundingDisbursedInfo sempOtherFundingDisbursedInfo : sempEnterpriseInfo.getSempOtherFundingDisbursedInfos()) {
                sempOtherFundingDisbursedInfo.setEnterpriseId(enterpriseId);
                iSempOtherFundingDisbursedInfoService.insertSempOtherFundingDisbursedInfo(sempOtherFundingDisbursedInfo);

            }
        }

        iSempAgreementHonourInfoService.deleteSempAgreementHonourInfoByEnterpriseId(enterpriseId);
        if (!CollectionUtils.isEmpty(sempEnterpriseInfo.getSempAgreementHonourInfos())) {
            for (SempAgreementHonourInfo sempAgreementHonourInfo : sempEnterpriseInfo.getSempAgreementHonourInfos()) {
                sempAgreementHonourInfo.setEnterpriseId(enterpriseId);
                iSempAgreementHonourInfoService.insertSempAgreementHonourInfo(sempAgreementHonourInfo);

            }
        }

        iSempVisitInfoService.deleteSempVisitInfoByEnterpriseId(enterpriseId);
        if (!CollectionUtils.isEmpty(sempEnterpriseInfo.getSempVisitInfos())) {
            for (SempVisitInfo sempVisitInfo : sempEnterpriseInfo.getSempVisitInfos()) {
                sempVisitInfo.setEnterpriseId(enterpriseId);
                iSempVisitInfoService.insertSempVisitInfo(sempVisitInfo);
                //TODO
                iFileInfoService.updateFileByAgent(sempVisitInfo.getId(), "visit");
                if (!CollectionUtils.isEmpty(sempVisitInfo.getFileList())) {
                    for (SysFileInfo sysFileInfo : sempVisitInfo.getFileList()) {
                        sysFileInfo.setAgentId(sempVisitInfo.getId());
                        sysFileInfo.setAgentType("visit");
                        iFileInfoService.insertSysFileInfo(sysFileInfo);

                    }
                }
            }
        }


        iSempClaimsSolutionsInfoService.deleteSempClaimsSolutionsInfoByEnterpriseId(enterpriseId);
        if (!CollectionUtils.isEmpty(sempEnterpriseInfo.getSempClaimsSolutionsInfos())) {
            for (SempClaimsSolutionsInfo sempClaimsSolutionsInfo : sempEnterpriseInfo.getSempClaimsSolutionsInfos()) {
                sempClaimsSolutionsInfo.setEnterpriseId(enterpriseId);
                iSempClaimsSolutionsInfoService.insertSempClaimsSolutionsInfo(sempClaimsSolutionsInfo);

            }
        }

        return result;
    }

    /**
     * 批量删除企业信息
     *
     * @param enterpriseIds 需要删除的企业信息主键
     * @return 结果
     */
    @Override
    public int deleteSempEnterpriseInfoByEnterpriseIds(Long[] enterpriseIds) {
        return sempEnterpriseInfoMapper.deleteSempEnterpriseInfoByEnterpriseIds(enterpriseIds);
    }

    /**
     * 删除企业信息信息
     *
     * @param enterpriseId 企业信息主键
     * @return 结果
     */
    @Override
    public int deleteSempEnterpriseInfoByEnterpriseId(Long enterpriseId) {
        return sempEnterpriseInfoMapper.deleteSempEnterpriseInfoByEnterpriseId(enterpriseId);
    }
}
