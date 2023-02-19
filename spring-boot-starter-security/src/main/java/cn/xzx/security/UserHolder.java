package cn.xzx.security;

import cn.xzx.security.model.User;

/**
 * @author zhanxin.xu
 * @version Created in 2023/2/19 14:33
 * <p>用户身份上下文</p>
 */
public class UserHolder {
    private static final ThreadLocal<User> holder = new ThreadLocal<User>();

    public static void bind(User user) {
        if (null == user) {
            return;
        }
        holder.set(user);
    }

    public static User getUser() {
        return holder.get();
    }

    public static void unbind() {
        holder.remove();
    }

}
