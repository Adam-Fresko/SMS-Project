package deadswine.com.sms.activities;


import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import deadswine.com.sms.R;


public class NewSmsActivity extends SherlockFragmentActivity implements OnClickListener {

    Button   btnSend;
    EditText edtAddress;
    EditText edtBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	setTheme(FragmentLayoutSupport.THEME); // Used for theme switching
					       // in samples
	super.onCreate(savedInstanceState);
	setContentView(R.layout.create_sms_activity_layout);

	getSupportActionBar().setDisplayShowTitleEnabled(false); // hide title
	getSupportActionBar().setDisplayHomeAsUpEnabled(true); // set if home icon is navigation
	getSupportActionBar().setDisplayUseLogoEnabled(false); // donot use logo
	getSupportActionBar().setDisplayShowHomeEnabled(true); // hide home icon

	btnSend = (Button) findViewById(R.id.btnSEND);
	btnSend.setOnClickListener(this);
	edtAddress = (EditText) findViewById(R.id.editTextAddContact);
	edtBody = (EditText) findViewById(R.id.editTextSMS);

    }

    // top action bar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

	// Create the search view
	// SearchView searchView = new SearchView(getSupportActionBar().getThemedContext());
	// searchView.setQueryHint("Search Recipient");

	// menu.add("Compose").setIcon(R.drawable.ic_compose).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
	//menu.add("Search").setIcon(R.drawable.ic_search).setActionView(searchView).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

	// SubMenu sub = menu.addSubMenu("Theme");
	// sub.add(0, R.style.Theme_Sherlock, 0, "Default");
	// sub.add(0, R.style.Theme_Sherlock_Light, 0, "Light");
	// sub.add(0, R.style.Theme_Sherlock_Light_DarkActionBar, 0, "Light (Dark Action Bar)");
	// sub.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

	return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

	switch (item.getItemId()) {
	    case android.R.id.home:
		// app icon in action bar clicked; go home
		Intent intent = new Intent(this, FragmentLayoutSupport.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		return true;
	    default:
		return super.onOptionsItemSelected(item);
	}

    }

    @Override
    public void onClick(View v) {

	switch (v.getId()) {
	    case R.id.btnSEND:
		sendSMS(edtAddress.getText().toString(), edtBody.getText().toString());
		break;

	}

    }

    private void sendSMS(String phoneNumber, String message) {
	Log.v("phoneNumber", phoneNumber);
	Log.v("MEssage", message);
	SmsManager sms = SmsManager.getDefault();
	sms.sendTextMessage(phoneNumber, null, message, null, null);
	super.onBackPressed();
    }

}