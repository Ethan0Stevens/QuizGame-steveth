package com.steveth.quizgame.Models;

public class Question {

    private final String question;
    private final int answer;

    public Question(String question, int reponse) {
        this.question = question;
        this.answer = reponse;
    }

    public String getQuestion() {
        return question;
    }

    public int getAnswer() {
        return answer;
    }
}
