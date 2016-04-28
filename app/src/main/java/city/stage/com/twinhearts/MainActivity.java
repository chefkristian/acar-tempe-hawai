package city.stage.com.twinhearts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookContentProvider;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.maps.model.LatLng;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView meditate_now,meditasi_BI,ask_blessing,group_meditasi,learn_ph;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    private boolean isReceiverRegistered;
    public static Double latitude;
    public static Double longitude;
    String lng,lat;
    String MY_PREFS_NAME;

    String url,gcmtext;

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

        Log.d("Atas", "atas24");
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
//                    Toast.makeText(MainActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(MainActivity.this,"Gagal",Toast.LENGTH_SHORT).show();
                }
            }
        };
        Log.d("Atas","atas23");
        registerReceiver();

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }


//        mulai sini

        Intent intent = new Intent(MainActivity.this, GPSTrackerActivity.class);
        startActivityForResult(intent, 1);

        Log.d("Atas", "atas21");


        Log.d("Atas", "atas22");

//          sampai sini

        try {
            url = getIntent().getExtras().getString("url");
            if (!url.equals("")) {
                Intent intentWebview = new Intent(this, WebViewPh.class);
                intentWebview.putExtra("url", url);
                startActivity(intentWebview);
            }
        }catch (Exception e){

        }

        try{
            gcmtext = getIntent().getExtras().getString("gcm");
            if (!gcmtext.equals("")) {
               Toast.makeText(this,gcmtext,Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){

        }

    }
//get location
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            Bundle extras = data.getExtras();
            Double longitude = extras.getDouble("Longitude");
            lng = Double.toString(longitude);
            Log.d("longitude",lng);
            Double latitude = extras.getDouble("Latitude");
            lat = Double.toString(latitude);
            Log.d("latitude",lat);
        }

        new MyAsyncTask(lat,lng).execute("http://twinheart.stage.city/twinheartapi/saveDevice?lat=" + lat + "&lng=" + lng);

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


    private class MyAsyncTask extends AsyncTask<String, Void, String> {

        String lat,lng;

        public  MyAsyncTask(String l1,String l2){
            lat = l1;
            lng = l2;

        }

        @Override
        protected String doInBackground(String... params) {
            Log.d("halo1","haloooo1");
//            // TODO Auto-generated method stub
//            postData(params[0]);
//            return null;
            BufferedReader inBuffer = null;
            String url = "http://twinheart.stage.city/twinheartapi/saveDevice?lat="+lat+"&lng="+lng;
            String result = "fail";
            Log.d("halo1a","haloooo1a " + url);
            try {

                SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                String fb_id= prefs.getString("access token", ("AccessToken.getCurrentAccessToken().getUserId()"));
                String restoredText = String.valueOf(fb_id);
                Log.d("halo1b","haloooo1b"+restoredText);



                InstanceID instanceID = InstanceID.getInstance(getApplicationContext());
                String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                        GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                Log.d("halo1c","haloooo1c"+token);




//
////                String fb_id = AccessToken.getCurrentAccessToken().getUserId().toString();
//                String fb_name = Profile.getCurrentProfile().toString();
                String type = "android";



                Log.d("halo2","haloooo2");
                HttpClient httpClient = new DefaultHttpClient();

                Log.d("url with lat long : ",params[0]);
                HttpPost request = new HttpPost(url);

                List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
                postParameters.add(new BasicNameValuePair("device_id", token));
                postParameters.add(new BasicNameValuePair("fb_id", restoredText));
//                postParameters.add(new BasicNameValuePair("fb_name",fb_name));
                postParameters.add(new BasicNameValuePair("type",type));

                Log.d("halo3", "dev id " + token);
                Log.d("fb_id", "fb id " + restoredText);
                Log.d("type", "type id " + type);


//                postParameters.add(new BasicNameValuePair("img", byteArray1));
//                postParameters.add(new BasicNameValuePair("name", params[0]));
                Log.d("halo3", "haloooo3 "+type);

                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
                        postParameters);
                Log.d("halo4", "haloooo4 ");
                request.setEntity(formEntity);
                Log.d("halo5", "haloooo5 " + type);

                httpClient.execute(request);
                Log.d("halo6", "haloooo6 " + type);
                result="got it";
                Log.d("halo6a", "haloooo6a" + type);

//                buat Setting awal
                HttpClient httpclient = new DefaultHttpClient();

                // Prepare a request object
                HttpGet httpget = new HttpGet("http://twinheart.stage.city/twinheartapi/getSetting");

                // Execute the request
                HttpResponse response;

                try {
                    response = httpclient.execute(httpget);
                    // Examine the response status
                    Log.d("Status Code",response.getStatusLine().toString());

                    // Get hold of the response entity
                    HttpEntity entity = response.getEntity();
                    // If the response does not enclose an entity, there is no need
                    // to worry about connection release

                    if (entity != null) {

                        // A Simple JSON Response Read
                        InputStream instream = entity.getContent();
                        String settingText= convertStreamToString(instream);
                        // now you have the string representation of the HTML request
                        instream.close();

                        Log.d("Setting text", settingText);


                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE).edit();
                        editor.putString("Setting",settingText);
                        editor.commit();
                    }


                } catch (Exception e) {}
//                buat setting akhir



            } catch(Exception e) {
                Log.d("halo6b", "haloooo6b ");
                // Do something about exceptions
                result = e.getMessage();
                Log.d("halo7", "haloooo7 ");
            } finally {
                if (inBuffer != null) {
                    try {
                        inBuffer.close();
                        Log.d("halo8", "haloooo8 ");
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("halo9", "haloooo9 ");
                    }
                }
            }
            return  result;
        }


        protected void onPostExecute(String result){
//            pb.setVisibility(View.GONE);
//            et_info.setText("");
//            gambar_upload.setImageDrawable(null);
//            Toast.makeText(MainActivity.this, "command sent", Toast.LENGTH_LONG).show();
        }
//
    }


//    tambahan buat setting awal
    private static String convertStreamToString(InputStream is) {
    /*
     * To convert the InputStream to String we use the BufferedReader.readLine()
     * method. We iterate until the BufferedReader return null which means
     * there's no more data to read. Each line will appended to a StringBuilder
     * and returned as String.
     */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
//    tambahan buat setting akhir
}
