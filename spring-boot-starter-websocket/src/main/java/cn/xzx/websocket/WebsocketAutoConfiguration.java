package cn.xzx.websocket;

import cn.xzx.websocket.authorization.BaseAuthorizationListener;
import cn.xzx.websocket.configuration.WebsocketProperties;
import cn.xzx.websocket.exception.BaseExceptionListener;
import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.corundumstudio.socketio.listener.ExceptionListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

/**
 * @author ：1329576606@qq.com
 * @date ：Created in 2023/2/15 14:41
 * @description：WebsocketAutoConfiguration
 */
@Configuration
@EnableConfigurationProperties(WebsocketProperties.class)
@Slf4j
public class WebsocketAutoConfiguration implements CommandLineRunner {
    private SocketIOServer socketIOServer;

    @Bean
    @ConditionalOnMissingBean(AuthorizationListener.class)
    AuthorizationListener authorizationListener() {
        return new BaseAuthorizationListener();
    }

    @Bean
    @ConditionalOnMissingBean(ExceptionListener.class)
    ExceptionListener exceptionListener() {
        return new BaseExceptionListener();
    }

    @Bean
    com.corundumstudio.socketio.Configuration configuration(WebsocketProperties websocketProperties
            , AuthorizationListener authorizationListener
            , ExceptionListener exceptionListener) {
        com.corundumstudio.socketio.Configuration configuration = new com.corundumstudio.socketio.Configuration();
        configuration.setPort(websocketProperties.getPort());
        configuration.setMaxFramePayloadLength(websocketProperties.getMaxFramePayloadLength());
        configuration.setPingTimeout(websocketProperties.getPingTimeout());
        configuration.setPingInterval(websocketProperties.getPingInterval());
        configuration.setAuthorizationListener(authorizationListener);
        configuration.setExceptionListener(exceptionListener);
        return configuration;
    }

    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);
    }

    @Bean
    SocketIOServer socketIOServer(com.corundumstudio.socketio.Configuration configuration) {
        socketIOServer = new SocketIOServer(configuration);
        return socketIOServer;
    }

    @PreDestroy
    public void shutDown() {
        log.info("WebsocketShutDownHook......");
        socketIOServer.stop();
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("socketIOServer start......");
        socketIOServer.start();
    }
}
