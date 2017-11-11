package com.myapplication.gaffey.scoretes;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 72356 on 2017/11/5.
 */

public class SetAnswerAdapter extends RecyclerView.Adapter<SetAnswerAdapter.ViewHolder> {


    private List<Answer> answerList = new ArrayList<>();

    public SetAnswerAdapter(List<Answer> list) {
        this.answerList = list;
    }


    @Override
    public SetAnswerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.change_paper_answer_item, parent, false);
        final SetAnswerAdapter.ViewHolder holder = new SetAnswerAdapter.ViewHolder(view);
        holder.group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                RadioButton select = (RadioButton) group.findViewById(checkedId);
                String result = select.getText().toString();
                String correct_answer = null;
                switch (checkedId) {
                    case R.id.select_a:
                        correct_answer = "A";
                        break;
                    case R.id.select_b:

                        correct_answer = "B";
                        break;
                    case R.id.select_c:
                        correct_answer = "C";
                        break;
                    case R.id.select_d:
                        correct_answer = "D";
                        break;
                    default:
                        break;

                }
                int id = Integer.parseInt(holder.answerId.getText().toString());
                Answer answer= answerList.get(id - 1);
                answer.setAnswerText(correct_answer);
                String ans_id = Integer.toString(answer.getAnswerId());
            }
        });
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Answer answer= answerList.get(position);
        if(answer.getAnswerId()<10) {
            holder.answerId.setText(  Integer.toString(answer.getAnswerId()));
        }else
        {
            holder.answerId.setText(Integer.toString(answer.getAnswerId()));
        }
    }

    @Override
    public int getItemCount() {
        return answerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView answerId;
        private RadioGroup group;

        public ViewHolder(View itemView) {
            super(itemView);
            answerId = (TextView) itemView.findViewById(R.id.answer_id);
            group = (RadioGroup) itemView.findViewById(R.id.select_answer);
        }
    }



}
