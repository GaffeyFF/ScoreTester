package com.myapplication.gaffey.scoretes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by 72356 on 2017/11/10.
 */

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {

    private List<Answer> answerList ;
    private Context mContext;



    public AnswerAdapter(List<Answer> list)
    {
        answerList=list;
    }
    @Override
    public AnswerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext==null)
        {
            mContext=parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.score_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AnswerAdapter.ViewHolder holder, int position)
    {
        Answer answer=answerList.get(position);
        holder.myAnswer.setText(answer.getMyAnswer());
        if(answer.getAnswerId()<10) {
            holder.id.setText(Integer.toString(answer.getAnswerId()));
        }else{
            holder.id.setText(Integer.toString(answer.getAnswerId()));
        }
        holder.answer.setText(answer.getAnswerText());
    }

    @Override
    public int getItemCount() {
        return answerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView answer;
        TextView id;
        TextView myAnswer;
        public ViewHolder(View view) {
            super(view);
            answer =view.findViewById(R.id.score_answer);
            id =view.findViewById(R.id.score_id);
            myAnswer = view.findViewById(R.id.my_answer);

        }
    }
}
