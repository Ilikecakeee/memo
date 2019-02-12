package com.example.memo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.memo.db.memos;
import com.example.memo.otherclass.ImageAdapter;
import com.example.memo.otherclass.image1;

import org.litepal.LitePal;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class ShowmemoActivity extends AppCompatActivity {
    private EditText title;
    private EditText tests;
    private List<image1> image1List=new ArrayList<>();
    private ImageAdapter adapter;
    private ScrollView memoLayout;
    public static final int TAKE_PHOTO=1;
    public static final int CHOOSE_PHOTO=2;
    private String imagepaths="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showmemo);
       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent=getIntent();
        final int data=intent.getIntExtra("memoid",0);//ID
        memoLayout=(ScrollView) findViewById(R.id.add_layout);
        title=(EditText) findViewById(R.id.title);
        tests=(EditText)findViewById(R.id.texts);


       List<memos> memoss1=LitePal.select("id","title","texts","imagePath").where("id = ?",String.valueOf(data))
                .find(memos.class);
       memos memos11=memoss1.get(0);
       title.setText(memos11.getTitle());
       tests.setText(memos11.getTexts());
       imagepaths=memos11.getImagePath();
          toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_back);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        String paths []=memos11.getImagePath().split(",");
        for(int i=0;i<paths.length;i++){
            image1 im=new image1(paths[i]);
            image1List.add(im);
        }
        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new ImageAdapter(image1List);
        adapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(ShowmemoActivity.this, "click " + image1List.get(position).getPath(), Toast.LENGTH_SHORT).show();
            }
        });
        adapter.setOnItemLongClickListener(new ImageAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, final int position) {//长按
                final String paths2=image1List.get(position).getPath();
                AlertDialog.Builder dialog=new AlertDialog.Builder(ShowmemoActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("删除图片？");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.removeData(position);
                        String paths []=imagepaths.split(",");
                        imagepaths="";
                        for(int i=0;i<paths.length;i++){
                            if(!paths[i].equals(paths2)){
                                imagepaths+=paths[i]+",";
                            }
                        }

                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
            }
        });
        recyclerView.setAdapter(adapter);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tuku:
                        MultiImageSelector.create().showCamera(false)
                                .start(ShowmemoActivity.this,CHOOSE_PHOTO);
                        break;
                    case R.id.paishe:

                        break;
                    case R.id.save:
                        String titile=title.getText().toString();
                        String texts=tests.getText().toString();
                        memos memos1=new memos();
                        memos1.setTitle(titile);
                        memos1.setTexts(texts);
                        memos1.setImagePath(imagepaths);
                        memos1.update(data);

                        break;
                    default:
                        break;
                }
                return true;
            }

        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;

    }



    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        //  删除数据

        switch(requestCode){
            case TAKE_PHOTO:

                break;
            case CHOOSE_PHOTO:

                if(resultCode == RESULT_OK){
                    // 获取返回的图片列表
                    List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    for(String paths:path){
                        imagepaths+=paths+",";
                        image1 im=new image1(paths);
                        image1List.add(im);
                    }
                    RecyclerView recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
                    LinearLayoutManager layoutManager=new LinearLayoutManager(this);
                    recyclerView.setLayoutManager(layoutManager);
                    adapter=new ImageAdapter(image1List);


                    adapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Toast.makeText(ShowmemoActivity.this, "click " + image1List.get(position).getPath(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    adapter.setOnItemLongClickListener(new ImageAdapter.OnItemLongClickListener() {
                        @Override
                        public void onItemLongClick(View view, final int position) {//长按
                            final String paths2=image1List.get(position).getPath();
                            AlertDialog.Builder dialog=new AlertDialog.Builder(ShowmemoActivity.this);
                            dialog.setTitle("提示");
                            dialog.setMessage("删除图片？");
                            dialog.setCancelable(false);
                            dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    adapter.removeData(position);
                                    String paths []=imagepaths.split(",");
                                    imagepaths="";
                                    for(int i=0;i<paths.length;i++){
                                        if(!paths[i].equals(paths2)){
                                            imagepaths+=paths[i]+",";
                                        }
                                    }

                                }
                            });
                            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dialog.show();
                        }
                    });
                    recyclerView.setAdapter(adapter);

                }
                break;
            default:
                break;
        }
    }





}
