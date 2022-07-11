package google.firebase.fcm;

import java.io.InputStream;
import java.util.List;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;

import google.firebase.fcm.model.SendPushVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FcmSendPush {
	
	public FcmSendPush() {
		FirebaseApp firebaseApp =null;
		try {
			List<FirebaseApp> firebaseApps = FirebaseApp.getApps();
			
			if(firebaseApps != null && !firebaseApps.isEmpty()) {
				for (FirebaseApp app : firebaseApps) {
					if (app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)) {
						firebaseApp = app;
					}
				}
			}else {
				try (InputStream is = getClass().getResourceAsStream(FirebaseFcmConfig.getServiceAccount())){
					@SuppressWarnings("deprecation")
					FirebaseOptions option = new FirebaseOptions.Builder()
						.setCredentials(GoogleCredentials.fromStream(is))
						.setDatabaseUrl(FirebaseFcmConfig.getDatabaseUrl())
						.build();
					firebaseApp = FirebaseApp.initializeApp(option);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Exception Message ::: " + e.getMessage());
			firebaseApp.delete();
		}
	}
	
	public void sendSubscribeTopic(SendPushVO sendPush) throws FirebaseException{
		
		//Notificaton send 
//		Message message = Message.builder()
//			.setNotification(Notification.builder()
//					.setTitle(sendPush.getTitle())
//					.setBody(sendPush.getContents()).build())
//			.setTopic(sendPush.getTopic())
//			.build();
		
		String topicCondition = new String();
		if("ALL".equals(sendPush.getTopic())) {
			topicCondition = "'ANDROID' in topics || 'IOS' in topics"; 
		}else {
			topicCondition = "'" + sendPush.getTopic() + "' in topics"; 
		}
		//User putData send
		Message message = Message.builder()
				.putData("Topic", sendPush.getTopic())
				.putData("TitleTh", sendPush.getTitleTh())
				.putData("TitleEn", sendPush.getTitleEn())
				.putData("ContentsTh", sendPush.getContentsTh())
				.putData("ContentsEn", sendPush.getContentsEn())
				.putData("ImagePath", sendPush.getImagePath())
				.putData("Type", sendPush.getType())
				.putData("Link", sendPush.getLink())
				//.setTopic(sendPush.getTopic())
				.setCondition(topicCondition)
				.build();
		
		String sendResMsg = FirebaseMessaging.getInstance().send(message);
		log.debug(sendResMsg + " messages were sent successfully");
	}
}
