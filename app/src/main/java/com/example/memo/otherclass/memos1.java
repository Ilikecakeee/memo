package com.example.memo.otherclass;

public class memos1 {
    private int id;
    private String title;
    private String texts;
    private String date;
    private int subid;
    public memos1(int id,String title,String texts,int subid,String date){
        this.id=id;
        this.title=title;
        this.texts=texts;
        this.subid=subid;
        this.date=date;

    }
    public int getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public String getTexts(){
        return texts;
    }
    public int getSubid(){
        return subid;
    }
    public String getDate(){
        return date;
    }

}
