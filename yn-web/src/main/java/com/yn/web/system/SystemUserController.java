package com.yn.web.system;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.SystemClock;
import com.yn.service.ISystemUserService;
import com.yn.common.entity.BaseController;
import com.yn.common.entity.BetterStoreConstants;
import com.yn.common.entity.GlobalResult;
import com.yn.domain.model.bo.Principal;
import com.yn.domain.model.dto.FetchUserListDTO;
import com.yn.domain.model.dto.OperateUserDTO;
import com.yn.domain.model.dto.SystemLoginDTO;
import com.yn.domain.model.po.SystemUserPO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.yn.common.utils.VerifyCodeUtils;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author yn
 * @since 2018-09-12
 */
@RestController
@Api(value="用户controller",tags={"用户操作接口"})
@RequestMapping("/yn/user")
public class SystemUserController extends BaseController {

    private static final Log log = LogFactory.getLog(SystemUserController.class);


    @Autowired
    private ISystemUserService systemUserService;

    /*@RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(@Validated SystemLoginDTO loginDTO, Errors errors, GlobalResult result) {
//		getSession().removeAttribute("user");
        if (errors.hasErrors()) {
            result.setMsg(errors.getFieldError().getDefaultMessage());
            return result;
        }
		*//*if(loginDTO.getSessionId().equals(getSession().getId())){//通过判断sessionId来判断是否为本人
			result.setSuccess(true);
			return result;
		}*//*
        Object verCode = getSession().getAttribute("verCode");
        if (null == verCode || false == loginDTO.getVerCode().equals(verCode)) {
            result.setMsg("验证码错误！");
            return result;
        }
        getSession().removeAttribute("verCode");
        try {
            result = systemUserService.login(loginDTO, result);
            //登录控制
            if (getSession().getAttribute(((Principal) result.getData()).getUserId().toString()) != null) {
                result.setSuccess(false);
                result.setData(null);//不能传数据过去
                result.setMsg("该账号正在使用中!");
                return result;
            }
            if (result.isSuccess()) {
//				getSession().setAttribute("user", result.getData());
                getSession().setAttribute(((Principal) result.getData()).getUserId().toString(), result.getData());//保存ID
            }
        } catch (Exception e) {
            result.setMsg("登录服务异常，请稍后再试!");
            log.error(e);
        }
        return result;
    }*/

    @RequestMapping(value = "verCode", method = RequestMethod.GET)
    @ApiOperation(value = "verCode", notes = "获取验证码")
    public void getVerCode(@RequestParam(defaultValue = "100") Integer w,
                           @RequestParam(defaultValue = "50") Integer h) {

        getResponse().setHeader("Pragma", "No-cache");
        getResponse().setHeader("Cache-Control", "no-cache");
        getResponse().setDateHeader("Expires", 0);
        getResponse().setContentType("image/jpeg");
        getResponse().setCharacterEncoding("UTF-8");
        try {
            if (w == 0) {
                w = 100;
            }
            if (h == 0) {
                h = 50;
            }
            if (w > 100 || h > 60) {
                getResponse().setContentType("text/plain");
                getResponse().setCharacterEncoding("UTF-8");
                getResponse().getWriter().print("获取验证码失败");
                return;
            }
            String verCode = VerifyCodeUtils.generateVerifyCode(4);
            getSession().setAttribute("verCode", verCode);
            // 生成图片
            VerifyCodeUtils.outputImage(w, h, getResponse().getOutputStream(), verCode);
        } catch (IOException e) {
            log.error(e);
        }
    }

    @RequestMapping(value = "fetchUserList", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "fetchUserList", notes = "获取批量用户信息")
    public Object fetchUserList(FetchUserListDTO fetch, GlobalResult result) {
        QueryWrapper<SystemUserPO> ew = new QueryWrapper<>();
        SystemUserPO systemUserPO = new SystemUserPO();
        BeanUtils.copyProperties(fetch, systemUserPO);
        ew.setEntity(systemUserPO);
        if (StringUtils.isNotEmpty(fetch.getUserName())) {
            ew.like("user_name", fetch.getUserName());
            ew.getEntity().setUserName(null);
        }
        if (fetch.getCreateTime() != null) {
            ew.ge("create_time", fetch.getCreateTime());
            ew.getEntity().setCreateTime(null);
            if (fetch.getEndTime() == null) {
                // 默认结束时间为第二天凌晨
                ew.lt("create_time", DateUtils.addDays(new Date(fetch.getCreateTime()), BetterStoreConstants.RANGE_ONE_DAY).getTime());
            }
        }
        if (fetch.getEndTime() != null) {
            ew.le("create_time", fetch.getEndTime());
        }
        ew.select("user_id,user_name,real_name,avatar,phone,email,sex,locked,create_time");
        try {
//            PublicUtils.pageToResult(result, systemUserService.page(fetch.getPage(), ew));
        } catch (Exception e) {
            log.error(e);
        }
        return result;
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "logout", notes = "用户登出")
    public Object logout(Integer userId, GlobalResult result) {
        getSession().removeAttribute(userId.toString());
        result.setSuccess(true);
        return result;
    }

    @RequestMapping(value = "operateUser", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "operateUser", notes = "新建用户")
    public Object operateUser(@Validated OperateUserDTO user, GlobalResult result, Errors errors) {
        if (errors.hasErrors()) {
            result.setMsg(errors.getFieldError().getDefaultMessage());
            return result;
        }
        // 保存
        if (user.getUserId() == null) {
            try {
                SystemUserPO userPO = user.getUserPO();
                userPO.setCreateTime(SystemClock.now());
                userPO.setSalt(RandomUtil.randomString(6));
                userPO.setLocked(BetterStoreConstants.STATUS_YES);
                userPO.setPassWord(DigestUtils.md5Hex(user.getPassWord() + userPO.getSalt()));
                if (userPO.insert()) {
                    result.setSuccess(true);
                } else {
                    result.setMsg(BetterStoreConstants.OPERATE_ERROR_MSG);
                }
            } catch (Exception e) {
                result.setMsg((BetterStoreConstants.OPERATE_SERVER_ERROR_MSG));
                log.error(e);
            }
        } else {
            result.setMsg(BetterStoreConstants.OPERATE_ERROR_MSG);
        }
        return result;
    }

    @RequestMapping(value = "editUser", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "editUser", notes = "修改用户")
    public Object editUser(OperateUserDTO user, GlobalResult result) {
        if (user != null && user.getUserId() != null) {
            try {
                // 修改
                if (user.getUserPO().updateById()) {
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

    @RequestMapping(value = "patchDelUsers", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "patchDelUsers", notes = "批量逻辑删除用户")
    public Object patchDelUsers(@RequestBody List<SystemUserPO> users, GlobalResult result) {
        if (CollectionUtils.isNotEmpty(users)) {
            try {
                if (systemUserService.updateBatchById(users)) {
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
}

