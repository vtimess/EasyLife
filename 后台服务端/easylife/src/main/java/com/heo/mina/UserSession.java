package com.heo.mina;

import com.heo.domain.Message;
import com.heo.mina.message.ClientMessage;
import com.heo.mina.message.ServerMessage;
import com.heo.repository.MessageRepository;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class UserSession {

    private final static Logger logger = LoggerFactory.getLogger(UserSession.class);


    public static HashMap<Integer,IoSession> userSessions = new HashMap<>();

    public static IoSession getUserSession(Integer userId){
        return userSessions.get(userId);
    }

    public static ArrayList<ClientMessage> clientMessages = new ArrayList<>();


    /**
     * 登陆
     * @param userId
     */
    public static void Login(Integer userId,IoSession ioSession){
        ClientMessage message = new ClientMessage();
        //先判断user是否已经在线
        IoSession session = userSessions.get(userId);
        if (session != null){
            message.setCode(999);
            message.setMsg("你已经被挤下线");
            session.write(message);
        }else{
            ClientMessage clientMessage = new ClientMessage();
            clientMessage.setCode(5); //回复信息
            clientMessage.setMsg("登陆成功");
            ioSession.write(clientMessage);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i=0;i < clientMessages.size();i++ ) {
                        ClientMessage msg = clientMessages.get(i);
                        if (msg.getToUserId().equals(userId)) {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            logger.info("{}上线，发送离线的信息给他",userId);
                            ioSession.write(clientMessages.remove(i));
                            i--;
                        }
                    }
                }
            }).start();
        }

        userSessions.put(userId,ioSession);

        logger.info("{}用户连接了,当前的用户数量为:{}",userId,UserSession.userSessions.size());
    }

    public static void removeUserSeeison(IoSession ioSession){
        Integer userId = -1;
        for (Map.Entry<Integer, IoSession> entry : userSessions.entrySet()) {
            if(entry.getValue().equals(ioSession)) {
                userId = entry.getKey();
                break;
            }
        }
        userSessions.remove(userId);
        logger.info("{}退出了,当前的用户数量为:{}",userId,UserSession.userSessions.size());

    }

    public static void removeUserSession(Integer userId){
        userSessions.remove(userId);
        logger.info("{}退出了,当前的用户数量为:{}",userId,UserSession.userSessions.size());

    }

    /**
     *  单发给某个用户
     * @param toUserId  发送的目标人id
     * @param formUserId  发送人id
     * @param msg 发送的信息
     */
    public static void sendToUsre(Integer toUserId,Integer formUserId ,String msg){
        IoSession session = getUserSession(toUserId);
        ClientMessage clientMessage = new ClientMessage();
        clientMessage.setCode(0);   //聊天室信息
        clientMessage.setFormUserId(formUserId); //发送人
        clientMessage.setToUserId(toUserId);
        clientMessage.setMsg(msg);
        clientMessage.setDate(new Date());
        if (session == null){
            clientMessages.add(clientMessage);
            logger.info("对方不在线 保存了 {}",toUserId);
        }else{
            session.write(clientMessage);
            logger.info("发送给了{}"+toUserId);
        }
    }

    /**
     * 官方发布新的信息
     * @param msg 信息
     */
    public static void sendAllUser(String msg){
        for (Map.Entry<Integer, IoSession> entry : userSessions.entrySet()) {
            ClientMessage  clientMessage = new ClientMessage();
            clientMessage.setCode(0);   //聊天室信息
            clientMessage.setFormUserId(0); //发送人 官方
            clientMessage.setMsg(msg);
            clientMessage.setDate(new Date());
            entry.getValue().write(clientMessage);
        }
    }

    /**
     * 推送某个人
     * @param toUserId
     * @param msg
     */
    public static void sendNotice(Integer toUserId,String msg){
        IoSession session = getUserSession(toUserId);
        if (session == null){
            //用户不在线
            System.out.println(toUserId+"不在线");
        }else{
            ClientMessage  clientMessage = new ClientMessage();
            clientMessage.setCode(1);   //通知
            clientMessage.setFormUserId(0); //发送人
            clientMessage.setMsg(msg);
            session.write(clientMessage);
            System.out.println("发送给"+toUserId);
        }
    }

    /**
     * 推送
     * @param msg
     */
    public static void sendNotice(String msg){
        for (Map.Entry<Integer, IoSession> entry : userSessions.entrySet()) {
            ClientMessage  clientMessage = new ClientMessage();
            clientMessage.setCode(1);   //通知
            clientMessage.setFormUserId(0); //发送人 官方
            clientMessage.setMsg(msg);
            clientMessage.setDate(new Date());
            entry.getValue().write(clientMessage);
        }
    }

}
