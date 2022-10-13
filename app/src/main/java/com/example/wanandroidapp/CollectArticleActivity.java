package com.example.wanandroidapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wanandroidapp.Adapter.ArticleAdapter;
import com.example.wanandroidapp.Adapter.CollectArticleAdapter;
import com.example.wanandroidapp.Thread.GetNetDataThread;
import com.example.wanandroidapp.Tool.CollectArticlePost;
import com.example.wanandroidapp.Tool.DBHelper;
import com.example.wanandroidapp.Tool.GetPostData;
import com.example.wanandroidapp.Tool.UnCollectPost;
import com.example.wanandroidapp.bean.Article;
import com.example.wanandroidapp.bean.NavigationData;
import com.example.wanandroidapp.bean.UnCollectArticle;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CollectArticleActivity extends AppCompatActivity {

    private TextView textView;
    private ImageView imageView;
    ArrayList<HashMap<String, Object>> listItem = new ArrayList<>();
    //ListView listView = findViewById(R.id.collectArticle_listView);
    int articleIsClick = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collectarticle);

        initDatas();
    }

    private void initDatas() {
//        Log.d("Aaron","收藏网址数据是 articleList ==" + collectArticleJsonData("articleList") );
//        Log.d("Aaron","收藏网址数据是 titleList ==" + collectArticleJsonData("titleList") );
        ArrayList<String> articleLinkList = new ArrayList<String>();
        ArrayList<String> titleList = new ArrayList<String>();
        articleLinkList = collectArticleJsonData("articleList");
        titleList = collectArticleJsonData("titleList");
        ListView listView = findViewById(R.id.collectArticle_listView);


//        ArticleAdapter articleAdapter=new ArticleAdapter(CollectArticleActivity.this,R.layout.trueitem,titleList);
//        listView.setAdapter(articleAdapter);

        CollectArticleAdapter collectArticleAdapter = new CollectArticleAdapter(CollectArticleActivity.this,R.layout.trueitem,titleList);
        listView.setAdapter(collectArticleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ImageView imageView =view.findViewById(R.id.true_iv_collect);

                view.findViewById(R.id.true_iv_collect).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        ArrayList<String> idList = new ArrayList<String>();
                        idList = collectArticleJsonData("idList");
                        articleIsClick++;


                        if (articleIsClick % 2 == 0) {
                            //Log.d("Aaron",idList.get(i));
                            UnCollectPost unCollectPost = new UnCollectPost();
                            unCollectPost.setIdStr(idList.get(i));
                            unCollectPost.setOriginId("-1");

                            DBHelper dbHelper = new DBHelper(CollectArticleActivity.this, "cookies.db", null, 1);
                            SQLiteDatabase db = dbHelper.getWritableDatabase();

                            Cursor cursor = db.query("cookies", null, null, null, null, null, null);

                            // 将游标移到开头
                            cursor.moveToFirst();

                            while (!cursor.isAfterLast()) { // 游标只要不是在最后一行之后，就一直循环

                                int id=cursor.getInt(0);
                                String cookie=cursor.getString(1);
                                unCollectPost.setCookie(cookie);
                                cursor.moveToNext();

                            }
                            unCollectPost.start();
                            imageView.setImageResource(R.drawable.collect_false);
                            Toast.makeText(CollectArticleActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
                        } else {
                            imageView.setImageResource(R.drawable.collect_true);
                            Toast.makeText(CollectArticleActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                        }

                    }

                });
                if(articleIsClick % 2 == 0) {
                    //Toast.makeText(MainActivity.this, "你点击了第" + i + "行", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                    //Log.d("Aaron","getApplicationContext() == " + getApplicationContext());
                    //it.putExtra("number",i);
                    Bundle bundle = new Bundle();
                    //传递的数据自己定义，我这边传递的数据是id为tv_content的文本内容
                    //bundle.putInt("number", i);
                    bundle.putString("url",collectArticleJsonData("articleList").get(i));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }


        });

    }

    // 获取收藏网址数据
    private ArrayList<String> collectArticleJsonData(String param) {   //設置一个param用來返回不同的值
        Gson gson = new Gson();
        ArrayList<String> articleList = new ArrayList<String>();
        ArrayList<String> titleList = new ArrayList<String>();
        ArrayList<String> idList = new ArrayList<String>();
        GetPostData getPostData = new GetPostData();
        String collectArticle;
        DBHelper dbHelper = new DBHelper(this, "cookies.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("cookies", null, null, null, null, null, null);

        // 将游标移到开头
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) { // 游标只要不是在最后一行之后，就一直循环

            int id = cursor.getInt(0);
            //Log.d("Aaron","id==" + id);
            String cookie = cursor.getString(1);

            getPostData.setCookie(cookie);
            //Log.d("Aaron","cookie==" + cookie);
            // 将游标移到下一行
            cursor.moveToNext();

        }
        getPostData.setUrl("https://www.wanandroid.com/lg/collect/list/0/json");
        getPostData.start();

        while (getPostData.getContent() == null) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        collectArticle = getPostData.getContent();
        db.close();
        //Log.d("Aaron","收藏网址数据是==" + collectArticle);

        Article article = gson.fromJson(collectArticle, Article.class);
        List<Article.DataDTO.DatasDTO> datasDTOSList = article.getData().getDatas();
        for (int j = 0; j < datasDTOSList.size(); j++) {
            Article.DataDTO.DatasDTO datasDTO = datasDTOSList.get(j);
            String link = datasDTO.getLink();
            String title = datasDTO.getTitle();
            int id_number = datasDTO.getId();
            String id = String.valueOf(id_number);
            articleList.add(link);
            titleList.add(title);
            idList.add(id);
            //Log.d("Aaron","Title==" + title );

        }
        if (param == "articleList") {
            return articleList;
        } else if (param == "titleList") {
            return titleList;
        } else if (param == "idList") {
            return idList;
        } else
            return articleList;
    }

}
