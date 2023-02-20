package cn.xzx.websocket;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.CommandLineRunner;

/**
 * @author zhanxin.xu
 * @version Created in 2023/2/20 21:04
 */
@Slf4j
public class SocketServer  implements CommandLineRunner, DisposableBean {
    private SocketIOServer socketIOServer;

    public SocketServer(SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;
    }

    @Override
    public void run(String... args) {
        socketIOServer.start();
        log.info("[websocket] 服务已启动 ......");
    }

    @Override
    public void destroy() throws Exception {
        socketIOServer.stop();
        log.info("[websocket] 服务已关闭 ......");
    }
}
