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
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wiss.data.NewsArticle;
import com.example.wiss.data.WeatherFunction;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class NewsFragment extends Fragment {
    private List<NewsArticle> newsArticleList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NewsArticleAdapter mAdapter;
    private ProgressDialog mProgressDialog;
    private String url1 = "https://news.abs-cbn.com/list/tag/weather";
    private String url2 = "https://www.rappler.com/previous-articles?filterCategory=223";
    private String url3 = "https://newsinfo.inquirer.net/tag/weather";
    private ArrayList<String> mBlogTitleList = new ArrayList<>();
    private ArrayList<String> mBlogUploadDateList = new ArrayList<>();
    private ArrayList<String> mBlogSourceList = new ArrayList<>();
    private ArrayList<String> mBlogBodyList = new ArrayList<>();
    private ArrayList<String> mBlogLinkList = new ArrayList<>();
    private ArrayList<String> mBlogImageList = new ArrayList<>();
    public Context context;
    ProgressBar loader;

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
        loader = (ProgressBar) view.findViewById(R.id.loader_news);

        mAdapter = new NewsArticleAdapter(newsArticleList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        context = getContext();

        if (WeatherFunction.isNetworkAvailable(getActivity().getApplicationContext())) {
            new Description().execute();
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }




        return view;
    }


    private class Description extends AsyncTask<Void, Void, Void> {
        String desc;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            mProgressDialog = new ProgressDialog(getContext());
////            mProgressDialog.setTitle("Android Basic JSoup Tutorial");
//            mProgressDialog.setMessage("Loading...");
//            mProgressDialog.setIndeterminate(false);
//            mProgressDialog.show();
//            loader.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // ABS-CBN
                Document mBlogDocument1 = Jsoup.connect(url1).get();
                Elements mElementDataSize = mBlogDocument1.select("article[class=clearfix]");
                Elements mElementLinkSize = mBlogDocument1.select("article[class=clearfix] > a");
                for(Element item : mElementLinkSize){
                    String link = "https://news.abs-cbn.com";
                    String link2  = item.attr("href");
                    mBlogLinkList.add(link.concat(link2));
//                    System.out.println("LINK NewsFragment: " + link);
                }

                int mElementSize = mElementDataSize.size();

                for (int i = 0; i < mElementSize; i++) {

                    Elements mElementBlogTitle = mBlogDocument1.select("p[class=title]").select("a").eq(i);
                    String mBlogTitle = mElementBlogTitle.text();

                    Elements mElementBlogSource = mBlogDocument1.select("span[class=author]").eq(i);
                    String mBlogSource = mElementBlogSource.text();

                    Elements mElementBlogUploadDate = mBlogDocument1.select("span[class=datetime]").eq(i);
                    String mBlogUploadDate = mElementBlogUploadDate.text();

                    Elements mElementBlogBody = mBlogDocument1.select("p[class=text-gray] + p").eq(i);
                    String mBlogBody = mElementBlogBody.text();

                    Elements mElementImageBody = mBlogDocument1.select("img[class=col-3 first]").eq(i);
                    String mBlogImage = mElementImageBody.attr("src");

                    mBlogSourceList.add(mBlogSource);
                    mBlogUploadDateList.add(mBlogUploadDate);
                    mBlogTitleList.add(mBlogTitle);

                    mBlogBodyList.add(mBlogBody);
                    mBlogImageList.add(mBlogImage);
//                    System.out.println("img: " + mBlogImage);
                }
                // Rappler
                Document mBlogDocument2 = Jsoup.connect(url2).get();
                mElementDataSize = mBlogDocument2.select("div[class=col-xs-12 col-sm-8]");

                mElementSize = mElementDataSize.size();

                for (int i = 0; i < mElementSize; i++) {
                    //title
                    Elements mElementBlogTitle = mBlogDocument2.select("h3[class=no-margin]").select("a").eq(i);
                    String mBlogTitle = mElementBlogTitle.text();
                    //author
//                    Elements mElementBlogSource = mBlogDocument2.select("span[class=author]").eq(i);
                    String mBlogSource = "RAPPLER";
                    //datetime
                    Elements mElementBlogUploadDate = mBlogDocument2.select("div[class=padding bottom]").select("span").eq(i);
                    String mBlogUploadDate = mElementBlogUploadDate.text();
                    //body
                    Elements mElementBlogBody = mBlogDocument2.select("div[class=padding bottom] + p").eq(i);
                    String mBlogBody = mElementBlogBody.text();
                    //image
                    Elements mElementImageBody = mBlogDocument2.select("img[class=rappler_asset]").eq(i);
                    String mBlogImage = mElementImageBody.attr("data-original");

                    Elements mElementLinkBody = mBlogDocument2.select("h3[class=no-margin] > a").eq(i);
                    String mBlogLink = mElementLinkBody.attr("href");

                    String link = "https://www.rappler.com";
//                    String dummyIMG = "https://pbs.twimg.com/media/D4pIj4SUwAAk7CN.jpg";


                    mBlogTitleList.add(mBlogTitle);
                    mBlogSourceList.add(mBlogSource);
                    mBlogUploadDateList.add(mBlogUploadDate);
                    mBlogBodyList.add(mBlogBody);
                    mBlogImageList.add(mBlogImage);
                    mBlogLinkList.add(link.concat(mBlogLink));

//                    System.out.println("LINKEU: " + mBlogLink);
                }
                // Inquirer
                Document mBlogDocument3 = Jsoup.connect(url3).get();
                mElementDataSize = mBlogDocument3.select("div[id=ch-cat]");

                mElementSize = mElementDataSize.size();

                for (int i = 0; i < mElementSize; i++) {
                    //title
                    Elements mElementBlogTitle = mBlogDocument3.select("div[id=ch-cat] + h2").select("a").eq(i);
                    String mBlogTitle = mElementBlogTitle.text();
                    //author
//                    Elements mElementBlogSource = mBlogDocument2.select("span[class=author]").eq(i);
                    String mBlogSource = "INQUIRER";
                    //datetime
                    Elements mElementBlogUploadDate = mBlogDocument3.select("div[id=ch-postdate] > span").eq(i);
                    String mBlogUploadDate = mElementBlogUploadDate.text();
                    //body
//                    Elements mElementBlogBody = mBlogDocument3.select("div[class=padding bottom] + p").eq(i);
                    String mBlogBody = "";
                    //image
                    Elements mElementImageBody = mBlogDocument3.select("div[id=ch-ls-img]").eq(i);
                    String mBlogImage = mElementImageBody.attr("style");
                    mBlogImage = mBlogImage.substring( mBlogImage.indexOf("https://"), mBlogImage.indexOf(")") );

                    Elements mElementLinkBody = mBlogDocument3.select("div[id=ch-ls-box] > a").eq(i);
                    String mBlogLink = mElementLinkBody.attr("href");

//                    String link = "https://www.rappler.com";
//                    String dummyIMG = "https://pbs.twimg.com/media/D4pIj4SUwAAk7CN.jpg";


                    mBlogTitleList.add(mBlogTitle);
                    mBlogSourceList.add(mBlogSource);
                    mBlogUploadDateList.add(mBlogUploadDate);
                    mBlogBodyList.add(mBlogBody);
                    mBlogImageList.add(mBlogImage);
                    mBlogLinkList.add(mBlogLink);

                    System.out.println("LINK: " + mBlogLink);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            NewsArticle newsarticle;
            System.out.println("size: " + mBlogTitleList.size());
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
//            mProgressDialog.dismiss();
            loader.setVisibility(View.GONE);

        }
    }
}