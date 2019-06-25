package com.yn.web.global;

import com.alibaba.fastjson.JSONObject;
import com.yn.common.entity.BaseController;
import com.yn.common.entity.BetterStoreConstants;
import com.yn.common.entity.GlobalError;
import com.yn.common.entity.GlobalResult;
import com.yn.common.exception.ApiException;
import com.yn.common.exception.BusinessException;
import io.swagger.annotations.Api;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@RequestMapping("/")
@ControllerAdvice
@Api(value="测试controller",tags={"测试操作接口"}, hidden = true)
public class GlobalExceptionHandler extends BaseController {
    private GlobalError globalError = null;
    private GlobalResult resultPage = null;

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object exceptionHandler(Exception exception) {
        // 可以自定义response的status或者做其他的操作
        HttpServletResponse response = getResponse();
        exception.printStackTrace();
        // log.error(com.yn.common.exception.getMessage());
        // 业务异常处理
        if (exception instanceof BusinessException) {
            globalError = new GlobalError();
            response.setStatus(581);
            BusinessException businessException = (BusinessException) exception;
            globalError.setCode(businessException.getMsgCode());
            globalError.setMessage(businessException.getMsg());
            globalError.setParams(businessException.getParams());
            globalError.setParamsArgs(businessException.getParamArgs());
        } else if (exception instanceof HttpRequestMethodNotSupportedException) {
            globalError = new GlobalError();
            globalError.setCode(BetterStoreConstants.SYSTEM_ERROR_CODE);
            globalError.setMessage(exception.getMessage());
            globalError.setParams(JSONObject.toJSONString(getParamMapFromRequest(getRequest())));
        } else if (exception instanceof ApiException) {
            ApiException apiException = (ApiException) exception;
            resultPage = new GlobalResult();
            resultPage.setMsg(apiException.getMsg());
            resultPage.setCode(apiException.getMsgCode());
            return resultPage;
        } else {
            globalError = new GlobalError();
            response.setStatus(500);
            globalError.setCode(BetterStoreConstants.SYSTEM_ERROR_CODE);
            globalError.setMessage("系统异常");
        }
        return globalError;
    }
}
