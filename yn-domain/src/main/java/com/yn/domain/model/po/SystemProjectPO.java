package com.yn.domain.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * <p>
 * 系统
 * </p>
 *
 * @author yn
 * @since 2018-09-12
 */
@TableName("system_project")
public class SystemProjectPO extends Model<SystemProjectPO> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "project_id", type = IdType.AUTO)
    private Integer projectId;
    /**
     * 图标
     */
    @TableField("project_icon")
    private String projectIcon;
    /**
     * 背景
     */
    @TableField("project_banner")
    private String projectBanner;
    /**
     * 主题
     */
    @TableField("project_theme")
    private String projectTheme;
    /**
     * 根目录
     */
    @TableField("project_base_path")
    private String projectBasePath;
    /**
     * 状态(-1:黑名单,1:正常)
     */
    @TableField("project_status")
    private Integer projectStatus;
    /**
     * 系统名称
     */
    @TableField("project_name")
    private String projectName;
    /**
     * 系统标题
     */
    @TableField("project_title")
    private String projectTitle;
    /**
     * 系统描述
     */
    @TableField("project_description")
    private String projectDescription;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Long createTime;
    /**
     * 排序
     */
    private Long orders;


    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectIcon() {
        return projectIcon;
    }

    public void setProjectIcon(String projectIcon) {
        this.projectIcon = projectIcon;
    }

    public String getProjectBanner() {
        return projectBanner;
    }

    public void setProjectBanner(String projectBanner) {
        this.projectBanner = projectBanner;
    }

    public String getProjectTheme() {
        return projectTheme;
    }

    public void setProjectTheme(String projectTheme) {
        this.projectTheme = projectTheme;
    }

    public String getProjectBasePath() {
        return projectBasePath;
    }

    public void setProjectBasePath(String projectBasePath) {
        this.projectBasePath = projectBasePath;
    }

    public Integer getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(Integer projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getOrders() {
        return orders;
    }

    public void setOrders(Long orders) {
        this.orders = orders;
    }

    @Override
    protected Serializable pkVal() {
        return this.projectId;
    }

    @Override
    public String toString() {

        // ReflectionToStringBuilder 性能远大于Object toString
        // ToStringStyle 一定要设置为jsonStyle 以便于数据处理
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
