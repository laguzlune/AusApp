package com.example.maist.ausapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by MaIst on 1/8/2559.
 */
public class QnA extends Fragment{

    private List<QuestionAnswer> itemLists;
    private TextView question, questionNum;
    private RadioButton opt1, opt2, opt3, opt4;
    private Button nextBtn;

    int qid = 0;
    int score = 0;
    QuestionAnswer currentQ;

    DBHelper mHelper;

    public  QnA() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super .onCreate(savedInstanceState);
        final View questionView = inflater.inflate(R.layout.fragment_txt_question, container, false);

        question = (TextView) questionView.findViewById(R.id.question);
        questionNum = (TextView) questionView.findViewById(R.id.question_num_indicate);
        opt1 = (RadioButton) questionView.findViewById(R.id.opt1);
        opt2 = (RadioButton) questionView.findViewById(R.id.opt2);
        opt3 = (RadioButton) questionView.findViewById(R.id.opt3);
        opt4 = (RadioButton) questionView.findViewById(R.id.opt4);
        nextBtn = (Button) questionView.findViewById(R.id.nextBtn);

        mHelper = new DBHelper(getActivity());

        itemLists = mHelper.getQuestionandAnswer();
        currentQ = itemLists.get(qid);



        return questionView;
    }

    private void setQuestionView()
    {
        questionNum.setText("คำถาม");
        question.setText(currentQ.getQuestion_txt());
        opt1.setText(currentQ.getOption1());
        opt2.setText(currentQ.getOption2());
        opt3.setText(currentQ.getOption3());
        opt4.setText(currentQ.getOption4());
        qid++;
    }

}
