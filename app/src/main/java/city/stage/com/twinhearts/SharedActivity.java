package city.stage.com.twinhearts;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by indomegabyte on 29/03/16.
 */
public class SharedActivity extends AppCompatActivity implements View.OnClickListener {
    Button button_share,button_submit_share, button_share_atas;
    EditText et_sharing;
    String MY_PREFS_NAME;
//    ProgressBar progresBar;
    private ProgressDialog progress;
    LinearLayout linear_share2;
    int flag_linear_share;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_new);
        button_submit_share = (Button)findViewById(R.id.button_submit_share);
        button_share = (Button)findViewById(R.id.button_share);
        et_sharing = (EditText)findViewById(R.id.et_sharing);
//        progresBar = (ProgressBar)findViewById(R.id.progresBar);
        button_share_atas = (Button)findViewById(R.id.button_share_atas);
        linear_share2 = (LinearLayout)findViewById(R.id.linear_share2);

        linear_share2.setVisibility(View.GONE);

        flag_linear_share = 0;

//        progresBar.setVisibility(View.GONE);

        button_share.setOnClickListener(this);
        button_submit_share.setOnClickListener(this);
        button_share_atas.setOnClickListener(this);
        progress=new ProgressDialog(this);






    }

    @Override
    public void onClick(View view) {
        if (view.getId()== R.id.button_share){

            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            String url_sharing = prefs.getString("url_dishare",null);
            Log.d("zzzzzzz",url_sharing);

            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
            i.putExtra(Intent.EXTRA_TEXT, url_sharing);
            startActivity(Intent.createChooser(i, "Share URL"));

        }

        if (view.getId()== R.id.button_share_atas){

            Log.d("share_Atas",Integer.toString(flag_linear_share));
            if (flag_linear_share == 0){
                linear_share2.setVisibility(View.VISIBLE);
                flag_linear_share = 1;
                Log.d("share_Atas_0",Integer.toString(flag_linear_share));
            }
            else if (flag_linear_share == 1) {
                linear_share2.setVisibility(View.GONE);
                flag_linear_share = 0;
                Log.d("share_Atas_1",Integer.toString(flag_linear_share));
            }

        }
        else if (view.getId()==R.id.button_submit_share){

            if(et_sharing.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "Please fill your meditation experience", Toast.LENGTH_LONG).show();

            }
            else {

//                progresBar.setVisibility(View.VISIBLE);
                progress.setMessage("Submitting your experience");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                progress.setProgress(0);
                progress.show();
                new MyAsyncTask().execute("http://twinheart.stage.city/twinheartapi/saveFeedback");
            }


                //get message from message box
//                String  share = et_sharing.getText().toString();
//
//                //check whether the msg empty or not
//                if(share.length()>0) {
//                    HttpClient httpclient = new DefaultHttpClient();
//                    HttpPost httppost = new HttpPost("http://twinheart.stage.city/twinheartapi/saveFeedback");
//
//                    try {
//                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
////                        nameValuePairs.add(new BasicNameValuePair("id_device", "01"));
//                        nameValuePairs.add(new BasicNameValuePair("msg", share));
//                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//                        httpclient.execute(httppost);
////                        et_sharing.setText(""); //reset the message text field
//                        Toast.makeText(getBaseContext(), "Sent", Toast.LENGTH_SHORT).show();
//                    } catch (ClientProtocolException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    //display message if text field is empty
//                    Toast.makeText(getBaseContext(),"All fields are required",Toast.LENGTH_SHORT).show();
//                }
            }


        }
    private class MyAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
//            // TODO Auto-generated method stub
//            postData(params[0]);
//            return null;
            BufferedReader inBuffer = null;
            String url = "http://twinheart.stage.city/twinheartapi/saveFeedback";
            String result = "fail";
            try {

                SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                String fb_id= prefs.getString("access token", ("AccessToken.getCurrentAccessToken().getUserId()"));
                String restoredText = String.valueOf(fb_id);

                String share = et_sharing.getEditableText().toString();

                InstanceID instanceID = InstanceID.getInstance(getApplicationContext());
                String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                        GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);




                HttpClient httpClient = new DefaultHttpClient();
                HttpPost request = new HttpPost(url);
                List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
//                postParameters.add(new BasicNameValuePair("device_id",device_id));
                postParameters.add(new BasicNameValuePair("msg", share));
                postParameters.add(new BasicNameValuePair("fb_id", restoredText));
                postParameters.add(new BasicNameValuePair("device_id",token));
                postParameters.add(new BasicNameValuePair("name", params[0]));

                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
                        postParameters);

                request.setEntity(formEntity);
                httpClient.execute(request);
                result = "got it";

            } catch (Exception e) {
                // Do something about exceptions
                result = e.getMessage();
            } finally {
                if (inBuffer != null) {
                    try {
                        inBuffer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return result;
        }

        protected void onPostExecute(String result) {
//            pb.setVisibility(View.GONE);
            et_sharing.setText("");
//            progresBar.setVisibility(View.GONE);
            progress.dismiss();
//            gambar_upload.setImageDrawable(null);
            Toast.makeText(getApplicationContext(), "Your experience has been successfully sent", Toast.LENGTH_LONG).show();
            Intent i = new Intent(SharedActivity.this,MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        }
    }



    }


