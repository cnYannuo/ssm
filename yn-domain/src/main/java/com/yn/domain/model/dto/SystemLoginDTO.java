package com.yn.domain.model.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @className: (系统用户登录的数据传输对象)
 * @date 2018/09/12 17:06:32
 */
public class SystemLoginDTO implements Serializable {

//	private String sessionId;

    @NotEmpty(message = "请输入用户名")
    @Pattern(regexp = "^[\\u4E00-\\u9FA5|\\w]{2,16}$", message = "不合法的登录用户名")
    private String userName;
    @Size(max = 16, min = 6, message = "登录密码必须是大于4小于等于16的字符")
    private String userPwd;

    @Min(value = 4, message = "验证码格式错误！")
    private String verCode;

	/*public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}*/

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }

    @Override
    public String toString() {
        // ReflectionToStringBuilder 性能远大于Object toString
        // ToStringStyle 一定要设置为jsonStyle 以便于数据处理
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
