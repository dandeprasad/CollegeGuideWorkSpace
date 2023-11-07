import org.UploadWorkspace.UsersGetNotify;

public class TestingFirebase {

	public static  void  main (String[] args) {
		String NOTIFICATION_MESSAGE = "Test";
		String NOTIFICATION_HEADER ="Test";
		String NOTIFY_ID ="Test again";
		
		String input = "{\"to\": \"eB_h5U3oHKg:APA91bFXUnvOYHbVSNHkKWA7N6IIJYl_VvON_GpJwCHYOVpfoocAlZa0lao5bg4J2zYqzpNNiZPy1ohwRpgeDrGpViAd9K9dokL4kNrp3ra8Gf9KnrQquIRMniHIYUEFL6AxkzBzCs2e\",\"priority\": \"high\",\"data\": {\"MYDATA\": {\"APP_KEY\": \"BASIC\",\"TITLE\": \""+NOTIFICATION_HEADER+"\",\"MESSAGE_DETAIL\": \""+NOTIFICATION_MESSAGE+"\"}}}";
	      UsersGetNotify req = new UsersGetNotify();
			req.newsmessage(input);
	}
}
