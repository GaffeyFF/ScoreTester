package com.myapplication.gaffey.scoretes;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 72356 on 2017/10/15.
 */

public class ScoreFragment extends Fragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        dbHelper = new DatabaseHelper(getActivity(), "AnswerDatabase.db", null, 2);
//        initScore();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.score_layout, container, false);


        //绑定适配器
        LinearLayoutManager layoutManager =new LinearLayoutManager(getContext());

        RecyclerView recyclerView = view.findViewById(R.id.score_list);

        recyclerView.setLayoutManager(layoutManager);
        AnswerAdapter adapter = new AnswerAdapter(PaperActivity.list);
        recyclerView.setAdapter(adapter);


        int score =returnScore();
        TextView result =view.findViewById(R.id.result);
        result.setText("正确数量:"+Integer.toString(score));

        return view;
    }

    private int returnScore() {
        int score =0;
        for(int i=0;i<PaperActivity.list.size();i++)
        {
            Answer answer = PaperActivity.list.get(i);
            if(answer.getAnswerText().equals(answer.getMyAnswer()))
            {
                score++;
            }
        }
        return score;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
}

