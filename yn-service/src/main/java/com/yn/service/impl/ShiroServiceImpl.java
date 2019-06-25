package com.yn.service.impl;

import com.yn.service.ShiroService;
import com.yn.domain.mapper.ShiroMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by MIfengHe on 2017/10/23.
 */
@Service
public class ShiroServiceImpl implements ShiroService {

    @Autowired private ShiroMapper shiroNMapper;

    @Override
    @Transactional(readOnly = true)
    public List<String> getRoles(String principal) {
        return shiroNMapper.getRoles(principal);
    }
}
//    @Override
//    @Transactional(readOnly = true)
//    public List<String> getPerms(String principal) {
//        return shiroNMapper.getPerms(principal);
//    }

//    @Override
//    @Transactional(readOnly = true)
//    public List<ShiroChainBean> getShiroPermssionChainBeans() {
//        return shiroNMapper.getShiroPermssionChainBeans();
//    }

    /**
     * 演示方法
     * @param params
     * @return
     */
//    public List<SysMenu> testPageQuery(Map<String, Object> params) {
//        // 前台传参数 pageNo pageSize
//        NPageHelper.startPage(params);
//        Example example = new Example(SysMenu.class);
//        NQueryHelper.create(example.createCriteria(), params)
//                .andLike("menuName", "id")
//                .andEqualTo("id", "id1")
//                .andGreaterThanOrEqualTo("instDate", "startDate")
//                .andLessThanOrEqualTo("instDate", "endDate");
//
//        return SpringContextHolder.getBean(SysMenuMapper.class).selectByExample(example);
//    }
//}
