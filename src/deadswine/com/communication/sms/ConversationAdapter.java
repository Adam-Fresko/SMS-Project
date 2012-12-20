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

public class ConversationAdapter extends BaseAdapter {

    TextView		      listDate, listBody, listWithWho;
    QuickContactBadge	     listQuickContactBadge;
    ImageView		     imgArrow;

    private Activity	      activity;
    static List<String>	  msgList;
    private static LayoutInflater inflater = null;

    public String		 messageCount, messageHasAttachment, messageRead, messageDate,messageSnippet;
    public String		 contactPhone, contactName;
    public String		 conversationID;

    public ConversationAdapter(Activity a, List<String> data) {
	activity = a;
	msgList = data;
	inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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

	querryConversationDB(position);

	
	
	listBody.setText(messageSnippet);
	Log.d("getView(", " sms txt = "+messageSnippet);
	Long timestamp = Long.parseLong(messageDate);
	Calendar calendar = Calendar.getInstance();
	calendar.setTimeInMillis(timestamp);
	Date finaldate = calendar.getTime();
	String smsDate = finaldate.toString();

	listDate.setText(smsDate);

	querrySmsDB();
//
	listWithWho.setText(contactPhone);
	
	listQuickContactBadge.assignContactFromPhone(contactPhone, false);
	querryPeople();
	return vi;
    }

    public void querryConversationDB(int position) {

	Log.d("ConversationAdapter", "querryConversationDB() CALLED ");

	Uri uri = Uri.parse("content://mms-sms/conversations?simple=true");
	Cursor cur = activity.getApplicationContext().getContentResolver().query(uri, null, null, null, "date DESC");
	cur.moveToPosition(position);

	messageSnippet = cur.getString(cur.getColumnIndex("snippet"));
	messageCount = cur.getString(cur.getColumnIndex("message_count"));
	messageHasAttachment = cur.getString(cur.getColumnIndex("has_attachment"));
	messageRead = cur.getString(cur.getColumnIndex("read"));
	messageDate = cur.getString(cur.getColumnIndex("date"));
	conversationID = cur.getString(cur.getColumnIndex("_id"));
	Log.d("xxxx", conversationID);

    }

    public void querrySmsDB() {
	Log.d("ConversationAdapter", "querrySmsDB() CALLED ");
	
	final String[] projection = new String[] { "*" };
	String selection = "thread_id = " + conversationID;
	Uri uri = Uri.parse("content://sms");
	Cursor cur = activity.getApplicationContext().getContentResolver().query(uri, projection, selection, null, null);
	cur.moveToFirst();
	Log.d("xxxx", conversationID);
	contactPhone = cur.getString(cur.getColumnIndexOrThrow("address"));
	//contactName = cur.getString(cur.getColumnIndex("person"));

    }

    public void querryPeople() {
	Log.d("ConversationAdapter", "querryPeople() CALLED ");
	
	
    }

}
