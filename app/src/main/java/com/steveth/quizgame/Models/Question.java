package com.steveth.quizgame.Models;

public class Question {

    private final String question;
    private final int answer;

    /**
     * Constructeur de la question
     * @param question libéllé de la question
     * @param reponse réponse de la question
     */
    public Question(String question, int reponse) {
        this.question = question;
        this.answer = reponse;
    }

    /**
     * @return le libéllé de la question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @return la réponse de la question
     */
    public int getAnswer() {
        return answer;
    }
}
