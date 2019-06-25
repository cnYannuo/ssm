package com.yn.common.context;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class StaticBeans {
    private static ObjectMapper objectMapper;
    private static XmlMapper xmlMapper;
    private static RestTemplate restTemplate;

    public static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = AppContext.getBean(ObjectMapper.class);
        }
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
        return objectMapper;
    }

    public static XmlMapper getXmlMapper() {
        if (xmlMapper == null) {
            xmlMapper = AppContext.getBean(XmlMapper.class);
        }
        if (xmlMapper == null) {
            xmlMapper = new XmlMapper();
            xmlMapper.setSerializationInclusion(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL);
            xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
        return xmlMapper;
    }

    public static RestTemplate getRestTemplate() {
        if (restTemplate == null) {
            restTemplate = AppContext.getBean(RestTemplate.class);
        }
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
            for (HttpMessageConverter converter : restTemplate.getMessageConverters()) {
                if (converter instanceof MappingJackson2HttpMessageConverter) {
                    MappingJackson2HttpMessageConverter messageConverter = (MappingJackson2HttpMessageConverter) converter;
                    List<MediaType> supportTypes = new ArrayList<>();
                    supportTypes.add(MediaType.APPLICATION_JSON);
                    supportTypes.add(new MediaType("application", "*+json"));
                    supportTypes.add(MediaType.TEXT_PLAIN);
                    supportTypes.add(MediaType.TEXT_HTML);
                    messageConverter.setSupportedMediaTypes(supportTypes);
                }
            }
        }
        return restTemplate;
    }
}
