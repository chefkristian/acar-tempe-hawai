package city.stage.com.twinhearts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView meditate_now,meditasi_BI,ask_blessing,group_meditasi,learn_ph;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    private boolean isReceiverRegistered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        meditate_now= (TextView)findViewById(R.id.meditate_now);
        meditasi_BI=(TextView)findViewById(R.id.meditasi_BI);
        ask_blessing=(TextView)findViewById(R.id.ask_blessing);
        group_meditasi=(TextView)findViewById(R.id.group_meditasi);
        learn_ph=(TextView)findViewById(R.id.learn_ph);

        meditate_now.setOnClickListener(this);
        meditasi_BI.setOnClickListener(this);
        ask_blessing.setOnClickListener(this);
        group_meditasi.setOnClickListener(this);
        learn_ph.setOnClickListener(this);


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    Toast.makeText(MainActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this,"Gagal",Toast.LENGTH_SHORT).show();
                }
            }
        };
        registerReceiver();

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }

    }
    private void registerReceiver(){
        if(!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
            isReceiverRegistered = true;
        }
    }
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if  (view.getId() == R.id.meditate_now) {
            Intent intent = new Intent(this,FirsttimeActivity.class);
            intent.putExtra("meditasi","english");
            startActivity(intent);
        }
        if  (view.getId() == R.id.meditasi_BI) {
            Intent intent = new Intent(this,FirsttimeActivity.class);
            intent.putExtra("meditasi","indo");
            startActivity(intent);
        }
        if (view.getId() == R.id.ask_blessing) {
            Intent intent = new Intent(this, AskblessingActivity.class);
            startActivity(intent);
        }
        if (view.getId() == R.id.group_meditasi){
            Intent intent = new Intent(this, GroupmeditasiActivity.class);
            startActivity(intent);
        }
        if (view.getId()==R.id.learn_ph){
            Intent inten = new Intent(this, LearnPhActivity.class);
            startActivity(inten);
        }
    }
}
