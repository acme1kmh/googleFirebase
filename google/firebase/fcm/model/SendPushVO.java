package google.firebase.fcm.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SendPushVO {
	
	private String topic;
	
	private String titleTh;
	private String titleEn;
	private String contentsTh;
	private String contentsEn;
	private String imagePath;
	private String regToken;
	private String link;
	private String type;
}
