package com.myapplication.gaffey.scoretes;

/**
 * Created by 72356 on 2017/11/2.
 */

public class Answer {
//    private int paperId ;
    private int answerId;
    private String answerText;
    private String myAnswer;
    Answer(int answerId, String answerText){
//        this.paperId=paperId;
        this.answerText=answerText;
        this.answerId=answerId;
    }

//    public int getPaperId() {
//        return paperId;
//    }


    public String getMyAnswer() {
        return myAnswer;
    }

    public void setMyAnswer(String myAnswer) {
        this.myAnswer = myAnswer;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public int getAnswerId() {
        return answerId;
    }

    public String getAnswerText() {
        return answerText;
    }
}
