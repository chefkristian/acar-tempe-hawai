package city.stage.com.twinhearts;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    TextView meditate_now,meditasi_BI,ask_blessing,group_meditasi,learn_ph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        meditate_now= (TextView)findViewById(R.id.meditate_now);
        meditasi_BI=(TextView)findViewById(R.id.meditasi_BI);
        ask_blessing=(TextView)findViewById(R.id.ask_blessing);
        group_meditasi=(TextView)findViewById(R.id.group_meditasi);
        learn_ph=(TextView)findViewById(R.id.learn_ph);

        meditate_now.setOnClickListener(this);
        meditasi_BI.setOnClickListener(this);
        ask_blessing.setOnClickListener(this);
        group_meditasi.setOnClickListener(this);
        learn_ph.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if  (view.getId() == R.id.meditate_now) {
            Intent intent = new Intent(this,FirsttimeActivity.class);
            startActivity(intent);
        }
        if  (view.getId() == R.id.meditasi_BI) {
            Intent intent = new Intent(this,FirsttimeActivity.class);
            startActivity(intent);
        }
        if (view.getId() == R.id.ask_blessing) {
            Intent intent = new Intent(this, AskblessingActivity.class);
            startActivity(intent);
        }
        if (view.getId() == R.id.group_meditasi){
            Intent intent = new Intent(this, GroupmeditasiActivity.class);
            startActivity(intent);
        }
        if (view.getId()==R.id.learn_ph){
            Intent inten = new Intent(this, LearnPhActivity.class);
            startActivity(inten);
        }
    }
}
