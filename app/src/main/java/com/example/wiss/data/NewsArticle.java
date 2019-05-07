package com.example.wiss.data;

import android.content.Context;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class NewsArticle implements Comparable<NewsArticle> {
    private String title, body, source, link, image;
    private Date date;
    private Context context;

    public NewsArticle(){

    }

    public NewsArticle(String image, String title, String source, Date date, String body, String link, Context context){
        this.title = title;
        this.source = source;
        this.date = date;
        this.link = link;
        this.context = context;
        this.body = body;
        this.image = image;


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

    public Date getDate() {  return date; }

    public void setDate(Date date) { this.date = date; }

    public void setSource(String source) { this.source = source; }

    public String getSource() { return source; }

    public String getLink() { return link; }

    public Context getContext() { return context; }

    public String getImage() { return image; }

    @Override
    public int compareTo(NewsArticle u) {
        if (getDate() == null || u.getDate() == null) {
            return 0;
        }
        return getDate().compareTo(u.getDate());
    }



}
