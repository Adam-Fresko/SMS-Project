/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package deadswine.com.communication.sms;

import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.actionbarsherlock.widget.SearchView;

/**
 * Demonstration of using fragments to implement different activity layouts.
 * This sample provides a different layout (and activity flow) when run in
 * landscape.
 */
public class FragmentLayoutSupport extends SherlockFragmentActivity {

    public static int      THEME      = R.style.Theme_Sherlock;
    private static Context context;
    public static String   thread_ids;
    public static int      isOpen     = 0;

    public static String   tagDetails = "tagDetails";
    public static String   tagTitles  = "tagTitles";
    public static String   tagSms     = "tagTitles";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	setTheme(THEME); // Used for theme switching in samples
	super.onCreate(savedInstanceState);

	setContentView(R.layout.fragment_layout_support);

	context = getApplicationContext();
	getSupportActionBar().setDisplayShowTitleEnabled(false); // hide title

	getSupportActionBar().setDisplayHomeAsUpEnabled(false); // set if home icon is navigation
	getSupportActionBar().setDisplayUseLogoEnabled(false); // donot use logo
	getSupportActionBar().setDisplayShowHomeEnabled(true); // hide home icon

	isOpen = 1;

    }

    @Override
    protected void onPause() {
	super.onPause();
	Log.d("FragmentLayoutSupport", "CALLED -- onPause()");

	isOpen = 0;

    }

    @Override
    protected void onPostResume() {
	super.onPostResume();
	Log.d("FragmentLayoutSupport", "CALLED -- onPostResume()");

	new ReceiverClass().execute("");
    }

    public static Context getAppContext() {
	return FragmentLayoutSupport.context;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

	if (item.getTitle() == "Compose") {
	    Log.d("cccccccccccccccccccccccccccccc", "compose");
	    Intent intent = new Intent();
	    intent.setClass(this, NewSmsActivity.class);

	    startActivity(intent);

	}

	return super.onOptionsItemSelected(item);
    }

    /**
     * This is the "top-level" fragment, showing a list of items that the user
     * can pick. Upon picking an item, it takes care of displaying the data to
     * the user as appropriate based on the currrent UI layout.
     */
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static class TitlesFragment extends SherlockListFragment {
	static boolean	     mDualPane;
	int			mCurCheckPosition = 0;

	static ConversationAdapter adapter;

	static Activity	    activity;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);

	    activity = getActivity();
	    DataGetters dataGetters = new DataGetters();

	    List<String> msgList = dataGetters.getCONVERSATIONS(activity.getApplicationContext());

	    adapter = new ConversationAdapter(activity, msgList);

	    setListAdapter(adapter);

	    // Check to see if we have a frame in which to embed the details
	    // fragment directly in the containing UI.
	    View detailsFrame = getActivity().findViewById(R.id.details);

	    mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

	    if (savedInstanceState != null) {
		// Restore last state for checked position.
		mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
	    }

	    if (mDualPane) {
		// In dual-pane mode, the list view highlights the selected
		// item.
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		// Make sure our UI is in the correct state.
		showDetails(mCurCheckPosition);

	    }
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    outState.putInt("curChoice", mCurCheckPosition);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

	    showDetails(position);
	}

	/**
	 * Helper function to show the details of a selected item, either by
	 * displaying a fragment in-place in the current UI, or starting a whole
	 * new activity in which it is displayed.
	 */
	void showDetails(int index) {
	    mCurCheckPosition = index;

	    if (mDualPane) {
		// We can display everything in-place with fragments, so update
		// the list to highlight the selected item and show the data.
		getListView().setItemChecked(index, true);

		// Check what fragment is currently shown, replace if needed.
		DetailsFragment details = (DetailsFragment) getFragmentManager().findFragmentById(R.id.details);
		if (details == null || details.getShownIndex() != index) {
		    // Make new fragment to show this selection.
		    details = DetailsFragment.newInstance(index);

		    // Execute a transaction, replacing any existing fragment
		    // with this one inside the frame.
		    FragmentTransaction ft = getFragmentManager().beginTransaction();

		    ft.replace(R.id.details, details);

		    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		    ft.commit();
		}

	    } else {
		// Otherwise we need to launch a new activity to display
		// the dialog fragment with selected text.
		Intent intent = new Intent();
		intent.setClass(getActivity(), DetailsActivity.class);
		intent.putExtra("index", index);
		startActivity(intent);
	    }
	}
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * This is a secondary activity, to show what the user has selected when the
     * screen is not large enough to show it all in one activity.
     */

    public static class DetailsActivity extends SherlockFragmentActivity {
	//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    setTheme(FragmentLayoutSupport.THEME); // Used for theme switching
						   // in samples
	    super.onCreate(savedInstanceState);

	    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
		// If the screen is now in landscape mode, we can show the
		// dialog in-line with the list so we don't need this activity.
		finish();
		return;

	    }

	    getSupportActionBar().setDisplayShowTitleEnabled(false); // hide title
	    getSupportActionBar().setDisplayHomeAsUpEnabled(true); // set if home icon is navigation
	    getSupportActionBar().setDisplayUseLogoEnabled(false); // donot use logo
	    getSupportActionBar().setDisplayShowHomeEnabled(true); // hide home icon

	    if (savedInstanceState == null) {
		// During initial setup, plug in the details fragment.
		DetailsFragment details = new DetailsFragment();
		details.setArguments(getIntent().getExtras());
		getSupportFragmentManager().beginTransaction().add(android.R.id.content, details).commit();
	    }
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

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * This is the secondary fragment, displaying the details of a particular
     * item.
     */

    public static class DetailsFragment extends SherlockFragment {
	/**
	 * Create a new instance of DetailsFragment, initialized to show the
	 * text at 'index'.
	 */

	static DetailsAdapter adapter;
	static Activity       activity;
	static int	    intWitchConversationShown;

	public static DetailsFragment newInstance(int index) {
	    DetailsFragment f = new DetailsFragment();

	    // Supply index input as an argument.
	    Bundle args = new Bundle();
	    args.putInt("index", index);
	    f.setArguments(args);

	    return f;
	}

	public int getShownIndex() {

	    return getArguments().getInt("index", 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    if (container == null) {
		// We have different layouts, and in one of them this
		// fragment's containing frame doesn't exist. The fragment
		// may still be created from its saved state, but there is
		// no reason to try to create its view hierarchy because it
		// won't be displayed. Note this is not needed -- we could
		// just run the code below, where we would create and return
		// the view hierarchy; it would just never be used.
		return null;
	    }
	    intWitchConversationShown = getShownIndex();
	    activity = getActivity();
	    DataGetters dataGetters = new DataGetters();

	    ListView coversacja = new ListView(getActivity());
	    List<String> msgList = dataGetters.getSMS(activity.getApplicationContext(), getShownIndex());

	    adapter = new DetailsAdapter(activity, msgList);
	    coversacja.setAdapter(adapter);
	    coversacja.setTranscriptMode(2);

	    return coversacja;

	}

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
