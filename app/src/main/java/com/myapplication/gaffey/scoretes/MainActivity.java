package com.myapplication.gaffey.scoretes;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    private List<Paper> paperList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PaperAdapter adapter;
    private DrawerLayout mDrawerLayout;
    private SwipeRefreshLayout swipeRefresh;
    private DatabaseHelper dbHelper;
    public static boolean is_in_action_mode = false;
    public static List<Paper> selectList = new ArrayList<>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        Log.d(TAG, "onCreate: onCreateOptionsMenu   :" + menu);
        if (!is_in_action_mode) {
            getMenuInflater().inflate(R.menu.toolbar, menu);
        } else {
            getMenuInflater().inflate(R.menu.nav_select_menu, menu);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DatabaseHelper(this, "Database1.db", null, 2);
        //设置toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        Log.d(TAG, "Create recycler");

        //填充数据到recyclerView
        initPaper();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PaperAdapter(paperList);
        recyclerView.setAdapter(adapter);



        mDrawerLayout = findViewById(R.id.drawer_layout);


        //下拉刷新
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshPaper();
            }
        });


    }

    private void refreshPaper() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                        initPaper();

                        recyclerView.setAdapter(new PaperAdapter(paperList));
                    }
                });
            }
        }).start();
    }

    @Override
    protected void onRestart() {
        initPaper();
        recyclerView.setAdapter(new PaperAdapter(paperList));
        super.onRestart();

    }

    private void initPaper() {
        paperList.clear();
//        for (int i = 0; i < 5; i++) {
//            Paper paper = new Paper(i, "试卷", R.color.color0 + i);
//            paperList.add(paper);
//
//        }
//        paperList.add(new Paper(-1, "添加试卷", R.drawable.ic_add_circle_outline_black_24dp));
//         数据库查询

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("paper", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                try {
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String name = cursor.getString(cursor.getColumnIndex("paperName"));
                    int imageId = cursor.getInt(cursor.getColumnIndex("paperImage"));
                    paperList.add(new Paper(id, name,imageId));
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.add:
                //Toast.makeText(this, "you click backup", Toast.LENGTH_SHORT).show();
                //Log.d("MainActivity", "onOptionsItemSelected: backup");
                Intent intent = new Intent(MainActivity.this, AddPaperActivity.class);
                startActivity(intent);
                break;
            case R.id.delete:
                is_in_action_mode = true;
                recyclerView.setAdapter(adapter);
                invalidateOptionsMenu();
                //Toast.makeText(this, "you click delete", Toast.LENGTH_SHORT).show();
                Log.d("MainActivity", "onOptionsItemSelected: delete");
                break;
            case R.id.setting:
                Toast.makeText(this, "you click setting", Toast.LENGTH_SHORT).show();
                Log.d("MainActivity", "onOptionsItemSelected: setting");
                break;
            case R.id.nav_done:
                is_in_action_mode = false;
                //删数据
                deleteData();
                initPaper();
                recyclerView.setAdapter(adapter);
                invalidateOptionsMenu();
                selectList.clear();
                break;
            case R.id.nav_cancel:
                is_in_action_mode = false;
                initPaper();
                recyclerView.setAdapter(adapter);
                invalidateOptionsMenu();
                selectList.clear();
                break;

            default:
        }
        return true;
    }

    private void deleteData() {
        for(int i=0;i<selectList.size();i++){
            try {
                int id = selectList.get(i).getId();
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("paper", "id = ?", new String[]{Integer.toString(id)});
                Toast.makeText(MainActivity.this, "delete data successful", Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
