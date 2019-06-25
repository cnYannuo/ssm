package com.yn.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.yn.domain.mapper*")
/**
 * mapper文件扫描
 */
@SpringBootApplication(scanBasePackages = {"com.yn.service", "com.yn.web",
        "com.yn.annotation", "com.yn.common"})
/**
 *包含了@ComponentScan、@Configuration和@EnableAutoConfiguration注解。
 * 其中@ComponentScan让spring Boot扫描到Configuration类并把它加入到程序上下文，
 * @EnableAutoConfiguration让spring boot根据类路径中的jar包依赖为当前项目进行自动配置。
 */
@EnableTransactionManagement(proxyTargetClass = true)
/**
 *事务管理
 */
@PropertySource(value = {"classpath:app.properties"}, ignoreResourceNotFound = true, encoding = "UTF-8")
/**
 * 资源文件扫描
 */
public class ComYnApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ComYnApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ComYnApplication.class);
    }
}
