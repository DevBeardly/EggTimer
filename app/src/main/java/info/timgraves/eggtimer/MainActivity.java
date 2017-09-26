package info.timgraves.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    SeekBar timerSeekBar;
    TextView timerTextView;
    Boolean counterIsActive = false;
    Button controllerButton;
    CountDownTimer countDownTimer;

    public void updateTimer(int secondsLeft) {

        int minutes = (int) secondsLeft / 60;
        int seconds = secondsLeft - (minutes * 60);

        String secondString = Integer.toString(seconds);

        if (secondString.length() == 1) {
            secondString = "0" + secondString;
        }

        timerTextView.setText(Integer.toString(minutes) + ":" + secondString);

    }

    public void controlTimer(View view) {

        if (counterIsActive == false) {

            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            controllerButton.setText("Stop");

            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long l) {

                    updateTimer((int) l / 1000);
                    timerSeekBar.setProgress((int) l / 1000);

                }

                @Override
                public void onFinish() {
                    
                    MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.foghorn);
                    mPlayer.start();
                    resetTimer();

                }
            }.start();

        } else if (counterIsActive) {

            resetTimer();

        }
    }

    public void resetTimer() {
        counterIsActive = false;
        timerTextView.setText("0:30");
        timerSeekBar.setProgress(30);
        timerSeekBar.setEnabled(true);
        controllerButton.setText("Go!");
        countDownTimer.cancel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = (SeekBar)findViewById(R.id.timerSeekBar);
        timerTextView = (TextView)findViewById(R.id.timerTextView);
        controllerButton = (Button)findViewById(R.id.controllerButton);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                updateTimer(i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
