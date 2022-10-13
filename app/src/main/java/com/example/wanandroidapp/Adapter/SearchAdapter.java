package com.example.wanandroidapp.Adapter;

import android.content.Context;

import com.example.wanandroidapp.R;
import com.example.wanandroidapp.util.CommonAdapter;
import com.example.wanandroidapp.util.ViewHolder;
import com.example.wanandroidapp.model.Bean;

import java.util.List;

/**
 * Created by yetwish on 2015-05-11
 */

public class SearchAdapter extends CommonAdapter<Bean>{

    public SearchAdapter(Context context, List<Bean> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, int position) {
        holder.setText(R.id.item_search_tv_title,mData.get(position).getTitle())
                .setText(R.id.item_search_tv_content,mData.get(position).getContent())
                .setText(R.id.item_search_tv_comments,mData.get(position).getComments());
    }
}
