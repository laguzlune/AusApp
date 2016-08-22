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
public class ExamRoot extends Fragment implements QuizListAdapter.onQuizItemClicked{

    //private QuizListAdapter adapter;
    //DBHelper mhelper;

    public ExamRoot() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View rootView = inflater.inflate(R.layout.exam_main, container, false);

        if (savedInstanceState == null) {

            final FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.quiz_root_layout, new QuizList(), "QuizList");
            ft.commit();
        }
        return rootView;
    }

    @Override
    public void replaceFragment(Fragment target_frag) {

        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.quiz_root_layout, target_frag, "target_frag");
        ft.commit();
    }



}
