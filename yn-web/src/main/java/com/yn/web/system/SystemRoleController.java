package com.yn.web.system;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.SystemClock;
import com.yn.common.entity.BaseController;
import com.yn.common.entity.BetterStoreConstants;
import com.yn.common.entity.GlobalResult;
import com.yn.common.exception.ApiException;
import com.yn.common.utils.PublicUtils;
import com.yn.domain.model.bo.Principal;
import com.yn.domain.model.dto.FetchRoleListDTO;
import com.yn.domain.model.dto.OperateRoleDTO;
import com.yn.domain.model.po.RolePermissionPO;
import com.yn.domain.model.po.SystemRolePO;
import com.yn.domain.model.po.UserRolePO;
import com.yn.service.IRolePermissionService;
import com.yn.service.ISystemPermissionService;
import com.yn.service.ISystemRoleService;
import com.yn.service.IUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 系统角色 前端控制器
 * </p>
 *
 * @author yn
 * @since 2018-09-12
 */
@Api(value="角色controller",tags={"角色操作接口"})
@RequestMapping("/role")
@RestController
public class SystemRoleController extends BaseController {
    private static final Log log = LogFactory.getLog(SystemRoleController.class);

    @Autowired
    private ISystemRoleService roleService;
    @Autowired
    private ISystemPermissionService permissionService;
    @Autowired
    private IRolePermissionService rolePermissionService;
    @Autowired
    private IUserRoleService userRoleService;

    @RequestMapping(value = "fetchRoleList", method = RequestMethod.POST)
    @ApiOperation(value = "fetchRoleList", notes = "查询角色列表")
    public Object fetchRoleList(FetchRoleListDTO roleQuery, GlobalResult result) {
        QueryWrapper<SystemRolePO> ew = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(roleQuery.getRoleName())) {
            ew.like("role_name", roleQuery.getRoleName());
        }
        if (roleQuery.getLocked() != null) {
            ew.eq("locked", roleQuery.getLocked());
        }
        if (roleQuery.getCreateTime() != null) {
            ew.ge("create_time", roleQuery.getCreateTime());
        }
        if (roleQuery.getEndTime() != null) {
            ew.le("create_time", roleQuery.getEndTime());
        }
        try {
            PublicUtils.pageToResult(result, roleService.page(roleQuery.getPage(), ew));
        } catch (Exception e) {
            log.error(e);
        } finally {
            ew = null;
        }
        return result;
    }

    @RequestMapping(value = "addRole", method = RequestMethod.POST)
    @ApiOperation(value = "addRole", notes = "添加角色")
    public Object addRole(@Validated OperateRoleDTO role, Errors errors, GlobalResult result) {
        if (errors.hasErrors()) {
            result.setMsg(errors.getFieldError().getDefaultMessage());
            return result;
        }
        if (role.getRoleId() == null) {
            SystemRolePO rolePO = role.getRolePO();
            rolePO.setCreateTime(SystemClock.now());
            try {
                if (rolePO.insert()) {
                    result.setSuccess(true);
                } else {
                    result.setMsg(BetterStoreConstants.OPERATE_ERROR_MSG);
                }
            } catch (Exception e) {
                result.setMsg(BetterStoreConstants.OPERATE_SERVER_ERROR_MSG);
                log.error(e);
            } finally {
                role = null;
                rolePO = null;
            }
        } else {
            result.setMsg(BetterStoreConstants.OPERATE_ERROR_MSG);
        }
        return result;
    }

    @PostMapping(value = "editRole")
    @ApiOperation(value = "editRole", notes = "编辑角色")
    public Object editRole(@RequestBody OperateRoleDTO role, GlobalResult result) {
        if (role == null || role.getRoleId() == null) {
            result.setMsg(BetterStoreConstants.OPERATE_ERROR_MSG);
            return result;
        }
        try {
            if (role.getRolePO().updateById()) {
                result.setSuccess(true);
            } else {
                result.setMsg(BetterStoreConstants.OPERATE_ERROR_MSG);
            }
        } catch (Exception e) {
            result.setMsg(BetterStoreConstants.OPERATE_SERVER_ERROR_MSG);
            log.error(e);
        }
        return result;
    }

    @RequestMapping(value = "fetchRPTree", method = RequestMethod.GET)
    @ApiOperation(value = "fetchRPTree", notes = "查询角色权限或所有权限")
    public Object fetchRPTree(@RequestParam boolean onlyAll, @RequestParam(required = false) Integer roleId,
                              @RequestParam(required = false, defaultValue = "false") boolean checkBox, GlobalResult result) {
        try {
            if (onlyAll == false && roleId == null) {
                Principal user = (Principal) getRequest().getSession().getAttribute("user");
                roleId = user.getRoleId();
            }
            result.setData(permissionService.queryAllPermissionTree(onlyAll, roleId, checkBox));
            result.setSuccess(true);
        } catch (Exception e) {
            log.error(e);
        }
        return result;
    }

    @RequestMapping(value = "patchDelRoles", method = RequestMethod.POST)
    @ApiOperation(value = "patchDelRoles", notes = "批量更新角色")
    public Object patchDelRoles(@RequestBody List<SystemRolePO> roles, GlobalResult result) {
        if (CollectionUtils.isNotEmpty(roles)) {
            try {
                if (roleService.updateBatchById(roles)) {
                    result.setSuccess(true);
                } else {
                    result.setMsg(BetterStoreConstants.OPERATE_ERROR_MSG);
                }
            } catch (Exception e) {
                result.setMsg(BetterStoreConstants.OPERATE_SERVER_ERROR_MSG);
                log.error(e);
            }
        } else {
            result.setMsg(BetterStoreConstants.OPERATE_ERROR_MSG);
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "setRolePermissions", notes = "设置角色权限")
    @RequestMapping(value = "setRolePermissions", method = RequestMethod.POST)
    public Object setRolePermissions(@RequestBody List<RolePermissionPO> rps, GlobalResult result) {
        if (CollectionUtils.isNotEmpty(rps)) {
            if (rolePermissionService.remove(new QueryWrapper<RolePermissionPO>().eq("role_id", rps.get(0).getRoleId()))) {
                if (rolePermissionService.saveBatch(rps)) {
                    result.setSuccess(true);
                } else {
                    throw new ApiException(BetterStoreConstants.OPERATE_SERVER_ERROR_MSG);
                }
            } else {
                result.setMsg(BetterStoreConstants.OPERATE_ERROR_MSG);
            }
        } else {
            result.setMsg(BetterStoreConstants.OPERATE_ERROR_MSG);
        }
        return result;
    }

    @RequestMapping(value = "fetchRolesByTransfer", method = RequestMethod.GET)
    @ApiOperation(value = "fetchRolesByTransfer", notes = "查询权限，fetchStatus=1则只查询系统权限")
    public Object fetchRolesByTransfer(Integer fetchStatus, Integer userId, GlobalResult result) {
        if (fetchStatus != null) {
            try {
                // 查询（用户的和系统的）
                if (fetchStatus == 0) {
                    JSONObject object = new JSONObject();
                    object.put("userRole", roleService.queryRoles(true, userId));
                    object.put("sysRole", roleService.queryRoles(false, null));
                    result.setData(object);
                    result.setSuccess(true);
                } else if (fetchStatus == 1) {// 系统
                    result.setData(roleService.queryRoles(false, null));
                    result.setSuccess(true);
                } else {
                    result.setData(roleService.queryRoles(true, userId));
                    result.setSuccess(true);
                }
            } catch (Exception e) {
                result.setMsg(BetterStoreConstants.OPERATE_SERVER_ERROR_MSG);
                log.error(e);
            }
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "addUserRoles", method = RequestMethod.POST)
    @ApiOperation(value = "addUserRoles", notes = "为角色添加权限")
    public Object addUserRoles(@RequestBody List<UserRolePO> userRole, GlobalResult result) {
        if (CollectionUtils.isNotEmpty(userRole)) {
            if (userRoleService.remove(new QueryWrapper<UserRolePO>().eq("user_id", userRole.get(0).getUserId()))) {
                if (userRoleService.saveBatch(userRole)) {
                    result.setSuccess(true);
                } else {
                    throw new ApiException(BetterStoreConstants.OPERATE_ERROR_MSG);
                }
            } else {
                result.setMsg(BetterStoreConstants.OPERATE_ERROR_MSG);
            }
        } else {
            result.setMsg(BetterStoreConstants.OPERATE_ERROR_MSG);
        }
        return result;
    }

}
