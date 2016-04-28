package city.stage.com.twinhearts;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Created by indomegabyte on 11/04/16.
 */
public class LearnAdapter extends BaseAdapter {

    JSONArray mJsonArray, mJArray;
    JSONObject mJsonObject, mJsonObject2;
    Context mContext;
    LayoutInflater mInflater;
    private int lastPosition = -1;

    private int sizeFirstArr = 0;


    public LearnAdapter(Context context, LayoutInflater inflater) {
        mContext = context;
        mInflater = inflater;
        mJsonArray = new JSONArray();
    }


    @Override
    public int getCount() {
        return mJsonArray.length();
    }

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
//        jadwal.jadwal_kursus = (TextView)view.findViewById(R.id.jadwal_course);
//        jadwal.tipe_kursus = (TextView)view.findViewById(R.id.nama_course);
//        jadwal.lokasi_kursus = (TextView)view.findViewById(R.id.lokasi_course);
        jadwal.jenis_kursus = (TextView) view.findViewById(R.id.jenis_course);
        jadwal.desc_course = (TextView) view.findViewById(R.id.desc_course);
        jadwal.lokasi_course = (TextView) view.findViewById(R.id.lokasi_course);
        jadwal.jadwal_course = (TextView) view.findViewById(R.id.jadwal_course);
        jadwal.price_course = (TextView) view.findViewById(R.id.price_course);
        jadwal.tvHeader = (TextView) view.findViewById(R.id.tvHeader);

        JSONObject jsonObject = (JSONObject) getItem(i);
//        jadwal.tipe_kursus.setText(jsonObject.optString("jdwl_kursus_type"));
//        jadwal.jadwal_kursus.setText(jsonObject.optString("jdwl_date_start"));
//        jadwal.lokasi_kursus.setText(jsonObject.optString("jdwl_lokasi"));


        jadwal.jenis_kursus.setText(jsonObject.optString("name"));
        jadwal.desc_course.setText(jsonObject.optString("descr"));
        jadwal.tvHeader.setText(jsonObject.optString("courses"));



//        String lokasi_kursus = "";

        try {
            JSONArray kursus_array = jsonObject.optJSONArray("kursus_array");
            for (int j = 0; j < kursus_array.length(); j++) {

//            untuk munculin headers di item pertama tiap array mulai
                if (i == sizeFirstArr || i == 0)
                    jadwal.tvHeader.setVisibility(View.VISIBLE);

                else {
                    jadwal.tvHeader.setVisibility(View.GONE);
                }

//            akhir munculin headers
                try {
                    JSONObject kursus_detail = kursus_array.getJSONObject(0);
                    String lokasi_kursus = kursus_detail.optString("jdwl_lokasi");
                    String jadwal_kursus = kursus_detail.optString("jdwl_date_start");
                    String harga_course = kursus_detail.optString("jdwl_price");
//                if (lokasi_kursus.equals("")){
//                    break;
//                }
                    jadwal.lokasi_course.setText(lokasi_kursus);
                    jadwal.jadwal_course.setText(jadwal_kursus);
                    jadwal.price_course.setText(harga_course);
//                Log.d("lokasi_kursus", lokasi_kursus);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }


    public void masukin(JSONObject json) {
//        mJsonObject = json.optJSONObject("courses");
        mJsonArray = json.optJSONObject("courses").optJSONArray("entry");
        sizeFirstArr = mJsonArray.length();

        mJArray = json.optJSONObject("courses").optJSONArray("other");
        for (int x= 0; x <mJArray.length(); x++){
            // do something
            try {
//                gabungin mjarray dengan mjsonarray
                mJsonArray.put(mJArray.getJSONObject(x));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //harus ada!!
        notifyDataSetChanged();
    }

//    public void masukin2(JSONObject json) {
//        mJsonObject = json;
//        mJsonArray = json.optJSONObject("courses").optJSONArray("other");
//
//
//        //harus ada!!
//        notifyDataSetChanged();
//    }

    private static class JadwalPHResult {
        public TextView tipe_kursus;
        public TextView jadwal_kursus;
        public TextView lokasi_kursus;
        public TextView jenis_kursus;
        public TextView desc_course, lokasi_course, jadwal_course, price_course;
        public TextView tvHeader;

    }

}

