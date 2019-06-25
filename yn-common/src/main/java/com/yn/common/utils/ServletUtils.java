package com.yn.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yn.common.context.StaticBeans;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;

/**
 * @author dhc
 * 2019-03-05 14:07
 */
public class ServletUtils {
    private static final Logger LOG = LoggerFactory.getLogger(ServletUtils.class);

    /**
     * 获取数字类型参数值
     *
     * @param request 请求体
     * @param name    参数名
     * @param <T>     Number的实现类型
     * @return 数字类型值
     */
    @SuppressWarnings("unchecked")
    public static <T extends Number> T getNumberParam(ServletRequest request, String name) {
        String value = request.getParameter(name);
        if (StringUtils.isNotBlank(value)) {
            try {
                return (T) NumberFormat.getInstance().parse(value);
            } catch (ParseException e) {
                LOG.warn("Request param '{} = {}' isn't a number.", name, value);
            }
        }
        return null;
    }

    /**
     * 根据请求获取响应类型
     *
     * @param request 请求体
     * @return 响应类型
     */
    public static List<MediaType> getMediaTypes(HttpServletRequest request) {
        String accept = request.getHeader("Accept");
        List<MediaType> types = MediaType.parseMediaTypes(accept);
        String contentType = request.getContentType();
        if (contentType != null && contentType.trim().length() > 0) {
            types.add(MediaType.parseMediaType(request.getContentType()));
        }
        return types;
    }

    /**
     * 从请求中获取输入的字符串
     *
     * @return 请求体中的字符串
     */
    public static String getRequestText(HttpServletRequest request) {
        byte[] bytes = new byte[request.getContentLength()];
        try {
            InputStream is = request.getInputStream();
            int length = is.read(bytes);
            return length == 0 ? null : new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从请求中获取实体对象
     *
     * @param request 请求内容
     * @param clz     对象类
     * @param <T>     对象类型
     * @return 获取的实体对象或null
     */
    public static <T> T getRequestBody(HttpServletRequest request, Class<T> clz) throws IOException {
        String text = getRequestText(request);
        if (StringUtils.isNotBlank(text)) {
            text = text.trim();
            ObjectMapper mapper;
            if (text.startsWith("<")) {
                mapper = StaticBeans.getXmlMapper();
            } else {
                mapper = StaticBeans.getObjectMapper();
            }
            return mapper.readValue(text, clz);
        }
        return null;
    }

    /**
     * 利用 ServletResponse 输出字符串
     *
     * @param response 上下文的 ServletResponse 对象
     * @param text     需要输出的字符串
     */
    public static void responseText(ServletResponse response, String text) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
        try (PrintWriter writer = response.getWriter()) {
            writer.write(text);
            writer.flush();
        } catch (IOException e) {
            LOG.error("HttpResponse输出错误：" + e.getMessage(), e);
        }
    }

    /**
     * 根据请求类型输出对象
     *
     * @param request  请求体
     * @param response 上下文的 ServletResponse 对象
     * @param object   需要输出的对象
     * @return 是否已输出
     */
    public static boolean responseBody(HttpServletRequest request, HttpServletResponse response, Object object) {
        List<MediaType> types = getMediaTypes(request);
        if (types != null && types.size() > 0) {
            for (MediaType type : types) {
                if (type.getSubtype().equalsIgnoreCase("json")) {
                    responseJson(response, object);
                    return true;
                }
                if (type.getSubtype().matches("([A-Za-z]+\\+)?xml|html")) {
                    responseXml(response, object);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 利用 ServletResponse 输出对象
     *
     * @param response     上下文的 ServletResponse 对象
     * @param object       需要输出的对象
     * @param objectMapper 输出对象的序列化 Mapper
     */
    public static void responseBody(ServletResponse response, Object object, ObjectMapper objectMapper) {
        if (object == null) {
            responseText(response, "null");
        }
        try {
            response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
            objectMapper.writeValue(response.getWriter(), object);
        } catch (IOException e) {
            LOG.error("Object转化为字符串错误：" + e.getMessage(), e);
        }
    }

    /**
     * 利用 HttpServletResponse 输出二进制文件
     *
     * @param response 应答对象
     * @param bytes    需要输出的数据
     * @param name     输出显示的文件名称
     * @throws IOException 输出错误
     */
    public static void responseBytes(HttpServletResponse response, byte[] bytes, String name) throws IOException {
        if (response.isCommitted()) return;
        response.setHeader("Content-Disposition", "attachment;filename=" + name);
        OutputStream stream = response.getOutputStream();
        stream.write(bytes);
        stream.close();
    }

    /**
     * 根据请求类型输出对象，如果未找到合适的输出方式，则输出为 JSON
     *
     * @param request  请求体
     * @param response 上下文的 ServletResponse 对象
     * @param object   需要输出的对象
     */
    public static void responseBodyOrJson(HttpServletRequest request, HttpServletResponse response, Object object) {
        if (!responseBody(request, response, object)) {
            responseJson(response, object);
        }
    }

    /**
     * 通过 ServletResponse 输出对象为 Json 字符串
     *
     * @param response 上下文的 ServletResponse 对象
     * @param object   需要输出的对象
     */
    public static void responseJson(ServletResponse response, Object object) {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        responseBody(response, object, StaticBeans.getObjectMapper());
    }


    /**
     * 通过 ServletResponse 输出对象为 Xml 字符串
     *
     * @param response 上下文的 ServletResponse 对象
     * @param object   需要输出的对象
     */
    public static void responseXml(ServletResponse response, Object object) {
        response.setContentType(MediaType.APPLICATION_XML_VALUE);
        responseBody(response, object, StaticBeans.getXmlMapper());
    }
}
