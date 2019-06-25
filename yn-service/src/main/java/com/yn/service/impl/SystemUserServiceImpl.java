package com.yn.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yn.service.*;
import com.yn.common.entity.BetterStoreConstants;
import com.yn.common.entity.GlobalResult;
import com.yn.domain.mapper.SystemUserMapper;
import com.yn.domain.model.bo.Principal;
import com.yn.domain.model.dto.SystemLoginDTO;
import com.yn.domain.model.po.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author yn
 * @since 2018-09-12
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUserPO> implements ISystemUserService {

    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IRolePermissionService rolePermissionService;
    @Autowired
    private ISystemPermissionService systemPermissionService;
    @Autowired
    private ISystemRoleService systemRoleService;

    @Override
    public GlobalResult login(SystemLoginDTO loginDTO, GlobalResult result) {
        try {
            QueryWrapper<SystemUserPO> qw = new QueryWrapper<>();
            qw.lambda()
                    .eq(SystemUserPO::getUserName, loginDTO.getUserName())
                    .eq(SystemUserPO::getLocked, BetterStoreConstants.STATUS_YES);
            /*qw.eq("user_name", loginDTO.getUserName());
            qw.eq("locked", BetterStoreConstants.STATUS_YES);*/
            SystemUserPO systemUserPO = this.getOne(qw);
            if (null == systemUserPO) {
                result.setMsg("用户不存在或已失效！");
                return result;
            }
//            if (false == (systemUserPO.getPassWord().equals(DigestUtils.md5Hex(loginDTO.getUserPwd() + systemUserPO.getSalt())))) {
            if(false == (systemUserPO.getPassWord().equals(loginDTO.getUserPwd()))){
                result.setMsg("用户名或密码输入错误！");
                return result;
            }
            result.setSuccess(true);
            Principal principal = new Principal();
            BeanUtils.copyProperties(principal, systemUserPO);
            List<UserRolePO> userRolePOS = userRoleService.list(new QueryWrapper<UserRolePO>().eq("user_id", systemUserPO.getUserId()));
            if (CollectionUtils.isNotEmpty(userRolePOS)) {
                Set<SystemPermissionPO> permissionPOSet = new HashSet<>();
                // 查询权限和角色
                // 查询角色名称(如果有多个，逗号隔开)
                List<Integer> roleIds = userRolePOS.stream().map(UserRolePO::getRoleId).collect(Collectors.toList());
                QueryWrapper<SystemRolePO> roleEw = new QueryWrapper<>();
                roleEw.in("role_id", roleIds);
                roleEw.eq("locked", BetterStoreConstants.STATUS_YES);
                roleEw.select("role_id, role_Name, locked, orders");
                List<SystemRolePO> systemRolePOS = systemRoleService.list(roleEw);
                if (CollectionUtils.isNotEmpty(systemRolePOS)) {
                    principal.setRoleName(systemRolePOS.stream().map(SystemRolePO::getRoleName).collect(Collectors.joining(",")));
                }
                List<RolePermissionPO> rolePermissionPOS = rolePermissionService.list(new QueryWrapper<RolePermissionPO>().in("role_id", roleIds.toArray()));

                if (CollectionUtils.isNotEmpty(rolePermissionPOS)) {
                    Map<Integer, List<RolePermissionPO>> collect1 = rolePermissionPOS.stream().collect(Collectors.groupingBy(RolePermissionPO::getRoleId));
                    JSONObject jsonObject = new JSONObject();
                    int asInt = collect1.values().stream().mapToInt(s -> s.size()).max().getAsInt();
                    collect1.forEach((k, v) -> jsonObject.put("" + v.size(), k));
                    principal.setRoleId(jsonObject.getInteger("" + asInt));
                    permissionPOSet.addAll(systemPermissionService.listByIds(rolePermissionPOS.stream().map(RolePermissionPO::getPermissionId).collect(Collectors.toList())));
                }
                if (permissionPOSet.size() > 0) {
                    // 重新组装权限
                    Map<Integer, List<SystemPermissionPO>> collect = permissionPOSet.stream().collect(Collectors.groupingBy(SystemPermissionPO::getPid));
                    // collect.remove(0);
                    collect.forEach((k, v) -> v.sort(Comparator.comparing(SystemPermissionPO::getOrders)));
                    principal.setRole(collect);
                }
            }
            result.setData(principal);
        } catch (Exception e) {
            result.setMsg("登录服务异常，请稍后再试！");
            e.printStackTrace();
        }
        result.setMsg("验证成功！");
        return result;
    }
}
