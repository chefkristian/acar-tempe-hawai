package city.stage.com.twinhearts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by indomegabyte on 09/05/16.
 */
public class InboxDetailActivity extends AppCompatActivity {
    TextView title_inbox,msg_inbox;
    ImageView image_inbox;
    Button button_inbox,button_send_message, button_phone, button_send_mail;
    JSONObject activeObj;
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox_detail);

        String jsonString = this.getIntent().getExtras().getString("json");


        try {
            JSONObject jsonObj = new JSONObject(jsonString);

            activeObj = jsonObj;

            title_inbox = (TextView)findViewById(R.id.title_inbox);
            title_inbox.setText(jsonObj.optString("camp_title"));

            image_inbox = (ImageView)findViewById(R.id.image_inbox);
            if (jsonObj.optString("camp_img").equals("")){
                image_inbox.setVisibility(View.GONE);
            }
            else {
                Picasso.with(getApplicationContext()).load(jsonObj.optString("camp_img")).into(image_inbox);
            }

            msg_inbox= (TextView)findViewById(R.id.msg_inbox);
            msg_inbox.setText(jsonObj.optString("camp_msg"));

            button_inbox = (Button)findViewById(R.id.button_inbox);
            button_send_message = (Button)findViewById(R.id.button_send_message);
            button_phone = (Button)findViewById(R.id.button_phone);
            button_send_mail = (Button)findViewById(R.id.button_send_mail);

            if (jsonObj.optString("camp_url").equals("")){
                button_inbox.setVisibility(View.GONE);
            }
            if (jsonObj.optString("camp_phone").equals("")){
                button_phone.setVisibility(View.GONE);
            }
            if (jsonObj.optString("camp_mail").equals("")){
                button_send_mail.setVisibility(View.GONE);
            }
            if (jsonObj.optString("camp_sms").equals("")){
                button_send_message.setVisibility(View.GONE);
            }

//            else {
//
//                button_inbox.setOnClickListener(this);
//                String url = jsonObj.optString("camp_url");
//                Log.d("urllll",url);
//            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        sendScreenImageName();

    }

    public void open(View v){

        String url = activeObj.optString("camp_url");
        Log.d("urllll", url);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void send_sms (View v) {

        String number = activeObj.optString("camp_sms");
        Log.d("smsss", number);
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
    }

    public void call (View v) {
        String callNumber = activeObj.optString("camp_phone");
        Log.d("calll", callNumber);
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + callNumber));
        startActivity(intent);
    }
    public void send_mail(View v){
        String email = activeObj.optString("camp_mail");
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto",email, null));
        startActivity(Intent.createChooser(intent, "Choose an Email client :"));
    }
    @Override
    protected void onResume() {
        super.onResume();
        sendScreenImageName();
    }

    private void sendScreenImageName() {

        // [START screen_view_hit]
        Log.i("TAG", "Inbox Detail Activity");
        mTracker.setScreenName("Inbox Detail Activity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        // [END screen_view_hit]
    }

}
