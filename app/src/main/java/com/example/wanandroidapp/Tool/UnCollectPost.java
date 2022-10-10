package com.example.wanandroidapp.Tool;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UnCollectPost extends Thread{
    String url;
    String idStr;
    String originId;
    String cookie;

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    @Override
    public void run() {
        //userlogin_json = doPost();
        //datalist = doPost();
        this.url = "https://www.wanandroid.com/lg/uncollect/" + this.idStr + "/json";
        //Log.d("Aaron","url == " + this.url);
        getNetDataByOkHttp(this.url);
    }

    public void getNetDataByOkHttp(String address) {

        OkHttpClient client = new OkHttpClient();

        FormBody formBody = new FormBody.Builder()
                .add("originId", this.originId)
                .build();

        Request request = new Request.Builder()
                .url(address)
                .post(formBody)
                .addHeader("cookie",cookie)
                .build();

        Call call = client.newCall(request);

        //异步处理请求
        call.enqueue(new Callback()
        {
            //失败的实例方法
            @Override
            public void onFailure(Call call, IOException e) {

            }
            //成功调用的处理方法
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resp = response.body().string();
                //Log.d("Aaron",  "requestooo==" + request.toString());
            }
        });


    }
}
