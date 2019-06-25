package com.yn.common.entity;

import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @param <T>
 * @Description: (暴露web接口返回参数基础类)
 * @date 2017年9月6日 下午5:16:45
 */
public class GlobalResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiParam(hidden = true)
    private String code;

    @ApiParam(hidden = true)
    private boolean success = false;

    @ApiParam(hidden = true)
    private String msg;

    @ApiParam(hidden = true)
    private T data;

    @ApiParam(hidden = true)
    private String xml;

    @ApiParam(hidden = true)
    private String url;

    @ApiParam(hidden = true)
    private long pageNo = 1;

    @ApiParam(hidden = true)
    private long pageSize = 10;

    @ApiParam(hidden = true)
    private long pages = 0;

    @ApiParam(hidden = true)
    private long totalSize = 0;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getXml() {
        return xml;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getPageNo() {
        return pageNo;
    }

    public void setPageNo(long pageNo) {
        this.pageNo = pageNo;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    @Override
    public String toString() {
        // ReflectionToStringBuilder 性能远大于Object toString
        // ToStringStyle 一定要设置为jsonStyle 以便于数据处理
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}