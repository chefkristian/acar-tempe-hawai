package city.stage.com.twinhearts;

import android.*;
import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookContentProvider;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.FileNameMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView meditate_now,meditasi_BI,ask_blessing,group_meditasi,learn_ph,inbox,contact_us, share;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private boolean isReceiverRegistered;
    String lng,lat;
    String MY_PREFS_NAME,MY_PREFS_NAME_LOCATION;
    String url,gcmtext;
    private Tracker mTracker;


//
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
//
//
//    String[] perms = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION" , "android.permission.READ_EXTERNAL_STORAGE" , "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CALL_PHONE"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        sendScreenImageName();

//        getPermissionToReadUserLocation();


//        String[] PERMISSIONS = {android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.CALL_PHONE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
//
//        if(!hasPermissions(this, PERMISSIONS)){
//            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
//        }

            meditate_now = (TextView) findViewById(R.id.meditate_now);
            meditasi_BI = (TextView) findViewById(R.id.meditasi_BI);
            ask_blessing = (TextView) findViewById(R.id.ask_blessing);
            group_meditasi = (TextView) findViewById(R.id.group_meditasi);
            group_meditasi.setVisibility(View.GONE);
            learn_ph = (TextView) findViewById(R.id.learn_ph);
            inbox = (TextView) findViewById(R.id.inbox);
            contact_us = (TextView) findViewById(R.id.contact_us);
            share = (TextView) findViewById(R.id.share);

            meditate_now.setOnClickListener(this);
            meditasi_BI.setOnClickListener(this);
            ask_blessing.setOnClickListener(this);
            group_meditasi.setOnClickListener(this);
            learn_ph.setOnClickListener(this);
            inbox.setOnClickListener(this);
            contact_us.setOnClickListener(this);
            share.setOnClickListener(this);

        if(checkAndRequestPermissions()) {
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
            Log.d("Atas", "atas23");
            registerReceiver();


            if (checkPlayServices()) {
                // Start IntentService to register this application with GCM.
                Intent intent = new Intent(this, RegistrationIntentService.class);
                startService(intent);
            }


            try {
                url = getIntent().getExtras().getString("url");
                if (!url.equals("")) {
                    Intent intentWebview = new Intent(this, WebViewPh.class);
                    intentWebview.putExtra("url", url);
                    startActivity(intentWebview);
                }
            } catch (Exception e) {

            }

            try {
                gcmtext = getIntent().getExtras().getString("gcm");
                if (!gcmtext.equals("")) {
                    Intent intentInbox = new Intent(this, InboxActivity.class);
                    intentInbox.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intentInbox);

                    Toast.makeText(this, gcmtext, Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {

            }

            ////        mulai sini get location

            Intent intent = new Intent(MainActivity.this, GPSTrackerActivity.class);
            startActivityForResult(intent, 1);

            Log.d("Atas", "atas21");

            Log.d("Atas", "atas22");

////          sampai sini



        }

    }

//    public static boolean hasPermissions(Context context, String... permissions) {
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
//            for (String permission : permissions) {
//                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }

    private  boolean checkAndRequestPermissions() {
        int permissionReadExternalStorage = ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE);
        int locationPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionCall = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (permissionCall != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
        }
        if (permissionReadExternalStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d(TAG, "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(android.Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CALL_PHONE, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                            perms.get(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "storage, location, and call services permission granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d(TAG, "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                            showDialogOK("Storage, Call and Location Services Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

//    private static final int READ_LOCATION_PERMISSIONS_REQUEST = 1;
//
//    public void getPermissionToReadUserLocation() {
//        // 1) Use the support library version ContextCompat.checkSelfPermission(...) to avoid
//        // checking the build version since Context.checkSelfPermission(...) is only available
//        // in Marshmallow
//        // 2) Always check for permission (even if permission has already been granted)
//        // since the user can revoke permissions at any time through Settings
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            // The permission is NOT already granted.
//            // Check if the user has been asked about this permission already and denied
//            // it. If so, we want to give more explanation about why the permission is needed.
//            if (shouldShowRequestPermissionRationale(
//                    Manifest.permission.ACCESS_FINE_LOCATION)) {
//                // Show our own UI to explain to the user why we need to read the contacts
//                // before actually requesting the permission and showing the default UI
//            }
//
//            // Fire off an async request to actually get the permission
//            // This will show the standard permission request dialog UI
//            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                    READ_LOCATION_PERMISSIONS_REQUEST);
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           @NonNull String permissions[],
//                                           @NonNull int[] grantResults) {
//        // Make sure it's our original READ_CONTACTS request
//        if (requestCode == READ_LOCATION_PERMISSIONS_REQUEST) {
//            if (grantResults.length == 1 &&
//                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Read Location permission granted", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Read Location permission denied", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//    }


//get location
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && data != null){
            Bundle extras = data.getExtras();
            Double longitude = extras.getDouble("Longitude");
            lng = Double.toString(longitude);
            Log.d("longitude",lng);
            Double latitude = extras.getDouble("Latitude");
            lat = Double.toString(latitude);
            Log.d("latitude",lat);

            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME_LOCATION, MODE_PRIVATE).edit();
            editor.putString("latitude", lat);
            editor.putString("longitude", lng);
            editor.commit();
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

//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

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
        if (view.getId()==R.id.inbox){
            Intent intent = new Intent(this, InboxActivity.class);
            startActivity(intent);
        }
        if (view.getId()==R.id.contact_us){
            Intent intent = new Intent(this, ContactActivity.class);
            startActivity(intent);
        }
        if (view.getId()==R.id.share){
            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            String url_sharing = prefs.getString("url_dishare",null);
            Log.d("zzzzzzz",url_sharing);

            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
            i.putExtra(Intent.EXTRA_TEXT, url_sharing);
            startActivity(Intent.createChooser(i, "Share URL"));
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


                        JSONObject setting_obj = new JSONObject(settingText);

                        JSONArray settingResults = setting_obj.optJSONArray("results");
                        int sizeFirstArr = settingResults.length();

                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE).edit();

                        for (int x= 0; x < sizeFirstArr; x++){
                            JSONObject settingObject = settingResults.getJSONObject(x);
                            if(settingObject.optString("set_id").equals("App_Version_Android")){
                                Log.d("Versi sekarang", settingObject.optString("set_value"));
                                editor.putString("Versi", settingObject.optString("set_value"));
                            }
                            else if (settingObject.optString("set_id").equals("App_URL_Android")){
                                Log.d("url play store",settingObject.optString("set_value"));
                                editor.putString("url_playstore", settingObject.optString("set_value"));
                            }
                            else if (settingObject.optString("set_id").equals("share_url")){
                                Log.d("url_sharing",settingObject.optString("set_value"));
                                editor.putString("url_dishare", settingObject.optString("set_value"));
                            }
                        }
//


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

            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            String CurrentString = prefs.getString("Versi", null);

            String urlUpdate = prefs.getString("url_playstore",null);
            Log.d("zxcvb",urlUpdate);

            Log.d("qwert",CurrentString);
//            String CurrentString = settingObject.optString("set_value");
            String[] separated = CurrentString.split(";");

            Log.d("Versi Number", separated[0]);
            Log.d("Apa harus update", separated[1]);

            Integer versi = Integer.parseInt(separated[0]);
            Integer forceUpdate = Integer.parseInt(separated[1]);

            int versionCode = 0;
            try {
                versionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;


            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            if(versi>versionCode){
                //harus diupdate
                if(forceUpdate == 1){
                    //harus di force, kalu tidak di close
                    initiatePopupWindow();

                }
                else{
                    //popup biasa bisa di cancel
                    initiatePopupWindownoForce();
                }
            }


        }

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



    private void initiatePopupWindow() {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        final String url = prefs.getString("url_playstore",null);


        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("A new Update is Available");
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse
                        (url)));
                dialog.dismiss();
                finish();
            }
        });

        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });

        builder.setCancelable(false);
        builder.show();
    }

    private void initiatePopupWindownoForce() {

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        final String url = prefs.getString("url_playstore",null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("A new Update is Available");
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse
                        (url)));
                dialog.dismiss();

            }
        });

        builder.setNegativeButton("No,thanks", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        builder.setCancelable(false);
        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendScreenImageName();
    }

    private void sendScreenImageName() {

        // [START screen_view_hit]
        Log.i("TAG", "Main Activity");
        mTracker.setScreenName("Main Activity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        // [END screen_view_hit]
    }

}



