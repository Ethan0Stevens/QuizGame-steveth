package com.steveth.quizgame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.steveth.quizgame.Controllers.QuestionManager;
import com.steveth.quizgame.Models.Question;

import java.util.concurrent.TimeUnit;

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

    QuestionManager qManager;
    Question myQuestion;

    Runnable questionRunnable = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //recupère les paramètres de l'activitée
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

        qManager = new QuestionManager(this);
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

    /**
     * Lance un chrono de 4 seconde avant de lancer le jeu
     */
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
                ChangeQuestion();
            }
        }.start();
    }

    /**
     * Change de question automatiquement au bout d'une seconde
     */
    private void ChangeQuestion() {
        Handler handler = new Handler();
        questionRunnable = new Runnable() {

            @Override
            public void run() {
                if(qManager.isListEmpty()){
                    StopGame();
                    handler.removeCallbacks(this, 100);
                }else{
                    SetQuestion();
                    handler.postDelayed(this,2000);
                }
            }
        };
        handler.postDelayed(questionRunnable,2000);
    }

    /**
     * Stock la question recupérée et l'affiche
     */
    private void SetQuestion() {
        Button_p1.setEnabled(true);
        Button_p2.setEnabled(true);

        myQuestion = qManager.getQuestion();

        Question_p1.setText(myQuestion.getQuestion());
        Question_p2.setText(myQuestion.getQuestion());
    }

    /**
     * Test si la réponse est correct ou non et, attribut les points en fonction de la réponse
     * @param view le bouton appuyé
     */
    @SuppressLint("SetTextI18n")
    public void TestAnswer(View view) {
        int answer = myQuestion.getAnswer();
        int scoreP1 = Integer.parseInt(Score_p1.getText().toString());
        int scoreP2 = Integer.parseInt(Score_p2.getText().toString());

        final int SCORE_MAX = 99;
        final int CORRECT = 1;

        Button_p1.setEnabled(false);
        Button_p2.setEnabled(false);

        //Modifier le score du jouer dont le bouton a été préssé
        if (view.getId() == R.id.button_p1) {
            // si la réponse est correct alors ajouter un point au score
            // sinon ajouter un point a l'adversaire
            if (answer == CORRECT) {
                if (scoreP1 < SCORE_MAX) {
                    Score_p1.setText(Integer.toString(scoreP1 + 1));
                }
            } else {
                if (scoreP1 > -SCORE_MAX) {
                    Score_p2.setText(Integer.toString(scoreP2 + 1));
                }
            }
        }

        if (view.getId() == R.id.button_p2){
            if (answer == CORRECT) {
                if (scoreP2 < SCORE_MAX) {
                    Score_p2.setText(Integer.toString(scoreP2 + 1));
                }
            } else {
                if (scoreP2 > -SCORE_MAX) {
                    Score_p1.setText(Integer.toString(scoreP1 + 1));
                }
            }
        }
    }

    /**
     * Arrete de jeu
     */
    @SuppressLint("SetTextI18n")
    public void StopGame() {
        //désactive les boutons
        Button_p1.setEnabled(false);
        Button_p2.setEnabled(false);
        //Vide les champs de questions
        if (Integer.parseInt(Score_p1.getText().toString()) == Integer.parseInt(Score_p2.getText().toString())) {
            Question_p1.setText("Egalité");
            Question_p2.setText("Egalité");
        } else if (Integer.parseInt(Score_p1.getText().toString()) > Integer.parseInt(Score_p2.getText().toString())) {
            Question_p1.setText("Gagnant");
            Question_p2.setText("Perdant");
        } else {
            Question_p1.setText("Perdant");
            Question_p2.setText("Gagnant");
        }
        //rend visible les boutons de menu et de restart
        Button_Menu.setVisibility(View.VISIBLE);
        Button_Restart.setVisibility(View.VISIBLE);
    }

    /**
     * Relancer le jeu
     * @param view la vue
     */
    public void restartGame(View view) {
        Intent intent = getIntent();
        //il s'arrete lui meme
        finish();
        //et se relance juste apres
        startActivity(intent);
        //animation de changement d'activity
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /**
     * Retourne a la page d'accueil
     * @param view la vue
     */
    public void goToMainMenu(View view) {
        Intent returnIntent = new Intent(this, MainActivity.class);
        setResult(RESULT_OK,returnIntent);
        //Termine l'activitée
        finish();
    }

}
