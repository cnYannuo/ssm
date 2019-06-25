package com.yn.domain.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.yn.domain.model.po.SystemUserPO;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @className: (操作系统用户数据传输类)
 * @date 2018/10/13 15:36:53
 */
@ApiModel
public class OperateUserDTO implements Serializable {
    /**
     * 编号
     */
    private Integer userId;
    /**
     * 帐号
     */
    @Pattern(regexp = "^[\\u4E00-\\u9FA5|\\w]{2,16}$", message = "请输入长度为2-16的中文或者字母组成的字符")
    @ApiModelProperty(value = "帐号")
    private String userName;
    /**
     * 密码MD5(密码+盐)
     */
    @ApiModelProperty(value = "密码")
    @Size(min = 6, max = 16, message = "请输入长度为6-16的任意字符")
    private String passWord;
    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    @Pattern(regexp = "^[\\u4E00-\\u9FA5|\\w]{2,16}$", message = "请输入长度为2-16的中文或者字母组成的字符")
    private String realName;
    /**
     * 头像
     */
    @ApiModelProperty(value = "头像", hidden = true)
    private String avatar;
    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    @Pattern(regexp = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$|^0\\d{2,3}-?\\d{7,8}$"
            , message = "请输入11位的手机号码或者标准的固定电话")
    private String phone;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    @Pattern(regexp = "(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))", message = "请输入合法的邮箱")
    private String email;
    /**
     * 性别(0-男；1-女)
     */
    @ApiModelProperty(value = "性别(0-男；1-女)")
    @Max(value = 1, message = "性别只能是数字0或者1")
    @Min(value = 0, message = "性别只能是数字0或者1")
    private Integer sex;
    /**
     * 状态(0:正常,1:锁定)
     */
    @ApiModelProperty(hidden = true)
    @Max(value = 1, message = "状态只能是数字0或者1")
    @Min(value = 0, message = "状态只能是数字0或者1")
    private Integer locked;
    /**
     * 专业
     */
    @ApiModelProperty(value = "专业")
    private String major;

    /**
     * 具体地点
     */
    @ApiModelProperty(value = "具体地点")
    private String workPlace;

    /**
     * 国家
     */
    @ApiModelProperty(value = "国家")
    private String country;

    /**
     * 省
     */
    @ApiModelProperty(value = "省")
    private String province;

    /**
     * 市
     */
    @ApiModelProperty(value = "市")
    private String city;

    /**
     * 县/地区
     */
    @ApiModelProperty(value = "县/地区")
    private String county;

    @ApiModelProperty(hidden = true)
    public SystemUserPO getUserPO() {
        return new SystemUserPO(this.userId, this.userName, this.passWord, this.realName, this.avatar, this.phone, this.email, this.sex, this.locked, this.major, this.workPlace, this.city, this.country, this.county, this.province);
    }

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
    public String toString() {
        // ReflectionToStringBuilder 性能远大于Object toString
        // ToStringStyle 一定要设置为jsonStyle 以便于数据处理
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
