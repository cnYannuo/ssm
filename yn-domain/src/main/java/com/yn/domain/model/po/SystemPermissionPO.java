package com.yn.domain.model.po;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 权限菜单
 * </p>
 *
 * @since 2018-09-12
 */
@TableName("system_permission")
public class SystemPermissionPO extends Model<SystemPermissionPO> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "permission_id", type = IdType.AUTO)
    private Integer permissionId;
    /**
     * 编号
     */
    @TableField("system_id")
    private Integer systemId;
    /**
     * 所属上级
     */
    private Integer pid;
    /**
     * 路由名称
     */
    @TableField("permission_title")
    private String permissionTitle;
    /**
     * 菜单或者目录按钮名称
     */
    @TableField("permission_name")
    private String permissionName;
    /**
     * 类型(1:目录,2:菜单,3:按钮)
     */
    @TableField("permission_type")
    private Integer permissionType;
    /**
     * 权限值
     */
    @TableField("permission_value")
    private String permissionValue;
    /**
     * 路径
     */
    @TableField("permission_uri")
    private String permissionUri;
    /**
     * 图标
     */
    @TableField("permission_icon")
    private String permissionIcon;
    /**
     * 状态(0:正常,1:禁止)
     */
    @TableField("permission_status")
    private Integer permissionStatus;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Long createTime;
    /**
     * 排序
     */
    private Long orders;

    @TableField(exist = false)
    private List<SystemPermissionPO> pLst;


    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public Integer getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(Integer permissionType) {
        this.permissionType = permissionType;
    }

    public String getPermissionValue() {
        return permissionValue;
    }

    public void setPermissionValue(String permissionValue) {
        this.permissionValue = permissionValue;
    }

    public String getPermissionUri() {
        return permissionUri;
    }

    public void setPermissionUri(String permissionUri) {
        this.permissionUri = permissionUri;
    }

    public String getPermissionIcon() {
        return permissionIcon;
    }

    public void setPermissionIcon(String permissionIcon) {
        this.permissionIcon = permissionIcon;
    }

    public Integer getPermissionStatus() {
        return permissionStatus;
    }

    public void setPermissionStatus(Integer permissionStatus) {
        this.permissionStatus = permissionStatus;
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

    public String getPermissionTitle() {
        return permissionTitle;
    }

    public void setPermissionTitle(String permissionTitle) {
        this.permissionTitle = permissionTitle;
    }

    public List<SystemPermissionPO> getpLst() {
        return pLst;
    }

    public void setpLst(List<SystemPermissionPO> pLst) {
        this.pLst = pLst;
    }

    @Override
    protected Serializable pkVal() {
        return this.permissionId;
    }

    @Override
    public String toString() {

        // ReflectionToStringBuilder 性能远大于Object toString
        // ToStringStyle 一定要设置为jsonStyle 以便于数据处理
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
