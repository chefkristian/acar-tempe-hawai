package city.stage.com.twinhearts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;

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
    Button button_share,button_submit_share;
    EditText et_sharing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared);
        button_submit_share = (Button)findViewById(R.id.button_submit_share);
        button_share = (Button)findViewById(R.id.button_share);
        et_sharing = (EditText)findViewById(R.id.et_sharing);

        button_share.setOnClickListener(this);
        button_submit_share.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        if (view.getId()== R.id.button_share){
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Detik");
            i.putExtra(Intent.EXTRA_TEXT, "http://www.detik.com");
            startActivity(Intent.createChooser(i, "Share URL"));

        }
        else if (view.getId()==R.id.button_submit_share){


            new MyAsyncTask().execute("http://twinheart.stage.city/twinheartapi/saveFeedback");

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
                String share = et_sharing.getEditableText().toString();

//                String fb_id = AccessToken.getCurrentAccessToken().getUserId().toString();


                HttpClient httpClient = new DefaultHttpClient();
                HttpPost request = new HttpPost(url);
                List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
//                postParameters.add(new BasicNameValuePair("device_id",device_id));
                postParameters.add(new BasicNameValuePair("msg", share));
//                postParameters.add(new BasicNameValuePair("fb_id", fb_id));
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
//            gambar_upload.setImageDrawable(null);
            Toast.makeText(getApplicationContext(), "command sent", Toast.LENGTH_LONG).show();
        }
    }



    }

