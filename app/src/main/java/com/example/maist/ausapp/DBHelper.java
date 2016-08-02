package com.example.maist.ausapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MaIst on 18/7/2559.
 */
public class DBHelper extends SQLiteOpenHelper {

    private final String TAG = getClass().getSimpleName();

    private SQLiteDatabase sqliteDatabase;

    public static final String DATABASE_NAME = "commu.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_COMMU = "commu_item_list";
    public static final String TABLE_QUIZ = "quiz";
    public static final String TABLE_QNA = "question_answer";



    public DBHelper (Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    String CREATE_COMMULIST_TABLE = String.format("CREATE TABLE %s" + "(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER)",
            TABLE_COMMU,
            CommuItemList.Column.ID,
            CommuItemList.Column.NAME,
            CommuItemList.Column.CATEGORY,
            CommuItemList.Column.URI,
            CommuItemList.Column.EDITABLE);

    String CREATE_QUIZ_TABLE = String.format("CREATE TABLE %s" + "(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER, %s TEXT)",
            TABLE_QUIZ,
            Quiz.Column.ID,
            Quiz.Column.NAME,
            Quiz.Column.DESCRIPTION,
            Quiz.Column.TYPE,
            Quiz.Column.NUMQUESTION,
            Quiz.Column.IMG);

    String CREATE_QNA_TABLE = String.format("CREATE TABLE %s" + "(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
            TABLE_QNA,
            QuestionAnswer.Column.ID,
            QuestionAnswer.Column.NAME,
            QuestionAnswer.Column.QUESTIONIMG,
            QuestionAnswer.Column.QUESTIONTXT,
            QuestionAnswer.Column.OPTION1,
            QuestionAnswer.Column.OPTION2,
            QuestionAnswer.Column.OPTION3,
            QuestionAnswer.Column.OPTION4,
            QuestionAnswer.Column.ANSWER);

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_COMMULIST_TABLE);
        db.execSQL(CREATE_QUIZ_TABLE);
        db.execSQL(CREATE_QNA_TABLE);

        Log.i(TAG, CREATE_COMMULIST_TABLE);
        Log.i(TAG, CREATE_QUIZ_TABLE);
        Log.i(TAG, CREATE_QNA_TABLE);
        Log.d("DBHelper","create");

    }

     @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {

         String DROP_COMMULIST_TABLE = "DROP TABLE IF EXISTS commu_list";
         String DROP_QUIZ_TABLE = "DROP TABLE IF EXISTS quiz";
         String DROP_QNA_TABLE = "DROP TABLE IF EXISTS question_answer";

         db.execSQL(DROP_COMMULIST_TABLE);
         db.execSQL(DROP_QUIZ_TABLE);
         db.execSQL(DROP_QNA_TABLE);

         onCreate(db);
     }

    public void addItem(CommuItemList commuItem) {
        sqliteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(CommuItemList.Column.NAME, commuItem.getName());
        values.put(CommuItemList.Column.CATEGORY, commuItem.getCategory());
        values.put(CommuItemList.Column.URI, commuItem.getUri());
        values.put(CommuItemList.Column.EDITABLE, commuItem.getEditable());

        sqliteDatabase.insert(TABLE_COMMU, null, values);

        sqliteDatabase.close();

    }

    public  void removeItem(int id) {
        sqliteDatabase = this.getWritableDatabase();
        sqliteDatabase.delete(TABLE_COMMU, CommuItemList.Column.ID + " = " + id, null);

        sqliteDatabase.close();
    }

    public List<CommuItemList> getCommuList(String selectedCategory) {
        List<CommuItemList> itemList = new ArrayList<CommuItemList>();
        Cursor mcursor;
        sqliteDatabase = this.getWritableDatabase();

        if (selectedCategory == null) {
            mcursor =  sqliteDatabase.query(TABLE_COMMU, null, null, null, null, null ,null);
        } else {
            mcursor = sqliteDatabase.query(TABLE_COMMU, null,
                    CommuItemList.Column.CATEGORY + "='" + selectedCategory + "'" , null, null, null, null);
        }

        if(mcursor != null) {
            mcursor.moveToFirst();
        }

        while(!mcursor.isAfterLast()) {

            CommuItemList items = new CommuItemList();
            items.setId(mcursor.getInt(0));
            items.setName(mcursor.getString(1));
            items.setUri(mcursor.getString(3));
            items.setEditable(mcursor.getInt(4));

            itemList.add(items);

            mcursor.moveToNext();
        }

        sqliteDatabase.close();

        return itemList;
    }

    public List<Quiz> getQuizList() {
        List<Quiz> itemList = new ArrayList<Quiz>();
        Cursor mcursor;
        sqliteDatabase = this.getWritableDatabase();

        mcursor =  sqliteDatabase.query(TABLE_QUIZ, null, null, null, null, null ,null);

        if(mcursor != null) {
            mcursor.moveToFirst();
        }

        while(!mcursor.isAfterLast()) {

            Quiz items = new Quiz();
            items.setId(mcursor.getInt(0));
            items.setName(mcursor.getString(1));
            items.setDescription(mcursor.getString(2));
            items.setType(mcursor.getString(3));
            items.setNum_question(mcursor.getInt(4));
            items.setImg(mcursor.getString(5));

            itemList.add(items);

            mcursor.moveToNext();
        }

        sqliteDatabase.close();

        return itemList;
    }

    public List<QuestionAnswer> getQuestionandAnswer() {
        List<QuestionAnswer> itemList = new ArrayList<QuestionAnswer>();
        Cursor mcursor;
        sqliteDatabase = this.getWritableDatabase();

        //mcursor = sqliteDatabase.query(TABLE_QNA, null,
               // CommuItemList.Column.NAME + "='" + qName + "'" , null, null, null, null);

        mcursor =  sqliteDatabase.query(TABLE_QNA, null, null, null, null, null ,null);

        if(mcursor != null) {
            mcursor.moveToFirst();
        }

        while(!mcursor.isAfterLast()) {

            QuestionAnswer items = new QuestionAnswer();
            items.setId(mcursor.getInt(0));
            items.setName(mcursor.getString(1));
            items.setQuestion_img(mcursor.getString(2));
            items.setQuestion_txt(mcursor.getString(3));
            items.setOption1(mcursor.getString(4));
            items.setOption1(mcursor.getString(5));
            items.setOption1(mcursor.getString(6));
            items.setOption1(mcursor.getString(7));
            items.setAnswer(mcursor.getString(8));

            itemList.add(items);

            mcursor.moveToNext();
        }

        sqliteDatabase.close();

        return itemList;
    }

}
