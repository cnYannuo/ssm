package com.yn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yn.service.ISystemRoleService;
import com.yn.service.IUserRoleService;
import com.yn.domain.mapper.SystemRoleMapper;
import com.yn.domain.model.po.SystemRolePO;
import com.yn.domain.model.po.UserRolePO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author yn
 * @since 2018-09-12
 */
@Service
public class SystemRoleServiceImpl extends ServiceImpl<SystemRoleMapper, SystemRolePO> implements ISystemRoleService {

    @Autowired
    private IUserRoleService userRoleService;

    @Override
    public List<SystemRolePO> queryRoles(boolean isMyself, Integer userId) throws Exception {

        try {
            if (isMyself) {
                List<UserRolePO> userRoles = userRoleService.list(new QueryWrapper<UserRolePO>().eq("user_id", userId));
                if (CollectionUtils.isNotEmpty(userRoles)) {
                    QueryWrapper<SystemRolePO> qw = new QueryWrapper<>();
                    qw.select("role_id,role_name,locked,orders");
                    qw.in("role_id", userRoles.stream().map(UserRolePO::getRoleId).toArray());
                    return this.list(qw);
                }
                return null;
            } else {
                return this.list(new QueryWrapper<SystemRolePO>().select("role_id,role_name,locked,orders"));
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
