package com.yn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yn.service.IRolePermissionService;
import com.yn.domain.mapper.RolePermissionMapper;
import com.yn.domain.model.po.RolePermissionPO;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色权限关联表 服务实现类
 * </p>
 *
 * @author yn
 * @since 2018-09-12
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermissionPO> implements IRolePermissionService {

}
