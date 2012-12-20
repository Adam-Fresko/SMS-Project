package deadswine.com.communication.sms;


import java.util.List;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

public class ReceiverClass extends AsyncTask<String, Void, String> {
    DataGetters dataGetters = new DataGetters();
	   List<String>	msgList;
	   Activity	    activity;
    @Override
    protected String doInBackground(String... params) {
	try {
	    
	    Log.d("ReceiverClass", "ReceiverClass CALLED");
	    Thread.sleep(1000);
	    Log.d("ReceiverClass", "ReceiverClass slept 2000");
	} catch (InterruptedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return null;
 }
    
    @Override
    protected void onPostExecute(String result) {


	try {
	    Log.d("ReceiverClass", "ReceiverClass onPostExecute called");
	    activity =FragmentLayoutSupport.TitlesFragment.activity;
	    
	    msgList= dataGetters.getCONVERSATIONS(activity.getApplicationContext());
	    
	  ConversationAdapter.msgList = msgList;
	   
	    FragmentLayoutSupport.TitlesFragment.adapter.notifyDataSetChanged();
	   
	  
	    Log.d("ReceiverClass", " adapter size after resetting adapter " + FragmentLayoutSupport.TitlesFragment.adapter.getCount());

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
   }
    
   
