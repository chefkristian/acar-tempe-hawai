package city.stage.com.twinhearts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by indomegabyte on 05/04/16.
 */
public class LearnPhActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView list_learn_ph, list_learn_ph2;
    LearnAdapter mJSONAdapter, mJSONAdapter2;
    ProgressBar progresBar;
    private Tracker mTracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_ph);




        list_learn_ph = (ListView)findViewById(R.id.list_learn_ph);
        progresBar = (ProgressBar)findViewById(R.id.progresBar);


        mJSONAdapter  = new LearnAdapter(this,getLayoutInflater());


        list_learn_ph.setAdapter(mJSONAdapter);





        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://twinheart.stage.city/twinheartapi/getJadwal",
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        // Toast.makeText(getApplicationContext(), jsonObject.optString("picture"), Toast.LENGTH_SHORT).show();
                        Log.i("aplikasi-mobile.com", jsonObject.toString());

//
                        mJSONAdapter.masukin(jsonObject);
                        progresBar.setVisibility(View.GONE);
//                        mJSONAdapter2.masukin2(jsonObject);


//                        mJSONAdapter.masukin2(jsonObject);

//                       Iterator<String> iter = jsonObject.keys();
//                        while (iter.hasNext()) {
//                            String key = iter.next();
//                            Log.d("halo1",key);
//                            try {
//                                Object value = jsonObject.get(key);
//                            } catch (JSONException e) {
//                                // Something went wrong!
//                            }
//                        }


//                        swipeContainer.setRefreshing(false);
///intent
                        //  jsonObject.toString();

                    }

                    @Override
                    public void onFailure(int statusCode, Throwable throwable, JSONObject error) {
                        // 15. Dismiss the ProgressDialog
//                        mDialog.dismiss();

                        //16. Keluarkan toast
                        Toast.makeText(getApplicationContext(), "Error: " + statusCode + " " + throwable.getMessage(), Toast.LENGTH_LONG).show();

                        //17. Print Log
                        Log.e("aplikasi-mobile.com", statusCode + " " + throwable.getMessage());
                    }

                });


        list_learn_ph.setOnItemClickListener(this);

        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        sendScreenImageName();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        TextView desc_course = (TextView)view.findViewById(R.id.desc_course);
        if (desc_course.getVisibility()==TextView.GONE){
            desc_course.setVisibility(TextView.VISIBLE);
        } else {
            desc_course.setVisibility(TextView.GONE);
        }

        LinearLayout ll = (LinearLayout)view.findViewById(R.id.line11);
        if (ll.getVisibility()==View.GONE){
            ll.setVisibility(View.VISIBLE);
        } else {
            ll.setVisibility(View.GONE);
        }


    }

    public void callSolo(View v){
        String number = "+62271652828";
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendScreenImageName();
    }

    private void sendScreenImageName() {

        // [START screen_view_hit]
        Log.i("TAG", "LearnPH Activity");
        mTracker.setScreenName("LearnPH Activity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        // [END screen_view_hit]
    }
}
