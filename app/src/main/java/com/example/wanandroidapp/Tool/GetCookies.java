package com.example.wanandroidapp.Tool;

import com.foxsteps.gsonformat.common.StringUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GetCookies {
//    //请求路径
//    String url = "";
//    OkHttpClient client = new OkHttpClient();
//    String anString = "";
//    String cookie = null;
//    //组装键值，params为键值，name为属性名
//    RequestBody formBody = new FormBody.Builder()
//            //需要的参数(key,value的格式可以一直add)
//            .add("username", "INIT")
//            .add("password", "123")
//            .add("offset", "0")
//            .add("limit", "10000")
//            .build();
//    //组装请求头
//    Request request = new Request.Builder()
//            .url(url)
//            .post(formBody)
//            //设置请求cookie(如果当前调用的是登录请求去获取cookie就不需要这个)
//            .addHeader("Cookie", cookie)
//            .build();
//
//    //该方法容易触发IOException异常
//        //获取返回值
//        Response response = null;
//        try {
//            response = client.newCall(request).execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //获取当前请求的cookie
//        if (response.isSuccessful()) {//response 请求成功
//            Headers headers = response.headers();
//            List<String> cookies = headers.values("Set-Cookie");
//            if (cookies.size() > 0) {
//                String session = cookies.get(0);
//                if (!StringUtils.isNullOrEmpty(session)) {
//                    int size = session.length();
//                    int i = session.indexOf(";");
//                    if (i < size && i >= 0) {
//                        //最终获取到的cookie
//                        cookie = session.substring(0, i);
//                    }
//                }
//            }
//        }
//        if(response.isSuccessful()) {
//            try {
//                anString += response.body().string();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }else {
//            anString+="error!";
//        }
//    }






}
