package city.stage.com.twinhearts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by indomegabyte on 29/03/16.
 */
public class SharedActivity extends AppCompatActivity implements View.OnClickListener {
    Button button_share,button_submit_share;
    EditText et_sharing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared);
        button_submit_share = (Button)findViewById(R.id.button_submit_share);
        button_share = (Button)findViewById(R.id.button_share);
        et_sharing = (EditText)findViewById(R.id.et_sharing);

        button_share.setOnClickListener(this);
        button_submit_share.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        if (view.getId()== R.id.button_share){
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Detik");
            i.putExtra(Intent.EXTRA_TEXT, "http://www.detik.com");
            startActivity(Intent.createChooser(i, "Share URL"));

        }
        if (view.getId()==R.id.button_submit_share){

        }



    }
}
