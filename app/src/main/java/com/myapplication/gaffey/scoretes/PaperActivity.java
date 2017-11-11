package com.myapplication.gaffey.scoretes;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 72356 on 2017/11/3.
 */

public class PaperActivity extends AppCompatActivity {
    final  private String TAG = "PaperActivity";
    public static final String PAPER_NAME="paper_name";
    public static final String PAPER_IMAGE="paper_image";
    public static final String PAPER_ID="paper_id";
    public static Bitmap bitmap;
    public static List<Answer> list;

    private DatabaseHelper dbHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paper_layout);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //返回数据
        Intent intent =getIntent();
        String name= intent.getStringExtra(PAPER_NAME);
        int image_id =intent.getIntExtra(PAPER_IMAGE,0);
        int paper_id =intent.getIntExtra(PAPER_ID,0);


        String tableName = "answer"+Integer.toString(paper_id);
        //toolbar设置
        Toolbar toolbar = findViewById(R.id.toolbar);
//        CollapsingToolbarLayout collapsingToolbarLayout =  findViewById(R.id.collapsing_toolbar);
        ImageView imageView =  findViewById(R.id.paper_image);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
//        collapsingToolbarLayout.setTitle(name);
        Log.d(TAG, "PAPER_NAME :"+name);
        Log.d(TAG, "PAPER_IMAGE : "+image_id);
        Log.d(TAG, "TABLE : "+tableName);
//        imageView.setImageResource(image_id);
//        Glide.with(this).load(image_id).into(imageView);

        list =new ArrayList<>();
        insertData(tableName);

    }

    private void insertData(String name) {
        dbHelper = new DatabaseHelper(this, "Database1.db", null, 2);
        SQLiteDatabase  db =dbHelper.getWritableDatabase();
        Cursor cursor =db.query(name,null,null,null,null,null,null);
        if(cursor.moveToFirst())
        {
            do{
               int answerId=cursor.getInt(cursor.getColumnIndex("id"));
                String answerText = cursor.getString(cursor.getColumnIndex("selectanswer"));
                Answer answer =new Answer(answerId,answerText);
                Log.d(TAG, "insertData: id: "+Integer.toString(answerId)+"   answer:"+answerText);
                list.add(answer);
            }while (cursor.moveToNext());

        }
        cursor.close();
    }

    @Override
    protected void onPause() {
        bitmap=null;
        super.onPause();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        Fragment fragment =null;
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch(item.getItemId())
            {
                case R.id.nav_tester:
                    fragment =new TakePhotoFragment();
                    break;
                case R.id.nav_score:
                    fragment = new ScoreFragment();
                    break;
                default:
                    break;
            }
            if(fragment!=null){
                getSupportFragmentManager().beginTransaction().replace(R.id.content,fragment).commit();
                return true;
            }else{
                return false;
            }

        }
    };
}
