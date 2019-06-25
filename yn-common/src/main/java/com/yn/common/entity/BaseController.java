package com.yn.common.entity;

import io.swagger.annotations.Api;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;


/**
 * @ClassName: BaseController
 * @Description: (control基类)
 * @date 2017年10月12日 上午11:02:32
 */
@ControllerAdvice
@Controller
@Api(value="测试controller",tags={"测试操作接口"}, hidden = true)
public abstract class BaseController implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    protected static ThreadLocal<HttpServletRequest> request = new ThreadLocal<>();
    protected static ThreadLocal<HttpServletResponse> response = new ThreadLocal<>();
    protected static ThreadLocal<HttpSession> session = new ThreadLocal<>();
    protected static ThreadLocal<ServletContext> application = new ThreadLocal<>();
    protected static ThreadLocal<Model> model = new ThreadLocal<>();

    @ModelAttribute
    public void init(HttpServletRequest request, HttpServletResponse response, Model model) {
        BaseController.request.set(request);
        BaseController.response.set(response);
        BaseController.session.set(request.getSession());
        BaseController.application.set(request.getServletContext());
        BaseController.model.set(model);
    }


    public HttpServletRequest getRequest() {
        return request.get();
    }

    public HttpServletResponse getResponse() {
        return response.get();
    }

    public HttpSession getSession() {
        return session.get();
    }

    public ServletContext getApplication() {
        return application.get();
    }

    public Model getModel() {
        return model.get();
    }

    public Map<String, String> getParamMapFromRequest(HttpServletRequest request) {
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> properties = request.getParameterMap();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name;
        while (entries.hasNext()) {
            String value = "";
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) {
                    value += values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();
            }
            params.put(name, value);
        }
        return params;
    }

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        Set<ErrorPage> errorPageSet = new HashSet<>();
        errorPageSet.add(new ErrorPage(HttpStatus.BAD_REQUEST, "/login.html"));
        errorPageSet.add(new ErrorPage(HttpStatus.NOT_FOUND, "/page_404.html"));
        errorPageSet.add(new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, "/login.html"));
        factory.setErrorPages(errorPageSet);
    }
}