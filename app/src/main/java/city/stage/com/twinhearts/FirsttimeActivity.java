package city.stage.com.twinhearts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by indomegabyte on 28/03/16.
 */
public class FirsttimeActivity extends AppCompatActivity implements View.OnClickListener {
    Button button_ya, button_tidak;
String bahasa;
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firsttime);
        button_ya = (Button) findViewById(R.id.button_ya);
        button_tidak = (Button) findViewById(R.id.button_tidak);

        button_ya.setOnClickListener(this);
        button_tidak.setOnClickListener(this);

        bahasa = getIntent().getExtras().getString("meditasi");

        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        sendScreenImageName();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_ya) {
            Intent intent = new Intent(this, ScreenSlidePagerActivity.class);
            intent.putExtra("answer",0);
            intent.putExtra("meditasi",bahasa);
            startActivity(intent);
        }
        else if (view.getId() == R.id.button_tidak) {
            Intent i = new Intent(this, ScreenSlidePagerActivity.class);
            i.putExtra("answer",1);
            i.putExtra("meditasi",bahasa);
            startActivity(i);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendScreenImageName();
    }

    private void sendScreenImageName() {

        // [START screen_view_hit]
        Log.i("TAG", "First time Activity");
        mTracker.setScreenName("First time Activity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        // [END screen_view_hit]
    }
}
