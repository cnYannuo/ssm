package com.yn.domain.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * <p>
 * 操作日志
 * </p>
 *
 * @author yn
 * @since 2018-09-12
 */
@TableName("system_log")
public class SystemLogPO extends Model<SystemLogPO> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "log_id", type = IdType.AUTO)
    private Long logId;
    /**
     * 操作描述
     */
    private String description;
    /**
     * 操作用户
     */
    @TableField("user_name")
    private String userName;
    /**
     * 操作时间
     */
    @TableField("start_time")
    private Long startTime;
    /**
     * 消耗时间
     */
    @TableField("spend_time")
    private Integer spendTime;
    /**
     * 根路径
     */
    @TableField("base_path")
    private String basePath;
    /**
     * 请求方法
     */
    private String method;
    /**
     * 请求参数
     */
    private String parameter;
    /**
     * 用户标识
     */
    @TableField("user_agent")
    private String userAgent;
    /**
     * IP地址
     */
    private String ip;
    /**
     * 请求结果
     */
    private String result;


    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Integer getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(Integer spendTime) {
        this.spendTime = spendTime;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    protected Serializable pkVal() {
        return this.logId;
    }

    @Override
    public String toString() {

        // ReflectionToStringBuilder 性能远大于Object toString
        // ToStringStyle 一定要设置为jsonStyle 以便于数据处理
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
