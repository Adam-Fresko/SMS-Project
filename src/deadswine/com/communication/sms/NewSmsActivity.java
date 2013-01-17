package deadswine.com.communication.sms;

import android.os.Bundle;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.actionbarsherlock.widget.SearchView;

public class NewSmsActivity extends SherlockFragmentActivity {
	//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    setTheme(FragmentLayoutSupport.THEME); // Used for theme switching
						   // in samples
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.create_sms_activity_layout);

	  getSupportActionBar().setDisplayShowTitleEnabled(false); // hide title
	   getSupportActionBar().setDisplayHomeAsUpEnabled(false); // set if home icon is navigation
	    getSupportActionBar().setDisplayUseLogoEnabled(false); // donot use logo
	   getSupportActionBar().setDisplayShowHomeEnabled(true); // hide home icon
	    
	   
	}

	// top action bar menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

	    // Create the search view
	    SearchView searchView = new SearchView(getSupportActionBar().getThemedContext());
	    searchView.setQueryHint("Search Recipient");

	    menu.add("Compose").setIcon(R.drawable.ic_compose).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
	    menu.add("Search").setIcon(R.drawable.ic_search).setActionView(searchView).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

	    SubMenu sub = menu.addSubMenu("Theme");
	    sub.add(0, R.style.Theme_Sherlock, 0, "Default");
	    sub.add(0, R.style.Theme_Sherlock_Light, 0, "Light");
	    sub.add(0, R.style.Theme_Sherlock_Light_DarkActionBar, 0, "Light (Dark Action Bar)");
	    sub.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

	    return super.onCreateOptionsMenu(menu);
	}

}