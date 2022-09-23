package com.example.wanandroidapp;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.wanandroidapp.Banner.Banner;
import com.example.wanandroidapp.Banner.GetViewPagerItemView;

import java.util.ArrayList;
import java.util.List;


public class BannerActivity extends AppCompatActivity implements GetViewPagerItemView {

    private Banner mBanner;

    private final int[] mDrawableIds = {R.mipmap.icon_true, R.mipmap.icon_true, R.mipmap.icon_true, R.mipmap.icon_true, R.mipmap.icon_true};

    //    private final String[] mNetPic = {"http://hbimg.b0.upaiyun.com/a09289289df694cd6157f997ffa017cc44d4ca9e288fb-OehMYA_fw658",
//            "http://img3.imgtn.bdimg.com/it/u=2744354780,1420965949&fm=26&gp=0.jpg",
//            "http://img05.tooopen.com/images/20150816/tooopen_sy_138252113351.jpg",
//            "http://pic.qiantucdn.com/58pic/13/77/15/29n58PICV6A_1024.jpg",
//            "http://img.tupianzj.com/uploads/allimg/160719/9-160GZZ151.jpg",
//            "http://img01.taopic.com/161124/240369-16112414241492.jpg"};
    private final String[] mNetPic = {"https://www.wanandroid.com/blogimgs/42da12d8-de56-4439-b40c-eab66c227a4b.png",
            "https://www.wanandroid.com/blogimgs/62c1bd68-b5f3-4a3c-a649-7ca8c7dfabe6.png",
            "https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png",
            "http://hbimg.b0.upaiyun.com/a09289289df694cd6157f997ffa017cc44d4ca9e288fb-OehMYA_fw658",
            "http://hbimg.b0.upaiyun.com/a09289289df694cd6157f997ffa017cc44d4ca9e288fb-OehMYA_fw658",
            "http://hbimg.b0.upaiyun.com/a09289289df694cd6157f997ffa017cc44d4ca9e288fb-OehMYA_fw658"};
    private List<String> mItemViews = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        initViews();
        initDatas();
    }

    private void initViews() {
        mBanner = findViewById(R.id.banner);
    }

    private void initDatas() {
        for (int i = 0; i < mDrawableIds.length; i++) {
//            mItemViews.add(Utils.idToUri(this, mDrawableIds[i]).toString());
            mItemViews.add(mNetPic[i]);
        }
        mBanner.setDataSet((ArrayList<String>) mItemViews, this);
    }

    @Override
    public View getViewPagerItemView(String url, int position) {
        ImageView pageIV = new ImageView(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        pageIV.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(this).load(url).apply(new RequestOptions()).into(pageIV);
        return pageIV;
    }

    @Override
    public void setItemBackground(final ViewGroup parentView, String url) {
        Glide.with(this).load(url).into(new SimpleTarget<Drawable>() {

            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                parentView.setBackground(resource);
            }
        });
    }
}
