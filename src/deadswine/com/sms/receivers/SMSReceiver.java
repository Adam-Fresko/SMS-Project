package deadswine.com.sms.receivers;

import deadswine.com.sms.AsyncTaskClass;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

/**
 * 
 * @author Deadswine Class that handle all sms related receivers
 * 
 * 
 * @see SMSReceiver#onReceive(Context, Intent)
 * 
 * @see SMSReceiver#sentSMS()
 * 
 * @see SMSReceiver#deliveredSMS()
 * 
 * @see SMSReceiver#receivedSMS()
 * 
 * 
 */

public class SMSReceiver extends BroadcastReceiver {

    Context context;

    public void onReceive(Context ctx, Intent intent) {
	context = ctx;
	if (intent.getAction().equals("deadswine.com.sms.SMS_DELIVERED")) {
	    deliveredSMS();
	} else if (intent.getAction().equals("deadswine.com.sms.SMS_SENT")) {
	    sentSMS();

	} else if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {

	    receivedSMS();
	}

    }

    /**
     * Handles receiver for sending sms action
     */
    void sentSMS() {
	switch (getResultCode()) {
	    case Activity.RESULT_OK:
		Toast.makeText(context, "SMS sent", Toast.LENGTH_LONG).show();
		break;
	    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
		Toast.makeText(context, "Generic failure", Toast.LENGTH_LONG).show();
		break;
	    case SmsManager.RESULT_ERROR_NO_SERVICE:
		Toast.makeText(context, "No service", Toast.LENGTH_LONG).show();
		break;
	    case SmsManager.RESULT_ERROR_NULL_PDU:
		Toast.makeText(context, "Null PDU", Toast.LENGTH_LONG).show();
		break;
	    case SmsManager.RESULT_ERROR_RADIO_OFF:
		Toast.makeText(context, "Radio off", Toast.LENGTH_LONG).show();
		break;
	}
    }

    /**
     * Handles receiver for sms delivery action
     */
    void deliveredSMS() {
	switch (getResultCode()) {
	    case Activity.RESULT_OK:
		Toast.makeText(context, "SMS delivered", Toast.LENGTH_LONG).show();
		break;
	    case Activity.RESULT_CANCELED:
		Toast.makeText(context, "SMS not delivered", Toast.LENGTH_LONG).show();
		break;
	}
    }

    /**
     * Handles receiving sms
     */
    void receivedSMS() {

	Toast.makeText(context, "SMS RECEIVED", Toast.LENGTH_LONG).show();
	new AsyncTaskClass().execute("");
    }

}
