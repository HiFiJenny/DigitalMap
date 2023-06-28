package com.comac.enterprise.domain;

import java.util.Date;

import com.comac.common.core.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.comac.common.core.annotation.Excel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 软件和信息技术服务业运营情况拓展对象 semp_enterprise_operation_ext_info
 * 
 * @author comac
 * @date 2023-03-29
 */
@Data
@NoArgsConstructor
public class SempEnterpriseOperationExtInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    private Long enterpriseId;

    /** 企业名称 */
    @Excel(name = "企业名称")
    private String enterpriseName;

    /** 社会信用代码 */
    @Excel(name = "社会信用代码")
    private String certCode;

    /** 主营业务 */
    @Excel(name = "主营业务")
    private String mainBusiness;

    /** 实际办公地址 */
    @Excel(name = "实际办公地址")
    private String actualOfficeAddress;

    /** 企业类型：1央企 2国企 3混合所有制企业 4民企 5外企 6合资 7其他 */
    @Excel(name = "企业类型：1央企 2国企 3混合所有制企业 4民企 5外企 6合资 7其他")
    private String enterpriseType;

    /** 联系人 */
    @Excel(name = "联系人")
    private String contact;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String contactPhone;

    /** 走访时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "走访时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date visitTime;

    /** 洽谈内容 */
    @Excel(name = "洽谈内容")
    private String negotiationContent;

    /** 年度 */
    @Excel(name = "年度")
    private String operationAnnual;

    /** 去年12月营收（万元） */
    @Excel(name = "去年12月营收", readConverterExp = "万=元")
    private String lastDecemberRevenue;

    /** 当年12月营收（万元） */
    @Excel(name = "当年12月营收", readConverterExp = "万=元")
    private String currentDecemberRevenue;

    /** 当年1-11月实际营收（万元） */
    @Excel(name = "当年1-11月实际营收", readConverterExp = "万=元")
    private String currentJanuaryToNovemberActualRevenue;

    /** 当年1-11月营收预测（万元） */
    @Excel(name = "当年1-11月营收预测", readConverterExp = "万=元")
    private String currentJanuaryToNovemberForecastRevenue;

    /** 去年1-11月营收（万元） */
    @Excel(name = "去年1-11月营收", readConverterExp = "万=元")
    private String lastJanuaryToNovemberRevenue;

    /** 当年1-8月实际营收（万元） */
    @Excel(name = "当年1-8月实际营收", readConverterExp = "万=元")
    private String currentJanuaryToAugustActualRevenue;

    /** 当年1-8月营收预测（万元） */
    @Excel(name = "当年1-8月营收预测", readConverterExp = "万=元")
    private String currentJanuaryToAugustForecastRevenue;

    /** 去年1-8月营收（万元） */
    @Excel(name = "去年1-8月营收", readConverterExp = "万=元")
    private String lastJanuaryToAugustRevenue;

    /** 当年1-5月实际营收（万元） */
    @Excel(name = "当年1-5月实际营收", readConverterExp = "万=元")
    private String currentJanuaryToMayActualRevenue;

    /** 当年1-5月营收预测（万元） */
    @Excel(name = "当年1-5月营收预测", readConverterExp = "万=元")
    private String currentJanuaryToMayForecastRevenue;

    /** 去年1-5月营收（万元） */
    @Excel(name = "去年1-5月营收", readConverterExp = "万=元")
    private String lastJanuaryToMayRevenue;

    /** 当年1-2月实际营收（万元） */
    @Excel(name = "当年1-2月实际营收", readConverterExp = "万=元")
    private String currentJanuaryToFebruaryActualRevenue;

    /** 当年1-2月营收预测（万元） */
    @Excel(name = "当年1-2月营收预测", readConverterExp = "万=元")
    private String currentJanuaryToFebruaryForecastRevenue;

    /** 去年1-2月营收（万元） */
    @Excel(name = "去年1-2月营收", readConverterExp = "万=元")
    private String lastJanuaryToFebruaryRevenue;

    /** 预测和实际差额 */
    @Excel(name = "预测和实际差额")
    private String forecastActualDifference;

    /** 1-X月同比增速 */
    @Excel(name = "1-X月同比增速")
    private String growthRate;

    /** 1-X月营收增加值 */
    @Excel(name = "1-X月营收增加值")
    private String revenueAdd;

    /** 营收波动或营收异常原因简述 */
    @Excel(name = "营收波动或营收异常原因简述")
    private String abnormalRevenueReason;

    /** 企业面临的主要困难和问题 */
    @Excel(name = "企业面临的主要困难和问题")
    private String problems;

    /** 下一步工作计划 */
    @Excel(name = "下一步工作计划")
    private String nextPlan;

    /** 四上责任部门 */
    @Excel(name = "四上责任部门")
    private String fourOnResponseDept;

    /** 亲商暖企项目专员 */
    @Excel(name = "亲商暖企项目专员")
    private String projectSpecialist;

    /** 备注 */
    @Excel(name = "备注")
    private String comment;

    /** 状态   1正常 0已删除 */
    @Excel(name = "状态   1正常 0已删除")
    private String status;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long createUser;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long updateUser;

}
