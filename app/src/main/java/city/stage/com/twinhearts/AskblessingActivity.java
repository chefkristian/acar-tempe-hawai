package city.stage.com.twinhearts;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

/**
 * Created by indomegabyte on 31/03/16.
 */
public class AskblessingActivity extends AppCompatActivity implements View.OnClickListener {

    TextView nama_profile;
    Button button_submit;
    ImageView button_upload, gambar_upload, button_camera;
    LoginButton button_facebook;
    private CallbackManager callbackManager;
    ProfilePictureView profilePictureView;
    EditText info;
    AccessTokenTracker accessTokenTracker;
    AccessToken accessToken;
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    Profile profile;
    ProfileTracker profileTracker;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();


        setContentView(R.layout.activity_askblessing);
        info = (EditText) findViewById(R.id.info);
        nama_profile = (TextView) findViewById(R.id.nama_profile);
        button_submit = (Button) findViewById(R.id.button_submit);
        button_upload = (ImageView) findViewById(R.id.button_upload);
        gambar_upload = (ImageView) findViewById(R.id.gambar_upload);
//        button_camera = (ImageView) findViewById(R.id.button_camera);
        button_facebook = (LoginButton) findViewById(R.id.button_facebook);
        profilePictureView = (ProfilePictureView) findViewById(R.id.profil_image);
        profilePictureView.setVisibility(View.GONE);
        info.setVisibility(View.GONE);
        nama_profile.setVisibility(View.GONE);
        button_submit.setVisibility(View.GONE);
//        button_camera.setVisibility(View.GONE);
        button_upload.setVisibility(View.GONE);
        gambar_upload.setVisibility(View.GONE);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        // ini check login ga
        if(accessToken!=null){
            profile = Profile.getCurrentProfile();
            nama_profile.setText(profile.getName() + "");
            profilePictureView.setVisibility(View.VISIBLE);
            profilePictureView.setProfileId(accessToken.getUserId());
            info.setVisibility(View.VISIBLE);
            nama_profile.setVisibility(View.VISIBLE);
            button_submit.setVisibility(View.VISIBLE);
            button_upload.setVisibility(View.VISIBLE);
//                button_camera.setVisibility(View.VISIBLE);
            gambar_upload.setVisibility(View.VISIBLE);
        }

        accessTokenTracker = new AccessTokenTracker() {

            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {

                if (currentAccessToken == null) {
                    //User logged out
                    profilePictureView.setVisibility(View.GONE);
                    info.setVisibility(View.GONE);
                    button_submit.setVisibility(View.GONE);
                    button_upload.setVisibility(View.GONE);
//                    button_camera.setVisibility(View.GONE);
                    gambar_upload.setVisibility(View.GONE);
                    nama_profile.setVisibility(View.GONE);

                }

             else {





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
                info.setVisibility(View.VISIBLE);
                nama_profile.setVisibility(View.VISIBLE);
                button_submit.setVisibility(View.VISIBLE);
                button_upload.setVisibility(View.VISIBLE);
//                button_camera.setVisibility(View.VISIBLE);
                gambar_upload.setVisibility(View.VISIBLE);




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




        button_upload.setOnClickListener(this);
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
                        80, 60));


                imgView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));


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
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
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
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.button_upload): {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

// Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);

            }

        }
    }


}



