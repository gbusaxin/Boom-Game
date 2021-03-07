package com.example.boomgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView textViewScore, textViewTime, textViewCountDown;
    private ImageView bomb1, bomb2, bomb3, bomb4, bomb5, bomb6, bomb7, bomb8, bomb9;
    private GridLayout gridLayout;
    int score = 0;
    Runnable runnable;
    Handler handler;
    ImageView[] bombsArray;
    MediaPlayer mediaPlayer;
    boolean mpStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewScore = findViewById(R.id.textViewScore);
        textViewTime = findViewById(R.id.textViewTime);
        textViewCountDown = findViewById(R.id.textViewCountDown);
        bomb1 = findViewById(R.id.bomb1);
        bomb2 = findViewById(R.id.bomb2);
        bomb3 = findViewById(R.id.bomb3);
        bomb4 = findViewById(R.id.bomb4);
        bomb5 = findViewById(R.id.bomb5);
        bomb6 = findViewById(R.id.bomb6);
        bomb7 = findViewById(R.id.bomb7);
        bomb8 = findViewById(R.id.bomb8);
        bomb9 = findViewById(R.id.bomb9);
        gridLayout = findViewById(R.id.gridLayout);
        mediaPlayer = MediaPlayer.create(this, R.raw.synthetic_explosion_1);

        bombsArray = new ImageView[]{bomb1, bomb2, bomb3, bomb4, bomb5, bomb6, bomb7, bomb8, bomb9};

        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                textViewCountDown.setText(String.valueOf(millisUntilFinished/1000));

            }

            @Override
            public void onFinish() {

                bombController();

                new CountDownTimer(30000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        textViewTime.setText("Remaining time : "+ millisUntilFinished/1000);
                    }

                    @Override
                    public void onFinish() {
                        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                        intent.putExtra("score",score);
                        startActivity(intent);
                        finish();
                    }
                }.start();

            }
        }.start();

    }

    private void bombController() {

        textViewCountDown.setVisibility(View.INVISIBLE);
        textViewTime.setVisibility(View.VISIBLE);
        textViewScore.setVisibility(View.VISIBLE);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                for (ImageView image : bombsArray){
                    image.setVisibility(View.INVISIBLE);
                    image.setImageResource(R.drawable.bomb);
                }
                gridLayout.setVisibility(View.VISIBLE);

                Random random = new Random();
                int i = random.nextInt(bombsArray.length);
                bombsArray[i].setVisibility(View.VISIBLE);

                if (score<= 5){
                    handler.postDelayed(runnable,2000);
                }else if (score>5 && score<= 10){
                    handler.postDelayed(runnable, 1500);
                }else if (score>10){
                    handler.postDelayed(runnable,1000);
                }

            }

        };

        handler.post(runnable);
    }

    public void increaseScoreByOne (View view ){
        ++score;
        textViewScore.setText(score+"");

        if (mediaPlayer.isPlaying()){
            mediaPlayer.seekTo(0);
            mediaPlayer.start();
        }
        mediaPlayer.start();

        if (view.getId() == bomb1.getId()){
            bomb1.setImageResource(R.drawable.explosion);
        }
        if (view.getId() == bomb2.getId()){
            bomb2.setImageResource(R.drawable.explosion);
        }
        if (view.getId() == bomb3.getId()){
            bomb3.setImageResource(R.drawable.explosion);
        }
        if (view.getId() == bomb4.getId()){
            bomb4.setImageResource(R.drawable.explosion);
        }if (view.getId() == bomb5.getId()){
            bomb5.setImageResource(R.drawable.explosion);
        }if (view.getId() == bomb6.getId()){
            bomb6.setImageResource(R.drawable.explosion);
        }if (view.getId() == bomb7.getId()){
            bomb7.setImageResource(R.drawable.explosion);
        }if (view.getId() == bomb8.getId()){
            bomb8.setImageResource(R.drawable.explosion);
        }if (view.getId() == bomb9.getId()){
            bomb9.setImageResource(R.drawable.explosion);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.volume){
            if (mpStatus != true){
                mediaPlayer.setVolume(0,0);
                item.setIcon(R.drawable.ic_baseline_volume_off_24);
                mpStatus = true;
            }else{
                mediaPlayer.setVolume(1,1);
                item.setIcon(R.drawable.ic_baseline_volume_up_24);
                mpStatus = false;
            }
        }

        return super.onOptionsItemSelected(item);

    }
}