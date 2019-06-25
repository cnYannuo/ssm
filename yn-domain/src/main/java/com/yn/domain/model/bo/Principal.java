package com.yn.domain.model.bo;


import com.yn.domain.model.po.SystemPermissionPO;

import java.util.List;
import java.util.Map;

/**
 * 登录的用户信息
 */
public class Principal implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private Integer userId;
    private String userName;
    private String roleName;
    private String avatar;
    private String phone;
    private String email;
    private Integer sex;
    private Integer locked;
    private Long createTime;
    private Integer roleId;

    private Map<Integer, List<SystemPermissionPO>> roles;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Map<Integer, List<SystemPermissionPO>> getRoles() {
        return roles;
    }

    public void setRole(Map<Integer, List<SystemPermissionPO>> roles) {
        this.roles = roles;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
