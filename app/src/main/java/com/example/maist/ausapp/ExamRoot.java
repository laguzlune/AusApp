package com.example.maist.ausapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by MaIst on 2/8/2559.
 */
public class ExamRoot extends Fragment{

    public ExamRoot() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View rootView = inflater.inflate(R.layout.exam_main, container, false);

        if (savedInstanceState == null) {

            final FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.quiz_root_layout, new QuizList(), "QnA");
            ft.commit();
        }
        return rootView;
    }
/*
    @Override
    public void replaceFragment() {

        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.quiz_root_layout, new QnA(), "QnA");
        ft.commit();
    }*/



}
