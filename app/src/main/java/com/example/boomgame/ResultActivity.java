package com.example.boomgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private TextView textViewInformation;
    private TextView textViewMyScore;
    private TextView textViewTotalScore;
    private TextView textViewPlayAgain;
    private TextView textViewExit;

    int currentScore = 0;
    int totalScore;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textViewInformation = findViewById(R.id.textViewInfo);
        textViewMyScore = findViewById(R.id.textViewMyScore);
        textViewTotalScore = findViewById(R.id.textViewTotalScore);

        currentScore = getIntent().getIntExtra("score", 0);
        textViewMyScore.setText("Your score: " + currentScore);

        sharedPreferences = this.getSharedPreferences("Score", MODE_PRIVATE);
        totalScore = sharedPreferences.getInt("totalScore", 0);

        if (currentScore >= totalScore) {
            sharedPreferences.edit().putInt("totalScore", currentScore).apply();
            textViewTotalScore.setText("Total score: " + currentScore);
            textViewInformation.setText("Oh eyah! New Score!!!");
        } else {
            textViewTotalScore.setText("Total score:" + totalScore);
            textViewInformation.setText("Not bad, but in previous times you were better");
        }
    }

    public void onPlayAgain(View view){
        startActivity(new Intent(ResultActivity.this,MainActivity.class));
    }

    public void onExit(View view){

        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);

    }

}