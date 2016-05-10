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

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by indomegabyte on 09/05/16.
 */
public class InboxDetailActivity extends AppCompatActivity {
    TextView title_inbox,msg_inbox;
    ImageView image_inbox;
    Button button_inbox;
    JSONObject activeObj;

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
            if (jsonObj.optString("camp_url").equals("")){
                button_inbox.setVisibility(View.GONE);
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


    }

    public void open(View v){

        String url = activeObj.optString("camp_url");
        Log.d("urllll",url);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

//    @Override
//    public void onClick(View view) {
//        String url = activeObj.optString("camp_url");
//
//        Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse(url) );
//       startActivity(i);
//    }
}
