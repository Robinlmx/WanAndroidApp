package com.example.wanandroidapp.Tool;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 *    用来发送POST请求
 */

public class UserLoginPost extends Thread{
    String url;
    String username;
    String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        //发送post给服务器的参考方法
//1,创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //String url = "https://www.wanandroid.com/user/login";//写自己的服务器url
//2.设置请求体,将参数放入
        FormBody formBody = new FormBody.Builder()
                .add("username", this.username)
                .add("password", this.password)
                .build();
//3.将请求体封装进request,指定请求方式post或者get
        Request request = new Request.Builder()
                .url(this.url)
                .post(formBody)//参数放在body体里
                .build();
//4.调用call函数用OkHttpClient进行请求
        Call call = mOkHttpClient.newCall(request);

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
                //Log.d("Aaron  requestooo==",request.toString());
                System.out.println(resp);
            }
        });

        //System.out.println(result);
        try (Response response = mOkHttpClient.newCall(request).execute()) {
            response.body();
            Log.d("Aaron  response.body==",response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Log.d("Aaron  response==",response.toString());
    }
}
