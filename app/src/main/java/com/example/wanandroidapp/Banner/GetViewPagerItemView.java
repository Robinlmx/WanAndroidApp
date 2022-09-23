package com.example.wanandroidapp.Banner;

import android.view.View;
import android.view.ViewGroup;

public interface GetViewPagerItemView {
    /**
     * 获取每个ViewPager 的 item 要显示的视图
     * @param url
     * @param position
     * @return
     */
    View getViewPagerItemView(String url, int position);

    /**
     * 设置item的背景
     * @param parentView
     * @param url
     */
    void setItemBackground(ViewGroup parentView, String url);
}
