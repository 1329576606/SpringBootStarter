package cn.xzx.websocket.authorization;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.HandshakeData;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ：1329576606@qq.com
 * @date ：Created in 2023/2/15 15:17
 * @description：
 */
@Slf4j
public class BaseAuthorizationListener implements AuthorizationListener {
    @Override
    public boolean isAuthorized(HandshakeData data) {
        log.info("Connect from {}",data.getAddress());
        return true;
    }
}
