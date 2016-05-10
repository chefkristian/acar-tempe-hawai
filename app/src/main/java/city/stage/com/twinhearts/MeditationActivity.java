package city.stage.com.twinhearts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by indomegabyte on 29/03/16.
 */
public class MeditationActivity extends AppCompatActivity {
    TextView twinheart, gmcks, start_time, end_time;
    ImageView imageView;
    Button button_pause, button_play;
    MediaPlayer mediaPlayer,mediaPlayer2;
    SeekBar seekBar,seekBar2;
    private double startTime = 0;
    private double finalTime = 100;
    private Handler myHandler = new Handler();
    public static int oneTimeOnly = 0;
    String bahasa, MY_PREFS_NAME_LOCATION, MY_PREFS_NAME;
    String LongitudePlay,LatitudePlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation);
        button_pause = (Button) findViewById(R.id.button_pause);
        button_play = (Button) findViewById(R.id.button_play);
        start_time = (TextView) findViewById(R.id.start_time);
        end_time = (TextView) findViewById(R.id.end_time);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar2 = (SeekBar)findViewById(R.id.seekBar);






        bahasa = getIntent().getExtras().getString("meditasi");
        if (bahasa != null && bahasa.contentEquals("english")){
            mediaPlayer = MediaPlayer.create(this, R.raw.twin_hearts_eng);
        }
        if (bahasa.contentEquals("indo")){
            mediaPlayer = MediaPlayer.create(this,R.raw.twin_hearts_indo);
        }


        seekBar.setClickable(false);
//        seekBar2.setClickable(false);
        button_pause.setEnabled(false);
        end_time.setVisibility(View.INVISIBLE);
        start_time.setVisibility(View.INVISIBLE);

        seekBar.setProgress((int) startTime);
//        seekBar2.setProgress((int) startTime);
        myHandler.postDelayed(UpdateSongTime, 100);



        button_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    mediaPlayer.start();
                    finalTime = mediaPlayer.getDuration();
                    startTime = mediaPlayer.getCurrentPosition();

                    if (oneTimeOnly == 0) {
                        seekBar.setMax(mediaPlayer.getDuration());
                        oneTimeOnly = 1;
                    }

                    end_time.setText(String.format("%d min, %d sec",
                                    TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                                    TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime)))
                    );

                    start_time.setText(String.format("%d min, %d sec",
                                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime)))
                    );

                seekBar.setProgress((int) startTime);
                myHandler.postDelayed(UpdateSongTime, 100);

                start_time.setVisibility(View.VISIBLE);
                end_time.setVisibility(View.VISIBLE);


                button_pause.setEnabled(true);
                button_play.setEnabled(false);

                SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME_LOCATION, MODE_PRIVATE);
                 LongitudePlay = prefs.getString("longitude", null);
                Log.d("oooooo",LongitudePlay);
                 LatitudePlay = prefs.getString("latitude",null);
                Log.d("pppppp",LatitudePlay);

                new MyAsyncTask(LatitudePlay,LongitudePlay).execute("http://twinheart.stage.city/twinheartapi/savePlay?lat=" + LatitudePlay + "&lng=" + LongitudePlay);

            }



        });

        button_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
                button_pause.setEnabled(false);
                button_play.setEnabled(true);
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

                Intent intent = new Intent(MeditationActivity.this, SharedActivity.class);
                startActivity(intent);


//              seekBar.setMax(mediaPlayer.getDuration());
                button_pause.setEnabled(false);
                button_play.setEnabled(true);
                seekBar.setProgress(0);
//                seekBar2.setProgress(0);
            }
        });

//        mediaPlayer2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mediaPlayer2) {
//
//                Intent intent = new Intent(MeditationActivity.this, SharedActivity.class);
//                startActivity(intent);
//
//
////              seekBar.setMax(mediaPlayer.getDuration());
//                button_pause.setEnabled(false);
//                button_play.setEnabled(true);
////                seekBar.setProgress(0);
//                seekBar2.setProgress(0);
//            }
//        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                if (fromUser && mediaPlayer.isPlaying()) {
                    //myProgress = oprogress;
                    seekBar.setMax(mediaPlayer.getDuration());

                    mediaPlayer.seekTo(progress);
                    seekBar.setProgress(progress);
                }
            }
        });
//        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//
//            }
//
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                // TODO Auto-generated method stub
//                if (fromUser && mediaPlayer2.isPlaying()) {
//                    //myProgress = oprogress;
//                    seekBar.setMax(mediaPlayer2.getDuration());
//
//                    mediaPlayer2.seekTo(progress);
//                    seekBar.setProgress(progress);
//                }
//            }
//        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        seekBar.setMax(mediaPlayer.getDuration());
//        seekBar.setMax(mediaPlayer2.getDuration());
        mediaPlayer.stop();
//        mediaPlayer2.stop();

    }

    @Override
    protected void onRestart() {

        super.onRestart();

     }

    @Override
    protected void onResume() {

        seekBar.setMax(mediaPlayer.getDuration());
//        seekBar.setMax(mediaPlayer2.getDuration());
        super.onResume();

    }

    @Override
    protected void onPause() {
        seekBar.setMax(mediaPlayer.getDuration());
//        seekBar.setMax(mediaPlayer2.getDuration());
        super.onPause();
    }
    @Override
    protected void onStop() {

        super.onStop();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        seekBar.setProgress(0);
//        seekBar.setProgress(0);
        mediaPlayer.stop();
//        mediaPlayer2.stop();

    }

    //

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            start_time.setText(String.format("%d min, %d sec",

                            TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                            TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                            toMinutes((long) startTime)))
            );
            seekBar.setProgress((int) startTime);
            myHandler.postDelayed(this, 100);
        }
//        public void run2() {
//            startTime = mediaPlayer2.getCurrentPosition();
//            start_time.setText(String.format("%d min, %d sec",
//
//                            TimeUnit.MILLISECONDS.toMinutes((long) startTime),
//                            TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
//                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
//                                            toMinutes((long) startTime)))
//            );
//            seekBar2.setProgress((int) startTime);
//            myHandler.postDelayed(this, 100);
//        }
    };

    private class MyAsyncTask extends AsyncTask<String, Void, String> {

        String LatitudePlay,LongitudePlay;

        public  MyAsyncTask(String l1,String l2){
            LatitudePlay = l1;
            LongitudePlay = l2;
        }


        @Override
        protected String doInBackground(String... params) {
//           Log.d("halo1","haloooo1");
//            // TODO Auto-generated method stub
//            postData(params[0]);
//            return null;
            BufferedReader inBuffer = null;
            String url = "http://twinheart.stage.city/twinheartapi/savePlay?lat="+LatitudePlay+"&lng="+LongitudePlay;
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

                Log.d("url with lat long : ", params[0]);
                HttpPost request = new HttpPost(url);

                List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
                postParameters.add(new BasicNameValuePair("device_id", token));
                postParameters.add(new BasicNameValuePair("fb_id", restoredText));
//                postParameters.add(new BasicNameValuePair("fb_name",fb_name));
                postParameters.add(new BasicNameValuePair("type",type));

//                Log.d("halo3", "dev id " + token);
//                Log.d("fb_id", "fb id " + restoredText);
//                Log.d("type", "type id " + type);


//                postParameters.add(new BasicNameValuePair("img", byteArray1));
//                postParameters.add(new BasicNameValuePair("name", params[0]));
//                Log.d("halo3", "haloooo3 "+type);

                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
                        postParameters);
                Log.d("halo4", "haloooo4 ");
                request.setEntity(formEntity);
//                Log.d("halo5", "haloooo5 " + type);

                httpClient.execute(request);
//                Log.d("halo6", "haloooo6 " + type);
                result="got it";
//                Log.d("halo6a", "haloooo6a" + type);


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

        protected void onPostExecute(String result) {
//            pb.setVisibility(View.GONE);
//            et_info.setText("");
//            gambar_upload.setImageDrawable(null);
//            Toast.makeText(getApplicationContext(), "command sent", Toast.LENGTH_LONG).show();
        }
    }

}
