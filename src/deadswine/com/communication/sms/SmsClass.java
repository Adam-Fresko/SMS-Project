package deadswine.com.communication.sms;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;

public class SmsClass {

    public void sendSMS(String phoneNumber, String message) {
	Log.v("phoneNumber", phoneNumber);
	Log.v("MEssage", message);
	SmsManager sms = SmsManager.getDefault();
	sms.sendTextMessage(phoneNumber, null, message, null, null);

    }

    //---sends an SMS message to another device---
    public void sendS(String phoneNumber, String message) {

	Context context = FragmentLayoutSupport.getAppContext();

	String SENT = "deadswine.com.communication.sms.SMS_SENT";
	String DELIVERED = "deadswine.com.communication.sms.SMS_DELIVERED";

	PendingIntent sentPI = PendingIntent.getBroadcast(context, 10, new Intent(SENT), 0);
	PendingIntent deliveredPI = PendingIntent.getBroadcast(context, 11, new Intent(DELIVERED), 0);

	SmsManager sms = SmsManager.getDefault();
	sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }

}
