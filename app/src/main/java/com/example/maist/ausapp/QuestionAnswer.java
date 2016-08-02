package com.example.maist.ausapp;

import android.provider.BaseColumns;

/**
 * Created by MaIst on 31/7/2559.
 */
public class QuestionAnswer {
    private int id;
    private String name;
    private String question_img;
    private String question_txt;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String answer;

    public QuestionAnswer() {

    }

    public QuestionAnswer (int id, String name, String question_img, String question_txt, String option1, String option2, String option3, String option4, String answer) {

        this.id = id;
        this.name = name;
        this.question_img = question_img;
        this.question_txt = question_txt;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;

    }

    public  class Column {

        public static final String ID = BaseColumns._ID;
        public static final String NAME = "name";
        public static final String QUESTIONIMG = "question_img";
        public static final String QUESTIONTXT = "question_txt";
        public static final String OPTION1 = "option1";
        public static final String OPTION2 = "option2";
        public static final String OPTION3 = "option3";
        public static final String OPTION4 = "option4";
        public static final String ANSWER = "answer";

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestion_img() {
        return question_img;
    }

    public void setQuestion_img(String question_img) {
        this.question_img = question_img;
    }

    public String getQuestion_txt() {
        return question_txt;
    }

    public void setQuestion_txt(String question_txt) {
        this.question_txt = question_txt;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }
}
