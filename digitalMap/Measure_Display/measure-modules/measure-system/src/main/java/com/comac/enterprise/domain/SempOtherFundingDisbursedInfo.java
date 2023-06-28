package com.comac.enterprise.domain;

import com.comac.common.core.annotation.Excel;
import com.comac.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 企业其他资金拨付信息对象 semp_other_funding_disbursed_info
 * 
 * @author comac
 * @date 2023-03-29
 */
public class SempOtherFundingDisbursedInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 企业ID */
    @Excel(name = "企业ID")
    private Long enterpriseId;

    /** 年度 */
    @Excel(name = "年度")
    private String otherFundingAnnual;

    /** 资金类别（国家/省级/市级/本级） */
    @Excel(name = "资金类别", readConverterExp = "国=家/省级/市级/本级")
    private String fundsCategory;

    /** 文件或项目名称 */
    @Excel(name = "文件或项目名称")
    private String fileOrProjectName;

    /** 金额 */
    @Excel(name = "金额")
    private String otherFundingAmount;

    /** 拨付部门 */
    @Excel(name = "拨付部门")
    private String disbursementDepartment;

    /** 备注 */
    @Excel(name = "备注")
    private String otherFundingComment;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long createUser;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long updateUser;

    /** 状态  1正常 0已删除 */
    @Excel(name = "状态  1正常 0已删除")
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setEnterpriseId(Long enterpriseId) 
    {
        this.enterpriseId = enterpriseId;
    }

    public Long getEnterpriseId() 
    {
        return enterpriseId;
    }
    public void setOtherFundingAnnual(String otherFundingAnnual) 
    {
        this.otherFundingAnnual = otherFundingAnnual;
    }

    public String getOtherFundingAnnual() 
    {
        return otherFundingAnnual;
    }
    public void setFundsCategory(String fundsCategory) 
    {
        this.fundsCategory = fundsCategory;
    }

    public String getFundsCategory() 
    {
        return fundsCategory;
    }
    public void setFileOrProjectName(String fileOrProjectName) 
    {
        this.fileOrProjectName = fileOrProjectName;
    }

    public String getFileOrProjectName() 
    {
        return fileOrProjectName;
    }
    public void setOtherFundingAmount(String otherFundingAmount) 
    {
        this.otherFundingAmount = otherFundingAmount;
    }

    public String getOtherFundingAmount() 
    {
        return otherFundingAmount;
    }
    public void setDisbursementDepartment(String disbursementDepartment) 
    {
        this.disbursementDepartment = disbursementDepartment;
    }

    public String getDisbursementDepartment() 
    {
        return disbursementDepartment;
    }
    public void setOtherFundingComment(String otherFundingComment) 
    {
        this.otherFundingComment = otherFundingComment;
    }

    public String getOtherFundingComment() 
    {
        return otherFundingComment;
    }
    public void setCreateUser(Long createUser) 
    {
        this.createUser = createUser;
    }

    public Long getCreateUser() 
    {
        return createUser;
    }
    public void setUpdateUser(Long updateUser) 
    {
        this.updateUser = updateUser;
    }

    public Long getUpdateUser() 
    {
        return updateUser;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("enterpriseId", getEnterpriseId())
            .append("otherFundingAnnual", getOtherFundingAnnual())
            .append("fundsCategory", getFundsCategory())
            .append("fileOrProjectName", getFileOrProjectName())
            .append("otherFundingAmount", getOtherFundingAmount())
            .append("disbursementDepartment", getDisbursementDepartment())
            .append("otherFundingComment", getOtherFundingComment())
            .append("createUser", getCreateUser())
            .append("createTime", getCreateTime())
            .append("updateUser", getUpdateUser())
            .append("updateTime", getUpdateTime())
            .append("status", getStatus())
            .toString();
    }
}
