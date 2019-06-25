package com.yn.domain.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

@TableName("system_user")
public class SystemUserPO extends Model<SystemUserPO> {

    public SystemUserPO() {
        super();
    }

    public SystemUserPO(Integer userId, String userName, String passWord, String realName, String avatar, String phone, String email, Integer sex, Integer locked, String major, String workPlace, String city, String country, String county, String province) {
        this.userId = userId;
        this.userName = userName;
        this.passWord = passWord;
        this.realName = realName;
        this.avatar = avatar;
        this.phone = phone;
        this.email = email;
        this.sex = sex;
        this.locked = locked;
        this.major = major;
        this.workPlace = workPlace;
        this.city = city;
        this.country = country;
        this.county = county;
        this.province = province;
    }

    /**
     * 编号
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;
    /**
     * 帐号
     */
    @TableField("user_name")
    private String userName;
    /**
     * 密码MD5(密码+盐)
     */
    @TableField("pass_word")
    private String passWord;
    /**
     * 盐
     */
    private String salt;
    /**
     * 姓名
     */
    @TableField("real_name")
    private String realName;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 电话
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 性别(0-男；1-女)
     */
    private Integer sex;
    /**
     * 状态(0:正常,1:锁定)
     */
    private Integer locked;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Long createTime;
    /**
     * 专业
     */
    private String major;

    /**
     * 具体地点
     */
    private String workPlace;

    /**
     * 国家
     */
    private String country;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 县/地区
     */
    private String county;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

    @Override
    public String toString() {
        // ReflectionToStringBuilder 性能远大于Object toString
        // ToStringStyle 一定要设置为jsonStyle 以便于数据处理
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
