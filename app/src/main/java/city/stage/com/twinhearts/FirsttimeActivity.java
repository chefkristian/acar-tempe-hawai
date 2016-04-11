package city.stage.com.twinhearts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by indomegabyte on 28/03/16.
 */
public class FirsttimeActivity extends AppCompatActivity implements View.OnClickListener {
    Button button_ya, button_tidak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firsttime);
        button_ya = (Button) findViewById(R.id.button_ya);
        button_tidak = (Button) findViewById(R.id.button_tidak);

        button_ya.setOnClickListener(this);
        button_tidak.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_ya) {
            Intent intent = new Intent(this, ScreenSlidePagerActivity.class);
            intent.putExtra("answer",0);
            startActivity(intent);
        }
        else if (view.getId() == R.id.button_tidak) {
            Intent i = new Intent(this, ScreenSlidePagerActivity.class);
            i.putExtra("answer",1);
            startActivity(i);
        }
    }
}
