package city.stage.com.twinhearts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by indomegabyte on 09/05/16.
 */
public class InboxAdapter extends BaseAdapter {

    JSONArray mJsonArray;
    JSONObject mJsonObject;
    Context mContext;
    LayoutInflater mInflater;
    private int lastPosition = -1;

    private int sizeFirstArr = 0;


    public InboxAdapter(Context context, LayoutInflater inflater) {
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
        InboxItem inbox;
        view= mInflater.inflate(R.layout.inbox,null);

        inbox = new InboxItem();
        inbox.tv_title_inbox = (TextView)view.findViewById(R.id.tv_title_inbox);
        inbox.tv_send_date = (TextView)view.findViewById(R.id.tv_send_date);

        JSONObject jsonObject = (JSONObject) getItem(i);

        inbox.tv_title_inbox.setText(jsonObject.optString("camp_title"));
        inbox.tv_send_date.setText(jsonObject.optString("camp_send_date"));





        return view;
    }

    public void masukin(JSONObject json) {
        mJsonObject= json;
        mJsonArray = json.optJSONArray("results");


        //harus ada!!
        notifyDataSetChanged();
    }

    private static class InboxItem {
        TextView tv_title_inbox, tv_send_date;


    }
}
