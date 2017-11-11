package com.myapplication.gaffey.scoretes;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static android.content.ContentValues.TAG;

/**
 * Created by 72356 on 2017/11/5.
 */

public class AddPaperActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "AddPaperActivity";
    private EditText questionNumber;
    private Button numberSub;
    private Button numberAdd;
    private RecyclerView recyclerView;
    private SetAnswerAdapter adapter;
    private Button submit;
    private Button cancel;
    private AutoCompleteTextView textView;
    private LinearLayout layout;
    private DatabaseHelper dbHelper;
    private List<Answer> answerList = new ArrayList<>();
    private int background;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_paper_answer_layout);
        questionNumber = (EditText) findViewById(R.id.question_number);
        numberAdd = (Button) findViewById(R.id.number_add);
        numberSub = (Button) findViewById(R.id.number_sub);
        submit = (Button) findViewById(R.id.set_submit);
        cancel = (Button) findViewById(R.id.set_cancel);
        layout = findViewById(R.id.linear_layout);
        numberAdd.setOnClickListener(this);
        numberSub.setOnClickListener(this);
        cancel.setOnClickListener(this);
        submit.setOnClickListener(this);
        setBg();

        initAnswer();
        recyclerView = (RecyclerView) findViewById(R.id.set_answer);
        LinearLayoutManager layoutManger = new LinearLayoutManager(this);
        layoutManger.setOrientation(LinearLayoutManager.VERTICAL);
        //适配器适配
        recyclerView.setLayoutManager(layoutManger);
        adapter = new SetAnswerAdapter(answerList);
        recyclerView.setAdapter(adapter);

        questionNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                Toast.makeText(SetAnswerActivity.this, questionNumber.getText().toString(), Toast.LENGTH_SHORT).show();
                initAnswer();
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(new SetAnswerAdapter(answerList));
            }
        });


        //AutoCompleteTextViews配置
        textView = findViewById(R.id.set_paper_name);
        textView.setOnClickListener(this);
        textView.setFocusable(true);
        Log.d(TAG, "textView false ");
    }

    private void setBg() {
        Random random = new Random();
        int index = random.nextInt(5);
        background = R.color.color0 + index;
        layout.setBackgroundResource(background);
    }

    @Override
    public void onClick(View v) {
        int number = Integer.parseInt(questionNumber.getText().toString());

        switch (v.getId()) {
            case R.id.set_paper_name:

                Log.d(TAG, "textView true ");
                break;

            case R.id.number_sub:
                if (number > 0) {

                    questionNumber.setText(Integer.toString(number - 1));
                }

                break;
            case R.id.number_add:
                if (number < 100) {
                    questionNumber.setText(Integer.toString(number + 1));
                }
                break;
            case R.id.set_cancel:
                finish();
                break;
            case R.id.set_submit:
                // Toast.makeText(SetAnswerActivity.this, "you click submit button ", Toast.LENGTH_SHORT).show();
                //检查radio button 是否全选

                if (AllSelectedIsOK()) {
                    try {
                        //create database
                        dbHelper = new DatabaseHelper(this, "Database1.db", null, 2);
                        insertData();
                        Toast.makeText(AddPaperActivity.this, "Update database successful", Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {

                        finish();
                    }
                } else {
                    Toast.makeText(AddPaperActivity.this, "Please Select All", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;

        }
    }


    private void insertData() {


        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("paperImage", background);
        values.put("paperName", textView.getText().toString());

        db.insert("paper", null, values);
        //TODO 填充数据给answer数据库

        //创建数据库 添加数据
        //android 中数据库处理，特别是使用cursor时，注意初始位置，好像是从下标为-1的地方开始的，也就是说一次查询中，返回给cursor查询结果时，不能够马上从cursor中提取值。
        Cursor cursor = db.rawQuery("select id from paper where paperName = '" + textView.getText().toString() + "'", null);
        int id = 0;
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }
        values.clear();
        //创建Table 添加数据
        String tableName = "answer" + Integer.toString(id);

        String CREATE_ANSWER = "create table " +
                tableName +
                "(" +
                "id integer," +
                "selectanswer string)";
        db.execSQL(CREATE_ANSWER);
        Log.d(TAG, "createTable: " + tableName);


        for (int i = 0; i < answerList.size(); i++) {
            Answer answer = answerList.get(i);
            values.put("id", answer.getAnswerId());
            values.put("selectanswer", answer.getAnswerText());
            db.insert(tableName, null, values);
            values.clear();
        }
    }


    private boolean AllSelectedIsOK() {
        int num = adapter.getItemCount();
        for (int i = 0; i < num; i++) {
            if (answerList.get(i).getAnswerText() == null) {
                return false;
            }
        }
        if (textView.getText().toString().isEmpty()) {
            return false;
        }
        return true;
    }


    public void initAnswer() {

        //answerList.clear();


        int number = Integer.parseInt(questionNumber.getText().toString());
        while (answerList.size() > number)//1~11>10
        {
            answerList.remove(answerList.size() - 1);

        }
        while (answerList.size() < number) {
            Answer answer = new Answer(answerList.size() + 1, null);
            answerList.add(answer);
        }


    }

}
