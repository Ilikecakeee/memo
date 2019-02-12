package com.example.memo;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Selection;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.memo.db.memos;
import com.example.memo.db.subject;
import com.example.memo.otherclass.ImageAdapter;
import com.example.memo.otherclass.image1;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class AddMemoActivity extends AppCompatActivity {
    private EditText title;
    private EditText tests;
    Calendar cal;
    String year;
    String month;
    String day;
    String my_time;
    String imagepaths="";
    public static final int TAKE_PHOTO=1;
    public static final int CHOOSE_PHOTO=2;
    private ImageView picture;
    private Uri imageuri;
    private List<image1> image1List=new ArrayList<>();
    private ImageAdapter adapter;
    private ScrollView memoLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memo);
        memoLayout=(ScrollView) findViewById(R.id.add_layout);
        title=(EditText) findViewById(R.id.title);
        tests=(EditText)findViewById(R.id.texts);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        //获取系统时间
        cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));//中国时区
        year = String.valueOf(cal.get(Calendar.YEAR));
        month = String.valueOf(cal.get(Calendar.MONTH))+1;
        day = String.valueOf(cal.get(Calendar.DATE));
        my_time = year + "-" + month + "-" + day;

       /* List<memos> memes=DataSupport.findAll(memos.class);//查询用
        for(memos memos:memes){
            Log.d("AddMemoActivity","ididididididididi"+memos.getId());
            Log.d("AddMemoActivity","titititititititititti"+memos.getTitle());
            Log.d("AddMemoActivity","txtxtxtxtxtxtxtx"+memos.getTexts());
            Log.d("AddMemoActivity","tiemtiemtiemtitmei"+memos.getDate());
            Log.d("AddMemoActivity","tyrtyrtyrtyrty"+memos.getImagePath());
        }*/
        //点击左边返回按钮监听事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tuku:

                        MultiImageSelector.create().showCamera(false)
                                .start(AddMemoActivity.this,CHOOSE_PHOTO);

                        break;
                    case R.id.paishe:
                        File outputImage=new File(getExternalCacheDir(),"output_image.jpg");
                        try{
                            if(outputImage.exists()){
                                outputImage.delete();
                            }
                            outputImage.createNewFile();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                        if(Build.VERSION.SDK_INT>=24){
                            imageuri=FileProvider.getUriForFile(AddMemoActivity.this,
                                    "com.example.memo.fileprovider",outputImage);
                        }else{
                            imageuri=Uri.fromFile(outputImage);
                        }
                        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageuri);
                        startActivityForResult(intent,TAKE_PHOTO);
                        break;
                    case R.id.save:

                        Intent intent2=getIntent();
                        int data=intent2.getIntExtra("subid",0);
                        String titile=title.getText().toString();
                        String texts=tests.getText().toString();
                        memos memos1=new memos();
                        memos1.setSubid(data);
                        memos1.setTitle(titile);
                        memos1.setTexts(texts);
                        memos1.setDate(my_time);
                        memos1.setImagePath(imagepaths);
                        memos1.save();

                        /*Intent intent=new Intent(AddMemoActivity.this,MemosActivity.class);
                        intent.putExtra("subid",data);*/
                        finish();
                        //startActivity(intent);
                        break;
                    default:
                        break;
                }
                return true;
            }

        });

    }

    public void openAlbm(){
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");

        startActivityForResult(intent,CHOOSE_PHOTO);
    }
    //  删除数据

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){

        switch(requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){

                    openAlbm();
                }else{
                    Toast.makeText(this,"申请权限失败",Toast.LENGTH_SHORT).show();
                }
                break;
                default:
        }
    }


    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        //  删除数据

        switch(requestCode){
            case TAKE_PHOTO:
                if(resultCode==RESULT_OK){
                    try{

                        Bitmap bitmap =BitmapFactory.decodeStream(getContentResolver().openInputStream(imageuri));
                        picture.setImageBitmap(bitmap);
                    }catch(FileNotFoundException e){
                        e.printStackTrace();

                    }
                }
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

                    //displayImage(path1[0]);

                    RecyclerView recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
                    LinearLayoutManager layoutManager=new LinearLayoutManager(this);
                    recyclerView.setLayoutManager(layoutManager);
                    adapter=new ImageAdapter(image1List);


                    adapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
                       @Override
                        public void onItemClick(View view, int position) {
                            Toast.makeText(AddMemoActivity.this, "click " + image1List.get(position).getPath(), Toast.LENGTH_SHORT).show();
                        }
                    });
                   adapter.setOnItemLongClickListener(new ImageAdapter.OnItemLongClickListener() {
                        @Override
                        public void onItemLongClick(View view, final int position) {//长按
                            final String paths2=image1List.get(position).getPath();
                            AlertDialog.Builder dialog=new AlertDialog.Builder(AddMemoActivity.this);
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
                                        //Log.d("AddMemoActivity","aaaaaa"+paths2);
                                       // Log.d("AddMemoActivity","bbbbb"+paths[i]);

                                    }
                                    Log.d("AddMemoActivity",imagepaths);
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

    private String getImagePath(Uri uri,String selection){
        String path=null;
        //通过URI和seleciton获取真实的图片路径
        Cursor cursor=getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath){
        if(imagePath!=null){
            Log.d("AddMemoActivitu","aaaaaaaaaaaaaa"+imagePath);
            imagepaths=imagePath;
            Bitmap bitmap=BitmapFactory.decodeFile(imagePath);
            picture.setImageBitmap(bitmap);
        }else{
            Toast.makeText(this,"false to get image",Toast.LENGTH_SHORT).show();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);

        return true;
    }




}
