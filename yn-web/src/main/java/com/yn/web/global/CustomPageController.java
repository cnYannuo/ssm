package com.yn.web.global;

import com.yn.common.entity.BaseController;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@Api(value="测试controller",tags={"测试操作接口"}, hidden = true)
public class CustomPageController extends BaseController {

    /**
     * 跳转页面
     *
     * @param folderName 文件夹名称
     * @param fileName   文件名称
     * @return
     * @PathVariable
     */
    @RequestMapping(value = "{folderName}/{fileName}.html", method = RequestMethod.GET)
    public String toPage(@PathVariable String folderName, @PathVariable String fileName) {
        //getModel().addAttribute("user", SessionUtil.getCurrentPrincipal());
        //getModel().addAttribute("openId", SessionUtil.getOpenId());
       /* Principal principal = new Principal();
		principal.setCategory(1);
		principal.setPhoneNo("13648286262");
		principal.setOpenId("oG7yB0Wr1Ueyj_Kf-j8wLNQjNs1M");
		principal.setStatus(0);
		principal.setPersonName("林明兴");
		principal.setCertificateType(0);
		principal.setPersonId(443);
		principal.setGender(1);
		principal.setCertificateNo("500241199611223412");
		SessionUtil.setCurrentLoginPrincipal(principal);
		getModel().addAttribute("user", principal);
        getModel().addAttribute("openId", "oG7yB0Wr1Ueyj_Kf-j8wLNQjNs1M");*/
        return folderName + "/" + fileName;
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }


    @GetMapping(value = "page_404")
    public String page404() {
        return "error/404";
    }
}
