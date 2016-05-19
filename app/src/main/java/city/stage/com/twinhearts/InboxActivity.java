package city.stage.com.twinhearts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

/**
 * Created by indomegabyte on 09/05/16.
 */
public class InboxActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView list_inbox;
    ProgressBar progresBar;
    InboxAdapter mJSONAdapter;
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        list_inbox = (ListView) findViewById(R.id.list_inbox);
        progresBar = (ProgressBar) findViewById(R.id.progresBar);

        mJSONAdapter = new InboxAdapter(this, getLayoutInflater());

        list_inbox.setAdapter(mJSONAdapter);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://twinheart.stage.city/twinheartapi/getNotifications",
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        // Toast.makeText(getApplicationContext(), jsonObject.optString("picture"), Toast.LENGTH_SHORT).show();
                        Log.i("aplikasi-mobile.com", jsonObject.toString());

//
                        mJSONAdapter.masukin(jsonObject);
                        progresBar.setVisibility(View.GONE);
//
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
        list_inbox.setOnItemClickListener(this);

        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        sendScreenImageName();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        JSONObject jsonObject = (JSONObject) mJSONAdapter.getItem(i);
        Intent detailIntent = new Intent(this, InboxDetailActivity.class);
        detailIntent.putExtra("json", jsonObject.toString());
        startActivity(detailIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendScreenImageName();
    }

    private void sendScreenImageName() {

        // [START screen_view_hit]
        Log.i("TAG", "Inbox Activity");
        mTracker.setScreenName("Inbox Activity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        // [END screen_view_hit]
    }
}
