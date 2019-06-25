package com.yn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yn.service.ISystemNoticeService;
import com.yn.domain.mapper.SystemNoticeMapper;
import com.yn.domain.model.po.SystemNoticePO;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yn
 * @since 2018-10-15
 */
@Service
public class SystemNoticeServiceImpl extends ServiceImpl<SystemNoticeMapper, SystemNoticePO> implements ISystemNoticeService {

}
