package deadswine.com.communication.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.util.Log;

public class SMSReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
	Log.d("SMSReceiver", "SMSReceiver CALLED");
	
	new ReceiverClass().execute("");	
    }
}