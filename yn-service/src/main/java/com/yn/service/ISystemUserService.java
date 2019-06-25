package com.yn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yn.common.entity.GlobalResult;
import com.yn.domain.model.dto.SystemLoginDTO;
import com.yn.domain.model.po.SystemUserPO;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @since 2018-09-12
 */
public interface ISystemUserService extends IService<SystemUserPO> {
    GlobalResult login(SystemLoginDTO loginDTO, GlobalResult result);
}
