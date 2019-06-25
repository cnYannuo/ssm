package com.yn.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yn.service.IRolePermissionService;
import com.yn.service.ISystemPermissionService;
import com.yn.domain.mapper.SystemPermissionMapper;
import com.yn.domain.model.bo.PermissionTreeVO;
import com.yn.domain.model.po.RolePermissionPO;
import com.yn.domain.model.po.SystemPermissionPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限菜单 服务实现类
 * </p>
 *
 * @author yn
 * @since 2018-09-12
 */
@Service
public class SystemPermissionServiceImpl extends ServiceImpl<SystemPermissionMapper, SystemPermissionPO> implements ISystemPermissionService {

    @Autowired
    private IRolePermissionService rolePermissionService;

    @Override
    public PermissionTreeVO[] queryAllPermissionTree(boolean onlyAll, Integer roleId, boolean checkBox) {
        List<SystemPermissionPO> pLst = null;
        if (onlyAll) {
            pLst = this.list(null);
        } else {
            // 根据角色id查询
            List<RolePermissionPO> roleIds = rolePermissionService.list(new QueryWrapper<RolePermissionPO>().eq("role_id", roleId));
            if (CollectionUtils.isNotEmpty(roleIds)) {
                pLst = (List<SystemPermissionPO>) this.listByIds(roleIds.stream().map(RolePermissionPO::getPermissionId).collect(Collectors.toList()));
            }
        }
        PermissionTreeVO[] pArray = null;
        // 分组权限
        if (CollectionUtils.isNotEmpty(pLst)) {
            if (checkBox) {
                for (SystemPermissionPO p : pLst) {
                    pArray = ArrayUtil.append(pArray, new PermissionTreeVO(p));
                }
                return pArray;
            } else {
                pArray = new PermissionTreeVO[1];
                Map<Integer, List<SystemPermissionPO>> collect = pLst.stream().collect(Collectors.groupingBy(SystemPermissionPO::getPid));
                // 获取根
                SystemPermissionPO spRoot = collect.get(0).get(0);
                PermissionTreeVO pt = new PermissionTreeVO(spRoot);
                List<PermissionTreeVO> childs = new ArrayList<>();
                // 一级菜单
                List<SystemPermissionPO> spRootChildOne = collect.get(spRoot.getPermissionId());
                for (SystemPermissionPO sp : spRootChildOne) {
                    childs.add(setUpPermission(sp, collect));
                }
                pt.setChildren(childs);
                pArray[0] = pt;
            }
        }
        return pArray;
    }

    public PermissionTreeVO setUpPermission(SystemPermissionPO p, Map<Integer, List<SystemPermissionPO>> collect) {
        PermissionTreeVO pTree = new PermissionTreeVO(p);
        List<SystemPermissionPO> pLst = collect.get(p.getPermissionId());
        if (CollectionUtils.isNotEmpty(pLst)) {
            List<PermissionTreeVO> newPlst = new ArrayList<>();
            for (SystemPermissionPO np : pLst) {
                PermissionTreeVO npTree = new PermissionTreeVO(np);
                if (collect.get(np.getPermissionId()) == null) {
                    newPlst.add(npTree);
                    continue;
                } else {
                    PermissionTreeVO pt = setUpPermission(np, collect);
                    if (pt != null) {
                        npTree.getChildren().add(pt);
                    }
                    newPlst.add(npTree);
                }
            }
            pTree.setChildren(newPlst);
        }
        return pTree;
    }
}
