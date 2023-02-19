package cn.xzx.security.annotation;

import cn.xzx.security.constant.Role;

import java.lang.annotation.*;

/**
 * @author zhanxin.xu
 * @version Created in 2023/2/19 14:43
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Authorize {
    Role[] mustRole() default {};
    Role[] haveRole() default {};
}
