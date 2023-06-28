package com.comac.system.domain;

import com.comac.common.core.annotation.Excel;
import com.comac.common.core.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 文件记录对象 sys_file_info
 * 
 * @author comac
 * @date 2023-03-31
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SysFileInfo
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long fileId;

    /** 文件名称 */
    @Excel(name = "文件名称")
    private String fileName;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String filePath;

    /** 文件描述 */
    @Excel(name = "文件描述")
    private String fileDescribe;

    /** 文件类型 */
    @Excel(name = "文件类型")
    private String fileType;

    /** 文件后缀 */
    @Excel(name = "文件后缀")
    private String fileSuffix;

    /** 文件大小 */
    @Excel(name = "文件大小")
    private Long fileSize;

    /** 文件外链路径 */
    @Excel(name = "文件外链路径")
    private String objectUrl;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 租户类型 */
    @Excel(name = "租户类型")
    private String agentType;

    /** 租户ID */
    @Excel(name = "租户ID")
    private Long agentId;

    /** 创建者id */
    @Excel(name = "创建者id")
    private String createUserName;

    /** 创建者名称 */
    @Excel(name = "创建者名称")
    private String createNickName;

    /** 更新者id */
    @Excel(name = "更新者id")
    private String updateUserName;

    /** 更新者名称 */
    @Excel(name = "更新者名称")
    private String updateNickName;
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
