package com.example.maist.ausapp;

import android.provider.BaseColumns;

/**
 * Created by MaIst on 31/7/2559.
 */
public class Quiz {
    private int id;
    private String name;
    private String description;
    private String type;
    private int num_question;
    private String img;

    public Quiz() {

    }

    public Quiz (int id, String name, String description, String type, int num_question, String img) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.num_question = num_question;
        this.img = img;

    }

    public  class Column {

        public static final String ID = BaseColumns._ID;
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String TYPE = "type";
        public static final String NUMQUESTION = "num_question";
        public static final String IMG = "img";

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNum_question() {
        return num_question;
    }

    public void setNum_question(int num_question) {
        this.num_question = num_question;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
