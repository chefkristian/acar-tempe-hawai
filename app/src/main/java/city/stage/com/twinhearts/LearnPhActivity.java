package city.stage.com.twinhearts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

/**
 * Created by indomegabyte on 05/04/16.
 */
public class LearnPhActivity extends AppCompatActivity {
    ListView list_learn_ph;
    LearnAdapter mJSONAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_ph);

        list_learn_ph = (ListView)findViewById(R.id.list_learn_ph);

        mJSONAdapter = new LearnAdapter(this,getLayoutInflater());

        list_learn_ph.setAdapter(mJSONAdapter);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://twinheart.stage.city/twinheartapi/getJadwal",
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        // Toast.makeText(getApplicationContext(), jsonObject.optString("picture"), Toast.LENGTH_SHORT).show();
                        Log.i("aplikasi-mobile.com", jsonObject.toString());
                        mJSONAdapter.masukin(jsonObject);
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
    }




}
