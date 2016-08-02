package com.example.maist.ausapp;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by gotzillaz on 7/31/16 AD.
 */
public class PickedListAdapter extends RecyclerView.Adapter<PickedListAdapter.MyViewHolder> {

    private Context mcontext;
    private List<CommuItemList> itemLists;

    public PickedListAdapter (Context mcontext, List<CommuItemList> itemLists){
        this.mcontext = mcontext;
        this.itemLists = itemLists;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView commuPic;
        public TextView description;
        public CardView cardView;

        public  MyViewHolder(View view) {
            super(view);
            commuPic = (ImageView) view.findViewById(R.id.commu_pic);
            description = (TextView) view.findViewById(R.id.pic_description);
            cardView = (CardView) view.findViewById(R.id.item_card);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.picked_list_item, parent, false);
        Log.d("picked_list_item","create");
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final CommuItemList commuItemList = itemLists.get(position);
        int resId = mcontext.getResources().getIdentifier(commuItemList.getUri(), "drawable", mcontext.getPackageName());
        holder.description.setText(commuItemList.getName());

        if (commuItemList.getEditable() == 0) {
//            Log.d("resID NO", commuItemList.getEditable()+"");
            Glide.with(mcontext).load(resId).fitCenter().into(holder.commuPic);
        } else {
            Log.d("resID YES", commuItemList.getEditable()+"");
            //Glide.with(mcontext).load(R.drawable.apple).fitCenter().into(holder.commuPic);
            Glide.with(mcontext).load(Uri.parse(commuItemList.getUri())).into(holder.commuPic);
//            holder.commuPic.setImageURI(null);
//            Toast.makeText(mcontext.getApplicationContext(), d+"",Toast.LENGTH_SHORT).show();
        }
    }

    public void addItem(int position, CommuItemList item) {
        Log.d("URI picked", item.getUri());
        this.itemLists.add(position, item);
        notifyDataSetChanged();
    }

    public void removeItem() {
        this.itemLists.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemLists.size();
    }

}
