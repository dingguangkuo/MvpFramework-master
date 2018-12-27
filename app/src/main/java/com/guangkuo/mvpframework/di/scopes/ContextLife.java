package com.guangkuo.mvpframework.di.scopes;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * ContextLife注解，用来标识
 */
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ContextLife {
    String value() default "Application";
}