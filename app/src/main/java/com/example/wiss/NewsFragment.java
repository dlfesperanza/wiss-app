package com.example.wiss;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    private ArrayList<Date> mBlogUploadDateList = new ArrayList<>();
    private ArrayList<String> mBlogSourceList = new ArrayList<>();
    private ArrayList<String> mBlogBodyList = new ArrayList<>();
    private ArrayList<String> mBlogLinkList = new ArrayList<>();
    private ArrayList<String> mBlogImageList = new ArrayList<>();

    private ArrayList<String> mBlogTitleListSorted = new ArrayList<>();
    private ArrayList<String> mBlogUploadDateListSorted = new ArrayList<>();
    private ArrayList<String> mBlogSourceListSorted = new ArrayList<>();
    private ArrayList<String> mBlogBodyListSorted = new ArrayList<>();
    private ArrayList<String> mBlogLinkListSorted = new ArrayList<>();
    private ArrayList<String> mBlogImageListSorted = new ArrayList<>();
    public Context context;
    ProgressBar loader;
    DateFormat format;
    Date dateFormat;
    String sort_choice = "";
    private ActionBar toolbar;


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

        sort_choice = "";
        init();

        final Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("News");

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId()==R.id.item1)
                {
                    sort_choice = "ABS-CBN";
//                    init();
                    sortSource(sort_choice);
                    toolbar.setTitle("News");
                    toolbar.setSubtitle(sort_choice);
                }
                else if(item.getItemId()== R.id.item2)
                {
                    sort_choice = "RAPPLER";
//                    init();
                    sortSource(sort_choice);
                    toolbar.setTitle("News");
                    toolbar.setSubtitle(sort_choice);
                }
                else if(item.getItemId()== R.id.item3)
                {
                    sort_choice = "INQUIRER";
//                    init();
                    sortSource(sort_choice);
                    toolbar.setTitle("News");
                    toolbar.setSubtitle(sort_choice);
                }

                return false;
            }
        });

        setHasOptionsMenu(true);

        return view;
    }

    public void init(){
        if (WeatherFunction.isNetworkAvailable(getActivity().getApplicationContext())) {
            new Description().execute();
//            offline();
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    public void sortSource(String source){
        NewsArticle newsarticle;
        newsArticleList.clear();
        System.out.println("size: " + mBlogImageList.size());
        for (int i=0;i < mBlogImageList.size();i++){
          if (mBlogSourceList.get(i).equals(source)){
              newsarticle = new NewsArticle(mBlogImageList.get(i),mBlogTitleList.get(i),mBlogSourceList.get(i),mBlogUploadDateList.get(i),mBlogBodyList.get(i),mBlogLinkList.get(i),context);
              newsArticleList.add(newsarticle);
          }

        }

//            Collections.sort(newsArticleList);

        mAdapter.notifyDataSetChanged();
//            mProgressDialog.dismiss();
        loader.setVisibility(View.GONE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sort_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
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
            ArrayList<Date> dateList=new ArrayList<>();

            try {
                if (sort_choice.equals("ABS-CBN") || sort_choice.equals("")){
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
                    //ABS-CBN
                    for (int i = 0; i < mElementSize; i++) {

                        Elements mElementBlogTitle = mBlogDocument1.select("p[class=title]").select("a").eq(i);
                        String mBlogTitle = mElementBlogTitle.text();

//                    Elements mElementBlogSource = mBlogDocument1.select("span[class=author]").eq(i);
                        String mBlogSource = "ABS-CBN";

                        Elements mElementBlogUploadDate = mBlogDocument1.select("span[class=datetime]").eq(i);
                        String mBlogUploadDate = mElementBlogUploadDate.text();

                        mBlogUploadDate = mBlogUploadDate.substring(0,6);
                        mBlogUploadDate = mBlogUploadDate.concat(", 2019");
                        format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
                        dateFormat = null;
                        try {
                            dateFormat = format.parse(mBlogUploadDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dateList.add(dateFormat);

                        DateFormat dateFormatRev = new SimpleDateFormat("yyyy-MM-dd");
                        String strDate = dateFormatRev.format(dateFormat);


                        Elements mElementBlogBody = mBlogDocument1.select("p[class=text-gray] + p").eq(i);
                        String mBlogBody = mElementBlogBody.text();

                        Elements mElementImageBody = mBlogDocument1.select("img[class=col-3 first]").eq(i);
                        String mBlogImage = mElementImageBody.attr("src");

                        mBlogSourceList.add(mBlogSource);
                        mBlogUploadDateList.add(dateFormat);
                        mBlogTitleList.add(mBlogTitle);

                        mBlogBodyList.add(mBlogBody);
                        mBlogImageList.add(mBlogImage);
//                    System.out.println("img: " + mBlogImage);
                    }
                }
                if (sort_choice.equals("RAPPLER") || sort_choice.equals("")){
                    // Rappler
                    Document mBlogDocument2 = Jsoup.connect(url2).get();
                    Elements mElementDataSize = mBlogDocument2.select("div[class=col-xs-12 col-sm-8]");

                    int mElementSize = mElementDataSize.size()-40;

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

                        mBlogUploadDate = mBlogUploadDate.substring(0,12);
                        format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
                        dateFormat = null;
                        try {
                            dateFormat = format.parse(mBlogUploadDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dateList.add(dateFormat);
                        DateFormat dateFormatRev = new SimpleDateFormat("yyyy-MM-dd");
                        String strDate = dateFormatRev.format(dateFormat);

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
                        mBlogUploadDateList.add(dateFormat);
                        mBlogBodyList.add(mBlogBody);
                        mBlogImageList.add(mBlogImage);
                        mBlogLinkList.add(link.concat(mBlogLink));

//                    System.out.println("LINKEU: " + mBlogLink);
                    }
                }
                if (sort_choice.equals("INQUIRER") || sort_choice.equals("")){
                    // Inquirer
                    Document mBlogDocument3 = Jsoup.connect(url3).get();
                    Elements mElementDataSize = mBlogDocument3.select("div[id=ch-cat]");

                    int mElementSize = mElementDataSize.size();

                    for (int i = 0; i < mElementSize; i++) {
                        //title
                        Elements mElementBlogTitle = mBlogDocument3.select("div[id=ch-cat] + h2").select("a").eq(i);
                        String mBlogTitle = mElementBlogTitle.text();
                        //author
//                    Elements mElementBlogSource = mBlogDocument2.select("span[class=author]").eq(i);
                        String mBlogSource = "INQUIRER";
                        //datetime
                        Elements mElementBlogUploadDate = mBlogDocument3.select("div[id=ch-postdate] > span:first-child").eq(i);
                        String mBlogUploadDate = mElementBlogUploadDate.text();

                        format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
                        dateFormat = null;
                        try {
                            dateFormat = format.parse(mBlogUploadDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dateList.add(dateFormat);
                        DateFormat dateFormatRev = new SimpleDateFormat("yyyy-MM-dd");
                        String strDate = dateFormatRev.format(dateFormat);
                        //body
//                    Elements mElementBlogBody = mBlogDocument3.select("div[class=padding bottom] + p").eq(i);
                        String mBlogBody = "";
                        //image
                        Elements mElementImageBody = mBlogDocument3.select("div[id=ch-ls-img]").eq(i);
                        String mBlogImage = mElementImageBody.attr("style");
                        mBlogImage = mBlogImage.substring( mBlogImage.indexOf("https://"), mBlogImage.indexOf(")") );

                        Elements mElementLinkBody = mBlogDocument3.select("div[id=ch-ls-box] > a").eq(i);
                        String mBlogLink = mElementLinkBody.attr("href");

                        mBlogTitleList.add(mBlogTitle);
                        mBlogSourceList.add(mBlogSource);
                        mBlogUploadDateList.add(dateFormat);
                        mBlogBodyList.add(mBlogBody);
                        mBlogImageList.add(mBlogImage);
                        mBlogLinkList.add(mBlogLink);
                }


                }



            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            NewsArticle newsarticle;
            for (int i=0;i < mBlogImageList.size();i++){
//                newsarticle = new NewsArticle(mBlogImageListSorted.get(i),mBlogTitleListSorted.get(i),mBlogSourceListSorted.get(i),mBlogUploadDateListSorted.get(i),mBlogBodyListSorted.get(i),mBlogLinkListSorted.get(i),context);
                newsarticle = new NewsArticle(mBlogImageList.get(i),mBlogTitleList.get(i),mBlogSourceList.get(i),mBlogUploadDateList.get(i),mBlogBodyList.get(i),mBlogLinkList.get(i),context);

                newsArticleList.add(newsarticle);
            }

//            Collections.sort(newsArticleList);

            mAdapter.notifyDataSetChanged();
//            mProgressDialog.dismiss();
            loader.setVisibility(View.GONE);

        }
    }


}