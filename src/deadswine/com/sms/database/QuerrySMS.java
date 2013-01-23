package deadswine.com.sms.database;

import deadswine.com.sms.activities.SetupActivity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

public class QuerrySMS extends AsyncTask<String, Void, Void> {
    int     i;
    int     b = 0;
    Context context = SetupActivity.context;
    
    static String ID;
    
    
    static String DATE;
    static String  MESSAGE_COUNT;
    static String RECIPIENT_IDS;
    static String SNIPPET ;
    static String READ ;
    static String TYPE;
    static String ERROR;
    static String HAS_ATTACHMENT ;
    
    DatabaseClass database;
    
    
    
    @Override
    protected Void doInBackground(String... params) {

	database = new DatabaseClass(context);
	
	Uri uri = Uri.parse("content://mms-sms/conversations?simple=true");
Log.d("conversations", "ID  +  DATE  +  MESSAGE_COUNT   +   RECIPIENT_IDS  +  SNIPPET  +  READ  +  TYPE  +  ERROR  +  HAS_ATTACHMENT");
	Cursor cur = context.getContentResolver().query(uri, null, null, null, "date DESC");
	while (cur.moveToNext()) {

	    ID = cur.getString(cur.getColumnIndex("_id"));
	    DATE= cur.getString(cur.getColumnIndex("date"));
	    MESSAGE_COUNT = cur.getString(cur.getColumnIndex("message_count"));
	    RECIPIENT_IDS = cur.getString(cur.getColumnIndex("recipient_ids"));
	    SNIPPET = cur.getString(cur.getColumnIndex("snippet"));
	    READ = cur.getString(cur.getColumnIndex("read"));
	    TYPE = cur.getString(cur.getColumnIndex("type"));
	    ERROR = cur.getString(cur.getColumnIndex("error"));
	    HAS_ATTACHMENT= cur.getString(cur.getColumnIndex("has_attachment"));
	    Log.d("conversations", ID + " " +  DATE  + " " + MESSAGE_COUNT  + " " +  RECIPIENT_IDS  + " " +  SNIPPET  + " " +  READ  + " " +  TYPE  + " " +  ERROR  + " " +  HAS_ATTACHMENT);
	}

	publishProgress();

	return null;
    }

    @Override
    protected void onPostExecute(Void result) {

	super.onPostExecute(result);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
	SetupActivity.pb.setProgress(i);
	//SetupActivity.textProgress.setText(i + "%");
	if (b == 0) {
	    SetupActivity.ts.setText("Preparing.");
	    b = 1;
	} else if (b == 1) {
	    SetupActivity.ts.setText("Preparing..");
	    b = 2;
	} else {
	    SetupActivity.ts.setText("Preparing...");
	    b = 0;
	}
	super.onProgressUpdate(values);
    }

}
