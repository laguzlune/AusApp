package com.example.maist.ausapp;

import android.provider.BaseColumns;

public class CommuItemList {

    private int id;
    private String name;
    private String category;
    private int editable;
    private String uri;

    public CommuItemList() {

    }

    public CommuItemList(int id, String name, String category, int editable, String uri) {

        this.id = id;
        this.name = name;
        this.category = category;
        this.editable = editable;
        this.uri = uri;

    }

    public  class Column {

        public static final String ID = BaseColumns._ID;
        public static final String NAME = "name";
        public static final String CATEGORY = "category";
        public static final String URI = "uri";
        public static final String EDITABLE = "editable";


    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getEditable() {
        return editable;
    }

    public String getUri() {
        return uri;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setEditable(int editable) {
        this.editable = editable;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getId() {
        return id;
    }
}
