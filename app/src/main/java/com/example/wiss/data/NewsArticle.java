package com.example.wiss.data;

import android.content.Context;

public class NewsArticle {
    private String title, body, date, source, link;
    private Context context;

    public NewsArticle(){

    }

    public NewsArticle(String title, String source, String date, String link, Context context){
        this.title = title;
        this.source = source;
        this.date = date;
        this.link = link;
        this.context = context;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getBody(){
        return body;
    }

    public void setBody(String body){
        this.body = body;
    }

    public String getDate() {  return date; }

    public void setDate(String date) { this.date = date; }

    public void setSource(String source) { this.source = source; }

    public String getSource() { return source; }

    public String getLink() { return link; }

    public Context getContext() { return context; }
}
