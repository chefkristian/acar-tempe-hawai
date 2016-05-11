package city.stage.com.twinhearts;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by indomegabyte on 31/03/16.
 */
public class AskblessingActivity extends AppCompatActivity {

    TextView nama_profile;
    Button button_submit;
    ImageView button_upload, gambar_upload;
    LoginButton button_facebook;
    private CallbackManager callbackManager;
    ProfilePictureView profilePictureView;
    EditText et_info, et_info2;
    AccessTokenTracker accessTokenTracker;
    AccessToken accessToken;
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString, MY_PREFS_NAME;
    Profile profile;
    ProfileTracker profileTracker;
    public static String URL = "<http://twinheart.stage.city/twinheartapi/saveBlessing>";
    Bitmap bitmap;
    SharedPreferences pref;
    private ProgressDialog progress;
    LinearLayout linear_ask2;
    int flag_gambar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        flag_gambar = 0;

        setContentView(R.layout.activity_askblessing);
        et_info = (EditText) findViewById(R.id.info);
        et_info2 = (EditText)findViewById(R.id.info2);
        nama_profile = (TextView) findViewById(R.id.nama_profile);
        button_submit = (Button) findViewById(R.id.button_submit);
        button_upload = (ImageView) findViewById(R.id.button_upload);
        gambar_upload = (ImageView) findViewById(R.id.gambar_upload);
//        button_camera = (ImageView) findViewById(R.id.button_camera);
        button_facebook = (LoginButton) findViewById(R.id.button_facebook);
        profilePictureView = (ProfilePictureView) findViewById(R.id.profil_image);
        linear_ask2 = (LinearLayout)findViewById(R.id.linear_ask2);


        profilePictureView.setVisibility(View.GONE);
        et_info.setVisibility(View.GONE);
        nama_profile.setVisibility(View.GONE);
        button_submit.setVisibility(View.GONE);
//        button_camera.setVisibility(View.GONE);
        button_upload.setVisibility(View.GONE);
        gambar_upload.setVisibility(View.GONE);
        linear_ask2.setVisibility(View.GONE);

        progress=new ProgressDialog(this);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        // ini check login ga
        if (accessToken != null) {
            profile = Profile.getCurrentProfile();
            nama_profile.setText(profile.getName() + "");
            profilePictureView.setVisibility(View.VISIBLE);
            profilePictureView.setProfileId(accessToken.getUserId());
            et_info.setVisibility(View.VISIBLE);
            nama_profile.setVisibility(View.VISIBLE);
            button_submit.setVisibility(View.VISIBLE);
            button_upload.setVisibility(View.VISIBLE);
//                button_camera.setVisibility(View.VISIBLE);
            gambar_upload.setVisibility(View.VISIBLE);
            linear_ask2.setVisibility(View.VISIBLE);
        }

        accessTokenTracker = new AccessTokenTracker() {

            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {

                if (currentAccessToken == null) {
                    //User logged out
                    profilePictureView.setVisibility(View.GONE);
                    et_info.setVisibility(View.GONE);
                    button_submit.setVisibility(View.GONE);
                    button_upload.setVisibility(View.GONE);
//                    button_camera.setVisibility(View.GONE);
                    gambar_upload.setVisibility(View.GONE);
                    nama_profile.setVisibility(View.GONE);
                    linear_ask2.setVisibility(View.GONE);

                } else {


                }
            }

            // Set the access token using
            // currentAccessToken when it's loaded or set.

        };


        button_facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {


            @Override
            public void onSuccess(LoginResult loginResult) {

                profile = Profile.getCurrentProfile();
                nama_profile.setText(profile.getName() + "");
                profilePictureView.setVisibility(View.VISIBLE);
                profilePictureView.setProfileId(loginResult.getAccessToken().getUserId());
                et_info.setVisibility(View.VISIBLE);
                nama_profile.setVisibility(View.VISIBLE);
                button_submit.setVisibility(View.VISIBLE);
                button_upload.setVisibility(View.VISIBLE);
//                button_camera.setVisibility(View.VISIBLE);
                gambar_upload.setVisibility(View.VISIBLE);
                linear_ask2.setVisibility(View.VISIBLE);



                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE).edit();
                editor.putString("access token", (AccessToken.getCurrentAccessToken().getUserId()));
                editor.commit();




            }


            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Login attemp canceled", Toast.LENGTH_SHORT).show();
//                info.setText("Login attempt canceled.");

            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(getApplicationContext(), "Login attemp failed", Toast.LENGTH_SHORT).show();
//                info.setText("Login attempt failed.");

            }

        });

        button_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

// Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);

            }
        });

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int err = 0;
                String errmsg = new String();
                //if check semua form

                if(flag_gambar == 0){
                    err++;
                    errmsg += "Please upload Image";
//                    Toast.makeText(getApplicationContext(), "Please upload picture of the person you want to be blessed ", Toast.LENGTH_LONG).show();
                }
                if(et_info.getText().toString()==null || et_info.getText().toString().trim().equals("") ){

                    if(err > 0)
                    errmsg = errmsg+"\r\n";

                    errmsg += "Please fill the name";
                    err = err + 1;
//                    Toast.makeText(getApplicationContext(), "Please fill the full name of the person you want to be blessed ", Toast.LENGTH_LONG).show();
                }
                if(et_info2.getText().toString() == null || et_info2.getText().toString().trim().equals("")){

                    if(err > 0)
                    errmsg += "\r\n";

                    errmsg += "Please fill the goal/problem";
                    err++;
//                    Toast.makeText(getApplicationContext(), "Please address the goal/problem to be blessed ", Toast.LENGTH_LONG).show();
                }
                if(err == 0) {

                    progress.setMessage("Submitting Blessing");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setIndeterminate(true);
                    progress.setProgress(0);
                    progress.show();


                    new MyAsyncTask().execute("http://twinheart.stage.city/twinheartapi/saveBlessing");
                }else{
                    Toast.makeText(getApplicationContext(), errmsg, Toast.LENGTH_LONG).show();

                }



//
//                String info = et_info.getEditableText().toString();
//
//                String fb_id = AccessToken.getCurrentAccessToken().getUserId().toString();
//
////                Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(), R.id.gambar_upload);
//                bitmap = ((BitmapDrawable) gambar_upload.getDrawable()).getBitmap();
//                ByteArrayOutputStream bao = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
//                byte [] ba = bao.toByteArray();
//                String ba1= Base64.encodeToString(ba, Base64.DEFAULT);

//                HttpClient httpclient = new DefaultHttpClient();
//                String responseStr="";
////                String URL= SyncStateContract.Constants.API_URL+"details/";
//                HttpPost httppost = new HttpPost("http://twinheart.stage.city/twinheartapi/saveBlessing");
//
//                try {
//                    // Add your data
//                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//                    nameValuePairs.add(new BasicNameValuePair("msg", info));
//                    nameValuePairs.add(new BasicNameValuePair("fb_id", fb_id));
//                    nameValuePairs.add(new BasicNameValuePair("img", ba1));
//                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//
//                    // Execute HTTP Post Request
//                    HttpResponse response = httpclient.execute(httppost);
//
//                    int responseCode = response.getStatusLine().getStatusCode();
//                    switch(responseCode) {
//                        case 200:
//                            HttpEntity entity = response.getEntity();
//                            if(entity != null) {
//                                String responseBody = EntityUtils.toString(entity);
//                                responseStr=responseBody;
//                            }
//                            break;
//                    }
//                } catch (ClientProtocolException e) {
//                    // TODO Auto-generated catch block
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                }
//                System.out.println("this is response "+responseStr);
//
//                if (info.length() > 0) {
//                    HttpClient httpclient = new DefaultHttpClient();
//                    HttpPost httppost = new HttpPost("http://twinheart.stage.city/twinheartapi/saveBlessing");
//
//                    try {
//                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
////                        nameValuePairs.add(new BasicNameValuePair("id_device", "01"));
//                        nameValuePairs.add(new BasicNameValuePair("msg", info));
//                        nameValuePairs.add(new BasicNameValuePair("fb_id", fb_id));
//                        nameValuePairs.add(new BasicNameValuePair("img",ba1));
//                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//
//                        HttpResponse response = httpclient.execute(httppost);
//
//                        et_info.setText(""); //reset the message text field
//                        Toast.makeText(getBaseContext(), "Sent", Toast.LENGTH_SHORT).show();
//                    } catch (ClientProtocolException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    //display message if text field is empty
//                    Toast.makeText(getBaseContext(), "All fields are required", Toast.LENGTH_SHORT).show();
//                }
            }
        });


//        button_upload.setOnClickListener(this);
//        button_camera.setOnClickListener(this);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);


        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

//                Bitmap bMap= BitmapFactory.decodeFile(filePathColumn[0]);
//                Bitmap out = Bitmap.createScaledBitmap(bMap, 150, 150, false);
//                File resizedFile = new File(imageStorageDir, "resize.png");

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();


                ImageView imgView = (ImageView) findViewById(R.id.gambar_upload);

                // Set the Image in ImageView after decoding the String
                imgView.setImageBitmap(decodeSampledBitmapFromResource(imgDecodableString,
                        150, 150));

                flag_gambar = 1;
//                imgView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));


            } else {
//                Toast.makeText(this, "You haven't picked Image",
//                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
//            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
//                    .show();
        }

    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }


    public static Bitmap decodeSampledBitmapFromResource(String path,
                                                         int reqWidth, int reqHeight) {
        Log.d("path", path);
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 150,
                150);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }


    private class MyAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
//            // TODO Auto-generated method stub
//            postData(params[0]);
//            return null;
            BufferedReader inBuffer = null;
            String url = "http://twinheart.stage.city/twinheartapi/saveBlessing";
            String result = "fail";
            try {
                String info = et_info.getEditableText().toString();

                String info2 = et_info2.getEditableText().toString();

                String fb_id = AccessToken.getCurrentAccessToken().getUserId().toString();


                gambar_upload.buildDrawingCache();
                Bitmap bmp = gambar_upload.getDrawingCache();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG,100,stream);
                byte [] byteArray = stream.toByteArray();
                String byteArray1 = Base64.encodeToString(byteArray,Base64.DEFAULT);
                Log.d("bas64",byteArray1);

//                Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(), R.id.gambar_upload);
//                bitmap = ((BitmapDrawable) gambar_upload.getDrawable()).getBitmap();
//                ByteArrayOutputStream bao = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
//                byte [] ba = bao.toByteArray();
//                String ba1= Base64.encodeToString(ba, Base64.DEFAULT);
//                Log.d(ba1, "gambar upload");


                HttpClient httpClient = new DefaultHttpClient();
                HttpPost request = new HttpPost(url);
//
//                ContentValues values=new ContentValues();
//
//                values.put("msg",info);
//                values.put("fb_id",fb_id);
//                values.put("img",byteArray1);

                List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
                postParameters.add(new BasicNameValuePair("msg", info2));
                postParameters.add(new BasicNameValuePair("name", info));
                postParameters.add(new BasicNameValuePair("fb_id", fb_id));
                postParameters.add(new BasicNameValuePair("img", byteArray1));
//                postParameters.add(new BasicNameValuePair("name", params[0]));

                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
                        postParameters);

                request.setEntity(formEntity);
                httpClient.execute(request);
                result="got it";

            } catch(Exception e) {
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
            return  result;
        }

        protected void onPostExecute(String result){
//            pb.setVisibility(View.GONE);

            progress.dismiss();

            et_info.setText("");
            gambar_upload.setImageDrawable(null);
            Toast.makeText(getApplicationContext(), "Your data has been saved and will be process", Toast.LENGTH_LONG).show();
            Intent i = new Intent (AskblessingActivity.this,MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        }
//        protected void onProgressUpdate(String... progress){
////            pb.setProgress(progress[0]);
//        }
//
//        public void postData(String valueIWantToSend) {
//            // Create a new HttpClient and Post Header
//
//                String info = et_info.getEditableText().toString();
//
//                String fb_id = AccessToken.getCurrentAccessToken().getUserId().toString();
//
////                Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(), R.id.gambar_upload);
//                bitmap = ((BitmapDrawable) gambar_upload.getDrawable()).getBitmap();
//                ByteArrayOutputStream bao = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
//                byte [] ba = bao.toByteArray();
//                String ba1= Base64.encodeToString(ba, Base64.DEFAULT);
//
//            HttpClient httpclient = new DefaultHttpClient();
//            HttpPost httppost = new HttpPost("http://twinheart.stage.city/twinheartapi/saveBlessing");
//
//            try {
//                // Add your data
//                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//                nameValuePairs.add(new BasicNameValuePair("msg", info));
//                nameValuePairs.add(new BasicNameValuePair("fb_id", fb_id));
//                nameValuePairs.add(new BasicNameValuePair("img", ba1));
//                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//
//                // Execute HTTP Post Request
//                HttpResponse response = httpclient.execute(httppost);
//
//            } catch (ClientProtocolException e) {
//                // TODO Auto-generated catch block
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//            }
//        }
//
    }
}









