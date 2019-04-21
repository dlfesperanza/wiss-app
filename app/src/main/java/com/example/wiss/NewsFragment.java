package com.example.wiss;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wiss.data.NewsArticle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class NewsFragment extends Fragment {
    private List<NewsArticle> newsArticleList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NewsArticleAdapter mAdapter;
    private ProgressDialog mProgressDialog;
    private String url = "https://news.abs-cbn.com/list/tag/weather";
    private ArrayList<String> mBlogTitleList = new ArrayList<>();
    private ArrayList<String> mBlogUploadDateList = new ArrayList<>();
    private ArrayList<String> mBlogSourceList = new ArrayList<>();
    private ArrayList<String> mBlogBodyList = new ArrayList<>();
    private ArrayList<String> mBlogLinkList = new ArrayList<>();
    private ArrayList<String> mBlogImageList = new ArrayList<>();
    public Context context;

    @Nullable
    @Override
    public Context getContext() {
        return super.getContext();
    }

    //    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_news, null);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        mAdapter = new NewsArticleAdapter(newsArticleList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        context = getContext();

        new Description().execute();
//        prepareNewsArticleData();



        return view;
    }
    private void prepareNewsArticleData() {
//        NewsArticle newsarticle = new NewsArticle("Lorem Ipsum", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean mattis lorem quam, molestie iaculis nibh pulvinar ut. Quisque molestie magna ut metus blandit sodales. In hac habitasse platea dictumst. Pellentesque magna tortor, laoreet ut suscipit ac, iaculis sit amet ex. Cras vehicula ligula nec enim rhoncus pharetra. Quisque maximus vel lacus...");
//        newsArticleList.add(newsarticle);
//
//        newsarticle = new NewsArticle("Phasellus auctor", "Phasellus auctor bibendum ex non venenatis. Donec iaculis dictum fermentum. Sed id pretium massa. Duis neque sem, finibus in dictum at, hendrerit quis urna. Nunc a sagittis urna, in tincidunt nisi. Vestibulum placerat nulla vitae nisl elementum eleifend. Mauris tristique sed diam ut pellentesque. Maecenas in lectus vitae nibh efficitur ultricies. Aliq...");
//        newsArticleList.add(newsarticle);
//
//        newsarticle = new NewsArticle("In volutpat odio", "In volutpat odio vel mi ullamcorper, eu auctor nulla aliquam. Mauris dapibus orci metus, quis tincidunt mauris maximus tempus. Suspendisse eget commodo neque. Nulla sed blandit lacus, eu sodales augue. Fusce auctor et dui sit amet auctor. Pellentesque et metus non massa dictum placerat vitae sit amet lacus. Sed efficitur non orci et laoreet...");
//        newsArticleList.add(newsarticle);

        mAdapter.notifyDataSetChanged();
    }

    private class Description extends AsyncTask<Void, Void, Void> {
        String desc;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getContext());
//            mProgressDialog.setTitle("Android Basic JSoup Tutorial");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document mBlogDocument = Jsoup.connect(url).get();
                // Using Elements to get the Meta data
                Elements mElementDataSize = mBlogDocument.select("article[class=clearfix]");
                Elements mElementLinkSize = mBlogDocument.select("article[class=clearfix] > a");
                for(Element item : mElementLinkSize){
                    String link  = item.attr("href");
                    mBlogLinkList.add(link);
//                    System.out.println("LINK NewsFragment: " + link);
                }
//                Elements mElementImageSize = mBlogDocument.select("article[class=clearfix] + a + img");
//                for(Element item : mElementImageSize){
//                    String img  = item.attr("src");
////                    mBlogLinkList.add(link);
//                    System.out.println("Image NewsFragment: " + img);
//                    mBlogImageList.add(img);
//                }



                // Locate the content attribute
                int mElementSize = mElementDataSize.size();

                for (int i = 0; i < mElementSize; i++) {
//                    Elements mElementAuthorName = mBlogDocument.select("span[class=vcard author post-author test]").select("a").eq(i);
//                    String mLink = mElementAuthorName.text();

                    Elements mElementBlogTitle = mBlogDocument.select("p[class=title]").select("a").eq(i);
                    String mBlogTitle = mElementBlogTitle.text();

                    Elements mElementBlogSource = mBlogDocument.select("span[class=author]").eq(i);
                    String mBlogSource = mElementBlogSource.text();


                    Elements mElementBlogUploadDate = mBlogDocument.select("span[class=datetime]").eq(i);
                    String mBlogUploadDate = mElementBlogUploadDate.text();

//                    Elements mElementBlogLink = mBlogDocument.select("a[href]").eq(i);
//                    String mBlogLink = mElementBlogLink.text();

                    Elements mElementBlogBody = mBlogDocument.select("p[class=text-gray] + p").eq(i);
                    String mBlogBody = mElementBlogBody.text();

                    Elements mElementImageBody = mBlogDocument.select("img[class=col-3 first]").eq(i);
                    String mBlogImage = mElementImageBody.attr("src");
//                    String mBlogImage = url.text();

                    mBlogSourceList.add(mBlogSource);
                    mBlogUploadDateList.add(mBlogUploadDate);
                    mBlogTitleList.add(mBlogTitle);

                    mBlogBodyList.add(mBlogBody);
                    mBlogImageList.add(mBlogImage);
//                    System.out.println("img: " + mBlogImage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            NewsArticle newsarticle;
            for (int i=0;i < mBlogTitleList.size();i++){
                newsarticle = new NewsArticle(mBlogImageList.get(i),mBlogTitleList.get(i),mBlogSourceList.get(i),mBlogUploadDateList.get(i),mBlogBodyList.get(i),mBlogLinkList.get(i),context);
                newsArticleList.add(newsarticle);
//                System.out.println("LINK:" + mBlogLinkList.get(i));
            }

//
            // Set description into TextView

//            RecyclerView mRecyclerView = (RecyclerView)findViewById(R.id.act_recyclerview);
//
//            DataAdapter mDataAdapter = new DataAdapter(MainActivity.this, mBlogTitleList, mAuthorNameList, mBlogUploadDateList);
//            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//            mRecyclerView.setLayoutManager(mLayoutManager);
//            mRecyclerView.setAdapter(mDataAdapter);

            mAdapter.notifyDataSetChanged();
            mProgressDialog.dismiss();

        }
    }
}