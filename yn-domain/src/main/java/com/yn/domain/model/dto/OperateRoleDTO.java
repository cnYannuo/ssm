package com.yn.domain.model.dto;

import com.yn.domain.model.po.SystemRolePO;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @className: (角色操作数据传输类)
 * @date 2018/10/13 15:26:41
 */
public class OperateRoleDTO implements Serializable {

    private Integer roleId;

    /**
     * 角色名称
     */
    @Pattern(regexp = "^[\\u4E00-\\u9FA5|\\w]{2,16}$", message = "请输入长度为2-16的中文或者字母组成的字符")
    private String roleName;
    /**
     * 角色标题
     */
    @Size(min = 1, max = 20, message = "请输入不超过20位的任意字符")
    private String roleTitle;
    /**
     * 角色描述
     */
    @Size(min = 1, max = 512, message = "请输入不超过20位的任意字符")
    private String roleDescription;
    /**
     * 状态(0:正常,1:锁定)
     */
    @Max(value = 1, message = "状态只能是数字0或者1")
    @Min(value = 0, message = "状态只能是数字0或者1")
    private Integer locked;

    /**
     * 排序
     */
    private Long orders;


    public SystemRolePO getRolePO() {
        return new SystemRolePO(this.roleId, this.roleName, this.roleTitle, this.roleDescription, this.locked, this.orders);
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

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    public Long getOrders() {
        return orders;
    }

    public void setOrders(Long orders) {
        this.orders = orders;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        // ReflectionToStringBuilder 性能远大于Object toString
        // ToStringStyle 一定要设置为jsonStyle 以便于数据处理
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
