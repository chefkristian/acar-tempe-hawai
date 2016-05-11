package city.stage.com.twinhearts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by indomegabyte on 11/05/16.
 */
public class ContactActivity extends AppCompatActivity {
    TextView tv_contact_solo,tv_contact_jakarta,tv_email_contact,tv_web_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        tv_web_contact = (TextView)findViewById(R.id.tv_web_contact);
        tv_contact_solo = (TextView)findViewById(R.id.tv_contact_solo);
        tv_contact_jakarta = (TextView)findViewById(R.id.tv_contact_jakarta);
        tv_email_contact = (TextView)findViewById(R.id.tv_email_contact);

    }

    public void callSolo(View v){
        String number = "+62271652828";
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);
    }

    public void callJakarta(View v){
        String number = "+622122210269";
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);
    }
    public void email(View v){
        String email = "loveprana@hotmail.com";
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto",email, null));
//        intent.putExtra(Intent.EXTRA_SUBJECT, "haloo");
//        intent.putExtra(Intent.EXTRA_TEXT, "apa kabar");
        startActivity(Intent.createChooser(intent, "Choose an Email client :"));
    }

    public void openURL (View v){
        String url = "http://www.indonesiapranichealing.org";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
