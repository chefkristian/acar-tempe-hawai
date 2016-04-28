package city.stage.com.twinhearts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_ph);




        list_learn_ph = (ListView)findViewById(R.id.list_learn_ph);

//        list_learn_ph2 = (ListView)findViewById(R.id.list_learn_ph2);

        mJSONAdapter  = new LearnAdapter(this,getLayoutInflater());
//        mJSONAdapter2 = new LearnAdapter(this,getLayoutInflater());


        list_learn_ph.setAdapter(mJSONAdapter);
//        list_learn_ph2.setAdapter(mJSONAdapter2);




        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://twinheart.stage.city/twinheartapi/getJadwal",
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        // Toast.makeText(getApplicationContext(), jsonObject.optString("picture"), Toast.LENGTH_SHORT).show();
                        Log.i("aplikasi-mobile.com", jsonObject.toString());

//
                        mJSONAdapter.masukin(jsonObject);
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
//        list_learn_ph2.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        TextView desc_course = (TextView)view.findViewById(R.id.desc_course);
        if (desc_course.getVisibility()==TextView.GONE){
            desc_course.setVisibility(TextView.VISIBLE);
        } else {
            desc_course.setVisibility(TextView.GONE);
        }

        TextView lokasi_couses = (TextView)view.findViewById(R.id.lokasi_course);
        if (lokasi_couses.getVisibility()==TextView.GONE){
            lokasi_couses.setVisibility(TextView.VISIBLE);
        } else {
            lokasi_couses.setVisibility(TextView.GONE);
        }

        TextView lokasi = (TextView)view.findViewById(R.id.tv_lokasi);
        if (lokasi.getVisibility()==TextView.GONE){
            lokasi.setVisibility(TextView.VISIBLE);
        } else {
            lokasi.setVisibility(TextView.GONE);
        }

        TextView jadwal_course = (TextView)view.findViewById(R.id.jadwal_course);
        if (jadwal_course.getVisibility()==TextView.GONE){
            jadwal_course.setVisibility(TextView.VISIBLE);
        } else {
            jadwal_course.setVisibility(TextView.GONE);
        }

        TextView jadwal = (TextView)view.findViewById(R.id.tv_jadwal);
        if (jadwal.getVisibility()==TextView.GONE){
            jadwal.setVisibility(TextView.VISIBLE);
        } else {
            jadwal.setVisibility(TextView.GONE);
        }

        TextView harga_course = (TextView)view.findViewById(R.id.price_course);
        if (harga_course.getVisibility()==TextView.GONE){
            harga_course.setVisibility(TextView.VISIBLE);
        } else {
            harga_course.setVisibility(TextView.GONE);
        }

        TextView harga = (TextView)view.findViewById(R.id.tv_harga);
        if (harga.getVisibility()==TextView.GONE){
            harga.setVisibility(TextView.VISIBLE);
        } else {
            harga.setVisibility(TextView.GONE);
        }
//        JSONObject jsonObject = (JSONObject) mJSONAdapter.getItem(i);




    }
}
