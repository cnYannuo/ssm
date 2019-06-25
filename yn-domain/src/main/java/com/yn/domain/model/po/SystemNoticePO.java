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
 *
 * </p>
 *
 * @author yn
 * @since 2018-10-15
 */
@TableName("system_notice")
public class SystemNoticePO extends Model<SystemNoticePO> {

    private static final long serialVersionUID = 1L;

    public SystemNoticePO() {
        super();
    }

    public SystemNoticePO(Integer noticeId, Integer noticeType, String noticeTitle, String noticeBody, Integer noticeStatus, Long releaseTime) {
        this.noticeId = noticeId;
        this.noticeType = noticeType;
        this.noticeTitle = noticeTitle;
        this.noticeBody = noticeBody;
        this.noticeStatus = noticeStatus;
        this.releaseTime = releaseTime;
    }

    /**
     * 编号
     */
    @TableId(value = "notice_id", type = IdType.AUTO)
    private Integer noticeId;
    /**
     * 公告类型(0-系统消息，1-系统公告，2-应用公告)
     */
    @TableField("notice_type")
    private Integer noticeType;
    /**
     * 标题
     */
    @TableField("notice_title")
    private String noticeTitle;
    /**
     * 公告内容
     */
    @TableField("notice_body")
    private String noticeBody;
    /**
     * 状态
     */
    @TableField("notice_status")
    private Integer noticeStatus;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Long createTime;
    /**
     * 发布时间
     */
    @TableField("release_time")
    private Long releaseTime;


    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Long releaseTime) {
        this.releaseTime = releaseTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.noticeId;
    }

    @Override
    public String toString() {

        // ReflectionToStringBuilder 性能远大于Object toString
        // ToStringStyle 一定要设置为jsonStyle 以便于数据处理
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
