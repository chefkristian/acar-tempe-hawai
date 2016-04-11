package city.stage.com.twinhearts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

/**
 * Created by indomegabyte on 01/04/16.
 */
public class PhCenterLocation extends AppCompatActivity implements View.OnClickListener {
    Button button_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phcenter_location);
        button_map = (Button)findViewById(R.id.button_map);

        button_map.setOnClickListener(this);
        
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_map){

//            String uri = "http://maps.google.com/maps?saddr=" + "9982878"+","+"76285774"+"&daddr="+"9992084"+","+"76286455";
//            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
//            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
//            startActivity(intent);

//            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                    Uri.parse("http://maps.google.com/maps"));
//            startActivity(intent);

            String geoUri = "http://maps.google.com/maps?q=loc:" + -6.2813599 + "," + 106.7127195 ;
//            String uri = String.format(Locale.ENGLISH, "geo:%f,%f", -6.2813599, 106.7127195);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
           startActivity(intent);
        }
    }
}
