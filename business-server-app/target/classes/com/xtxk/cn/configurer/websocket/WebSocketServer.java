package com.xtxk.cn.configurer.websocket;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-30 14:17
 */
@Component
@ServerEndpoint("/websocket/{account}")
public class WebSocketServer {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    /**
     * 静态变量，用来记录当前在线连接数，线程安全的类。
     */
    private static AtomicInteger onlineSession = new AtomicInteger(0);

    /**
     * 存放所有在线的客户端
     */
    private static Map<String, Session> onlineSessionMap = new ConcurrentHashMap<>();


    /**
     * 连接sid和连接会话
     */
    private String account;
    private Session session;

    /**
     * 连接建立成功调用的方法。由前端<code>new WebSocket</code>触发
     * @param account 用户账号
     * @param session 与某个客户端的连接会话，需要通过它来给客户端发送消息
     */
    @OnOpen
    public void onOpen(@PathParam("account") String account, Session session) {
        logger.info("连接建立中 ==> account = {}， session = {}",account, session.getId());
        onlineSessionMap.put(account, session);
        //在线数加1
        onlineSession.incrementAndGet();
        this.account = account;
        this.session = session;
        sendToOne(account, "连接成功");
        logger.info("连接建立成功，当前在线数为：{} ==> 开始监听新连接：account = {}, session_id = {}", onlineSession, account, session.getId());
    }

    /***
     * 连接关闭调用的方法
     * 由前端<code>socket.close()</code>触发
     * @param account
     * @param session
     */
    @OnClose
    public void onClose(@PathParam("account") String account,Session session) {
        onlineSessionMap.remove(account);
        onlineSession.decrementAndGet();
        logger.info("连接关闭成功，当前在线数为：{} ==> 关闭该连接信息：account = {},sessionId = {}", onlineSession,account, session.getId());
    }

    /**
     * 收到客户端消息后调用的方法。由前端<code>socket.send</code>触发
     * * 当服务端执行toSession.getAsyncRemote().sendText(xxx)后，前端的socket.onmessage得到监听。
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        /**
         * html界面传递来得数据格式，可以自定义.
         * {"sid":"user-1","message":"hello websocket"}
         */
        JSONObject jsonObject = JSONUtil.toBean(message, JSONObject.class);
        String toAccount = jsonObject.getStr("account");
        String msg = jsonObject.getStr("message");
        logger.info("服务端收到客户端消息 ==> fromSessionId = {}, toAccount = {}, message = {}", session.getId(), toAccount, message);

        /**
         * 模拟约定：如果未指定toAccount信息，则群发，否则就单独发送
         */
        if (StringUtils.isBlank(toAccount)) {
            sendToAll(msg);
        } else {
            sendToOne(toAccount, msg);
        }
    }

    /**
     * 发生错误调用的方法
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
       // logger.error("WebSocket发生错误，错误信息为：" + error.getMessage());
        //error.printStackTrace();
    }

    /**
     * 群发消息
     * @param message 消息
     */
    public void sendToAll(String message) {
        // 遍历在线map集合
        onlineSessionMap.forEach((onlineSessionAccount, toSession) -> {
            // 排除掉自己
            logger.info("服务端给客户端群发消息 ==> sessionAccount = {}, toAccount = {}, message = {}", account, onlineSessionAccount, message);
            toSession.getAsyncRemote().sendText(message);
        });
    }

    /**
     * 指定发送消息
     * @param account
     * @param message
     */
    public void sendToOne(String account, String message) {
        // 通过account查询map中是否存在
        Session toSession = onlineSessionMap.get(account);
        if (ObjectUtil.isNull(toSession)) {
            logger.error("服务端给客户端发送消息 ==> toSessionAccount = {} 不存在, message = {}", account, message);
            return;
        }
        // 异步发送
        toSession.getAsyncRemote().sendText(message);
        logger.info("服务端给客户端发送消息 ==> toAccount = {}, message = {}", account, message);
    }

}
