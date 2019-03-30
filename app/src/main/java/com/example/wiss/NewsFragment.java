package com.example.wiss;

import android.os.Bundle;
//import android.support.annotation.Nullable;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wiss.data.NewsArticle;


public class NewsFragment extends Fragment {
    private List<NewsArticle> newsArticleList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NewsArticleAdapter mAdapter;
//    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_news);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        View view = inflater.inflate(R.layout.fragment_news, null);
//        mainWebView = (WebView) view.findViewById(R.id.mainWebView);
//        return view;

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        mAdapter = new NewsArticleAdapter(newsArticleList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareNewsArticleData();

        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        return view;
    }
    private void prepareNewsArticleData() {
        NewsArticle newsarticle = new NewsArticle("GOT7 World Tour 2019", "JB, Mark, Jackson, Jinyoung, Youngjae, Bambam, Yugyeom");
        newsArticleList.add(newsarticle);

        newsarticle = new NewsArticle("SABLAY 2019", "Dannah Louise Esperanza");
        newsArticleList.add(newsarticle);

        newsarticle = new NewsArticle("Thank you", "Thank you God, Mama and GOT7.");
        newsArticleList.add(newsarticle);

        mAdapter.notifyDataSetChanged();
    }
}