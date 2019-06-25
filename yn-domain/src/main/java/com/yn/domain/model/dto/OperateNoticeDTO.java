package com.yn.domain.model.dto;

import com.yn.domain.model.po.SystemNoticePO;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @className: (公告数据传输类)
 * @date 2018/10/15 18:39:06
 */
public class OperateNoticeDTO implements Serializable {

    private Integer noticeId;

    @Max(value = 2, message = "状态只能是数字0,1,2")
    @Min(value = 0, message = "状态只能是数字0,1,2")
    private Integer noticeType;
    @Size(min = 1, max = 128, message = "请输入不超过128位的任意字符")
    private String noticeTitle;
    @NotEmpty(message = "请输入不超过4000位的任意字符")
    private String noticeBody;
    @Max(value = 2, message = "状态只能是数字0,1,2")
    @Min(value = 0, message = "状态只能是数字0,1,2")
    private Integer noticeStatus;
    private Long releaseTime;

    public SystemNoticePO getNoticePO() {
        return new SystemNoticePO(this.noticeId, this.noticeType, this.noticeTitle, this.noticeBody, this.noticeStatus, this.releaseTime);
    }

    public Integer getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(Integer noticeType) {
        this.noticeType = noticeType;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeBody() {
        return noticeBody;
    }

    public void setNoticeBody(String noticeBody) {
        this.noticeBody = noticeBody;
    }

    public Integer getNoticeStatus() {
        return noticeStatus;
    }

    public void setNoticeStatus(Integer noticeStatus) {
        this.noticeStatus = noticeStatus;
    }

    public Long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Long releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    @Override
    public String toString() {

        // ReflectionToStringBuilder 性能远大于Object toString
        // ToStringStyle 一定要设置为jsonStyle 以便于数据处理
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
