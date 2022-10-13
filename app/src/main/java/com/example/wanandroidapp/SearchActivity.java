package com.example.wanandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wanandroidapp.Adapter.ArticleAdapter;
import com.example.wanandroidapp.Adapter.SearchArticleAdapter;
import com.example.wanandroidapp.Thread.GetNetDataThread;
import com.example.wanandroidapp.Tool.SearchPost;
import com.example.wanandroidapp.bean.NavigationData;
import com.example.wanandroidapp.bean.Search;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_list);
        Bundle bundle = getIntent().getExtras();
        String searchText = bundle.getString("searchText");
        ArrayList<HashMap<String, Object>> listSearchItem = new ArrayList<>();
        //Log.d("Aaron","List==" + searchJsonData("link",searchText));
        ListView search_listView = findViewById(R.id.search_listView);
        ArrayList<String> chapterNameList = new ArrayList<String>();
        ArrayList<String> linkList = new ArrayList<String>();
        ArrayList<String> niceShareDateList = new ArrayList<String>();
        ArrayList<String> shareUserList = new ArrayList<String>();
        ArrayList<String> superChapterNameList = new ArrayList<String>();
        ArrayList<String> titleList = new ArrayList<String>();

//        chapterNameList = searchJsonData("chapterName",searchText);
//        linkList = searchJsonData("link",searchText);
//        niceShareDateList = searchJsonData("niceShareDate",searchText);
//        shareUserList = searchJsonData("shareUser",searchText);
//        superChapterNameList = searchJsonData("superChapterName",searchText);
//        titleList = searchJsonData("title",searchText);

        for (int i = 0; i < titleList.size(); i++) {
            HashMap<String, Object> map = new HashMap<>();
            //加入图片
            textView = (TextView) findViewById(R.id.tv_title);
            //map.put("ItemImage", R.drawable.me);
            //map.put("ItemText", "这是第" + i + "行");
            map.put("ItemText", titleList.get(i));
            listSearchItem.add(map);
            //Log.d("Aaron","listWenDaItem==" + listWenDaItem );
            //Log.d("Aaron", "i==" + i);
        }

//        SimpleAdapter search_adapter = new SimpleAdapter(this,
//                //绑定的数据
//                listSearchItem,
//                //每一行的布局
//                R.layout.item_search,
//                //动态数组中的数据源的键映射到布局文件对应的控件中
//                new String[]{"ItemText"},
//                new int[]{R.id.tv_title});
        SearchArticleAdapter searchArticleAdapter = new SearchArticleAdapter(SearchActivity.this,R.layout.item_search,searchJsonData(searchText));
        //Log.d("Aaron","titleList == " + titleList);
        search_listView.setAdapter(searchArticleAdapter);

        search_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(MainActivity.this, "你点击了第" + i + "行", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                Bundle bundle = new Bundle();
                //bundle.putInt("number", i);
                bundle.putString("url", searchJsonData(searchText).get(i).getLink());
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });


    }

    // 获取搜索的数据
    public List<Search.DataDTO.DatasDTO> searchJsonData(String searchText) {   //設置一个param用來返回不同的值
        Gson gson = new Gson();
        SearchPost searchPost = new SearchPost();
        ArrayList<String> chapterNameList = new ArrayList<String>();
        ArrayList<String> linkList = new ArrayList<String>();
        ArrayList<String> niceShareDateList = new ArrayList<String>();
        ArrayList<String> shareUserList = new ArrayList<String>();
        ArrayList<String> superChapterNameList = new ArrayList<String>();
        ArrayList<String> titleList = new ArrayList<String>();

        searchPost.setUrl("https://www.wanandroid.com/article/query/0/json");
        searchPost.setK(searchText);
        searchPost.start();
        while (searchPost.getSearchData() == null) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String searchJson = searchPost.getSearchData();

        Search search = gson.fromJson(searchJson, Search.class);

        List<Search.DataDTO.DatasDTO> datasDTOSList = search.getData().getDatas();
//        for (int j = 0; j < datasDTOSList.size(); j++) {
//            Search.DataDTO.DatasDTO datasDTO = datasDTOSList.get(j);
//            String chapterName = datasDTO.getChapterName();
//            String link = datasDTO.getLink();
//            String niceShareDate = datasDTO.getNiceShareDate();
//            String shareUser = datasDTO.getShareUser();
//            String superChapterName = datasDTO.getSuperChapterName();
//            String title = datasDTO.getTitle();
//
//            chapterNameList.add(chapterName);
//            linkList.add(link);
//            niceShareDateList.add(niceShareDate);
//            shareUserList.add(shareUser);
//            superChapterNameList.add(superChapterName);
//            titleList.add(title);
//            //Log.d("Aaron","Title==" + title );
//
//        }
        return datasDTOSList;
    }
//    public ArrayList<String> searchJsonData(String param,String searchText) {   //設置一个param用來返回不同的值
//        Gson gson = new Gson();
//        SearchPost searchPost = new SearchPost();
//        ArrayList<String> chapterNameList = new ArrayList<String>();
//        ArrayList<String> linkList = new ArrayList<String>();
//        ArrayList<String> niceShareDateList = new ArrayList<String>();
//        ArrayList<String> shareUserList = new ArrayList<String>();
//        ArrayList<String> superChapterNameList = new ArrayList<String>();
//        ArrayList<String> titleList = new ArrayList<String>();
//
//        searchPost.setUrl("https://www.wanandroid.com/article/query/0/json");
//        searchPost.setK(searchText);
//        searchPost.start();
//        while (searchPost.getSearchData() == null) {
//            try {
//                Thread.sleep(100);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        String searchJson = searchPost.getSearchData();
//
//        Search search = gson.fromJson(searchJson, Search.class);
//
//        List<Search.DataDTO.DatasDTO> datasDTOSList = search.getData().getDatas();
//        for (int j = 0; j < datasDTOSList.size(); j++) {
//            Search.DataDTO.DatasDTO datasDTO = datasDTOSList.get(j);
//            String chapterName = datasDTO.getChapterName();
//            String link = datasDTO.getLink();
//            String niceShareDate = datasDTO.getNiceShareDate();
//            String shareUser = datasDTO.getShareUser();
//            String superChapterName = datasDTO.getSuperChapterName();
//            String title = datasDTO.getTitle();
//
//            chapterNameList.add(chapterName);
//            linkList.add(link);
//            niceShareDateList.add(niceShareDate);
//            shareUserList.add(shareUser);
//            superChapterNameList.add(superChapterName);
//            titleList.add(title);
//            //Log.d("Aaron","Title==" + title );
//
//        }
//        switch (param) {
//            case "chapterName":
//                return chapterNameList;
//            case "link":
//                return linkList;
//            case "niceShareDate":
//                return niceShareDateList;
//            case "shareUser":
//                return shareUserList;
//            case "superChapterName":
//                return superChapterNameList;
//            case "title":
//                return titleList;
//        }
//
//        return chapterNameList;
//    }
}
