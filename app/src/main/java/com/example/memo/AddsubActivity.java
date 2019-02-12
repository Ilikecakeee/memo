package com.example.memo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.memo.db.subject;

import org.litepal.crud.DataSupport;
import org.litepal.crud.LitePalSupport;

import java.util.List;


public class AddsubActivity extends AppCompatActivity {
    private EditText subjectname;
    private Button addbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsub);
        subjectname=(EditText) findViewById(R.id.subject);
        addbutton=(Button)findViewById(R.id.addbutton);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        /*List<subject> subjects=DataSupport.findAll(subject.class);//查询用
        for(subject subject:subjects){
            Log.d("AddsubActivity","dddddddddddddddd"+subject.getId());
            Log.d("AddsubActivity","aaaaaaasafsdfsdfsdfsaa"+subject.getName());
        }*/
        //点击左边返回按钮监听事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        addbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String sub=subjectname.getText().toString();
                subject subject1=new subject();
                subject1.setName(sub);
                subject1.save();

                Intent intent=new Intent(AddsubActivity.this,MainActivity.class);

                startActivity(intent);


            }
        });

    }




}
