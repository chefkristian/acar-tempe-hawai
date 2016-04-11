package city.stage.com.twinhearts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by indomegabyte on 28/03/16.
 */
public class ScreenSlidePageFragment2 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page2, container, false);

        return rootView;


    }
    public static ScreenSlidePageFragment2 newInstance(String text) {

        ScreenSlidePageFragment2 f = new ScreenSlidePageFragment2();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }


}

