package deadswine.com.sms.activities;

import deadswine.com.sms.R;
import deadswine.com.sms.database.QuerrySMS;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

public class SetupActivity extends Activity implements ViewFactory {

  static public ProgressBar pb;
  static public TextView    textProgress;
  static public TextSwitcher ts;
  static public Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_setup);
	
	context = getApplicationContext();
	
	pb = (ProgressBar) findViewById(R.id.progressBar);
	
	 ts = (TextSwitcher) findViewById(R.id.textSwitcher1);
	
	Animation in = AnimationUtils.loadAnimation(this,android.R.anim.fade_in);
	Animation out = AnimationUtils.loadAnimation(this,android.R.anim.fade_out);
	ts.setInAnimation(in);
	ts.setOutAnimation(out);
	
	new QuerrySMS().execute("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.activity_setup, menu);
	return true;
    }

    @Override
    public View makeView() {
	
	return null;
    }

}
