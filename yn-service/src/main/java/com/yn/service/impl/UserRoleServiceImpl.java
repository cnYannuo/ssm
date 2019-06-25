package com.yn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yn.service.IUserRoleService;
import com.yn.domain.mapper.UserRoleMapper;
import com.yn.domain.model.po.UserRolePO;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色关联表 服务实现类
 * </p>
 *
 * @author yn
 * @since 2018-09-12
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRolePO> implements IUserRoleService {

}
