package city.stage.com.twinhearts;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
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
    MediaPlayer mediaPlayer;
    SeekBar seekBar;
    private double startTime = 0;
    private double finalTime = 100;
    private Handler myHandler = new Handler();
    public static int oneTimeOnly = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation);
        button_pause = (Button) findViewById(R.id.button_pause);
        button_play = (Button) findViewById(R.id.button_play);
        start_time = (TextView) findViewById(R.id.start_time);
        end_time = (TextView) findViewById(R.id.end_time);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        mediaPlayer = MediaPlayer.create(this, R.raw.like);
        seekBar.setClickable(false);
        button_pause.setEnabled(false);
        end_time.setVisibility(View.INVISIBLE);
        start_time.setVisibility(View.INVISIBLE);

        seekBar.setProgress((int) startTime);
        myHandler.postDelayed(UpdateSongTime, 100);
        button_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mediaPlayer.start();

                seekBar.setProgress((int) startTime);
                myHandler.postDelayed(UpdateSongTime, 100);

                start_time.setVisibility(View.VISIBLE);
                end_time.setVisibility(View.VISIBLE);

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


                button_pause.setEnabled(true);
                button_play.setEnabled(false);


            }
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
            }
        });

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

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        seekBar.setMax(mediaPlayer.getDuration());
        mediaPlayer.stop();

    }

    @Override
    protected void onRestart() {

        super.onRestart();

     }

    @Override
    protected void onResume() {

        seekBar.setMax(mediaPlayer.getDuration());
        super.onResume();

    }

    @Override
    protected void onPause() {
        seekBar.setMax(mediaPlayer.getDuration());
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
        mediaPlayer.stop();

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
    };

}
