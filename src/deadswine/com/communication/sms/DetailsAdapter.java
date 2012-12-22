package deadswine.com.communication.sms;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.R.bool;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DetailsAdapter extends BaseAdapter {

    TextView		      listDate, listBody, listWithWho;
    RelativeLayout		secondLayout;
    QuickContactBadge	     listQuickContactBadge;
    ImageView		     imgArrow;

    private Activity	      activity;
    static List<String>	   msgList;
    private static LayoutInflater inflater = null;

    public String		 messageCount, messageHasAttachment, messageRead, messageDate, messageSnippet, messageType;
    public String		 contactPhone, contactName;
    public String		 conversationID;

    public String		 ConversationSelection;

    public int		    intWitchConversationShown;

    public Cursor		 curQuerrySmsDB;

    public Uri		    smsUri   = Uri.parse("content://sms");

    public boolean		testXX   = false;

    public DetailsAdapter(Activity a, List<String> data) {
	activity = a;
	msgList = data;
	inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	intWitchConversationShown = FragmentLayoutSupport.DetailsFragment.intWitchConversationShown;
	querryConversationDB();

	testXX = false;
	Log.d("DetailsAdapter", "DetailsAdapter CALLED");
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
	    vi = inflater.inflate(R.layout.specific_conversation_row, null);

	listQuickContactBadge = (QuickContactBadge) vi.findViewById(R.id.quickContactBadge);
	listWithWho = (TextView) vi.findViewById(R.id.WithWho);
	listBody = (TextView) vi.findViewById(R.id.smsBody);
	listDate = (TextView) vi.findViewById(R.id.date);
	secondLayout = (RelativeLayout) vi.findViewById(R.id.Layout2);

	querryConversationDB();
	querrySmsDB(position);

	Long timestamp = Long.parseLong(messageDate);
	Calendar calendar = Calendar.getInstance();
	calendar.setTimeInMillis(timestamp);
	Date finaldate = calendar.getTime();
	String smsDate = finaldate.toString();

	if (messageType.equals("1")) {
	    secondLayout.setBackgroundResource(R.drawable.msgbox_other);
	} else if (messageType.equals("2")) {
	    secondLayout.setBackgroundResource(R.drawable.msgbox_self);
	}

	//
	listBody.setText(messageSnippet);
	listDate.setText(smsDate);

	Log.d("getView()", "position =" + position);

	//listQuickContactBadge.assignContactFromPhone(contactPhone, false);

	return vi;
    }

    public void querrySmsDB(int position) {
	Log.d("Details Adapter", "querrySmsDB() called");

	if (testXX == false) {
	    curQuerrySmsDB = activity.getApplicationContext().getContentResolver().query(smsUri, null, ConversationSelection, null, "date ASC");
	    Log.d("querrySmsDB() called ", " cur count" + curQuerrySmsDB.getCount());
	    testXX = true;
	}

	if (curQuerrySmsDB.moveToPosition(position)) {

	    contactPhone = curQuerrySmsDB.getString(curQuerrySmsDB.getColumnIndexOrThrow("address"));
	    messageDate = curQuerrySmsDB.getString(curQuerrySmsDB.getColumnIndexOrThrow("date"));
	    messageSnippet = curQuerrySmsDB.getString(curQuerrySmsDB.getColumnIndexOrThrow("body"));
	    messageType = curQuerrySmsDB.getString(curQuerrySmsDB.getColumnIndexOrThrow("type"));

	    Log.d(" sms details ", " person=" + curQuerrySmsDB.getString(curQuerrySmsDB.getColumnIndexOrThrow("person")) + "  protocol= " + curQuerrySmsDB.getString(curQuerrySmsDB.getColumnIndexOrThrow("protocol")) + " status = " + curQuerrySmsDB.getString(curQuerrySmsDB.getColumnIndexOrThrow("status")) + "  type= " + curQuerrySmsDB.getString(curQuerrySmsDB.getColumnIndexOrThrow("type")) + " ");

	    Log.d("querrySmsDB() called ", " cur position" + curQuerrySmsDB.getPosition());
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
