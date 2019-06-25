package com.yn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yn.domain.model.po.SystemRolePO;

import java.util.List;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author yn
 * @since 2018-09-12
 */
public interface ISystemRoleService extends IService<SystemRolePO> {
    List<SystemRolePO> queryRoles(boolean isMyself, Integer userId) throws Exception;
}
