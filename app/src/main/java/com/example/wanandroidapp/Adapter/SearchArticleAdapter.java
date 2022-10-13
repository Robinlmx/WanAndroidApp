package com.example.wanandroidapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wanandroidapp.R;
import com.example.wanandroidapp.bean.Search;

import java.util.ArrayList;
import java.util.List;

public class SearchArticleAdapter extends ArrayAdapter<Search.DataDTO.DatasDTO> {

public SearchArticleAdapter(@NonNull Context context, int resource, @NonNull List<Search.DataDTO.DatasDTO> searchList) {
    super(context, resource,searchList);
}
    //每个子项被滚动到屏幕内的时候会被调用
    //private Callback mCallback;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Search.DataDTO.DatasDTO datasDTO = getItem(position);

        //mCallback = callback;
        //为每一个子项加载设定的布局
        View view= LayoutInflater.from(getContext()).inflate(R.layout.item_search,parent,false);

        TextView titieView =view.findViewById(R.id.tv_title);
        TextView shareUserView =view.findViewById(R.id.tv_author);
        TextView superChapterNameView =view.findViewById(R.id.tv_chapter_name);
        TextView niceShareDateView =view.findViewById(R.id.tv_time);


        titieView.setText(datasDTO.getTitle());
        shareUserView.setText(datasDTO.getShareUser());
        superChapterNameView.setText(datasDTO.getSuperChapterName());
        niceShareDateView.setText(datasDTO.getNiceShareDate());

        //articleName.setText("title");
        return view;
    }
}
