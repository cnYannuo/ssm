package com.yn.domain.model.bo;



import com.yn.domain.model.po.SystemPermissionPO;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: (权限树)
 * @date 2018/10/12 17:53:44
 */
public class PermissionTreeVO extends WebTreeVO {
    private static final long serialVersionUID = 1L;

    public PermissionTreeVO(SystemPermissionPO p) {
        this.setLabel(p.getPermissionName());
        this.setId(p.getPermissionId());
        this.permissionId = p.getPermissionId();
        this.systemId = p.getSystemId();
        this.pid = p.getPid();
        this.permissionTitle = p.getPermissionTitle();
        this.permissionName = p.getPermissionName();
        this.permissionType = p.getPermissionType();
        this.permissionValue = p.getPermissionValue();
        this.permissionUri = p.getPermissionUri();
        this.permissionIcon = p.getPermissionIcon();
        this.permissionStatus = p.getPermissionStatus();
        this.createTime = p.getCreateTime();
        this.orders = p.getOrders();
    }

    private Integer permissionId;
    /**
     * 编号
     */
    private Integer systemId;
    /**
     * 所属上级
     */
    private Integer pid;
    /**
     * 路由名称
     */
    private String permissionTitle;
    /**
     * 菜单或者目录按钮名称
     */
    private String permissionName;
    /**
     * 类型(1:目录,2:菜单,3:按钮)
     */
    private Integer permissionType;
    /**
     * 权限值
     */
    private String permissionValue;
    /**
     * 路径
     */
    private String permissionUri;
    /**
     * 图标
     */
    private String permissionIcon;
    /**
     * 状态(0:正常,1:禁止)
     */
    private Integer permissionStatus;
    /**
     * 创建时间
     */
    private Long createTime;
    /**
     * 排序
     */
    private Long orders;

    private List<PermissionTreeVO> children = new ArrayList<>();


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

    public String getPermissionTitle() {
        return permissionTitle;
    }

    public void setPermissionTitle(String permissionTitle) {
        this.permissionTitle = permissionTitle;
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

    public List<PermissionTreeVO> getChildren() {
        return children;
    }

    public void setChildren(List<PermissionTreeVO> children) {
        this.children = children;
    }
}
