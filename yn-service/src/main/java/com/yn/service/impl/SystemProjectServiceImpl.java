package com.yn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yn.service.ISystemProjectService;
import com.yn.domain.mapper.SystemProjectMapper;
import com.yn.domain.model.po.SystemProjectPO;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统 服务实现类
 * </p>
 *
 * @author yn
 * @since 2018-09-12
 */
@Service
public class SystemProjectServiceImpl extends ServiceImpl<SystemProjectMapper, SystemProjectPO> implements ISystemProjectService {

}
