package com.yn.annotation;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.SystemClock;
import com.yn.common.utils.HttpUtil;
import com.yn.domain.model.po.SystemLogPO;
import com.yn.service.ISystemLogService;
import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.OperatingSystem;
import nl.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Aspect
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SystemLogAspect {
    // 注入Service用于把日志保存数据库
    @Autowired
    private ISystemLogService logService;

    private static Map<String, String> paramsMap = null;

    static {
        paramsMap = new ConcurrentHashMap<>();
    }

    // 本地异常日志记录对象
    private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);

    public SystemLogAspect() {
    }

    /*// Service层切点
    @Pointcut("@annotation(com.scuniversity.caipannotation.SystemServiceLog)")
    public void serviceAspect() {
    }*/

    // Controller层切点
   /* @Pointcut("@annotation(com.scuniversity.caipannotation.SystemControllerLog)")
    public void controllerAspect() {
    }*/

    /**
     * 前置通知 用于拦截Controller层记录用户的操作
     *
     * @param joinPoint 切点
     */
    @Around("@annotation(controllerLog)")
    public Object doBefore(ProceedingJoinPoint joinPoint, SystemControllerLog controllerLog) {
        Object proceed = null;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // HttpSession session = request.getSession();
        // 读取session中的用户
        // Principal principal=(Principal) session.getAttribute(SessionUtil.USER_SESSION);
        StringBuffer sb = new StringBuffer();
        SystemLogPO systemLog = new SystemLogPO();
        systemLog.setStartTime(SystemClock.now());
        // 是否保存成功
        boolean flag = false;
        try {
            String paramtersJsonStr = getRequestMethodParameters(joinPoint);
            //*========控制台输出=========*//
            logger.info("=====controller请求开始=====");
            logger.info("=====请求方法：" + joinPoint.getSignature().getName());
            sb.append("=====请求模块：").append(controllerLog.modular());
            sb.append("=====方法描述：").append(controllerLog.description());
            logger.info(sb.toString());
            //*========数据库日志=========*//
            // 请求的IP
            String ip = HttpUtil.getAddressIP(request);
            logger.info("=====请求IP:" + ip);
            UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
            Browser browser = userAgent.getBrowser();
            OperatingSystem os = userAgent.getOperatingSystem();
            // 清空拼接字符串
            sb.setLength(0);
            sb.append("=====客户操作浏览器：").append(browser.getName()).append("===").append(browser.getBrowserType()).append("===").append(browser);
            logger.info(sb.toString());
            logger.info("=====客户操作系统：" + os);
            sb.setLength(0);
            // 所在区域位置
            //String region= HttpUtil.getRegion(ip);
            // 设置有关用户的日志信息
            String custName = "匿名用户";
            if (false == "login".equals(StringUtils.isNotEmpty(joinPoint.getSignature().getName()) ? joinPoint.getSignature().getName().toLowerCase() : joinPoint.getSignature().getName())) {
                /*UpmsUser user = (UpmsUser) SecurityUtils.getSubject().getPrincipal();
                if(user!=null){
                    custName = user.getUserName();
                    systemLog.setPermissions(user.getMenus().toString());
                }*/
            } else {
                String user = paramsMap.get("user");
                if (StringUtils.isNotEmpty(user)) {
                    JSONObject jsonObject = JSONObject.parseObject(user);
                    if (jsonObject != null) {
                        custName = jsonObject.getString("username");
                    }
                }
            }
            logger.info("=====请求人: " + custName);
            proceed = joinPoint.proceed();
            systemLog.setBasePath(request.getRequestURL().toString());
            systemLog.setDescription(controllerLog.description());
            systemLog.setIp(ip);
            systemLog.setLogId(IdWorker.getId());
            systemLog.setMethod(joinPoint.getSignature().getName());
            systemLog.setParameter(paramtersJsonStr);
            systemLog.setResult(String.valueOf(proceed));
            systemLog.setSpendTime(Integer.parseInt(String.valueOf(SystemClock.now() - systemLog.getStartTime())));
            systemLog.setUserAgent(userAgent.toString());
            systemLog.setUserName(custName);
        } catch (Exception e) {
            systemLog.setResult(e.getMessage());
            logger.error(">>>>>>>>>>>>>>>>>>>>>>方法执行异常：", e);

        } catch (Throwable throwable) {
            systemLog.setResult(throwable.getMessage());
            logger.error(">>>>>>>>>>>>>>>>>>>>>>方法执行异常：", throwable);
        } finally {
            if (logService.save(systemLog)) {
                flag = true;
            }
            sb.append("=====结束=====数据库日志记录是否成功：").append(flag).append("=====消耗时间：")
                    .append((float) systemLog.getSpendTime() / 1000f).append("s");
            if (!CollectionUtils.isEmpty(paramsMap)) {
                paramsMap.clear();
            }
            logger.info(sb.toString());
        }
        return proceed;
    }

    /**
     * 异常通知 用于拦截service层记录异常日志
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "@annotation(serviceLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, SystemServiceLog serviceLog, Throwable e) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        // 读取session中的用户
        //Principal principal=(Principal) session.getAttribute(SessionUtil.USER_SESSION);
        // 请求的IP
        String ip = HttpUtil.getAddressIP(request);
        StringBuffer sb = new StringBuffer();
        try {
            /*========控制台输出=========*/
            String custName = "匿名用户";
            
            /*UpmsUser user = (UpmsUser) SecurityUtils.getSubject().getPrincipal();
            if(user!=null){
            	custName = user.getUserName();
            }*/

            logger.info("=====异常通知开始=====");
            sb.append("异常代码: ").append(e.getClass().getName());
            logger.info(sb.toString());
            sb.setLength(0);
            sb.append("异常信息: ").append(e.getMessage());
            logger.info(sb.toString());
            sb.setLength(0);
            sb.append("异常方法: ").append(joinPoint.getTarget().getClass().getName()).append(".").append(joinPoint.getSignature().getName());
            logger.info(sb.toString());
            sb.setLength(0);
            sb.append("方法描述: ").append(serviceLog.description());
            logger.info(sb.toString());
            logger.info("请求人：" + custName);
            logger.info("请求IP:" + ip);
            logger.debug("请求参数:" + getRequestMethodParameters(joinPoint));
            /*==========数据库日志=========*/
            UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
            Browser browser = userAgent.getBrowser();
            OperatingSystem os = userAgent.getOperatingSystem();
            sb.setLength(0);
            sb.append(browser.getName()).append("===").append(browser.getBrowserType()).append("===").append(browser);
            logger.info(sb.toString());
            logger.info("os: " + os);
            // 请求方法
            sb.setLength(0);
            sb.append(joinPoint.getTarget().getClass().getName()).append(".").append(joinPoint.getSignature().getName()).append("()");
            // 保存数据库

            logger.info("=====异常通知结束=====");
        } catch (Exception ex) {
            // 记录本地异常日志
            logger.error("==异常通知异常==");
            logger.error("异常信息:{}", ex.getMessage());
        }
        /*==========记录本地异常日志==========*/
        sb.setLength(0);
        sb.append("异常方法：").append(joinPoint.getTarget().getClass().getName())
                .append(joinPoint.getSignature().getName()).append("异常信息：")
                .append("异常代码：").append(e.getClass().getName())
                .append(e.getMessage()).append("参数：").append(getRequestMethodParameters(joinPoint));
        logger.debug(sb.toString());
    }

    /**
     * 获取请求方法或者service的参数值
     *
     * @param joinPoint
     * @return
     */
    private String getRequestMethodParameters(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        // 所有的参数名
        String[] parameterNames = methodSignature.getParameterNames();
        Object[] params = joinPoint.getArgs();//获取方法参数值
        // 重新组装参数值
        if (null != parameterNames && parameterNames.length > 0 && null != params && params.length > 0) {
            for (int i = 0; i < parameterNames.length; i++) {
                String content = String.valueOf(params[i]);
                if (!StringUtils.isEmpty(content) && content.length() > 3000) {
                    content = content.substring(0, 2999);
                }
                paramsMap.put(parameterNames[i], content);
            }
        }
        if (!CollectionUtils.isEmpty(paramsMap)) {
            return JSONObject.toJSONString(paramsMap);
        }
        return null;
    }
}