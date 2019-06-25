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
 * 角色
 * </p>
 *
 * @author yn
 * @since 2018-09-12
 */
@TableName("system_role")
public class SystemRolePO extends Model<SystemRolePO> {

    private static final long serialVersionUID = 1L;

    public SystemRolePO() {
        super();
    }

    public SystemRolePO(Integer roleId, String roleName, String roleTitle, String roleDescription, Integer locked, Long orders) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleTitle = roleTitle;
        this.roleDescription = roleDescription;
        this.locked = locked;
        this.orders = orders;
    }

    /**
     * 编号
     */
    @TableId(value = "role_id", type = IdType.AUTO)
    private Integer roleId;
    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;
    /**
     * 角色标题
     */
    @TableField("role_title")
    private String roleTitle;
    /**
     * 角色描述
     */
    @TableField("role_description")
    private String roleDescription;
    /**
     * 状态(0:正常,1:锁定)
     */
    private Integer locked;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Long createTime;
    /**
     * 排序
     */
    private Long orders;


    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleTitle() {
        return roleTitle;
    }

    public void setRoleTitle(String roleTitle) {
        this.roleTitle = roleTitle;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
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

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    @Override
    protected Serializable pkVal() {
        return this.roleId;
    }

    @Override
    public String toString() {

        // ReflectionToStringBuilder 性能远大于Object toString
        // ToStringStyle 一定要设置为jsonStyle 以便于数据处理
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
