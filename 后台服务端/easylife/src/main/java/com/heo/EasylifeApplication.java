package com.heo;

import com.heo.mina.MinaService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EasylifeApplication {

	public static void main(String[] args) {


		SpringApplication.run(EasylifeApplication.class, args);

		 new MinaService().start();
//		new Thread(new Runnable() {
//			int i=0;
//			@Override
//			public void run() {
//				while(true) {
//					IoSession ioSession = UserSession.getUserSession(3);
//					if(ioSession!=null) {
//						ClientMessage clientMessage = new ClientMessage();
//						clientMessage.setMsg(i+++"");
//						ioSession.write(clientMessage);
//					}
//					try {
//						Thread.sleep(3000);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}).run();
	}
}
