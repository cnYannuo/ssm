package com.yn.common.utils;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yn.common.entity.GlobalResult;

public class PublicUtils {

    /**
     * 分页结果转换
     *
     * @param result 接口返回对象
     * @param page   分页结果对象
     * @return GlobalResult
     */
    public static void pageToResult(GlobalResult<Object> result, IPage page) {
        if (page != null) {
            result.setSuccess(true);
            result.setData(page.getRecords());
            result.setPageNo(page.getCurrent());
            result.setPageSize(page.getSize());
            result.setPages(page.getPages());
            result.setTotalSize(page.getTotal());
        }
    }
}
