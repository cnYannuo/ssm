package com.yn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yn.service.IOrganizationService;
import com.yn.domain.mapper.OrganizationMapper;
import com.yn.domain.model.po.OrganizationPO;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 组织 服务实现类
 * </p>
 *
 * @author yn
 * @since 2018-09-12
 */
@Service
public class OrganizationServiceImpl extends ServiceImpl<OrganizationMapper, OrganizationPO> implements IOrganizationService {

}
