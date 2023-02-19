package cn.xzx.security.model;

import cn.xzx.security.constant.Role;
import lombok.*;

import java.util.List;

/**
 * @author zhanxin.xu
 * @version Created in 2023/2/19 14:27
 * <p>用户实体</p>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {
    private String id;
    private List<Role> authorities;
}
