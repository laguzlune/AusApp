package com.example.maist.ausapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by MaIst on 1/8/2559.
 */
public class QuizListAdapter extends RecyclerView.Adapter<QuizListAdapter.MyViewHolder>{

    private Context mcontext;
    private List<Quiz> itemLists;
    private onQuizItemClicked quizListener;

    public interface onQuizItemClicked {
        void replaceFragment(Fragment fragment);
    }

    public void setListener(onQuizItemClicked listener) {
        this.quizListener = listener;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView quizPic;
        public TextView quizName;
        public TextView quizDes;
        public TextView quizType;
        public TextView quizNum;
        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            quizPic = (ImageView) view.findViewById(R.id.quiz_pic);
            quizName = (TextView) view.findViewById(R.id.quiz_name);
            quizDes = (TextView) view.findViewById(R.id.quiz_description);
            quizType = (TextView) view.findViewById(R.id.quiz_type);
            quizNum = (TextView) view.findViewById(R.id.quiz_num);
            cardView = (CardView) view.findViewById(R.id.card_view);
        }

    }

    public QuizListAdapter (Context mcontext, List<Quiz> itemLists) {
        this.mcontext = mcontext;
        this.itemLists = itemLists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quiz_list_item, parent, false);
        Log.d("quiz_list_item","create");
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Quiz quizList = itemLists.get(position);
        holder.quizName.setText(quizList.getName());
        holder.quizDes.setText(quizList.getDescription());
        holder.quizType.setText("ประเภท :" + quizList.getType());
        holder.quizNum.setText(String.valueOf(quizList.getNum_question()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fm = new QnA();
                quizListener.replaceFragment(fm);
                //quizListener.getSelectedQuiz(quizList);
            }
        });

    }

    public int getItemCount() {
        return itemLists.size();
    }
}
