package com.heo.mina;

import com.heo.mina.message.ClientMessage;
import com.heo.mina.message.ServerMessage;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.ws.Service;

public class UserServerHandler  extends IoHandlerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(UserServerHandler.class);
    public static IoSession session;

    //服务器与客户端创建连接
    @Override
    public void sessionCreated(IoSession session) throws Exception {
        System.out.println("服务器与客户端创建连接...");
        super.sessionCreated(session);
    }


    @Override
    public void sessionOpened(IoSession session) throws Exception {
        System.out.println("服务器与客户端连接打开...");
        super.sessionOpened(session);
    }

    //消息的接收处理
    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {
        super.messageReceived(session, message);
        ServerMessage mMessage = (ServerMessage) message;
        switch (mMessage.getCode()){
            //心跳包
            case 0:
                break;

            //连接
            case 1:
                //保存用户session
               UserSession.Login(mMessage.getFromUserId(),session);
                break;

            //下线
            case -1:
                UserSession.removeUserSession(mMessage.getFromUserId());
                session.close(true);
                break;

            //转发
            case 2:
                UserSession.sendToUsre(mMessage.getToUserId(),mMessage.getFromUserId(),mMessage.getMsg());
                logger.info("转发了一条信息");
                break;

            default:break;
        }
    }

    @Override
    public void messageSent(IoSession session, Object message)
            throws Exception {
        super.messageSent(session, message);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        UserSession.removeUserSeeison(session);

        super.sessionClosed(session);
    }
}
