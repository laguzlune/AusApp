package com.example.maist.ausapp;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class QuizList extends Fragment implements QuizListAdapter.onQuizItemClicked{

    private RecyclerView quizRecycler;
    private QuizListAdapter adapter;
    private List<Quiz> itemLists;
    public String selectedquestion;

    DBHelper mHelper;

    public QuizList() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super .onCreate(savedInstanceState);
        final View examView = inflater.inflate(R.layout.quiz_list, container, false);

        mHelper = new DBHelper(getActivity());

        quizRecycler = (RecyclerView) examView.findViewById(R.id.exam_recycler);

        itemLists = mHelper.getQuizList();
        adapter = new QuizListAdapter(getActivity(), itemLists);
        adapter.setListener(this);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 5);
        quizRecycler.setLayoutManager(mLayoutManager);
        quizRecycler.addItemDecoration(new GridSpacingItemDecoration(5, dpToPx(10), true));
        quizRecycler.setItemAnimator(new DefaultItemAnimator());
        quizRecycler.setAdapter(adapter);

        return examView;
    }

    @Override
    public void replaceFragment() {

        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.quiz_root_layout, new QnA(), "QnA");
        ft.commit();
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}