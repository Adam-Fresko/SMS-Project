package deadswine.com.sms.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;


public class DatabaseClass extends SQLiteOpenHelper {

    private static final int    DATABASE_VERSION	    = 1;
    private static final String DATABASE_NAME_CONVERSATIONS = "messageDB";

    private static final String CONVERSATIONS_TABLE_NAME = "ConversationsTable";
    
    /*
    01-23 06:25:28.470: V/column names from sms conversations(26544): _id
    01-23 06:25:28.470: V/column names from sms conversations(26544): date
    01-23 06:25:28.470: V/column names from sms conversations(26544): message_count
    01-23 06:25:28.470: V/column names from sms conversations(26544): recipient_ids
    01-23 06:25:28.470: V/column names from sms conversations(26544): snippet
    01-23 06:25:28.470: V/column names from sms conversations(26544): read
    01-23 06:25:28.470: V/column names from sms conversations(26544): type
    01-23 06:25:28.470: V/column names from sms conversations(26544): error
    01-23 06:25:28.470: V/column names from sms conversations(26544): has_attachment
    01-23 06:25:28.470: V/column names from sms conversations(26544): unread_count
    //////////////////////////////////////
    01-23 06:26:32.631: V/column names from sms conversations(327): _id
    01-23 06:26:32.631: V/column names from sms conversations(327): date
    01-23 06:26:32.631: V/column names from sms conversations(327): message_count
    01-23 06:26:32.631: V/column names from sms conversations(327): recipient_ids
    01-23 06:26:32.631: V/column names from sms conversations(327): snippet
    01-23 06:26:32.631: V/column names from sms conversations(327): snippet_cs
    01-23 06:26:32.631: V/column names from sms conversations(327): read
    01-23 06:26:32.641: V/column names from sms conversations(327): type
    01-23 06:26:32.641: V/column names from sms conversations(327): error
    01-23 06:26:32.641: V/column names from sms conversations(327): has_attachment
    */
    static String ID = "id";
    static String DATE = "date";
    static String  MESSAGE_COUNT = "message_count";
    static String RECIPIENT_IDS= "recipient_ids";
    static String SNIPPET = "snippet";
    static String READ = "read";
    static String TYPE = "type";
    static String ERROR = "error";
    static String HAS_ATTACHMENT = "has_attachment";
    
  //  private static final String DICTIONARY_TABLE_CREATE     = "CREATE TABLE " + CONVERSATIONS_TABLE_NAME + " (" + KEY_WORD + " TEXT, " + KEY_DEFINITION + " TEXT);";
   private static final String CONVERSATIONS_TABLE_CREATE = "create table " + CONVERSATIONS_TABLE_NAME + "( " + BaseColumns._ID + " integer primary key autoincrement, " 
 + ID+ " integer, "    
 + DATE+ " integer, " 
 + MESSAGE_COUNT + " integer, " 
 + RECIPIENT_IDS + "integer,"
 + SNIPPET + "text,"
 + READ + "integer,"
 +TYPE + "integer,"
 +ERROR +"integer,"
 +HAS_ATTACHMENT + "integer," 
 +
		"+);";

    DatabaseClass(Context context) {
	super(context, DATABASE_NAME_CONVERSATIONS, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
	db.execSQL(CONVERSATIONS_TABLE_NAME);
	Log.d("EventsData", "onCreate: " + CONVERSATIONS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	

    }
}