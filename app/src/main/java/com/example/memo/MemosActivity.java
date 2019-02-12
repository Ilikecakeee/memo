package com.example.memo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.memo.db.memos;
import com.example.memo.otherclass.MemosAdapter;
import com.example.memo.otherclass.SubAdapter;
import com.example.memo.otherclass.memos1;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class MemosActivity extends AppCompatActivity {
    private List<memos1> memosList=new ArrayList<>();
    private int data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent=getIntent();
        data=intent.getIntExtra("subid",0);//defaultvalue:当传输错误就传输这个值

        initMemos(data);
        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        MemosAdapter adapter=new MemosAdapter(memosList);
        recyclerView.setAdapter(adapter);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        //Log.d("MemosActivity","asdasdasdasdasdasd"+data);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MemosActivity.this,AddMemoActivity.class);
                intent.putExtra("subid",data);

                startActivity(intent);

            }
        });
    }

    private void initMemos( int data){
        List<memos> memoss=LitePal.select("id","title","texts","date","subid")
                .where("subid = ?",String.valueOf(data)).order("id desc").find(memos.class);
        for(memos memos11:memoss){
           /* Log.d("AddsubActivity","dddddddddddddddd"+subject.getId());
            Log.d("AddsubActivity","aaaaaaasafsdfsdfsdfsaa"+subject.getName());*/
            memos1 me=new memos1(memos11.getId(),memos11.getTitle(),memos11.getTexts(),
                    memos11.getSubid(),memos11.getDate());
            memosList.add(me);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        memosList.clear();
        initMemos(data);


    }



}
