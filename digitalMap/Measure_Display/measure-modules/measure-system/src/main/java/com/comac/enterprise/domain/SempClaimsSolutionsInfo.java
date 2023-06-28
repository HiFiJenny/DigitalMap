package com.comac.enterprise.domain;

import java.util.Date;

import com.comac.common.core.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.comac.common.core.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 企业诉求信息对象 semp_claims_solutions_info
 * 
 * @author comac
 * @date 2023-03-29
 */
public class SempClaimsSolutionsInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 企业ID */
    @Excel(name = "企业ID")
    private Long enterpriseId;

    /** 诉求时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "诉求时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date claimsTime;

    /** 诉求内容 */
    @Excel(name = "诉求内容")
    private String claimsContent;

    /** 解决方式 */
    @Excel(name = "解决方式")
    private String solution;

    /** 解决结果 */
    @Excel(name = "解决结果")
    private String solutionResult;

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
    public void setClaimsTime(Date claimsTime) 
    {
        this.claimsTime = claimsTime;
    }

    public Date getClaimsTime() 
    {
        return claimsTime;
    }
    public void setClaimsContent(String claimsContent) 
    {
        this.claimsContent = claimsContent;
    }

    public String getClaimsContent() 
    {
        return claimsContent;
    }
    public void setSolution(String solution) 
    {
        this.solution = solution;
    }

    public String getSolution() 
    {
        return solution;
    }
    public void setSolutionResult(String solutionResult) 
    {
        this.solutionResult = solutionResult;
    }

    public String getSolutionResult() 
    {
        return solutionResult;
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
            .append("claimsTime", getClaimsTime())
            .append("claimsContent", getClaimsContent())
            .append("solution", getSolution())
            .append("solutionResult", getSolutionResult())
            .append("createUser", getCreateUser())
            .append("createTime", getCreateTime())
            .append("updateUser", getUpdateUser())
            .append("updateTime", getUpdateTime())
            .append("status", getStatus())
            .toString();
    }
}
