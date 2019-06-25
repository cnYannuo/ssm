package com.yn.domain.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

@TableName("user_role")
public class UserRolePO extends Model<UserRolePO> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "user_role_id", type = IdType.AUTO)
    private Integer userRoleId;
    /**
     * 用户编号
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 角色编号
     */
    @TableField("role_id")
    private Integer roleId;


    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    protected Serializable pkVal() {
        return this.userRoleId;
    }

    @Override
    public String toString() {

        // ReflectionToStringBuilder 性能远大于Object toString
        // ToStringStyle 一定要设置为jsonStyle 以便于数据处理
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
