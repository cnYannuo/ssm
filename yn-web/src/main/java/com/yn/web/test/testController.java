package com.yn.web.test;

import com.yn.common.entity.GlobalResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api(value="测试controller",tags={"测试操作接口"})
@RestController
@RequestMapping("/")
public class testController {

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "hello", notes = "若成功则返回hello")
    public Object hello(GlobalResult result) {
        result.setSuccess(true);
        result.setMsg("hello!");
        return result;
    }
}
