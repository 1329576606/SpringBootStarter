package cn.xzx.security.aspect;

import cn.xzx.security.UserHolder;
import cn.xzx.security.annotation.Authorize;
import cn.xzx.security.constant.Role;
import cn.xzx.security.model.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.nio.file.AccessDeniedException;

/**
 * @author zhanxin.xu
 * @version Created in 2023/2/19 14:34
 */
public class RoleAspect {
    @Pointcut("@annotation(cn.xzx.security.annotation.Authorize)")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void before(JoinPoint pjp) throws Throwable {
        Method method = getMethod(pjp);
        if (method != null) {
            //必须有的权限
            Authorize authorize = method.getAnnotation(Authorize.class);
            Role[] mustRole = authorize.mustRole();
            User user = UserHolder.getUser();
            for (int i = 0; i < mustRole.length; i++) {
                if (user == null || !user.getAuthorities().contains(mustRole[i])) {
                    throw new AccessDeniedException("您没有权限！");
                }
            }
            //有任意权限即可
            Role[] haveRole = authorize.haveRole();
            if (haveRole.length != 0) {
                boolean isBlock = true;
                for (Role role : haveRole) {
                    if (user.getAuthorities().contains(role)) {
                        isBlock = false;
                    }
                }
                if (isBlock) {
                    throw new AccessDeniedException("您没有权限！");
                }
            }
        }
    }

    private Method getMethod(JoinPoint pjp) {
        Method method = ((MethodSignature) (pjp.getSignature())).getMethod();
        if (method.getAnnotation(Authorize.class) == null) {
            try {
                method = pjp.getTarget().getClass().getMethod(method.getName(), method.getParameterTypes());
            } catch (NoSuchMethodException e) {
                method = null;
            }
        }
        return method;
    }
}
