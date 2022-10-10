package com.example.wanandroidapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.wanandroidapp.R;

import java.util.ArrayList;

import okhttp3.Callback;

public class ArticleAdapter extends ArrayAdapter<String>{
    public ArticleAdapter(@NonNull Context context, int resource,@NonNull ArrayList<String> titleList) {
        super(context, resource,titleList);
    }
    //每个子项被滚动到屏幕内的时候会被调用
    //private Callback mCallback;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //ArrayList<String> titleList = getItem(position);
        String title = getItem(position);
        //mCallback = callback;
        //为每一个子项加载设定的布局
        View view= LayoutInflater.from(getContext()).inflate(R.layout.item,parent,false);
        //分别获取 image view 和 textview 的实例
        ImageView articleImage =view.findViewById(R.id.iv_collect);
        TextView articleName =view.findViewById(R.id.textView);
        // 设置要显示的图片和文字
        articleImage.setTag(position);
        articleImage.setImageResource(R.drawable.collect_false);
        articleName.setText(title);

        //articleName.setText("title");
        return view;
    }

    //响应红心点击事件,调用子定义接口，并传入View

    public interface Callback {

        public void click(View v);

    }


}
