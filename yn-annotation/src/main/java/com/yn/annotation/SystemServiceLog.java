package com.yn.annotation;


import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.PACKAGE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemServiceLog {

    String description() default "";


}