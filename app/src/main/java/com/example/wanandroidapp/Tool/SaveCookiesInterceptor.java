package com.example.wanandroidapp.Tool;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
*   首先定义响应拦截器，该拦截器实现从response获取set-cookie字段的值，并将其保存在本地。
**/
public class SaveCookiesInterceptor implements Interceptor {

    private static final String COOKIE_PREF = "cookies_prefs";

    private Context mContext;

    protected SaveCookiesInterceptor(Context context) {

        mContext = context;

    }

    @Override

    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        Response response = chain.proceed(request);

//set-cookie可能为多个

        if (!response.headers("set-cookie").isEmpty()) {

            List cookies = response.headers("set-cookie");

            String cookie = encodeCookie(cookies);

            saveCookie(request.url().toString(), request.url().host(), cookie);

        }

        return response;

    }

    /**

     * 整合cookie为唯一字符串

     */

    private String encodeCookie(List<String> cookies) {

        StringBuilder sb = new StringBuilder();

        Set set = new HashSet<>();

        for (String cookie : cookies) {

            String[] arr = cookie.split(";");

            for (String s : arr) {

                if (set.contains(s)) {

                    continue;

                }

                set.add(s);

            }

        }

        String cookie;
        List<String> list = new ArrayList(set);
        for(int i = 0 ; i < set.size() ; i++){
            cookie = list.get(i);
            sb.append(cookie).append(";");
        }
//        for (String cookie : set) {
//
//            sb.append(cookie).append(";");
//
//        }

        int last = sb.lastIndexOf(";");

        if (sb.length() - 1 == last) {

            sb.deleteCharAt(last);

        }

        return sb.toString();

    }

    private void saveCookie(String url, String domain, String cookies) {

        SharedPreferences sp = mContext.getSharedPreferences(COOKIE_PREF, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();

        if (TextUtils.isEmpty(url)) {

            throw new NullPointerException("url is null.");

        } else {

            editor.putString(url, cookies);

        }

        if (!TextUtils.isEmpty(domain)) {

            editor.putString(domain, cookies);

        }

        editor.apply();

    }

    /**

     * 清除本地Cookie

     *

     * @param context Context

     */

    public static void clearCookie(Context context) {

        SharedPreferences sp = context.getSharedPreferences(COOKIE_PREF, Context.MODE_PRIVATE);

        sp.edit().clear().apply();

    }

}