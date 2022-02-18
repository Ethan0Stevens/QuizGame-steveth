package com.steveth.quizgame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.TimeUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.steveth.quizgame.Controllers.QuestionManager;
import com.steveth.quizgame.Models.Question;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class GameActivity extends AppCompatActivity {

    private Button Button_Menu;
    private Button Button_Restart;
    private Button Button_p1;
    private Button Button_p2;
    private TextView Score_p1;
    private TextView Score_p2;
    private TextView Question_p1;
    private TextView Question_p2;
    private TextView Player1_Name;
    private TextView Player2_Name;

    String player1 = "";
    String player2 = "";

    QuestionManager questionManager = new QuestionManager();
    Question myQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Bundle b = getIntent().getExtras();
        player1 = b.getString("player1");
        player2 = b.getString("player2");

        Score_p1 = findViewById(R.id.score_p1);
        Score_p2 = findViewById(R.id.score_p2);
        Button_p1 = findViewById(R.id.button_p1);
        Button_p2 = findViewById(R.id.button_p2);
        Button_Menu = findViewById(R.id.bt_menu);
        Button_Restart = findViewById(R.id.bt_restart);
        Question_p1 = findViewById(R.id.tv_question_p1);
        Question_p2 = findViewById(R.id.tv_question_p2);
        Player1_Name = findViewById(R.id.game_player1_name);
        Player2_Name = findViewById(R.id.game_player2_name);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Player1_Name.setText(player1);
        Player2_Name.setText(player2);
        Button_p1.setEnabled(false);
        Button_p2.setEnabled(false);
        StartGame();
    }

    private void StartGame() {
        long duration = TimeUnit.SECONDS.toMillis(4);

        new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long l) {
                long sDuration = TimeUnit.MILLISECONDS.toSeconds(l);
                Score_p1.setText(String.valueOf(sDuration));
                Score_p2.setText(String.valueOf(sDuration));
            }

            @Override
            public void onFinish() {
                Button_p1.setEnabled(true);
                Button_p2.setEnabled(true);
                SetQuestion();
            }
        }.start();
    }

    private void SetQuestion() {
        if (questionManager.isListEmpty()) {
            StopGame();
        } else {
            myQuestion = questionManager.getQuestion();
            Question_p1.setText(myQuestion.getQuestion());
            Question_p2.setText(myQuestion.getQuestion());
        }
    }

    @SuppressLint("SetTextI18n")
    public void TestAnswer(View view) {
        int answer = myQuestion.getAnswer();
        int scoreP1 = Integer.parseInt(Score_p1.getText().toString());
        int scoreP2 = Integer.parseInt(Score_p2.getText().toString());

        if (view.getId() == R.id.button_p1) {
            if (answer == 1) {
                if (scoreP1 < 99) {
                    Score_p1.setText(Integer.toString(scoreP1 + 1));
                }
            } else {
                if (scoreP1 > -99) {
                    Score_p1.setText(Integer.toString(scoreP1 - 1));
                    Score_p2.setText(Integer.toString(scoreP2 + 1));
                }
            }
        }

        if (view.getId() == R.id.button_p2){
            if (answer == 1) {
                if (scoreP2 < 99) {
                    Score_p2.setText(Integer.toString(scoreP2 + 1));
                }
            } else {
                if (scoreP2 > -99) {
                    Score_p1.setText(Integer.toString(scoreP1 + 1));
                    Score_p2.setText(Integer.toString(scoreP2 - 1));
                }
            }
        }
        SetQuestion();
    }

    public void StopGame() {
        Button_p1.setEnabled(false);
        Button_p2.setEnabled(false);
        Question_p1.setText("");
        Question_p2.setText("");
        Button_Menu.setVisibility(View.VISIBLE);
        Button_Restart.setVisibility(View.VISIBLE);
    }

    public void restartGame(View view) {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void goToMainMenu(View view) {
        Intent returnIntent = new Intent(this, MainActivity.class);
        setResult(RESULT_OK,returnIntent);
        finish();
    }
}
