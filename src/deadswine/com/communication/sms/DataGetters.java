package deadswine.com.communication.sms;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class DataGetters  {
  //  private Activity activity;
    
    public List<String> getCONVERSATIONS(Context context) {

	    List<String> sms = new ArrayList<String>();
	    Uri uri = Uri.parse("content://mms-sms/conversations?simple=true");
	    
	    Cursor cur = context.getContentResolver().query(uri, null, null, null, "date DESC");
	    while (cur.moveToNext()) {
		
		String snippet = cur.getString(cur.getColumnIndex("snippet"));
		String id = cur.getString(cur.getColumnIndex("_id"));
		String recipient_id = cur.getString(cur.getColumnIndex("recipient_ids"));

		Log.v("STRING _id = ", id);
		Log.v("STRING recipient_id = ", recipient_id);

		sms.add(snippet);
	    }
	    // prints columns names
	    for (int i = 0; i < cur.getColumnCount(); i++) {
		Log.v("column names", cur.getColumnName(i).toString());
	    }

	    return sms;
	}
    
    public List<String> getSMS(Context context, int ShownIndex) {
	  //  Log.v(" INDEXX", "" + getShownIndex());

	    Uri uri = Uri.parse("content://mms-sms/conversations?simple=true");

	    Cursor cursor = context.getContentResolver().query(uri, null, null, null, "date DESC");

	    cursor.moveToPosition(ShownIndex);
	    String id = cursor.getString(cursor.getColumnIndex("_id"));
	    String selection = "thread_id = " + id; // + "63"
	    cursor.close();

	    List<String> sms = new ArrayList<String>();

	    
	    uri = Uri.parse("content://sms");

	    
	    final String[] projection = new String[] { "*" };
	   
	    Cursor cur = context.getContentResolver().query(uri, null, selection, null, "date DESC");

	    while (cur.moveToNext()) {
		
		String phone = cur.getString(cur.getColumnIndexOrThrow("address"));
		int type = cur.getInt(cur.getColumnIndex("type"));// 2 = sent,
		String date = cur.getString(cur.getColumnIndexOrThrow("date"));
		String body = cur.getString(cur.getColumnIndexOrThrow("body"));
		String ids = cur.getString(cur.getColumnIndex("_id"));
		String thread_ids = cur.getString(cur.getColumnIndex("thread_id"));
		
		Log.d("sms test body + date", body + "    date:" + date + "  type:" + type + "   adress:" + phone + "           ID = " + ids + "        thread_ID = " + thread_ids + "        msg_ID = " + "msg_id");
		
		sms.add(body + "     date:" + date);
	    }
	    // prints columns names
	    for (int i = 0; i < cur.getColumnCount(); i++) {
		Log.v("column names from details activity", cur.getColumnName(i).toString());
	    }

	    return sms;
	}
    
}
