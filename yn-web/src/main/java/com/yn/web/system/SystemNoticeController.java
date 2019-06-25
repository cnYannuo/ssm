package com.yn.web.system;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.SystemClock;
import com.yn.annotation.SystemControllerLog;
import com.yn.common.entity.BaseController;
import com.yn.common.entity.BetterStoreConstants;
import com.yn.common.entity.GlobalResult;
import com.yn.common.utils.PublicUtils;
import com.yn.domain.model.dto.FetchNoticeListDTO;
import com.yn.domain.model.dto.OperateNoticeDTO;
import com.yn.domain.model.po.SystemNoticePO;
import com.yn.service.ISystemNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yn
 * @since 2018-10-15
 */
@Api(value="公告通知controller",tags={"公告通知操作接口"})
@RestController
@RequestMapping("/notice")
public class SystemNoticeController extends BaseController {

    private final static Log log = LogFactory.getLog(SystemNoticeController.class);
    @Autowired
    private ISystemNoticeService noticeService;

    @SystemControllerLog(modular = "notice", description = "查询公告列表")
    @ApiOperation(value = "notice", notes = "查询公告列表")
    @RequestMapping(value = "fetchNoticeList", method = RequestMethod.POST)
    public Object fetchNoticeList(FetchNoticeListDTO notice, GlobalResult result) {
        QueryWrapper<SystemNoticePO> ew = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(notice.getNoticeTitle())) {
            ew.like("notice_title", notice.getNoticeTitle());
        }
        if (notice.getNoticeType() != null) {
            ew.eq("notice_type", notice.getNoticeType());
        }
        if (notice.getNoticeStatus() != null) {
            ew.eq("notice_status", notice.getNoticeStatus());
        }
        if (notice.getCreateTime() != null) {
            ew.ge("create_time", notice.getCreateTime());
            if (notice.getEndTime() == null) {
                // 默认结束时间为第二天凌晨
                ew.lt("create_time", DateUtils.addDays(new Date(notice.getCreateTime()), BetterStoreConstants.RANGE_ONE_DAY).getTime());
            }
        }
        if (notice.getEndTime() != null) {
            ew.le("create_time", notice.getEndTime());
        }
        try {
            PublicUtils.pageToResult(result, noticeService.page(notice.getPage(), ew));
        } catch (Exception e) {
            log.error(e);
        }
        return result;
    }

    @RequestMapping(value = "addNotice", method = RequestMethod.POST)
    @ApiOperation(value = "addNotice", notes = "添加公告列表")
    public Object addNotice(@Validated OperateNoticeDTO notice, Errors errors, GlobalResult result) {
        if (errors.hasErrors()) {
            result.setMsg(errors.getFieldError().getDefaultMessage());
            return result;
        }
        if (notice.getNoticeId() != null) {
            result.setMsg(BetterStoreConstants.OPERATE_ERROR_MSG);
            return result;
        }
        if (notice.getNoticeStatus() == BetterStoreConstants.STATUS_YES && notice.getReleaseTime() == null) {
            result.setMsg("预约发布时间不能为空");
            return result;
        }
        SystemNoticePO noticePO = notice.getNoticePO();
        if (notice.getNoticeStatus() == BetterStoreConstants.STATUS_NO) {
            noticePO.setReleaseTime(SystemClock.now());
        }
        noticePO.setCreateTime(noticePO.getReleaseTime());
        try {
            if (noticePO.insert()) {
                result.setSuccess(true);
            } else {
                result.setMsg(BetterStoreConstants.OPERATE_ERROR_MSG);
            }
        } catch (Exception e) {
            result.setMsg(BetterStoreConstants.OPERATE_SERVER_ERROR_MSG);
            log.error(e);
        }
        return result;
    }

    @RequestMapping(value = "editNotice", method = RequestMethod.POST)
    @ApiOperation(value = "editNotice", notes = "编辑公告")
    public Object editNotice(OperateNoticeDTO notice, GlobalResult result) {
        if (notice == null || notice.getNoticeId() == null) {
            result.setMsg(BetterStoreConstants.OPERATE_ERROR_MSG);
            return result;
        }
        try {
            if (notice.getNoticePO().updateById()) {
                result.setSuccess(true);
            } else {
                result.setMsg(BetterStoreConstants.OPERATE_ERROR_MSG);
            }
        } catch (Exception e) {
            result.setMsg(BetterStoreConstants.OPERATE_SERVER_ERROR_MSG);
            log.error(e);
        }
        return result;
    }

    @RequestMapping(value = "patchDelNotice", method = RequestMethod.POST)
    @ApiOperation(value = "patchDelNotice", notes = "批量编辑公告")
    public Object patchDelNotice(@RequestBody List<SystemNoticePO> notices, GlobalResult result) {
        if (CollectionUtils.isNotEmpty(notices)) {
            try {
                if (noticeService.updateBatchById(notices)) {
                    result.setSuccess(true);
                } else {
                    result.setMsg(BetterStoreConstants.OPERATE_ERROR_MSG);
                }
            } catch (Exception e) {
                result.setMsg(BetterStoreConstants.OPERATE_SERVER_ERROR_MSG);
                log.error(e);
            }
        } else {
            result.setMsg(BetterStoreConstants.OPERATE_ERROR_MSG);
        }
        return result;
    }
}

