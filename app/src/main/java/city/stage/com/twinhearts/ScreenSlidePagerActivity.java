package city.stage.com.twinhearts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import java.util.List;

/**
 * Created by indomegabyte on 28/03/16.
 */
public class ScreenSlidePagerActivity extends FragmentActivity {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
//    private static final int NUM_PAGES = 5;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;


    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;
    Button prev_button, next_button;
    String bahasa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);


        class ScreenSlidePagerAdapter1 extends FragmentStatePagerAdapter {
            public ScreenSlidePagerAdapter1(FragmentManager fm) {
                super(fm);
            }

            @Override
            public Fragment getItem(int position) {
                switch (position) {

                    case 0:
                        return ScreenSlidePageFragment.newInstance("FirstFragment, Instance 1");
                    case 1:
                        return ScreenSlidePageFragment2.newInstance("SecondFragment, Instance 1");
                    case 2: return ScreenSlidePageFragment3.newInstance("ThirdFragment, Instance 1");
                    case 3: return ScreenSlidePageFragment4.newInstance("ThirdFragment, Instance 2");
                    case 4: return ScreenSlidePageFragment5.newInstance("ThirdFragment, Instance 3");
                    default:
                        return ScreenSlidePageFragment.newInstance("FirstFragment, Default");
                }
            }


            @Override
            public int getCount() {
                return 5;
            }
        }
        class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
            public ScreenSlidePagerAdapter(FragmentManager fm) {
                super(fm);
            }

            @Override
            public Fragment getItem(int position) {
                switch (position) {

                    case 0:
                        return ScreenSlidePageFragment.newInstance("FirstFragment, Instance 1");

//                    case 1:
//                        return ScreenSlidePageFragment2.newInstance("SecondFragment, Instance 1");
//                    case 2: return ScreenSlidePageFragment3.newInstance("ThirdFragment, Instance 1");
//                    case 3: return ScreenSlidePageFragment4.newInstance("ThirdFragment, Instance 2");
//                    case 4: return ScreenSlidePageFragment5.newInstance("ThirdFragment, Instance 3");
                    default:
                        return ScreenSlidePageFragment.newInstance("FirstFragment, Default");
                }
            }


            @Override
            public int getCount() {
                return 1;
            }
        }




        int answer = getIntent().getIntExtra("answer",0);
        if (answer == 0){
            // Instantiate a ViewPager and a PagerAdapter.
            mPager = (ViewPager) findViewById(R.id.pager);
            mPagerAdapter = new ScreenSlidePagerAdapter1(getSupportFragmentManager());
            mPager.setAdapter(mPagerAdapter);
        }
        else {
            // Instantiate a ViewPager and a PagerAdapter.
            mPager = (ViewPager) findViewById(R.id.pager);
            mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
            mPager.setAdapter(mPagerAdapter);

        }

        bahasa = getIntent().getExtras().getString("meditasi");




        next_button = (Button) findViewById(R.id.next_button);
        prev_button = (Button) findViewById(R.id.prev_button);




        next_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (mPager.getCurrentItem()== mPagerAdapter.getCount()-1) {

                    finish();
                    Intent i = new Intent(view.getContext(),MeditationActivity.class);
//                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("meditasi",bahasa);
                    startActivity(i);
                }

                else {
                    mPager.setCurrentItem(mPager.getCurrentItem()+1, true); //getItem(-1) for previous
                }
            }
        });
        prev_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPager.setCurrentItem(mPager.getCurrentItem()-1, true);

            }
        });




    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }




    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */

}



