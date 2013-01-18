package deadswine.com.communication.sms;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;

public class ConversationAdapter extends BaseAdapter {

    ImageView		     imgArrow;

    private static Activity       activity;
    static List<String>	   msgList;
    private static LayoutInflater inflater = null;

    public String		 messageCount, messageHasAttachment, messageRead, messageDate, messageSnippet;
    public String		 contactPhone, contactName;
    public String		 photo_id;
    public String		 contactImg;
    public String		 conversationID;
    
    public static String[][]	     tablicaData;   // 0 = listWithWho // 1 = listBody  // 2 = listDate // 3 = listQuickContactBadge

    public ConversationAdapter(Activity a, List<String> data) {
	activity = a;
	msgList = data;
	inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	tablicaData = new String[msgList.size()][5];

    }
    
   

    public static class ViewHolder {
	public TextView	  listDate, listBody, listWithWho;
	public QuickContactBadge listQuickContactBadge;
	public int	       position;
	public String	    phoneNUmber;

	public String	    messageCount, messageHasAttachment, messageRead, messageDate, messageSnippet;
	public String	    contactPhone, contactName;
	public String	    photo_id;
	public String	    contactImg;
	public String	    conversationID;
	
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

	Log.d("getView", " position = " + position + "  getCount() = " + getCount());
	ViewHolder holder;
	View vi = convertView;
	if (convertView == null) {
	    vi = inflater.inflate(R.layout.conversations_list_row, null);
	    holder = new ViewHolder();
	    holder.listWithWho = (TextView) vi.findViewById(R.id.WithWho);
	    holder.listBody = (TextView) vi.findViewById(R.id.smsBody);
	    holder.listDate = (TextView) vi.findViewById(R.id.date);
	    holder.listQuickContactBadge = (QuickContactBadge) vi.findViewById(R.id.quickContactBadge);

	    vi.setTag(holder);

	} else {
	    holder = (ViewHolder) vi.getTag();
	}

	
	    holder.position = position;
	    
	    if(tablicaData[position][0]==null){
		 new fillConversation(position, holder).execute("");
	    }else {
		 holder.listWithWho.setText(tablicaData[position][0]);
		holder.listBody.setText(tablicaData[position][1]);
		holder.listDate.setText(tablicaData[position][2]);
	    }
	    

	return vi;
    }

    public void querryConversationDB(int position) {

	Log.d("ConversationAdapter", "querryConversationDB() CALLED ");
	final String[] projection = new String[] { "snippet", "message_count", "has_attachment", "read", "date", "_id" };
	Uri uri = Uri.parse("content://mms-sms/conversations?simple=true");
	Cursor cur = activity.getApplicationContext().getContentResolver().query(uri, projection, null, null, "date DESC");
	cur.moveToPosition(position);

	messageSnippet = cur.getString(cur.getColumnIndex("snippet"));
	messageCount = cur.getString(cur.getColumnIndex("message_count"));
	messageHasAttachment = cur.getString(cur.getColumnIndex("has_attachment"));
	messageRead = cur.getString(cur.getColumnIndex("read"));
	messageDate = cur.getString(cur.getColumnIndex("date"));
	conversationID = cur.getString(cur.getColumnIndex("_id"));

    }

    public void querrySmsDB() {
	Log.d("ConversationAdapter", "querrySmsDB() CALLED ");

	final String[] projection = new String[] { "address" };
	String selection = "thread_id = " + conversationID;
	Uri uri = Uri.parse("content://sms");
	Cursor cur = activity.getApplicationContext().getContentResolver().query(uri, projection, selection, null, null);
	cur.moveToFirst();

	contactPhone = cur.getString(cur.getColumnIndexOrThrow("address"));

    }

    public void querryPeople() {
	final String[] PROJECTION = new String[] { PhoneLookup.DISPLAY_NAME };
	Uri personUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, contactPhone);
	Cursor cur = activity.getContentResolver().query(personUri, PROJECTION, null, null, null);
	if (cur.moveToFirst()) {
	    int nameIdx = cur.getColumnIndex(PhoneLookup.DISPLAY_NAME);
	    contactName = cur.getString(nameIdx);
	} else {
	    contactName = contactPhone;
	}
	cur.close();
    }

    private static class fillConversation extends AsyncTask<Object, Object, Object> {
	private int	mPosition;
	private ViewHolder mHolder;

	public fillConversation(int position, ViewHolder holder) {
	    
	    mPosition = position;
	    mHolder = holder;
	  

	}

	@Override
	protected void onPostExecute(Object result) {

	    if (mHolder.position == mPosition) {
		
		Long timestamp = Long.parseLong(mHolder.messageDate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timestamp);
		Date finaldate = calendar.getTime();
		String smsDate = finaldate.toString();
		
		mHolder.listWithWho.setText(mHolder.contactName);
		mHolder.listBody.setText(mHolder.messageSnippet);
		mHolder.listDate.setText(smsDate);
		
		tablicaData[mHolder.position][0] = mHolder.contactName;
		tablicaData[mHolder.position][1] = mHolder.messageSnippet;
		tablicaData[mHolder.position][2] = smsDate;

		mHolder.listQuickContactBadge.assignContactFromPhone(mHolder.contactPhone, false);
		new ThumbnailTask(mHolder.position, mHolder.contactPhone, mHolder).execute("");
	    }
	}

	@Override
	protected Object doInBackground(Object... params) {
	   
	    querryConversationDB(mPosition);
	    querrySmsDB();
	    querryPeople();

	    return null;
	}

	public void querryConversationDB(int position) {

	   
	    final String[] projection = new String[] { "snippet", "message_count", "has_attachment", "read", "date", "_id" };
	    Uri uri = Uri.parse("content://mms-sms/conversations?simple=true");
	    Cursor cur = activity.getApplicationContext().getContentResolver().query(uri, projection, null, null, "date DESC");
	    cur.moveToPosition(position);

	    mHolder.messageSnippet = cur.getString(cur.getColumnIndex("snippet"));
	    mHolder.messageCount = cur.getString(cur.getColumnIndex("message_count"));
	    mHolder.messageHasAttachment = cur.getString(cur.getColumnIndex("has_attachment"));
	    mHolder.messageRead = cur.getString(cur.getColumnIndex("read"));
	    mHolder.messageDate = cur.getString(cur.getColumnIndex("date"));
	    mHolder.conversationID = cur.getString(cur.getColumnIndex("_id"));

	}

	public void querrySmsDB() {
	   

	    final String[] projection = new String[] { "address" };
	    String selection = "thread_id = " + mHolder.conversationID;
	    Uri uri = Uri.parse("content://sms");
	    Cursor cur = activity.getApplicationContext().getContentResolver().query(uri, projection, selection, null, null);
	    cur.moveToFirst();

	    mHolder.contactPhone = cur.getString(cur.getColumnIndexOrThrow("address"));

	}

	public void querryPeople() {
	   
	    final String[] PROJECTION = new String[] { PhoneLookup.DISPLAY_NAME };
	    Uri personUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, mHolder.contactPhone);
	    Cursor cur = activity.getContentResolver().query(personUri, PROJECTION, null, null, null);
	    if (cur.moveToFirst()) {
		int nameIdx = cur.getColumnIndex(PhoneLookup.DISPLAY_NAME);
		mHolder.contactName = cur.getString(nameIdx);
	    } else {
		mHolder.contactName = mHolder.contactPhone;
	    }
	    cur.close();
	}

    }

    /////////////////////////////////////////////////
    private static class ThumbnailTask extends AsyncTask<Object, Object, Object> {
	private int	mPosition;
	private ViewHolder mHolder;
	private String     mPhoneNumber;
	private Bitmap     bitmap;

	public ThumbnailTask(int position, String PhoneNumber, ViewHolder holder) {
	    mPosition = position;
	    mHolder = holder;
	    mPhoneNumber = PhoneNumber;

	}

	@Override
	protected void onPostExecute(Object result) {

	    if (mHolder.position == mPosition) {
		mHolder.listQuickContactBadge.setImageBitmap(bitmap);
	    }
	}

	@Override
	protected Bitmap doInBackground(Object... arg0) {
	    return bitmap = getFacebookPhoto(mPhoneNumber);
	}

	public Bitmap getFacebookPhoto(String phoneNumber) {
	    Uri phoneUri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
	    Uri photoUri = null;
	    ContentResolver cr = activity.getContentResolver();
	    Cursor contact = cr.query(phoneUri, new String[] { ContactsContract.Contacts._ID }, null, null, null);

	    if (contact.moveToFirst()) {
		long userId = contact.getLong(contact.getColumnIndex(ContactsContract.Contacts._ID));
		photoUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, userId);

	    } else {
		// uncnown contact
		Bitmap defaultPhoto = BitmapFactory.decodeResource(activity.getResources(), R.drawable.light_contact_add);
		return defaultPhoto;
	    }
	    if (photoUri != null) {
		InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, photoUri);
		if (input != null) {
		    return BitmapFactory.decodeStream(input);
		}
	    } else {
		// contact nie wiem jaki - jêsli uri jest null?
		Bitmap defaultPhoto = BitmapFactory.decodeResource(activity.getResources(), android.R.drawable.ic_menu_report_image);
		return defaultPhoto;
	    }
	    //contact witchout photo
	    Bitmap defaultPhoto = BitmapFactory.decodeResource(activity.getResources(), R.drawable.light_user_no_img);
	    return defaultPhoto;
	}
    }

}
