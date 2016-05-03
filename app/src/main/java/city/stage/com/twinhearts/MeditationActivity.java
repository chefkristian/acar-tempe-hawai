package city.stage.com.twinhearts;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by indomegabyte on 29/03/16.
 */
public class MeditationActivity extends AppCompatActivity {
    TextView twinheart, gmcks, start_time, end_time;
    ImageView imageView;
    Button button_pause, button_play;
    MediaPlayer mediaPlayer,mediaPlayer2;
    SeekBar seekBar,seekBar2;
    private double startTime = 0;
    private double finalTime = 100;
    private Handler myHandler = new Handler();
    public static int oneTimeOnly = 0;
    String bahasa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation);
        button_pause = (Button) findViewById(R.id.button_pause);
        button_play = (Button) findViewById(R.id.button_play);
        start_time = (TextView) findViewById(R.id.start_time);
        end_time = (TextView) findViewById(R.id.end_time);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar2 = (SeekBar)findViewById(R.id.seekBar);






        bahasa = getIntent().getExtras().getString("meditasi");
        if (bahasa != null && bahasa.contentEquals("english")){
            mediaPlayer = MediaPlayer.create(this, R.raw.twin_hearts_eng);
        }
        if (bahasa.contentEquals("indo")){
            mediaPlayer = MediaPlayer.create(this,R.raw.twin_hearts_indo);
        }


        seekBar.setClickable(false);
//        seekBar2.setClickable(false);
        button_pause.setEnabled(false);
        end_time.setVisibility(View.INVISIBLE);
        start_time.setVisibility(View.INVISIBLE);

        seekBar.setProgress((int) startTime);
//        seekBar2.setProgress((int) startTime);
        myHandler.postDelayed(UpdateSongTime, 100);



        button_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    mediaPlayer.start();
                    finalTime = mediaPlayer.getDuration();
                    startTime = mediaPlayer.getCurrentPosition();

                    if (oneTimeOnly == 0) {
                        seekBar.setMax(mediaPlayer.getDuration());
                        oneTimeOnly = 1;
                    }

                    end_time.setText(String.format("%d min, %d sec",
                                    TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                                    TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime)))
                    );

                    start_time.setText(String.format("%d min, %d sec",
                                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime)))
                    );

                seekBar.setProgress((int) startTime);
                myHandler.postDelayed(UpdateSongTime, 100);

                start_time.setVisibility(View.VISIBLE);
                end_time.setVisibility(View.VISIBLE);


                button_pause.setEnabled(true);
                button_play.setEnabled(false);

            }




//                if (bahasa.contentEquals("indo")) {
//                    mediaPlayer2.start();
//                    finalTime = mediaPlayer2.getDuration();
//                    startTime = mediaPlayer2.getCurrentPosition();
//
//                    if (oneTimeOnly == 0) {
//                        seekBar2.setMax(mediaPlayer2.getDuration());
//                        oneTimeOnly = 1;
//                    }
//
//                    end_time.setText(String.format("%d min, %d sec",
//                                    TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
//                                    TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
//                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime)))
//                    );
//
//                    start_time.setText(String.format("%d min, %d sec",
//                                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
//                                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
//                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime)))
//                    );
//                }

//
//                seekBar2.setProgress((int) startTime);
//                myHandler.postDelayed(UpdateSongTime, 100);
//
//                start_time.setVisibility(View.VISIBLE);
//                end_time.setVisibility(View.VISIBLE);
//
//
//                button_pause.setEnabled(true);
//                button_play.setEnabled(false);
//
//            }

        });

        button_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
                button_pause.setEnabled(false);
                button_play.setEnabled(true);
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

                Intent intent = new Intent(MeditationActivity.this, SharedActivity.class);
                startActivity(intent);


//              seekBar.setMax(mediaPlayer.getDuration());
                button_pause.setEnabled(false);
                button_play.setEnabled(true);
                seekBar.setProgress(0);
//                seekBar2.setProgress(0);
            }
        });

//        mediaPlayer2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mediaPlayer2) {
//
//                Intent intent = new Intent(MeditationActivity.this, SharedActivity.class);
//                startActivity(intent);
//
//
////              seekBar.setMax(mediaPlayer.getDuration());
//                button_pause.setEnabled(false);
//                button_play.setEnabled(true);
////                seekBar.setProgress(0);
//                seekBar2.setProgress(0);
//            }
//        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                if (fromUser && mediaPlayer.isPlaying()) {
                    //myProgress = oprogress;
                    seekBar.setMax(mediaPlayer.getDuration());

                    mediaPlayer.seekTo(progress);
                    seekBar.setProgress(progress);
                }
            }
        });
//        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//
//            }
//
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                // TODO Auto-generated method stub
//                if (fromUser && mediaPlayer2.isPlaying()) {
//                    //myProgress = oprogress;
//                    seekBar.setMax(mediaPlayer2.getDuration());
//
//                    mediaPlayer2.seekTo(progress);
//                    seekBar.setProgress(progress);
//                }
//            }
//        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        seekBar.setMax(mediaPlayer.getDuration());
//        seekBar.setMax(mediaPlayer2.getDuration());
        mediaPlayer.stop();
//        mediaPlayer2.stop();

    }

    @Override
    protected void onRestart() {

        super.onRestart();

     }

    @Override
    protected void onResume() {

        seekBar.setMax(mediaPlayer.getDuration());
//        seekBar.setMax(mediaPlayer2.getDuration());
        super.onResume();

    }

    @Override
    protected void onPause() {
        seekBar.setMax(mediaPlayer.getDuration());
//        seekBar.setMax(mediaPlayer2.getDuration());
        super.onPause();
    }
    @Override
    protected void onStop() {

        super.onStop();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        seekBar.setProgress(0);
//        seekBar.setProgress(0);
        mediaPlayer.stop();
//        mediaPlayer2.stop();

    }

    //

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            start_time.setText(String.format("%d min, %d sec",

                            TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                            TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                            toMinutes((long) startTime)))
            );
            seekBar.setProgress((int) startTime);
            myHandler.postDelayed(this, 100);
        }
//        public void run2() {
//            startTime = mediaPlayer2.getCurrentPosition();
//            start_time.setText(String.format("%d min, %d sec",
//
//                            TimeUnit.MILLISECONDS.toMinutes((long) startTime),
//                            TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
//                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
//                                            toMinutes((long) startTime)))
//            );
//            seekBar2.setProgress((int) startTime);
//            myHandler.postDelayed(this, 100);
//        }
    };

}
