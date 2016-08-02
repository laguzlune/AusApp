package com.example.maist.ausapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gc.materialdesign.views.Card;

import java.lang.reflect.Field;
import java.util.List;

public class CommuListAdapter extends RecyclerView.Adapter<CommuListAdapter.MyViewHolder> {

    private Context mcontext;
    private List<CommuItemList> itemLists;
    private onCommuItemClicked pickedListener;

    public interface onCommuItemClicked {
        void updatePickedList(CommuItemList item);
        void removeCommuList(CommuItemList item);
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

    public CommuListAdapter (Context mcontext, List<CommuItemList> itemLists) {
        this.mcontext = mcontext;
        this.itemLists = itemLists;
    }

    public void setItemList(List<CommuItemList> itemLists) {
        this.itemLists = itemLists;
    }

    public void setListener(onCommuItemClicked listener) {
        this.pickedListener = listener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.commu_list_item, parent, false);
        Log.d("commu_list_item","create");
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final CommuItemList commuItemList = itemLists.get(position);
        int resId = mcontext.getResources().getIdentifier(commuItemList.getUri(), "drawable", mcontext.getPackageName());
        holder.description.setText(commuItemList.getName());

        if (commuItemList.getEditable() == 0) {
            Glide.with(mcontext).load(resId).override(400,400).centerCrop().into(holder.commuPic);
        } else {
            //Glide.with(mcontext).load(R.drawable.apple).fitCenter().into(holder.commuPic);
            Glide.with(mcontext).load(Uri.parse(commuItemList.getUri())).override(400,400).centerCrop().into(holder.commuPic);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickedListener.updatePickedList(commuItemList);
                Log.d("URI commu", commuItemList.getUri());
//                Toast.makeText(view.getContext(), commuItemList.getName(),Toast.LENGTH_SHORT).show();
            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                builder.setMessage("ยืนยันการลบรูปภาพ");
                builder.setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pickedListener.removeCommuList(commuItemList);
                        Log.d("Name commu", String.valueOf(commuItemList.getId()));
                    }
                });
                builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                return true;
            }
        });

    }

    public int getItemCount() {
        return itemLists.size();
    }


}
