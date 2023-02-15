package com.xzx.websocket.listener;

import com.corundumstudio.socketio.SocketIOServer;

/**
 * @author ：1329576606@qq.com
 * @date ：Created in 2023/2/15 14:38
 * @description：Listener
 */
public interface Listener {
    default void listener(SocketIOServer server) {
        server.addListeners(this);
    }
}
