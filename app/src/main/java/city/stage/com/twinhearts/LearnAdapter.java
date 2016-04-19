package city.stage.com.twinhearts;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by indomegabyte on 11/04/16.
 */
public class LearnAdapter extends BaseAdapter {

    JSONArray mJsonArray;
    JSONObject mJsonObject;
    Context mContext;
    LayoutInflater mInflater;
    private int lastPosition = -1;

    public LearnAdapter(Context context, LayoutInflater inflater) {
        mContext = context;
        mInflater = inflater;
        mJsonArray = new JSONArray();
    }

    @Override
    public int getCount() { return mJsonArray.length();}

    @Override
    public Object getItem(int i) {
        return mJsonArray.optJSONObject(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        JadwalPHResult jadwal;
        view = mInflater.inflate(R.layout.learn_ph_jadwal, null);

        jadwal = new JadwalPHResult();
        jadwal.jadwal_kursus = (TextView)view.findViewById(R.id.jadwal_course);
        jadwal.tipe_kursus = (TextView)view.findViewById(R.id.nama_course);
        jadwal.lokasi_kursus = (TextView)view.findViewById(R.id.lokasi_course);


        JSONObject jsonObject = (JSONObject) getItem(i);
        jadwal.tipe_kursus.setText(jsonObject.optString("jdwl_kursus_type"));
        jadwal.jadwal_kursus.setText(jsonObject.optString("jdwl_date_start"));
        jadwal.lokasi_kursus.setText(jsonObject.optString("jdwl_lokasi"));




        return view;
    }


    public void masukin(JSONObject json) {
        mJsonObject = json;
        mJsonArray = json.optJSONArray("results");

        //harus ada!!
        notifyDataSetChanged();
    }

    private static class JadwalPHResult {
        public TextView tipe_kursus;
        public TextView jadwal_kursus;
        public TextView lokasi_kursus;
    }

}

