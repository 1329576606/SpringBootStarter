package cn.xzx.websocket.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ：1329576606@qq.com
 * @date ：Created in 2023/2/15 14:37
 * @description：WebsocketProperties
 */
@ConfigurationProperties(prefix = "server.websocket")
@Data
public class WebsocketProperties {
    private int port=9091;
    private int maxFramePayloadLength = 64 * 1024;
    private int pingTimeout = 60000;
    private int pingInterval = 25000;
}
