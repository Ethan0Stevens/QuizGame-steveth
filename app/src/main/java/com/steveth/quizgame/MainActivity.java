package com.steveth.quizgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private TextView TV_Player_2;

    private TextInputEditText ED_Player_1;
    private TextInputEditText ED_Player_2;
    private TextInputLayout InLayout_p2;
    private LinearLayout LL_about;
    private Button BT_Start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mainToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);

        TV_Player_2 = findViewById(R.id.tv_p2);
        ED_Player_1 = findViewById(R.id.main_p1_ed);
        ED_Player_2 = findViewById(R.id.main_p2_ed);
        InLayout_p2 = findViewById(R.id.textInputLayout_p2);
        LL_about = findViewById(R.id.main_about_popup);
        BT_Start = findViewById(R.id.start_bt);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ED_Player_1.requestFocus();

        // Rend le champs de saisi du deuxieme joueur visible
        // si le premier joueur a rentré un nom
        ED_Player_1.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                TV_Player_2.setVisibility(View.VISIBLE);
                InLayout_p2.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        // Rend le bouton de lancement du jeu visible
        // si le nom du deuxieme joueur a été rentré
        ED_Player_2.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                BT_Start.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    /**
     * Création du menu
     * @param menu menu
     * @return un boolean
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * Effectue le bon code en fonction de l'option selectionnée dans le menu
     * @param item option selectionnée
     * @return un boolean
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                LL_about.setVisibility(View.VISIBLE);
                LL_about.setClickable(true);
                break;
            case R.id.action_settings:

                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    /**
     * Ouvre la fenetre du jeu
     * @param view la vue
     */
    public void playGame(View view) {
        Intent gameActivity = new Intent(this, GameActivity.class);

        //Parametres donnés a l'activité
        gameActivity.putExtra("player1", Objects.requireNonNull(ED_Player_1.getText()).toString());
        gameActivity.putExtra("player2", Objects.requireNonNull(ED_Player_2.getText()).toString());

        startActivity(gameActivity);

        //Animation de changement d'activité
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /**
     * Ferme la popup about en cliquant a côté
     * @param view la vue cliquée
     */
    public void CloseAbout(View view) {
        LL_about.setVisibility(View.INVISIBLE);
    }
}