package com.steveth.quizgame.Controllers;

import com.steveth.quizgame.Models.Question;

import java.util.ArrayList;
import java.util.Random;

public class QuestionManager {

    private ArrayList<Question> questionList = new ArrayList<>();

    public QuestionManager() {
        this.questionList = fillQuestionList();
    }

    private ArrayList<Question> fillQuestionList() {
        questionList.add( new Question("Théo est le plus nul", 1));
        questionList.add( new Question("Spider Man a été créé par fortnite", 0));
        questionList.add( new Question("Le rois des pirates, ce sera moi !!!!", 1));
        questionList.add( new Question("Zoro utilise trois sabres", 1));
        questionList.add( new Question("Nami est aimée pour sa force", 0));
        questionList.add( new Question("Chopper est un navigateur", 0));
        questionList.add( new Question("Harry potter est en réalitée une femme", 0));
        questionList.add( new Question("Le mot 'rat' se prononce 'Ratatus' en latin", 0));
        questionList.add( new Question("Barbie est blonde", 1));
        return questionList;
    }

    public ArrayList<Question> getQuestionList() {
        return questionList;
    }

    public Question getQuestion() {
        int index = getRandomIndex();
        Question myQuestion = questionList.get(index);

        questionList.remove(index);

        return myQuestion;
    }

    public boolean isListEmpty() {
        return questionList.isEmpty();
    }

    public int getRandomIndex() {
        Random random = new Random();
        return random.nextInt(questionList.size());
    }
}
