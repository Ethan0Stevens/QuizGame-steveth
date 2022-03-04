package com.steveth.quizgame.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QuizeGameSQLiteOpenHelper extends SQLiteOpenHelper{
    static String DB_NAME="QuizGame.db";
    static int DB_VERSION=1;
    public QuizeGameSQLiteOpenHelper(Context context)
    {
        super(context,DB_NAME,null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateDatatableQuiz = "CREATE TABLE quiz(idQuiz INTEGER PRIMARY KEY,question TEXT,reponse INTEGER);";
        db.execSQL(sqlCreateDatatableQuiz);
        db.execSQL("INSERT INTO quiz VALUES(1,\"La capitale de l'Australie est Sidney\",0)");
        db.execSQL("INSERT INTO quiz VALUES(2,\"Un octet vaut 7 bits\",0)");
        db.execSQL("INSERT INTO quiz VALUES(3,\"Le phénomène à l'origine de l'univers est le Big Bong\",0)");
        db.execSQL("INSERT INTO quiz VALUES(4,\"Il n'y a pas d'oxygène dans l'espace\",1)");
        db.execSQL("INSERT INTO quiz VALUES(5,\"Pythagore était grecque\",1)");
        db.execSQL("INSERT INTO quiz VALUES(6,\"Jules César est mort aux ides de Mars\",1)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
