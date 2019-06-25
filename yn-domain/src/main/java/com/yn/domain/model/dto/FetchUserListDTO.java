package com.yn.domain.model.dto;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @className: (获取用户信息的查询条件)
 * @date 2018/09/25 15:32:52
 */
public class FetchUserListDTO extends PublicPageFetchDTO {

    public FetchUserListDTO() {
        super();
    }

    private String userName;
    private String phone;
    private Integer sex;
    private Integer locked;
    private Long createTime;
    private Long endTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {

        // ReflectionToStringBuilder 性能远大于Object toString
        // ToStringStyle 一定要设置为jsonStyle 以便于数据处理
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
