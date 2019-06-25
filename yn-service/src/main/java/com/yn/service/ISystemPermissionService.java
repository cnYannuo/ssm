package com.yn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yn.domain.model.bo.PermissionTreeVO;
import com.yn.domain.model.po.SystemPermissionPO;

/**
 * <p>
 * 权限菜单 服务类
 * </p>
 *
 * @author yn
 * @since 2018-09-12
 */
public interface ISystemPermissionService extends IService<SystemPermissionPO> {

    /**
     * 查询权限树
     *
     * @param onlyAll 如果为true，查询所有的权限；否则查询角色对应的权限
     * @return
     */
    PermissionTreeVO[] queryAllPermissionTree(boolean onlyAll, Integer roleId, boolean checkBox);
}
