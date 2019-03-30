package com.example.wiss;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wiss.data.NewsArticle;

import java.util.List;

public class NewsArticleAdapter extends RecyclerView.Adapter<NewsArticleAdapter.MyViewHolder> {

    private List<NewsArticle> newsArticleList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, body;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            body = (TextView) view.findViewById(R.id.body);
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
        NewsArticle movie = newsArticleList.get(position);
        holder.title.setText(movie.getTitle());
        holder.body.setText(movie.getBody());
    }

    @Override
    public int getItemCount() {
        return newsArticleList.size();
    }
}