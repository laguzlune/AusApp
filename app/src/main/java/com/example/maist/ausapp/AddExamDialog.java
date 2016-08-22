package com.example.maist.ausapp;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

/**
 * Created by MaIst on 21/8/2559.
 */
public class AddExamDialog extends DialogFragment{

    private ImageView mExamImg;
    private Button mAddExamImgBtn;
    private EditText mExamName, mExamDescription;
    private Spinner mSelectQType, mSelectOType;
    private String[] mQType, mOType;

    private DBHelper mhelper;

    public AddExamDialog() {

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        String title = getArguments().getString("title");
        AlertDialog.Builder addExamDialog = new AlertDialog.Builder(getActivity());
        addExamDialog.setTitle(title);
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_exam_main, null);
        addExamDialog.setView(view);

        mExamImg = (ImageView) view.findViewById(R.id.exam_img);

        return addExamDialog.create();
    }
}
