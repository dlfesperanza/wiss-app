package com.example.wiss.data;

public class NewsArticle {
    private String title, body;

    public NewsArticle(){

    }

    public NewsArticle(String title, String body){
        this.title = title;
        this.body = body;
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
}
