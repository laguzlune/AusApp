package com.example.maist.ausapp;

import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CommuMain extends Fragment implements AddItemDialog.AddDialogListener, CommuListAdapter.onCommuItemClicked {

    private String[] mCategories;
    private ListView mListView;
    public String selectedCategory = null;
    private RecyclerView recyclerView;
    private RecyclerView showPickRecyclerView;
    private CommuListAdapter adapter;
    private PickedListAdapter pickedListAdapter;
    private List<CommuItemList> itemLists;
    private List<CommuItemList> pickedItemList;
    private Button removeBtn;
    private Button speakBtn;

    ArrayList<String> ttsTmp = new ArrayList();
    String ttsTxt;

    DBHelper mHelper;

    public CommuMain() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View commuView = inflater.inflate(R.layout.commu_main, container, false);

        mHelper = new DBHelper(getActivity());

        mCategories = getResources().getStringArray(R.array.categories);
        mListView = (ListView) commuView.findViewById(R.id.category_drawer);
        recyclerView = (RecyclerView) commuView.findViewById(R.id.commu_recycler);
        showPickRecyclerView = (RecyclerView) commuView.findViewById(R.id.show_pic_recycler);

        removeBtn = (Button) commuView.findViewById(R.id.obj_del_btn);
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickedListAdapter.removeItem();
                ttsTmp.clear();
            }
        });

        speakBtn = (Button) commuView.findViewById(R.id.speakBtn);
        speakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ttsTxt = convertToString(ttsTmp);
                TexttoSpeech.getInstance(getActivity()).speak(ttsTxt);
                Log.d("TTS", ttsTxt);
            }
        });

        itemLists = mHelper.getCommuList(selectedCategory);
        adapter = new CommuListAdapter(getActivity(), itemLists);
        adapter.setListener(this);
        pickedItemList = new ArrayList<>() ;
        pickedListAdapter = new PickedListAdapter(getActivity(), pickedItemList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 5);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(5, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        showPickRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        showPickRecyclerView.setAdapter(pickedListAdapter);

        ArrayAdapter<String> catAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, mCategories);

        mListView.setAdapter(catAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = mListView.getItemAtPosition(position).toString();
                onUpdateData();
            }
        });
        return commuView;
    }

    @Override
    public void updatePickedList(CommuItemList item) {
        pickedListAdapter.addItem(pickedListAdapter.getItemCount(), item);
        showPickRecyclerView.getLayoutManager().scrollToPosition(pickedListAdapter.getItemCount()-1);
        ttsTmp.add(item.getName());
    }

    @Override
    public void removeCommuList(CommuItemList item) {
        Log.d("Get ID", String.valueOf(item.getId()));
        mHelper.removeItem(item.getId());
        onUpdateData();
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

    private void setData(int imageID, String name, String uri) {

    }

    @Override
    public void onUpdateData() {
        itemLists = mHelper.getCommuList(selectedCategory);
        adapter = new CommuListAdapter(getActivity(), itemLists);
        adapter.setListener(this);
        recyclerView.setAdapter(null);
        recyclerView.setAdapter(adapter);
        Log.d("enter", "refresh");
    }

    private String convertToString(ArrayList<String> str) {
        StringBuilder builder = new StringBuilder();
        for(String s : str) {
            builder.append(s);
            builder.append(" ");
        }
        return builder.toString();
    }

}



