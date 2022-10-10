package com.example.wanandroidapp.Tool;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserRegisteredPost extends Thread{
    String url;
    String username;
    String password;
    String repassword;
    String user_registered_json;

    public String getUser_registered_json() {
        return user_registered_json;
    }

    public void setUser_registered_json(String user_registered_json) {
        this.user_registered_json = user_registered_json;
    }

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

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        user_registered_json = doPost();
    }

    private String doPost() {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Log.d("Aaron","username" + username);
        Log.d("Aaron","password" + password);
        Log.d("Aaron","repassword" + repassword);
        FormBody formBody = new FormBody.Builder()
                .add("username", this.username)
                .add("password", this.password)
                .add("repassword", this.repassword)
                .build();
        Request request = new Request.Builder()
                .url(this.url)
                .post(formBody)//参数放在body体里
                .build();
        Call call = mOkHttpClient.newCall(request);


        call.enqueue(new Callback() {
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

        try (Response response = mOkHttpClient.newCall(request).execute()) {
            response.body();
            //Log.d("Aaron  response.body==",response.body().string());
            //this.userlogin_json = response.body().string();
            return response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}

