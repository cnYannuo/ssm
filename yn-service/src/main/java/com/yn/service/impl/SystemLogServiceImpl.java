package com.yn.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yn.service.ISystemLogService;
import com.yn.domain.mapper.SystemLogMapper;
import com.yn.domain.model.po.SystemLogPO;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志 服务实现类
 * </p>
 *
 * @author yn
 * @since 2018-09-12
 */
@Service
public class SystemLogServiceImpl extends ServiceImpl<SystemLogMapper, SystemLogPO> implements ISystemLogService {

}
