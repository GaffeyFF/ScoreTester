package com.myapplication.gaffey.scoretes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Random;

/**
 * Created by 72356 on 2017/11/2.
 */

public class PaperAdapter extends RecyclerView.Adapter<PaperAdapter.ViewHolder> {

    private String TAG = "PaperAdapter";
    private Context mContext;
    private List<Paper> paperList;
    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView paperName;
        ImageView paperImage;
        CheckBox checkBox;
        LinearLayout linearLayout;
        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            linearLayout = view.findViewById(R.id.linear_layout);
            paperImage = (ImageView) view.findViewById(R.id.paper_image);
            paperName = (TextView) view.findViewById(R.id.paper_name);
            checkBox = view.findViewById(R.id.check_box);
        }
    }

    public PaperAdapter(List<Paper> list) {
        Log.d(TAG, "PaperAdapter Created");
        paperList = list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.paper_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);


        if (!MainActivity.is_in_action_mode){
            //隐藏checkbox
            holder.checkBox.setVisibility(View.GONE);
            holder.checkBox.setChecked(false);

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getAdapterPosition();
                    Paper paper =paperList.get(position);

                    Intent intent = new Intent(mContext, PaperActivity.class);
                    intent.putExtra(PaperActivity.PAPER_NAME,paper.getPaperName());
                    intent.putExtra(PaperActivity.PAPER_IMAGE,paper.getPaperImage());
                    intent.putExtra(PaperActivity.PAPER_ID,paper.getId());
                    mContext.startActivity(intent);

                }
            });
        }else {
            //显示checkbox
            holder.checkBox.setVisibility(View.VISIBLE);
           // holder.checkBox.setChecked(false);
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    int position = holder.getAdapterPosition();
                    Paper paper = paperList.get(position);
                    if(b)
                    {
                        MainActivity.selectList.add(paper);
                      //  Toast.makeText(mContext,"add "+paper.getPaperName(),Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        for(int i=0;i<MainActivity.selectList.size();i++) {
                            if (MainActivity.selectList.get(i).getId() == paper.getId()) {
                                MainActivity.selectList.remove(i);
                              //  Toast.makeText(mContext,"remove "+paper.getPaperName(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getAdapterPosition();
                    Paper paper = paperList.get(position);
                   //TODO: item选择事件
                    if(holder.checkBox.isChecked())
                    {


                        holder.checkBox.setChecked(false);

                    }
                    else {
                        holder.checkBox.setChecked(true);

                    }
                }
            });


        }
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Paper paper = paperList.get(position);
        holder.paperName.setText(paper.getPaperName().toString());

        holder.paperImage.setImageResource(paper.getPaperImage());

    }


    @Override
    public int getItemCount() {
        return paperList.size();
    }

}
