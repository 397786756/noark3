package xyz.noark.core.annotation;

import java.lang.annotation.*;

/**
 * ExceptionHandler异常处理器.
 *
 * @author 小流氓[176543888@qq.com]
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExceptionHandler {
    /**
     * 对应处理的异常集合
     *
     * @return 异常集合
     */
    Class<?>[] value() default {};
}