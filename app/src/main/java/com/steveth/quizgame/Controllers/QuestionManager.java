package com.steveth.quizgame.Controllers;

import com.steveth.quizgame.Models.Question;

import java.util.ArrayList;
import java.util.Random;

public class QuestionManager {

    private ArrayList<Question> questionList = new ArrayList<>();

    /**
     * Constructeur de Question Manager
     */
    public QuestionManager() {
        fillQuestionList();
    }

    /**
     * Rempli la liste de questions
     */
    private void fillQuestionList() {
        questionList.add( new Question("oui ou non ?", 0));
        questionList.add( new Question("Spider Man a été créé par fortnite", 0));
        questionList.add( new Question("Le rois des pirates, ce sera moi !!!!", 1));
        questionList.add( new Question("Zoro utilise trois sabres", 1));
        questionList.add( new Question("Nami est aimée pour sa force", 0));
        questionList.add( new Question("Chopper est un navigateur", 0));
        questionList.add( new Question("Harry potter est en réalitée une femme", 0));
        questionList.add( new Question("Le mot 'rat' se prononce 'Ratatus' en latin", 0));
        questionList.add( new Question("Barbie est blonde", 1));
    }

    /**
     * @return La liste des questions
     */
    public ArrayList<Question> getQuestionList() {
        return questionList;
    }

    /**
     * @return La question qui correspond a l'index tiré
     */
    public Question getQuestion() {
        int index = getRandomIndex();
        Question myQuestion = questionList.get(index);

        questionList.remove(index);

        return myQuestion;
    }

    /**
     * Test si la liste est vide ou non
     * @return un booléen
     */
    public boolean isListEmpty() {
        return questionList.isEmpty();
    }

    /**
     * Tirer un index aléatoire dans ma liste de questions
     * @return l'index tiré
     */
    private int getRandomIndex() {
        Random random = new Random();
        return random.nextInt(questionList.size());
    }
}
