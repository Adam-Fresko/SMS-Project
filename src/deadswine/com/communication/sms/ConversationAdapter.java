package deadswine.com.communication.sms;

import java.io.BufferedInputStream;
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
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.PhoneLookup;
import android.provider.ContactsContract.Profile;
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
    static List<String>	   msgList;
    private static LayoutInflater inflater = null;

    public String		 messageCount, messageHasAttachment, messageRead, messageDate, messageSnippet;
    public String		 contactPhone, contactName;
    public String		 photo_id;
    public String		 contactImg;
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
	Log.d("getView(", " sms txt = " + messageSnippet);
	Long timestamp = Long.parseLong(messageDate);
	Calendar calendar = Calendar.getInstance();
	calendar.setTimeInMillis(timestamp);
	Date finaldate = calendar.getTime();
	String smsDate = finaldate.toString();

	listDate.setText(smsDate);

	querrySmsDB();
	querryPeople();
	
	listWithWho.setText(contactName);

	listQuickContactBadge.assignContactFromPhone(contactPhone, false);
	
	listQuickContactBadge.setImageBitmap(getFacebookPhoto(contactPhone));
	
	
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
	final String[] PROJECTION = new String[] { PhoneLookup.DISPLAY_NAME, PhoneLookup.PHOTO_URI };
	Uri personUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, contactPhone);
	Cursor cur = activity.getContentResolver().query(personUri, null, null, null, null);
	if (cur.moveToFirst()) {
	    int nameIdx = cur.getColumnIndex(PhoneLookup.DISPLAY_NAME);
	    int photo = cur.getColumnIndex(PhoneLookup.PHOTO_URI);
	    contactName = cur.getString(nameIdx);
	    // listQuickContactBadge.setImageURI(photo);
	} else {
	    contactName = contactPhone;

	}//
	 // for (int i = 0; i < cur.getColumnCount(); i++) {
	 //Log.v("column names from phones", cur.getColumnName(i).toString());
	 //  }
	cur.close();
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
