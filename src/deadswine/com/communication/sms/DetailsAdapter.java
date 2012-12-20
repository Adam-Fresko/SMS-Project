package deadswine.com.communication.sms;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;

public class DetailsAdapter extends BaseAdapter {

    TextView		      listDate, listBody, listWithWho;
    QuickContactBadge	     listQuickContactBadge;
    ImageView		     imgArrow;

    private Activity	      activity;
    static List<String>	   msgList;
    private static LayoutInflater inflater = null;

    public String		 messageCount, messageHasAttachment, messageRead, messageDate, messageSnippet;
    public String		 contactPhone, contactName;
    public String		 conversationID;

    public String		 ConversationSelection;

    public int		    intWitchConversationShown;
    
    public Cursor curQuerrySmsDB;
    
    public Uri smsUri = Uri.parse("content://sms");

    public DetailsAdapter(Activity a, List<String> data) {
	activity = a;
	msgList = data;
	inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	
	intWitchConversationShown = FragmentLayoutSupport.DetailsFragment.intWitchConversationShown;
	querryConversationDB();
	
	
	 curQuerrySmsDB = activity.getApplicationContext().getContentResolver().query(smsUri, null, ConversationSelection, null, null);
    }

    public int getCount() {
	return msgList.size();
    }

    public Object getItem(int position) {
	return position;
    }

    public long getItemId(int position) {
	return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
	View vi = convertView;
	if (convertView == null)
	    vi = inflater.inflate(R.layout.conversations_list_row, null);

	listQuickContactBadge = (QuickContactBadge) vi.findViewById(R.id.quickContactBadge);
	listWithWho = (TextView) vi.findViewById(R.id.WithWho);
	listBody = (TextView) vi.findViewById(R.id.smsBody);
	listDate = (TextView) vi.findViewById(R.id.date);
	querryConversationDB();
	querrySmsDB();

	Long timestamp = Long.parseLong(messageDate);
	Calendar calendar = Calendar.getInstance();
	calendar.setTimeInMillis(timestamp);
	Date finaldate = calendar.getTime();
	String smsDate = finaldate.toString();

	listBody.setText(messageSnippet);
	listDate.setText(smsDate);

	listQuickContactBadge.assignContactFromPhone(contactPhone, false);

	return vi;
    }

    public void querrySmsDB() {
	Log.d("Details Adapter", "querrySmsDB() called");

	

	
	
	if(curQuerrySmsDB.moveToNext()){
	
	contactPhone =  curQuerrySmsDB.getString( curQuerrySmsDB.getColumnIndexOrThrow("address"));
	messageDate =  curQuerrySmsDB.getString( curQuerrySmsDB.getColumnIndexOrThrow("date"));
	messageSnippet =  curQuerrySmsDB.getString( curQuerrySmsDB.getColumnIndexOrThrow("body"));
	
	}
	
	
    }

    public void querryConversationDB() {
	Log.d("Details Adapter", "querryConversationDB() called");
	
	Uri uri = Uri.parse("content://mms-sms/conversations?simple=true");

	Cursor cursor = activity.getApplicationContext().getContentResolver().query(uri, null, null, null, "date DESC");

	cursor.moveToPosition(intWitchConversationShown);
	String id = cursor.getString(cursor.getColumnIndex("_id"));
	ConversationSelection = "thread_id = " + id; // + "63"
	cursor.close();
    }

}
