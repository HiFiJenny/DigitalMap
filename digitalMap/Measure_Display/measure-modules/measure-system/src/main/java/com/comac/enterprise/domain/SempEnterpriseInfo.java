package com.comac.enterprise.domain;

import java.util.Date;
import java.util.List;

import com.comac.common.core.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.comac.common.core.annotation.Excel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 企业信息对象 semp_enterprise_info
 *
 * @author comac
 * @date 2023-03-30
 */
@Data
@NoArgsConstructor
public class SempEnterpriseInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 企业ID
     */
    private Long enterpriseId;

    /**
     * 企业名称
     */
    @Excel(name = "企业名称")
    private String enterpriseName;

    /**
     * 社会信用代码
     */
    @Excel(name = "社会信用代码")
    private String certCode;

    /**
     * 项目简介
     */
    @Excel(name = "项目简介")
    private String introduction;

    /**
     * 是否签约
     */
    @Excel(name = "是否签约",readConverterExp = "0=否,1=是")
    private Integer sign;

    /**
     * 协议项目名称
     */
    @Excel(name = "协议项目名称")
    private String protocolProjectName;

    /**
     * 投资方
     */
    @Excel(name = "投资方")
    private String investor;

    /**
     * 投资方能级
     */
    @Excel(name = "投资方能级")
    private String investorLevel;

    /**
     * 投资方所在国家,城市
     */
    @Excel(name = "投资方所在国家,城市")
    private String investorPlace;

    /**
     * 联系人
     */
    @Excel(name = "联系人")
    private String contact;

    /**
     * 联系方式
     */
    @Excel(name = "联系方式")
    private String contactPhone;

    /**
     * 注册资本
     */
    @Excel(name = "注册资本")
    private String registeredCapital;

    /**
     * 实缴资本
     */
    @Excel(name = "实缴资本")
    private String contributedCapital;

    /**
     * 注册地址
     */
    @Excel(name = "注册地址")
    private String registeredAddress;

    /**
     * 项目属性
     */
    @Excel(name = "项目属性",readConverterExp ="1=洽谈,2=已签约,3=与新区其他部门签约,4=签约部门,5=未签约项目")
    private String itemAttribute;

    /**
     * 已签约项目运营状态
     */
    @Excel(name = "已签约项目运营状态",readConverterExp = "1=注册项目公司,2=拿地,3=签订租赁协议,4=装修载体,5=入驻运营,6=未阶段性履约")
    private String contractedProjectStatus;

    /**
     * 未阶段性履约原因及具体指标
     */
    @Excel(name = "未阶段性履约原因及具体指标")
    private String nonPhasedPerformanceReason;

    /**
     * 年度协议约定营收
     */
    @Excel(name = "年度协议约定营收")
    private String annualAgreementRevenue;

    /**
     * 年度实际营收
     */
    @Excel(name = "年度实际营收")
    private String annualActualRevenue;

    /**
     * 年度协议约定税收
     */
    @Excel(name = "年度协议约定税收")
    private String annualAgreementTax;

    /**
     * 投资总额
     */
    @Excel(name = "投资总额")
    private String totalInvestment;

    /**
     * 签约时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "签约时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date signTime;

    /**
     * 注册时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "注册时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date registrationTime;

    /**
     * 招商引资部门
     */
    @Excel(name = "招商引资部门")
    private String investmentPromotionDepartment;

    /**
     * 现日常服务部门
     */
    @Excel(name = "现日常服务部门")
    private String dailyServiceDepartment;

    /**
     * 局内项目负责人
     */
    @Excel(name = "局内项目负责人")
    private String projectInternalLeader;

    /**
     * 局内项目负责人联系方式
     */
    @Excel(name = "局内项目负责人联系方式")
    private String projectInternalLeaderPhone;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String basicComment;

    /**
     * 实际运营市辖区
     */
    @Excel(name = "实际运营市辖区")
    private String actualOperatingArea;

    /**
     * 是否在科学城
     */
    @Excel(name = "是否在科学城",readConverterExp = "0=否,1=是")
    private Integer inScienceCity;

    /**
     * 实际拿地及运营详细地址
     */
    @Excel(name = "实际拿地及运营详细地址")
    private String actualLandOperatingAddress;

    /**
     * 年度实际企业所得税
     */
    @Excel(name = "年度实际企业所得税")
    private String annualCorporateActualIncomeTax;

    /**
     * 年度实际增值税
     */
    @Excel(name = "年度实际增值税")
    private String annualActualVat;

    /**
     * 年度合计纳税
     */
    @Excel(name = "年度合计纳税")
    private String annualAggregateTax;

    /**
     * 年度协议入驻人数
     */
    @Excel(name = "年度协议入驻人数")
    private String annualAgreementEntrantsNumber;

    /**
     * 年度入驻人数
     */
    @Excel(name = "年度入驻人数")
    private String annualEntrantsNumber;

    /**
     * 入驻运营时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入驻运营时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date entryOperationTime;

    /**
     * 入驻面积
     * -仅载体项目填写
     */
    @Excel(name = "入驻面积 -仅载体项目填写")
    private String entryArea;

    /**
     * 行业
     */
    @Excel(name = "行业")
    private String industry;

    /**
     * 是否四上企业
     */
    @Excel(name = "是否四上企业",readConverterExp = "it=软件和信息技术服务业,other=或其他行业")
    private String fourOnEnterprise;

    /**
     * 企业类型：1央企 2国企 3混合所有制企业 4民企 5外企 6合资 7其他
     */
    @Excel(name = "企业类型：1央企 2国企 3混合所有制企业 4民企 5外企 6合资 7其他",readConverterExp = "1=央企,2=国企,3=混合所有制企业,4民企,5=外企,6=合资,7=其他")
    private String enterpriseType;

    /**
     * 实际办公地址
     */
    @Excel(name = "实际办公地址")
    private String actualOfficeAddress;

    /**
     * 主营业务
     */
    @Excel(name = "主营业务")
    private String mainBusiness;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String operationComment;

    /**
     * 人员规模
     */
    @Excel(name = "人员规模")
    private String personelSize;

    /**
     * 是否合作 0未合作 1合作
     */
    private String cooperate;

    /**
     * 状态  0未提交 1未审核 2已审核 3已驳回
     */
    private String status;

    /**
     * 状态  1正常 0已删除
     */
    private String flag;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createUser;

    /**
     * $column.columnComment
     */
    private Long updateUser;

    private List<SempEnterpriseOperationExtInfo> sempEnterpriseOperationExtInfos;

    private List<SempOtherFundingDisbursedInfo> sempOtherFundingDisbursedInfos;

    private List<SempAgreementHonourInfo> sempAgreementHonourInfos;

    private List<SempVisitInfo> sempVisitInfos;

    private List<SempClaimsSolutionsInfo> sempClaimsSolutionsInfos;
}
