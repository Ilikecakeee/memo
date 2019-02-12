package com.example.memo.db;

import org.litepal.crud.LitePalSupport;

public class memos extends LitePalSupport {
    private int id;
    private String title;
    private String texts;
    private String date;
    private String imagePath;
    private int subid;

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public String getTexts(){
        return texts;
    }
    public void setTexts(String texts){
        this.texts=texts;
    }
    public int getSubid(){
        return subid;
    }
    public void setSubid(int subid){
        this.subid=subid;
    }
    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date=date;
    }
    public String getImagePath(){
        return imagePath;
    }
    public void setImagePath(String imagePath){
        this.imagePath=imagePath;
    }

}
