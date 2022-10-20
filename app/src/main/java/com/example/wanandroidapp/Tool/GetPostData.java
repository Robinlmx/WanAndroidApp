package com.example.wanandroidapp.Tool;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetPostData extends Thread{
    String content;
    String url;
    String username;
    String cookie;

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @Override
    public void run() {
        String jsonArticleStr;

        String jsonArticleStrUrl = this.url;
        String add_article_json = "";   // 拼接字符串


        jsonArticleStr = getNetDataByOkHttp(jsonArticleStrUrl);
        content = jsonArticleStr;

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
                .addHeader("cookie", cookie)
                .build();

//        Request request = new Request.Builder()
//                .url(address)
//                .addHeader("cookie",cookie)
//                .build();
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
}
