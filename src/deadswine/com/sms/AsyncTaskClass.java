package deadswine.com.sms;

import java.util.List;
import deadswine.com.sms.activities.FragmentLayoutSupport;
import deadswine.com.sms.adapters.ConversationAdapter;
import deadswine.com.sms.adapters.DetailsAdapter;
import deadswine.com.sms.database.DataGetters;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

public class AsyncTaskClass extends AsyncTask<String, Void, String> {
    DataGetters  dataGetters = new DataGetters();
    List<String> msgList;
    Activity     activity;

    String[]     test;

    @Override
    protected String doInBackground(String... params) {
	try {
	    test = params;
	    Log.d("ReceiverClass", "ReceiverClass CALLED");
	    Thread.sleep(500);
	    Log.d("ReceiverClass", "ReceiverClass slept 1000");

	} catch (InterruptedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return null;
    }

    @Override
    protected void onPostExecute(String result) {

	try {
	    if (FragmentLayoutSupport.DetailsFragment.widoczny) {
	        activity = FragmentLayoutSupport.DetailsFragment.activity;
	        msgList = dataGetters.getSMS(FragmentLayoutSupport.DetailsFragment.activity.getApplicationContext(), FragmentLayoutSupport.DetailsFragment.intWitchConversationShown);

	        DetailsAdapter.msgList = msgList;

	        FragmentLayoutSupport.DetailsFragment.adapter.notifyDataSetChanged();
	    }
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	try {
	    if (FragmentLayoutSupport.TitlesFragment.widoczny) {
	        
	    	activity = FragmentLayoutSupport.TitlesFragment.activity;
	    	msgList = dataGetters.getCONVERSATIONS(activity.getApplicationContext());

	    	ConversationAdapter.msgList = msgList;

	    	FragmentLayoutSupport.TitlesFragment.adapter.notifyDataSetChanged();

	    }
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    @Override
    protected void onPreExecute() {
	// Analogicznie do metody onPostExecute, implenentujesz czynnosci do zrealizowania przed uruchomieniem w¹tku
    }

    @Override
    protected void onProgressUpdate(Void... values) {
	// Natomiast metoda onProgressUpdate umozliwia aktualizwanie watku g³ównej podczas dzia³ana naszego Wymagaj¹cegoWatku
    }

    public void notify(String[] test2) {

	if (test2[0].equals("1")) {

	} else {

	}
    }
}
