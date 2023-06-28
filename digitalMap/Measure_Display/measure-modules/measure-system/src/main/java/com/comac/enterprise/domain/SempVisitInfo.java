package com.comac.enterprise.domain;

import java.util.Date;
import java.util.List;

import com.comac.common.core.web.domain.BaseEntity;
import com.comac.system.domain.SysFileInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.comac.common.core.annotation.Excel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 企业走访信息对象 semp_visit_info
 * 
 * @author comac
 * @date 2023-03-29
 */
@Data
@NoArgsConstructor
public class SempVisitInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 企业ID */
    @Excel(name = "企业ID")
    private Long enterpriseId;

    /** 走访时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "走访时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date visitTime;

    /** 走访领导 */
    @Excel(name = "走访领导")
    private String visitLeader;

    /** 简报 */
    @Excel(name = "简报")
    private String visitComment;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long createUser;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long updateUser;

    /** 状态  1正常 0已删除 */
    @Excel(name = "状态  1正常 0已删除")
    private String status;

    private List<SysFileInfo> fileList;
 }
