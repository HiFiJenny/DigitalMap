package com.comac.enterprise.domain;

import com.comac.common.core.annotation.Excel;
import com.comac.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 企业协议兑现信息对象 semp_agreement_honour_info
 * 
 * @author comac
 * @date 2023-03-29
 */
public class SempAgreementHonourInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 企业ID */
    @Excel(name = "企业ID")
    private Long enterpriseId;

    /** 协议兑现年度 */
    @Excel(name = "协议兑现年度")
    private String agreementAnnual;

    /** 协议兑现名称 */
    @Excel(name = "协议兑现名称")
    private String agreementName;

    /** 资金类型 */
    @Excel(name = "资金类型")
    private String agreementFundsCategory;

    /** 金额 */
    @Excel(name = "金额")
    private String agreementAmount;

    /** 备注 */
    @Excel(name = "备注")
    private String agreementComment;

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
    public void setAgreementAnnual(String agreementAnnual) 
    {
        this.agreementAnnual = agreementAnnual;
    }

    public String getAgreementAnnual() 
    {
        return agreementAnnual;
    }
    public void setAgreementName(String agreementName) 
    {
        this.agreementName = agreementName;
    }

    public String getAgreementName() 
    {
        return agreementName;
    }
    public void setAgreementFundsCategory(String agreementFundsCategory) 
    {
        this.agreementFundsCategory = agreementFundsCategory;
    }

    public String getAgreementFundsCategory() 
    {
        return agreementFundsCategory;
    }
    public void setAgreementAmount(String agreementAmount) 
    {
        this.agreementAmount = agreementAmount;
    }

    public String getAgreementAmount() 
    {
        return agreementAmount;
    }
    public void setAgreementComment(String agreementComment) 
    {
        this.agreementComment = agreementComment;
    }

    public String getAgreementComment() 
    {
        return agreementComment;
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
            .append("agreementAnnual", getAgreementAnnual())
            .append("agreementName", getAgreementName())
            .append("agreementFundsCategory", getAgreementFundsCategory())
            .append("agreementAmount", getAgreementAmount())
            .append("agreementComment", getAgreementComment())
            .append("createUser", getCreateUser())
            .append("createTime", getCreateTime())
            .append("updateUser", getUpdateUser())
            .append("updateTime", getUpdateTime())
            .append("status", getStatus())
            .toString();
    }
}
