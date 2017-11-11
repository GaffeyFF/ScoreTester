package com.myapplication.gaffey.scoretes;

/**
 * Created by 72356 on 2017/11/2.
 */

public class Paper {
    private int paperId;
    private String paperName;
    private int paperImage;
    private String answerName;

    Paper(int id , String name , int image){
        this.paperId=id;
        this.paperName=name;
        this.paperImage=image;
    }


    public int getId() {
        return paperId;
    }

    public String getPaperName() {
        return paperName;
    }

    public int getPaperImage() {
        return paperImage;
    }
}
