package com.example.wanandroidapp.Thread;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetNetDataThread extends Thread{
    String content;
    String url;
    int count;
    ArrayList<String> articleJsonList = new ArrayList<String>();

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public ArrayList<String> getArticleJsonList() {
        return articleJsonList;
    }

    public void setArticleJsonList(ArrayList<String> articleJsonList) {
        this.articleJsonList = articleJsonList;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    @Override
    public void run() {
        //String str1;
        String jsonArticleStr;


//        String jsonArticleStrUrl = "https://www.wanandroid.com/article/list/1/json";
        String jsonArticleStrUrl = this.url;
        String add_article_json = "";   // 拼接字符串
//        jsonArticleStr = getNetDataByOkHttp(jsonArticleStrUrl);
//        content = jsonArticleStr;
        this.count = 0;

        if(this.url == "https://www.wanandroid.com/article/list/"){
            for(int i = 0 ; i < 3 ; i++ ) {
                //jsonArticleStr = jsonArticleStr + getNetDataByOkHttp(jsonArticleStrUrl + i + "/json");
                //add_article_json = add_article_json + getNetDataByOkHttp(jsonArticleStrUrl + i + "/json");
                this.count = i;
                add_article_json = getNetDataByOkHttp(jsonArticleStrUrl + i + "/json");
                this.articleJsonList.add(add_article_json);

            }
        }
        else{
            jsonArticleStr = getNetDataByOkHttp(jsonArticleStrUrl);
            content = jsonArticleStr;
        }
        //jsonArticleStr = getNetDataByOkHttp(url);
        //Log.d("Aaron","(run)str1 == " + str1);

        //Log.d("Aaron","(run)content == " + content);
    }
    // 使用OKHttp请求得到网站中的json数据，然后利用GSON来解析json数据，将网站的内容加载到WebView
    public String getNetDataByOkHttp(String address) {
        //String address = "https://www.wanandroid.com/article/list/0/json";

        String str2 = "";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(address)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String content = response.body().string();
            str2 = content;
            //setJsonStr(content);
            //MainActivity.jsonStr = content;
            //Log.d("Aaron", "(try)getNetDataByOkHttp: content=" + content);
            //Log.d("Aaron", "(try)jsonstr=" + jsonStr);
            //Log.d("Aaron", "(try)str2=" + str2);
            return str2;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        //Log.d("Aaron","str2==" + str2);
    }



//    public String doGet(String address) {
//        String resultString = "";
//        //String address = "https://www.wanandroid.com/article/list/0/json";
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(address)
//                .build();
//        try {
//            Response response = client.newCall(request).execute();
//            String content = response.body().string();
//            Log.d("Aaron", "getNetDataByOkHttp: content=" + content);
//            resultString = response.body().string();
//
//            return content;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "";
//        }
//
//    }



}
