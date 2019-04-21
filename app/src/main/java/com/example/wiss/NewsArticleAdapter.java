package com.example.wiss;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.wiss.data.NewsArticle;

import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class NewsArticleAdapter extends RecyclerView.Adapter<NewsArticleAdapter.MyViewHolder>   {

    private List<NewsArticle> newsArticleList;
    public String link;
    public Context context;
    NewsArticle article;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, body, date, source;
        public Button linkButton;
        public CardView cardview;


        public MyViewHolder(View view) {
            super(view);
            cardview = (CardView) view.findViewById(R.id.newscard);
            title = (TextView) view.findViewById(R.id.newstitle);
            date = (TextView) view.findViewById(R.id.newsdate);
            source = (TextView) view.findViewById(R.id.newssource);
            body = (TextView) view.findViewById(R.id.newsbody);
            linkButton = ((Button) view.findViewById(R.id.newsbutton));
            linkButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = (int) v.getTag();
            System.out.println("Button position: " + pos);
            String url = "https://news.abs-cbn.com";
            url = url.concat(newsArticleList.get(pos).getLink());
            System.out.println("Button link:" + url);
            Intent browserIntent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(url));
            context.startActivity(browserIntent);
        }

    }


    public NewsArticleAdapter(List<NewsArticle> newsArticleList) {
        this.newsArticleList = newsArticleList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        article = newsArticleList.get(position);
        holder.title.setText(article.getTitle());
        holder.date.setText(article.getDate());
        holder.source.setText(article.getSource());


        context = article.getContext();
        link = "https://news.abs-cbn.com";
        link = link.concat(article.getLink());
        System.out.println("LINK-- " + link);
//        holder.linkButton.set(article.getLink());
        holder.body.setText(link);
        holder.linkButton.setTag(position);
    }



    @Override
    public int getItemCount() {
        return newsArticleList.size();
    }

}