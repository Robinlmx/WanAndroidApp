package com.example.wanandroidapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.wanandroidapp.Banner.Banner;
import com.example.wanandroidapp.Banner.GetViewPagerItemView;
import com.example.wanandroidapp.Banner.GsonBannerData;
import com.example.wanandroidapp.Thread.GetNetDataThread;
import com.example.wanandroidapp.Adapter.ArticleAdapter;
import com.example.wanandroidapp.Tool.CircleImageView;
import com.example.wanandroidapp.Tool.CollectArticlePost;
import com.example.wanandroidapp.Tool.DBHelper;
import com.example.wanandroidapp.Tool.GetPostData;
import com.example.wanandroidapp.Tool.UserLoginPost;
import com.example.wanandroidapp.bean.Article;

import com.example.wanandroidapp.bean.Fruit;
import com.example.wanandroidapp.bean.NavigationData;
import com.example.wanandroidapp.bean.UserInfo;
import com.example.wanandroidapp.bean.WenDa;
import com.example.wanandroidapp.login.LoginActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class MainActivity extends AppCompatActivity implements GetViewPagerItemView{
    private View view;
    private ViewPager mViewPager;
    private ViewPager mViewBannerPager;
    private RadioGroup mRadioGroup;
    private RadioButton tab1, tab2, tab3, tab4;
    private List<View> mViews;   //存放视图
    private TextView textView,  exitLoginView;
    //private TextView userInfoLevel_tv ,  userInfoRank_tv;
    private ImageView article_iv,imageView;
    //private BannerActivity bannerActivity;
    private Banner mBanner;
    private final int[] mDrawableIds = {R.mipmap.icon_true, R.mipmap.icon_true, R.mipmap.icon_true, R.mipmap.icon_true, R.mipmap.icon_true};
    private List<String> mItemViews = new ArrayList();
    ArrayList<String> navigationNameList = new ArrayList<String>();

    public ArrayList<String> getNavigationNameList() {
        return navigationNameList;
    }

    private List<Fruit> fruitList = new ArrayList<>();

    ArrayList<Button> buttonList = new ArrayList<Button>();
    Button button742;
    int articleIsClick = 1;
    int isLogin1 = 0;
    int isLogin2 = 0;
    //String cookiestr;
    public void setNavigationNameList(ArrayList<String> navigationNameList) {
        this.navigationNameList = navigationNameList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        exitLoginView = findViewById(R.id.tv_login_out);
//        exitLoginView.setVisibility(View.GONE);
        initView();//初始化数据

        //初始化轮播图数据
        initBannerViews();
        initBannerDatas();


//        if(isLogin1 == 1 || isLogin2 == 1) {
//            exitLoginView.setVisibility(View.VISIBLE);
//        }else{
//            exitLoginView.setVisibility(View.GONE);
//        }


//        try {
//            Bundle bundle = getIntent().getExtras();
//            String cookiestr = bundle.getString("cookiestr");
//            Log.d("Aaron","传入的cookiestr == " + cookiestr);
//        }catch (Exception e){
//            e.printStackTrace();
//        }

//        Intent intent=getIntent();
//        Bundle bundle=intent.getBundleExtra("bundle");
//        String cookiestr = bundle.getString("cookiestr");
//        Log.d("Aaron","传入的cookiestr == " + cookiestr);

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_home:
                        mViewPager.setCurrentItem(0);
                        mBanner.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rb_contact:
                        mViewPager.setCurrentItem(1);
                        mBanner.setVisibility(View.GONE);
                        break;
                    case R.id.rb_popular_sites:
                        mViewPager.setCurrentItem(2);
                        mBanner.setVisibility(View.GONE);
                        break;
                    case R.id.rb_me:
                        mViewPager.setCurrentItem(3);
                        mBanner.setVisibility(View.GONE);
                        break;

                }
            }
        });


    }

    //    private void init() {
//        for (int i = 0; i < 50; i++) {
//            Fruit fruit = new Fruit(R.drawable.home,"Name"+i);
//            fruitList.add(fruit);
//        }
//    }
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.user_info:
                //Log.d("Aaron","点击个人数据");
                Intent it = new Intent(MainActivity.this, UserInfoActivity.class);
                startActivity(it);
                break;
            case R.id.civ_user_icon:
                //Log.d("Aaron","点击头像");
                it = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(it);
                //Log.d("Aaron", "个人信息 == " + userInfoJsonData());
                //finish();
                break;
            case R.id.ll_collect:
                //Log.d("Aaron","点击我的收藏");
                it = new Intent(MainActivity.this, CollectArticleActivity.class);
                startActivity(it);
                break;
            case R.id.tv_login_out:
//                DBHelper dbHelper = new DBHelper(this, "cookies.db", null, 1);
//                SQLiteDatabase db = dbHelper.getWritableDatabase();
//                db.delete("cookies","u_id = 1",null);
                Toast.makeText(MainActivity.this, "退出登录", Toast.LENGTH_SHORT).show();
                break;
        }

    }


    private void initBannerViews() {
        mBanner = findViewById(R.id.banner);

    }

    private void initBannerDatas() {
        GsonBannerData gsonBannerData = new GsonBannerData();
        gsonBannerData.parseBannerData();
        ArrayList<String> urlList = gsonBannerData.parseBannerData();


        //View viewHome = LayoutInflater.from(this).inflate(R.layout.home, null);
        View viewBanner = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        //ListView listView = viewBanner.findViewById(R.id.banner);
        Banner listView = viewBanner.findViewById(R.id.banner);
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<>();


        //Log.d("Aaron","mNetPic[0] ==" + urlList.get(0));

        mBanner.setDataSet((ArrayList<String>) urlList, this);
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


    private void initView() {
        //初始化控件
        mViewPager = findViewById(R.id.viewpager);
        mRadioGroup = findViewById(R.id.rg_tab);
        tab1 = findViewById(R.id.rb_home);
        tab2 = findViewById(R.id.rb_contact);
        tab3 = findViewById(R.id.rb_popular_sites);
        tab4 = findViewById(R.id.rb_me);
        view=View.inflate(getApplicationContext(),R.layout.me,null);
        //exitLoginView = findViewById(R.id.tv_login_out);
//        exitLoginView = findViewById(R.id.tv_login_out);
//        exitLoginView.setBackgroundColor(Color.WHITE);


        //exitLoginView.setVisibility(View.VISIBLE);

        mViews = new ArrayList<View>();//加载，添加视图

        article_iv = findViewById(R.id.iv_collect);

        View viewHome = LayoutInflater.from(this).inflate(R.layout.home, null);
        //View viewHome = LayoutInflater.from(this).inflate(R.layout.test_viewhome,null);
        View navigation_viewHome = LayoutInflater.from(this).inflate(R.layout.popular_sites, null);  //导航的视图
        View wenda_viewHome = LayoutInflater.from(this).inflate(R.layout.contact, null);
        View me_viewHome = LayoutInflater.from(this).inflate(R.layout.me, null);
        CircleImageView circleImageView = new CircleImageView(this);

        ListView listView = viewHome.findViewById(R.id.listView);
        ListView navigation_listView = navigation_viewHome.findViewById(R.id.navigation_listView);
        ListView wenda_listView = wenda_viewHome.findViewById(R.id.wenda_listView);
        //ListView navigation_listView1 = navigation_viewHome.findViewById(R.id.navigation_listView1);
        //ListView navigation_listView = navigation_viewHome.findViewById(R.id.listView);

        ArrayList<HashMap<String, Object>> listItem = new ArrayList<>();
        ArrayList<HashMap<String, Object>> listNavigationItem = new ArrayList<>();
        ArrayList<HashMap<String, Object>> listWenDaItem = new ArrayList<>();
        ArrayList<String> articleLinkList = new ArrayList<String>();
        ArrayList<String> titleList = new ArrayList<String>();
        ArrayList<String> wenDaLinkList = new ArrayList<String>();
        ArrayList<String> wenDaTitleList = new ArrayList<String>();
        articleLinkList = parseJsonData("articleList");
        titleList = parseJsonData("titleList");

        //Log.d("Aaron","问答数据==" + wendaJsonData("wenDaLink"));
        wenDaLinkList = wendaJsonData("wenDaLink");
        wenDaTitleList = wendaJsonData("wenDaTitle");
        //Log.d("Aaron","返回的数据是==" + navigationJsonData("navigationName"));
//        if(isLogin1 == 1 || isLogin2 == 1){
//            exitLoginView.setVisibility(View.VISIBLE);
//        }


        userInfoJsonData(me_viewHome);
//        DBHelper dbHelper = new DBHelper(this, "cookies.db", null, 1);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues cValue = new ContentValues();
//
//        cValue.put("u_id",2);
//        cValue.put("cookie","1");      // 1代表登录状态
//        db.insert("cookies",null,cValue);
//        db.close();

//        Cursor cursor = db.query("cookies", null, null, null, null, null, null);
//
//        // 将游标移到开头
//        cursor.moveToFirst();
//
//        while (!cursor.isAfterLast()) { // 游标只要不是在最后一行之后，就一直循环
//
//            int id=cursor.getInt(0);
//            Log.d("Aaron","id==" + id);
//            String cookie=cursor.getString(1);
//            Log.d("Aaron","cookie==" + cookie);
//            // 将游标移到下一行
//            cursor.moveToNext();
//
//        }
//
//        db.close();


//        for (int i = 0; i < articleLinkList.size(); i++) {
//            HashMap<String, Object> map = new HashMap<>();
//            //加入图片
//            textView = (TextView) findViewById(R.id.textView);
//            imageView = (ImageView) findViewById(R.id.iv_collect);
//            //map.put("ItemImage", R.drawable.me);
//            //map.put("ItemText", "这是第" + i + "行");
//            map.put("ItemText", titleList.get(i));
//            listItem.add(map);
//            //Log.d("Aaron", "i==" + i);
//        }

        //navigationNameList = navigationJsonData("navigationName");
        navigationNameList = navigationJsonData("navigationName");
        //Log.d("Aaron", "navigationNameList  主函数的数据是==" + navigationNameList);
        //circleImageView.setNavigationNameList(navigationNameList);
        for (int i = 0; i < navigationNameList.size(); i++) {
            HashMap<String, Object> map = new HashMap<>();
            textView = (TextView) findViewById(R.id.textView);
            map.put("ItemText", navigationNameList.get(i));
            listNavigationItem.add(map);

        }

        for (int i = 0; i < wenDaTitleList.size(); i++) {
            HashMap<String, Object> map = new HashMap<>();
            //加入图片
            textView = (TextView) findViewById(R.id.textView);
            //map.put("ItemImage", R.drawable.me);
            //map.put("ItemText", "这是第" + i + "行");
            map.put("ItemText", wenDaTitleList.get(i));
            listWenDaItem.add(map);
            //Log.d("Aaron","listWenDaItem==" + listWenDaItem );
            //Log.d("Aaron", "i==" + i);
        }


//        SimpleAdapter adapter = new SimpleAdapter(this,
//                //绑定的数据
//                listItem,
//                //每一行的布局
//                R.layout.item,
//                //动态数组中的数据源的键映射到布局文件对应的控件中
////                new String[]{"ItemImage", "ItemText"},
////                new int[]{R.id.imageView, R.id.textView});
//                new String[]{"ItemText"},
//                new int[]{R.id.textView}
//        );



        // 给导航栏数据添加一个适配器
        SimpleAdapter navigation_adapter = new SimpleAdapter(this,
                //绑定的数据
                listNavigationItem,
                //每一行的布局
                R.layout.frequentwebsite_item,
                //动态数组中的数据源的键映射到布局文件对应的控件中
                new String[]{"ItemText"},
                new int[]{R.id.frequentwebsite_textView});

        // 问答的适配器
        SimpleAdapter wenDa_adapter = new SimpleAdapter(this,
                //绑定的数据
                listWenDaItem,
                //每一行的布局
                R.layout.item,
                //动态数组中的数据源的键映射到布局文件对应的控件中
                new String[]{"ItemText"},
                new int[]{R.id.textView});

//        SimpleAdapter navigation_adapter1 = new SimpleAdapter(this,
//                //绑定的数据
//                listNavigationItem,
//                //每一行的布局
//                R.layout.navigation_item,
//                //动态数组中的数据源的键映射到布局文件对应的控件中
//                new String[]{"ItemText"},
//                new int[]{R.id.navigation_bt});



        ArticleAdapter articleAdapter=new ArticleAdapter(MainActivity.this,R.layout.item,titleList);
        //Log.d("Aaron","titleList == " + titleList);
        listView.setAdapter(articleAdapter);
        //listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                ImageView msg=(ImageView)view.findViewById(R.id.iv_collect);
//                msg.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        int imagePosition = (Integer) v.getTag();
//                        Toast.makeText(MainActivity.this, "你点击了红心的第" + imagePosition + "行", Toast.LENGTH_SHORT).show();
//                    }
//
//                });
                ImageView imageView =view.findViewById(R.id.iv_collect);
                view.findViewById(R.id.iv_collect).setOnClickListener(new View.OnClickListener() {

                     @Override
                     public void onClick(View v) {
                     //Toast.makeText(MainActivity.this, "你点击了红心的第" + i + "行", Toast.LENGTH_SHORT).show();titleList.get(i)
                         //Log.d("Aaron","点击了红心的第" + i + "行==" + titleList.get(i));
                         ArrayList<String> titleList = new ArrayList<String>();
                         ArrayList<String> articleLinkList = new ArrayList<String>();
                         titleList = parseJsonData("titleList");
                         articleLinkList = parseJsonData("articleList");
//                         Log.d("Aaron","点击了红心的第" + i + "行==" + titleList.get(i));
//                         Log.d("Aaron","articleLinkList==" + articleLinkList.get(i));
                        //adapter.notifyDataSetChanged();//更新listview
                         articleIsClick++;
                         //Log.d("Aaron","articleIsClick==" + articleIsClick);
//                         if(isLogin1 == 0 && isLogin2 == 0) {
//                             Toast.makeText(MainActivity.this, "请先登录!", Toast.LENGTH_SHORT).show();
//                         }else {
//                             if (articleIsClick % 2 == 0) {
//                                 imageView.setImageResource(R.drawable.collect_true);
//                                 Toast.makeText(MainActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
//                             } else {
//                                 imageView.setImageResource(R.drawable.collect_false);
//                                 Toast.makeText(MainActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
//                             }
//                         }
                         CollectArticlePost collectArticlePost = new CollectArticlePost();
                         collectArticlePost.setUrl("https://www.wanandroid.com/lg/collect/add/json");
                         collectArticlePost.setTitle(titleList.get(i));
                         collectArticlePost.setLink(articleLinkList.get(i));
                         collectArticlePost.setAuthor("");
                         //collectArticlePost.setCookie();
                         DBHelper dbHelper = new DBHelper(MainActivity.this, "cookies.db", null, 1);
                         SQLiteDatabase db = dbHelper.getWritableDatabase();

                         Cursor cursor = db.query("cookies", null, null, null, null, null, null);

                         // 将游标移到开头
                         cursor.moveToFirst();

                         while (!cursor.isAfterLast()) { // 游标只要不是在最后一行之后，就一直循环

                             int id=cursor.getInt(0);
                             //Log.d("Aaron","id==" + id);
                             String cookie=cursor.getString(1);
                             collectArticlePost.setCookie(cookie);
                             //Log.d("Aaron","cookie==" + cookie);
                             //Log.d("Aaron","cookie==" + cookie);
                             // 将游标移到下一行
                             cursor.moveToNext();

                         }
                         collectArticlePost.start();

                         if (articleIsClick % 2 == 0) {
                             imageView.setImageResource(R.drawable.collect_true);
                             //Log.d("Aaron","i==" + i + "title======" + titleList.get(i));
                             Toast.makeText(MainActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                         } else {
                             imageView.setImageResource(R.drawable.collect_false);
                             Toast.makeText(MainActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
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
                    bundle.putInt("number", i);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }


        });



        navigation_listView.setAdapter(navigation_adapter);
        navigation_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(MainActivity.this, "你点击了第" + i + "行", Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.this, (CharSequence) listNavigationItem.get(i), Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, navigationNameList.get(i), Toast.LENGTH_SHORT).show();
            }
        });

        wenda_listView.setAdapter(wenDa_adapter);
        wenda_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(MainActivity.this, "你点击了第" + i + "行", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("number", i);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });



//        navigation_listView1.setAdapter(navigation_adapter1);
//        navigation_listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(MainActivity.this, "你点击了第" + i + "行", Toast.LENGTH_SHORT).show();
//            }
//        });


        mViews.add(viewHome);

        //mViews.add(LayoutInflater.from(this).inflate(R.layout.contact, null));
        mViews.add(wenda_viewHome);
        mViews.add(navigation_viewHome);
        mViews.add(me_viewHome);
        //mViews.add(LayoutInflater.from(this).inflate(R.layout.popular_sites, null));
        //mViews.add(LayoutInflater.from(this).inflate(R.layout.me, null));


        mViewPager.setAdapter(new MyViewPagerAdapter());//设置一个适配器
        //对viewpager监听，让分页和底部图标保持一起滑动
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override   //让viewpager滑动的时候，下面的图标跟着变动
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        tab1.setChecked(true);
                        tab2.setChecked(false);
                        tab3.setChecked(false);
                        tab4.setChecked(false);
                        break;
                    case 1:
                        tab1.setChecked(false);
                        tab2.setChecked(true);
                        tab3.setChecked(false);
                        tab4.setChecked(false);
                        break;
                    case 2:
                        tab1.setChecked(false);
                        tab2.setChecked(false);
                        tab3.setChecked(true);
                        tab4.setChecked(false);
                        break;
                    case 3:
                        tab1.setChecked(false);
                        tab2.setChecked(false);
                        tab3.setChecked(false);
                        tab4.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(0);
    }




    //ViewPager适配器
    private class MyViewPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mViews.size();
        }


        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(mViews.get(position));
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(mViews.get(position));
            return mViews.get(position);
        }
    }

    public ArrayList<String> parseJsonData(String param) {     //設置一个param用來返回不同的值
        //文章Json数据
        Gson gson = new Gson();

        String str2 = "";
        GetNetDataThread getNetDataThread = new GetNetDataThread();
        ArrayList<String> articleList = new ArrayList<String>();
        ArrayList<String> titleList = new ArrayList<String>();
        ArrayList<String> articleJsonList = new ArrayList<String>();

        getNetDataThread.setUrl("https://www.wanandroid.com/article/list/");
        //getNetDataThread.setUrl("https://www.wanandroid.com/article/list/0/json" );
        getNetDataThread.start();
////            设置此函数用来确保多线程里的Content收到参数为止
////            Log.d("Aaron","i == " + i);
//        while (getNetDataThread.getContent() == null) {
//            try {
//                Thread.sleep(100);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//            String str1 = getNetDataThread.getContent();
//            Article article = gson.fromJson(str1, Article.class);
//            List<Article.DataDTO.DatasDTO> datasDTOSList = article.getData().getDatas();
//            for (int j = 0; j < datasDTOSList.size(); j++) {
//                Article.DataDTO.DatasDTO datasDTO = datasDTOSList.get(j);
//                String link = datasDTO.getLink();
//                String title = datasDTO.getTitle();
//                articleList.add(link);
//                titleList.add(title);
//                //Log.d("Aaron","Title==" + title );
//
//            }

        while (getNetDataThread.getContent() == null && getNetDataThread.getCount() < 2) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < 2; i++) {
            String str1 = getNetDataThread.getArticleJsonList().get(i);
            Article article = gson.fromJson(str1, Article.class);
            List<Article.DataDTO.DatasDTO> datasDTOSList = article.getData().getDatas();
            for (int j = 0; j < datasDTOSList.size(); j++) {
                Article.DataDTO.DatasDTO datasDTO = datasDTOSList.get(j);
                String link = datasDTO.getLink();
                String title = datasDTO.getTitle();
                articleList.add(link);
                titleList.add(title);
                //Log.d("Aaron","Title==" + title );

            }
        }
        if (param == "articleList") {
            return articleList;
        } else if (param == "titleList") {
            return titleList;
        } else
            return articleList;

    }

    // 获取导航栏数据
    public ArrayList<String> navigationJsonData(String param) {   //設置一个param用來返回不同的值
        Gson gson = new Gson();
        GetNetDataThread getNetDataThread = new GetNetDataThread();
        ArrayList<String> navigationNameList = new ArrayList<String>();
        getNetDataThread.setUrl("https://www.wanandroid.com/navi/json");
        getNetDataThread.start();
        while (getNetDataThread.getContent() == null) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String navigationJson = getNetDataThread.getContent();

        NavigationData navigationData = gson.fromJson(navigationJson, NavigationData.class);
        List<NavigationData.DataDTO> datasDTOSList = navigationData.getData();
        for (int j = 0; j < datasDTOSList.size(); j++) {
            NavigationData.DataDTO datasDTO = datasDTOSList.get(j);

            String navigationName = datasDTO.getName();
            navigationNameList.add(navigationName);
            //Log.d("Aaron","Title==" + title );

        }
        switch (param) {
            case "navigationName":
                return navigationNameList;
        }

        return navigationNameList;
    }

    //获取个人信息数据
    public void userInfoJsonData(View me_viewHome) {
        Gson gson = new Gson();
        GetPostData userInfoGet = new GetPostData();
        String userInfoJson;

        String rank , username;
        int level;
        DBHelper dbHelper = new DBHelper(this, "cookies.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.query("cookies", null, null, null, null, null, null);

        // 将游标移到开头
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) { // 游标只要不是在最后一行之后，就一直循环

            int id=cursor.getInt(0);
            //Log.d("Aaron","id==" + id);
            String cookie=cursor.getString(1);

            userInfoGet.setCookie(cookie);
            //Log.d("Aaron","cookie==" + cookie);
            // 将游标移到下一行
            cursor.moveToNext();

        }
        userInfoGet.setUrl("https://wanandroid.com//user/lg/userinfo/json");
        userInfoGet.start();
        while (userInfoGet.getContent() == null) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        db.close();
        userInfoJson = userInfoGet.getContent();
        UserInfo userInfo = gson.fromJson(userInfoJson, UserInfo.class);
        userInfo.getData().getCoinInfo().getLevel();
        level = userInfo.getData().getCoinInfo().getLevel();
        rank = userInfo.getData().getCoinInfo().getRank();
        username = userInfo.getData().getUserInfo().getPublicName();
        //Log.d("Aaron", "level是==" + level);
        TextView userInfoLevel_tv = (TextView) me_viewHome.findViewById(R.id.tv_user_level);
        TextView userInfoRank_tv = (TextView) me_viewHome.findViewById(R.id.tv_user_ranking);
        TextView userInfoName_tv = (TextView) me_viewHome.findViewById(R.id.tv_user_name);
        userInfoLevel_tv.setText(String.valueOf(level));
        userInfoRank_tv.setText(rank);
        userInfoName_tv.setText(username);
        Log.d("Aaron", "数据是==" + userInfoGet.getContent());
        //return userInfoGet.getContent();
    }

    //获取问答数据
    public ArrayList<String> wendaJsonData(String param) {     //設置一个param用來返回不同的值
        //文章Json数据
        Gson gson = new Gson();

        String str2 = "";
        GetNetDataThread getNetDataThread = new GetNetDataThread();
        ArrayList<String> wenDaLinkList = new ArrayList<String>();
        ArrayList<String> wenDatitleList = new ArrayList<String>();

        getNetDataThread.setUrl("https://wanandroid.com/wenda/list/0/json");
        getNetDataThread.start();

        while (getNetDataThread.getContent() == null) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        str2 = getNetDataThread.getContent();
        WenDa wenDa = gson.fromJson(str2, WenDa.class);
        List<WenDa.DataDTO.DatasDTO> datasDTOSList = wenDa.getData().getDatas();
        for (int j = 0; j < datasDTOSList.size(); j++) {
            WenDa.DataDTO.DatasDTO datasDTO = datasDTOSList.get(j);
            String link = datasDTO.getLink();
            String title = datasDTO.getTitle();
            wenDaLinkList.add(link);
            wenDatitleList.add(title);
            //Log.d("Aaron","Title==" + title );

        }

        switch (param) {
            case "wenDaLink":
                return wenDaLinkList;
            case "wenDaTitle":
                return wenDatitleList;

        }

        return wenDaLinkList;

    }

}














