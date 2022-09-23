package com.example.wanandroidapp.Thread;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetNetDataThread extends Thread{
    String content;
    String url;

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
        jsonArticleStr = getNetDataByOkHttp(jsonArticleStrUrl);
        //jsonArticleStr = getNetDataByOkHttp(url);
        //Log.d("Aaron","(run)str1 == " + str1);
        content = jsonArticleStr;
        //Log.d("Aaron","(run)content == " + content);
    }
    // 使用OKHttp请求得到网站中的json数据，然后利用GSON来解析json数据，将网站的内容加载到WebView
    public String getNetDataByOkHttp(String address) {
        //String address = "https://www.wanandroid.com/article/list/0/json";

        //2022.9.10  14:58:09
        //String address = "https://www.wanandroid.com/article/list/1/json";

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



    public String doGet(String address) {
        String resultString = "";
        //String address = "https://www.wanandroid.com/article/list/0/json";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String content = response.body().string();
            Log.d("Aaron", "getNetDataByOkHttp: content=" + content);
            resultString = response.body().string();

            return content;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

    }



}
