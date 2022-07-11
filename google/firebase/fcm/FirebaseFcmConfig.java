package google.firebase.fcm;

import java.util.ResourceBundle;

public class FirebaseFcmConfig {
	
	private static ResourceBundle config = ResourceBundle.getBundle("config.fcm.googleFirebaseConfig");
	
	
	public static String getDatabaseUrl() {
		return config.getString("databaseUrl");
	}
	
	public static String getServiceAccount() {
		return config.getString("serviceAccount");
	}
	
	
}
