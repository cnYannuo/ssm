package com.yn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yn.service.IUserOrganizationService;
import com.yn.domain.mapper.UserOrganizationMapper;
import com.yn.domain.model.po.UserOrganizationPO;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户组织关联表 服务实现类
 * </p>
 *
 * @author yn
 * @since 2018-09-12
 */
@Service
public class UserOrganizationServiceImpl extends ServiceImpl<UserOrganizationMapper, UserOrganizationPO> implements IUserOrganizationService {

}
