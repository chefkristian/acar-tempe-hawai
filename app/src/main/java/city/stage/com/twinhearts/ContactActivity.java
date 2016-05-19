package city.stage.com.twinhearts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by indomegabyte on 11/05/16.
 */
public class ContactActivity extends AppCompatActivity {
    TextView tv_contact_solo, tv_contact_jakarta, tv_email_contact, tv_web_contact;
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        tv_web_contact = (TextView) findViewById(R.id.tv_web_contact);
        tv_contact_solo = (TextView) findViewById(R.id.tv_contact_solo);
        tv_contact_jakarta = (TextView) findViewById(R.id.tv_contact_jakarta);
        tv_email_contact = (TextView) findViewById(R.id.tv_email_contact);

        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        sendScreenImageName();

    }

    public void callSolo(View v) {
        String number = "+62271652828";
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);
    }

    public void callJakarta(View v) {
        String number = "+622122210269";
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);
    }

    public void email(View v) {
        String email = "loveprana@hotmail.com";
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", email, null));
        startActivity(Intent.createChooser(intent, "Choose an Email client :"));
    }

    public void openURL(View v) {
        String url = "http://www.indonesiapranichealing.org";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void openURLGlobal(View v) {
        String url = "http://globalpranichealing.com";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void sendSMS(View v) {
        String number = "+62000000000";  // The number on which you want to send SMS
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendScreenImageName();
    }

    private void sendScreenImageName() {

        // [START screen_view_hit]
        Log.i("TAG", "Contact Activity");
        mTracker.setScreenName("Contact Activity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        // [END screen_view_hit]
    }
}


