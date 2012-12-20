package deadswine.com.communication.sms;



public class oldGetters {

    /*
      public List<String> getSMS() {
	    Log.v(" INDEXX", "" + getShownIndex());

	    Uri uri = Uri.parse("content://mms-sms/conversations?simple=true");

	    Cursor cursor = getActivity().getApplication().getApplicationContext().getContentResolver().query(uri, null, null, null, null);

	    cursor.moveToPosition(getShownIndex());
	    String id = cursor.getString(cursor.getColumnIndex("_id"));
	    String selection = "thread_id = " + id; // + "63"
	    cursor.close();

	    List<String> sms = new ArrayList<String>();

	    // Uri uri =
	    // Uri.parse("content://mms-sms/conversations?simple=true");
	    // Uri uri =
	    // Uri.parse("content://mms-sms/conversations?simple=true"+"/"+"3");
	    uri = Uri.parse("content://sms");

	    // content://mms-sms/conversations/" to "content://mms-sms/conversations"
	    // Cursor cur = getContentResolver().query(uri, null, null, null,
	    // null);
	    final String[] projection = new String[] { "*" };
	    // String selection =
	    // "thread_id = "+FragmentLayoutSupport.thread_ids; //+ "63"
	    Cursor cur = getActivity().getApplication().getApplicationContext().getContentResolver().query(uri, null, selection, null, null);

	    while (cur.moveToNext()) {
		Log.d("inside while", " xxxxxxx");
		// String address =
		// cur.getString(cur.getColumnIndex("address"));
		String phone = cur.getString(cur.getColumnIndexOrThrow("address"));
		int type = cur.getInt(cur.getColumnIndex("type"));// 2 = sent,
								  // etc.
		String date = cur.getString(cur.getColumnIndexOrThrow("date"));
		String body = cur.getString(cur.getColumnIndexOrThrow("body"));
		// columns date = cur.getString(cur.getColumnIndex("date"));
		// String snippet =
		// cur.getString(cur.getColumnIndex("snippet"));
		String ids = cur.getString(cur.getColumnIndex("_id"));
		String thread_ids = cur.getString(cur.getColumnIndex("thread_id"));
		// String msg_id =
		// cur.getString(cur.getColumnIndexOrThrow("msg_id"));
		Log.d("sms test body + date", body + "    date:" + date + "  type:" + type + "   adress:" + phone + "           ID = " + ids + "        thread_ID = " + thread_ids + "        msg_ID = " + "msg_id");
		// sms.add("Number: " + address + " .Message: " + body);
		sms.add(body + "     date:" + date);
	    }
	    // prints columns names
	    for (int i = 0; i < cur.getColumnCount(); i++) {
		Log.v("column names from details activity", cur.getColumnName(i).toString());
	    }

	    return sms;
	}
     */
    
}
