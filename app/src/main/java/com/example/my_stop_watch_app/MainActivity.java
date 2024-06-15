package com.example.my_stop_watch_app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView Time_Text;
    private ImageView start, stop, reset;
    private Handler handler;
    private long startTime, timeInMilliseconds = 0;
    private boolean running;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ensure you are referencing the correct IDs
        Time_Text = findViewById(R.id.Time_Text);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        reset = findViewById(R.id.reset);

        // Add null checks for safety
        if (Time_Text == null || start == null || stop == null || reset == null) {
            throw new NullPointerException("One of the views is not found. Please check your layout IDs.");
        }

        handler = new Handler();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStopwatch();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopStopwatch();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetStopwatch();
            }
        });
    }

    private void startStopwatch() {
        if (!running) {
            startTime = System.currentTimeMillis() - timeInMilliseconds;
            handler.postDelayed(updateTimerThread, 0);
            running = true;
        }
    }

    private void stopStopwatch() {
        if (running) {
            timeInMilliseconds = System.currentTimeMillis() - startTime;
            handler.removeCallbacks(updateTimerThread);
            running = false;
        }
    }

    private void resetStopwatch() {
        if (running) {
            handler.removeCallbacks(updateTimerThread);
            running = false;
        }
        timeInMilliseconds = 0;
        Time_Text.setText("00:00:0");
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = System.currentTimeMillis() - startTime;

            int seconds = (int) (timeInMilliseconds / 1000);
            int minutes = seconds / 60;
            int milliseconds = (int) ((timeInMilliseconds % 1000) / 100); // Get the first digit of milliseconds

            seconds = seconds % 60;

            Time_Text.setText(String.format("%02d:%02d:%d", minutes, seconds, milliseconds));
            handler.postDelayed(this, 100); // Update every 100 milliseconds
        }
    };
}
