package city.stage.com.twinhearts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by indomegabyte on 01/04/16.
 */
public class GroupmeditasiActivity extends AppCompatActivity implements View.OnClickListener {
    Button button_come,button_streaming;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupmeditasi);
        button_come = (Button)findViewById(R.id.button_come);
        button_streaming = (Button)findViewById(R.id.button_streaming);


        button_come.setOnClickListener(this);
        button_streaming.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_come){
            Intent i = new Intent(this,PhCenterLocation.class);
            startActivity(i);
        }
        if (view.getId()== R.id.button_streaming){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=JLf9q36UsBk")));
            Log.i("Video", "Video Playing....");
        }
    }
}
