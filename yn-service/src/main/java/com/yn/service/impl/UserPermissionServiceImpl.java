package com.yn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yn.service.IUserPermissionService;
import com.yn.domain.mapper.UserPermissionMapper;
import com.yn.domain.model.po.UserPermissionPO;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户权限关联表 服务实现类
 * </p>
 *
 * @author yn
 * @since 2018-09-12
 */
@Service
public class UserPermissionServiceImpl extends ServiceImpl<UserPermissionMapper, UserPermissionPO> implements IUserPermissionService {

}
