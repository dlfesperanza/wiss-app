package com.example.wiss;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        NewsArticle newsarticle = new NewsArticle("Lorem Ipsum", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean mattis lorem quam, molestie iaculis nibh pulvinar ut. Quisque molestie magna ut metus blandit sodales. In hac habitasse platea dictumst. Pellentesque magna tortor, laoreet ut suscipit ac, iaculis sit amet ex. Cras vehicula ligula nec enim rhoncus pharetra. Quisque maximus vel lacus...");
        newsArticleList.add(newsarticle);

        newsarticle = new NewsArticle("Phasellus auctor", "Phasellus auctor bibendum ex non venenatis. Donec iaculis dictum fermentum. Sed id pretium massa. Duis neque sem, finibus in dictum at, hendrerit quis urna. Nunc a sagittis urna, in tincidunt nisi. Vestibulum placerat nulla vitae nisl elementum eleifend. Mauris tristique sed diam ut pellentesque. Maecenas in lectus vitae nibh efficitur ultricies. Aliq...");
        newsArticleList.add(newsarticle);

        newsarticle = new NewsArticle("In volutpat odio", "In volutpat odio vel mi ullamcorper, eu auctor nulla aliquam. Mauris dapibus orci metus, quis tincidunt mauris maximus tempus. Suspendisse eget commodo neque. Nulla sed blandit lacus, eu sodales augue. Fusce auctor et dui sit amet auctor. Pellentesque et metus non massa dictum placerat vitae sit amet lacus. Sed efficitur non orci et laoreet...");
        newsArticleList.add(newsarticle);

        mAdapter.notifyDataSetChanged();
    }
}