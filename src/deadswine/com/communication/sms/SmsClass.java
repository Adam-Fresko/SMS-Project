package deadswine.com.communication.sms;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

/**
 * 
 * @author deadswine
 *         <p>
 *         class that handle all SMS sending
 * 
 * @see  SmsClass#sendSMS(String,String)
 * @see SmsClass#deleteSMS()
 */
public class SmsClass {

    /*
     * public void sendSMS(String phoneNumber, String message) {
     * Log.v("phoneNumber", phoneNumber); Log.v("MEssage", message); SmsManager
     * sms = SmsManager.getDefault(); sms.sendTextMessage(phoneNumber, null,
     * message, null, null);
     * 
     * }
     */

    /**
     * sms sender method, fires 2 pending intents SENT and DELIVERED
     * 
     * @param phoneNumber
     *            Number to witch we send data as String
     * @param message
     *            actual message text as String
     *            
     *            @see SMSReceiver
     */
    public void sendSMS(String phoneNumber, String message) {

	Context context = FragmentLayoutSupport.getAppContext();

	String SENT = "deadswine.com.communication.sms.SMS_SENT";
	String DELIVERED = "deadswine.com.communication.sms.SMS_DELIVERED";

	PendingIntent sentPI = PendingIntent.getBroadcast(context, 10, new Intent(SENT), 0);
	PendingIntent deliveredPI = PendingIntent.getBroadcast(context, 11, new Intent(DELIVERED), 0);

	SmsManager sms = SmsManager.getDefault();
	sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }
    
    /**
     * 
     */
    public void deleteSMS(){
	
    }

}
