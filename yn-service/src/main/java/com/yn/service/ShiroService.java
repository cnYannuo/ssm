package com.yn.service;

import java.util.List;


public interface ShiroService {
	/**
     * 获取角色
     * @return
     */
    List<String> getRoles(String principal);

    /**
     * 获取许可
     * @return
     */
   /* List<String> getPerms(String principal);*/

    /**
     * 获取过滤器定义
     * @return
     */
//    List<ShiroChainBean> getShiroPermssionChainBeans();
}
