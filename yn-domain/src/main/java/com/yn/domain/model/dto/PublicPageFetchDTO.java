package com.yn.domain.model.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

public class PublicPageFetchDTO {

    /**
     * 每页的条数
     */
    @ApiModelProperty(hidden = true)
    private Long limit = 10l;
    /**
     * 页码
     */
    @ApiModelProperty(hidden = true)
    private Long pageNo = 1l;
    /**
     * 是否升序（默认降序）
     */
    @ApiModelProperty(hidden = true)
    private Boolean isAsc = false;
    /**
     * 排序的字段
     */
    @ApiModelProperty(hidden = true)
    private String orderFiled;


    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public Long getPageNo() {
        return pageNo;
    }

    public void setPageNo(Long pageNo) {
        this.pageNo = pageNo;
    }

    @ApiModelProperty(hidden = true)
    public Boolean getAsc() {
        return isAsc;
    }

    public void setAsc(Boolean asc) {
        isAsc = asc;
    }

    public String getOrderFiled() {
        return orderFiled;
    }

    public void setOrderFiled(String orderFiled) {
        this.orderFiled = orderFiled;
    }

    /**
     * 获取分页对象
     *
     * @return
     */
    @ApiModelProperty(hidden = true)
    public Page getPage() {
        Page page = new Page();
        // 设置排序的字段
        if (StringUtils.isNotEmpty(this.getOrderFiled())) {
            if (this.isAsc) {
                page.setAsc(this.orderFiled);
            } else {
                page.setDesc(this.orderFiled);
            }
        }
        page.setCurrent(this.getPageNo());
        page.setSize(this.getLimit());
        return page;
    }

}
