package com.example.wanandroidapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.wanandroidapp.Adapter.SearchAdapter;
import com.example.wanandroidapp.Banner.Banner;
import com.example.wanandroidapp.Banner.GetViewPagerItemView;
import com.example.wanandroidapp.Banner.GsonBannerData;
import com.example.wanandroidapp.SQLite.AvatarDBHelper;
import com.example.wanandroidapp.SQLite.UpLoadAvatarDBHelper;
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
import com.example.wanandroidapp.model.Bean;
import com.example.wanandroidapp.util.BitmapUtils;
import com.example.wanandroidapp.util.CameraUtils;
import com.example.wanandroidapp.util.ImageUtil;
import com.example.wanandroidapp.util.SPUtils;
import com.example.wanandroidapp.widge.SearchView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;



public class MainActivity extends Activity implements GetViewPagerItemView,SearchView.SearchViewListener{
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
    private ImageView headView = null;
    ArrayList<String> navigationNameList = new ArrayList<String>();
    private RoundedImageView ivTouXiangAvatar;

    /**
     * 搜索结果列表view
     */
    private ListView lvResults;

    /**
     * 搜索view
     */
    private SearchView searchView;


    /**
     * 热搜框列表adapter
     */
    private ArrayAdapter<String> hintAdapter;

    /**
     * 自动补全列表adapter
     */
    private ArrayAdapter<String> autoCompleteAdapter;

    /**
     * 搜索结果列表adapter
     */
    private SearchAdapter resultAdapter;

    private List<Bean> dbData;

    /**
     * 热搜版数据
     */
    private List<String> hintData;

    /**
     * 搜索过程中自动补全数据
     */
    private List<String> autoCompleteData;

    /**
     * 搜索结果的数据
     */
    private List<Bean> resultData;

    /**
     * 默认提示框显示项的个数
     */
    private static int DEFAULT_HINT_SIZE = 4;

    /**
     * 提示框显示项的个数
     */
    private static int hintSize = DEFAULT_HINT_SIZE;

    /**
     * 设置提示框显示项的个数
     *
     * @param hintSize 提示框显示个数
     */
    public static void setHintSize(int hintSize) {
        MainActivity.hintSize = hintSize;
    }

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

    //权限请求
    private RxPermissions rxPermissions;

    //是否拥有权限
    private boolean hasPermissions = false;

    //底部弹窗
    private BottomSheetDialog bottomSheetDialog;
    //弹窗视图
    private View bottomView;

    //存储拍完照后的图片
    private File outputImagePath;
    //启动相机标识
    public static final int TAKE_PHOTO = 1;
    //启动相册标识
    public static final int SELECT_PHOTO = 2;

    //图片控件
    private ShapeableImageView ivHead ,ivAvatar;
    //Base64
    private String base64Pic;
    //拍照和相册获取图片的Bitmap
    private Bitmap orc_bitmap;
    private Bitmap avatar_bitmap;
    //Glide请求图片选项配置
    private RequestOptions requestOptions = RequestOptions.circleCropTransform()
            .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
            .skipMemoryCache(true);//不做内存缓存
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();//初始化数据

        //初始化轮播图数据
        initBannerViews();
        initBannerDatas();
        //初始化搜索框数据

        initSearchDatas();
        initSearchViews();

        //检查版本
        checkVersion();

        //取出缓存
        String imageUrl = SPUtils.getString("imageUrl",null,this);
//        if(imageUrl != null){
//            Glide.with(this).load(imageUrl).apply(requestOptions).into(ivHead);
//        }


        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_home:
                        mViewPager.setCurrentItem(0);
                        mBanner.setVisibility(View.VISIBLE);
                        searchView.setVisibility(View.VISIBLE);
                        lvResults.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rb_contact:
                        mViewPager.setCurrentItem(1);
                        mBanner.setVisibility(View.GONE);
                        searchView.setVisibility(View.GONE);
                        lvResults.setVisibility(View.GONE);
                        break;
                    case R.id.rb_popular_sites:
                        mViewPager.setCurrentItem(2);
                        mBanner.setVisibility(View.GONE);
                        searchView.setVisibility(View.GONE);
                        lvResults.setVisibility(View.GONE);
                        break;
                    case R.id.rb_me:
                        mViewPager.setCurrentItem(3);
                        mBanner.setVisibility(View.GONE);
                        searchView.setVisibility(View.GONE);
                        lvResults.setVisibility(View.GONE);
                        break;

                }
            }
        });


    }


    /**
     * 初始化搜索视图
     */
    private void initSearchViews() {
        lvResults = (ListView) findViewById(R.id.main_lv_search_results);
        searchView = (SearchView) findViewById(R.id.main_search_layout);
        //设置监听
        searchView.setSearchViewListener(this);
        //设置adapter
        searchView.setTipsHintAdapter(hintAdapter);
        searchView.setAutoCompleteAdapter(autoCompleteAdapter);

        lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(MainActivity.this, position + "", Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * 初始化搜索数据
     */
    private void initSearchDatas() {
        //从数据库获取数据
        getDbData();
        //初始化热搜版数据
        getHintData();
        //初始化自动补全数据
        getAutoCompleteData(null);
        //初始化搜索结果数据
        getResultData(null);
    }

    /**
     * 获取db 数据
     */
    private void getDbData() {
        int size = 100;
        dbData = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            //dbData.add(new Bean(R.drawable.icon, "android开发必备技能" + (i + 1), "Android自定义view——自定义搜索view", i * 20 + 2 + ""));
            dbData.add(new Bean( "android开发必备技能" + (i + 1), "Android自定义view——自定义搜索view", i * 20 + 2 + ""));
        }
    }

    /**
     * 获取热搜版data 和adapter
     */
    private void getHintData() {
        hintData = new ArrayList<>(hintSize);
        for (int i = 1; i <= hintSize; i++) {
            hintData.add("热搜版" + i + "：Android自定义View");
        }
        hintAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hintData);
    }

    /**
     * 获取自动补全data 和adapter
     */
    private void getAutoCompleteData(String text) {
        if (autoCompleteData == null) {
            //初始化
            autoCompleteData = new ArrayList<>(hintSize);
        } else {
            // 根据text 获取auto data
            autoCompleteData.clear();
            for (int i = 0, count = 0; i < dbData.size()
                    && count < hintSize; i++) {
                if (dbData.get(i).getTitle().contains(text.trim())) {
                    autoCompleteData.add(dbData.get(i).getTitle());
                    count++;
                }
            }
        }
        if (autoCompleteAdapter == null) {
            autoCompleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, autoCompleteData);
        } else {
            autoCompleteAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取搜索结果data和adapter
     */
    private void getResultData(String text) {
        if (resultData == null) {
            // 初始化
            resultData = new ArrayList<>();
        } else {
            resultData.clear();
            for (int i = 0; i < dbData.size(); i++) {
                if (dbData.get(i).getTitle().contains(text.trim())) {
                    resultData.add(dbData.get(i));
                }
            }
        }
        if (resultAdapter == null) {
            resultAdapter = new SearchAdapter(this, resultData, R.layout.item_bean_list);
        } else {
            resultAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 当搜索框 文本改变时 触发的回调 ,更新自动补全数据
     * @param text
     */
    @Override
    public void onRefreshAutoComplete(String text) {
        //更新数据
        getAutoCompleteData(text);
    }

    /**
     * 点击搜索键时edit text触发的回调
     *
     * @param text
     */
    @Override
    public void onSearch(String text) {
        //更新result数据
        //getResultData(text);
        //lvResults.setVisibility(View.VISIBLE);
        //第一次获取结果 还未配置适配器
//        if (lvResults.getAdapter() == null) {
//            //获取搜索数据 设置适配器
//            lvResults.setAdapter(resultAdapter);
//        } else {
//            //更新搜索数据
//            resultAdapter.notifyDataSetChanged();
//        }
        Toast.makeText(this, "完成搜素", Toast.LENGTH_SHORT).show();

        Intent it = new Intent(MainActivity.this, SearchActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("searchText", text);
        it.putExtras(bundle);
        startActivity(it);


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
            case R.id.iv_head:
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
        ivTouXiangAvatar = findViewById(R.id.iv_head);
//        SharedPreferences spfRecord = getSharedPreferences("spfRecord", MODE_PRIVATE);
//        String account = spfRecord.getString("account", "");
//        String image64 = spfRecord.getString("image_64", "");
//        Log.d("Aaron","account==" + account);
//        Log.d("Aaron","image64==" + image64);


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


        //第一次进app，默认为用户7433登陆
        DBHelper dbHelper = new DBHelper(this, "cookies.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("cookies","u_id = 1",null);
        ContentValues cValue = new ContentValues();
        cValue.put("u_id",1);
        cValue.put("cookie","token_pass_wanandroid_com=5d9b90bcb70640183e09d1e755ead823;loginUserName_wanandroid_com=zhou7433;JSESSIONID=CF318C6CA9C070F3BAA33A86844071EA");
        db.insert("cookies",null ,cValue);
        db.close();


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
//        if(isLogin1 == 1 || isLogin2 == 1){
//            exitLoginView.setVisibility(View.VISIBLE);
//        }

        userInfoJsonData(me_viewHome);
        //设置头像
//        SharedPreferences spfRecord = getSharedPreferences("spfRecord", MODE_PRIVATE);
//        String username = spfRecord.getString("username", "");
//        AvatarDBHelper avatarDBHelper = new AvatarDBHelper(this, "avatars.db", null, 1);
//        SQLiteDatabase avatar_db = avatarDBHelper.getWritableDatabase();
//        Cursor cursor = avatar_db.query("avatars", null, "username=?", new String[]{username}, null, null, null);
//        Log.d("Aaron","cursor.getCount()==" + cursor.getCount());
//
//        //Log.d("Aaron","base64==" + cursor.getString(2));
//        if(cursor.getCount() == 1){
//            cursor.moveToFirst();
//            avatar_bitmap = BitmapUtils.base64ToBitmap(cursor.getString(2));
//
//            //Log.d("Aaron","base64==" + cursor.getString(2));
//            //Log.d("Aaron","avatar_bitmap==" + avatar_bitmap);
//            ivAvatar = view.findViewById(R.id.iv_head);
//            //Log.d("Aaron","ivAvatar==" + ivAvatar);
//            ivAvatar.setImageBitmap(avatar_bitmap);
//        }
//        avatar_db.close();

        SharedPreferences spfRecord = getSharedPreferences("spfRecord", MODE_PRIVATE);
        String username = spfRecord.getString("username", "");
        UpLoadAvatarDBHelper upLoadAvatarDBHelper = new UpLoadAvatarDBHelper(this, "avatars.db", null, 1);
        SQLiteDatabase db = upLoadAvatarDBHelper.getWritableDatabase();
        Cursor cursor = db.query("avatars", null, "username=?", new String[]{username}, null, null, null);
        Log.d("Aaron","cursor.getCount()==" + cursor.getCount());
        if(cursor.getCount() == 1){
            cursor.moveToFirst();
//            byte[] in=cursor.getString(2);
//            bmpout=BitmapFactory.decodeByteArray(in,0,in.length);

            ivAvatar = view.findViewById(R.id.iv_head);
            @SuppressLint("Range") byte[] in=cursor.getBlob(cursor.getColumnIndex("express_img"));
            Bitmap bmpout=BitmapFactory.decodeByteArray(in,0,in.length);
//            Log.d("Aaron","cursor.getColumnIndex(\"express_img\")==" + cursor.getString(2));
            ivAvatar.setImageBitmap(bmpout);
//            @SuppressLint("Range") ByteArrayInputStream stream = new ByteArrayInputStream(cursor.getBlob(cursor.getColumnIndex("express_img")));
//            ivAvatar.setImageDrawable(Drawable.createFromStream(stream, "img"));
        }
        db.close();

        navigationNameList = navigationJsonData("navigationName");
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
                    //bundle.putInt("number", i);
                    bundle.putString("url",parseJsonData("articleList").get(i));
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
                //bundle.putInt("number", i);
                bundle.putString("url",wendaJsonData("wenDaLink").get(i));
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


    /**
     * 检查版本
     */
    private void checkVersion() {
        //Android6.0及以上版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //如果你是在Fragment中，则把this换成getActivity()
            rxPermissions = new RxPermissions(this);
            //权限请求
            rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(granted -> {
                        if (granted) {//申请成功
                            showMsg("已获取权限");
                            hasPermissions = true;
                        } else {//申请失败
                            showMsg("权限未开启");
                            hasPermissions = false;
                        }
                    });
        } else {
            //Android6.0以下
            showMsg("无需请求动态权限");
        }
    }

    /**
     * 更换头像
     *
     * @param view
     */
    public void changeAvatar(View view) {
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomView = getLayoutInflater().inflate(R.layout.dialog_bottom, null);
        bottomSheetDialog.setContentView(bottomView);
        //bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundColor(Color.TRANSPARENT);
        TextView tvTakePictures = bottomView.findViewById(R.id.tv_take_pictures);
        TextView tvOpenAlbum = bottomView.findViewById(R.id.tv_open_album);
        TextView tvCancel = bottomView.findViewById(R.id.tv_cancel);

        //拍照
        tvTakePictures.setOnClickListener(v -> {
            takePhoto();
            showMsg("拍照");
            bottomSheetDialog.cancel();
        });
        //打开相册
        tvOpenAlbum.setOnClickListener(v -> {
            openAlbum();
            showMsg("打开相册");
            bottomSheetDialog.cancel();
        });
        //取消
        tvCancel.setOnClickListener(v -> {
            bottomSheetDialog.cancel();
        });
        //底部弹窗显示
        bottomSheetDialog.show();
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        if (!hasPermissions) {
            showMsg("未获取到权限");
            checkVersion();
            return;
        }
        SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                "yyyy_MM_dd_HH_mm_ss");
        String filename = timeStampFormat.format(new Date());
        outputImagePath = new File(getExternalCacheDir(),
                filename + ".jpg");
        Intent takePhotoIntent = CameraUtils.getTakePhotoIntent(this, outputImagePath);
        // 开启一个带有返回值的Activity，请求码为TAKE_PHOTO
        startActivityForResult(takePhotoIntent, TAKE_PHOTO);
    }

    /**
     * 打开相册
     */
    private void openAlbum() {
        if (!hasPermissions) {
            showMsg("未获取到权限");
            checkVersion();
            return;
        }
        startActivityForResult(CameraUtils.getSelectPhotoIntent(), SELECT_PHOTO);
    }

    /**
     * 返回到Activity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //拍照后返回
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    //显示图片
                    displayImage(outputImagePath.getAbsolutePath());
                }
                break;
            //打开相册后返回
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    String imagePath = null;
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                        //4.4及以上系统使用这个方法处理图片
                        imagePath = CameraUtils.getImageOnKitKatPath(data, this);
                    } else {
                        imagePath = CameraUtils.getImageBeforeKitKatPath(data, this);
                    }
                    //显示图片
                    displayImage(imagePath);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 通过图片路径显示图片
     */
    private void displayImage(String imagePath) {
        if (!TextUtils.isEmpty(imagePath)) {

            //放入缓存
            SPUtils.putString("imageUrl",imagePath,this);
            ivHead = findViewById(R.id.iv_head);
            //显示图片
            //Glide.with(this).load(imagePath).apply(requestOptions).into(ivHead);
            //Glide.with(this).load("http://rjoyl2a7m.hn-bkt.clouddn.com/542c1db3b6d360845369369b3fb0ac24.jpeg").apply(requestOptions).into(ivHead);
            //压缩图片
            orc_bitmap = CameraUtils.compression(BitmapFactory.decodeFile(imagePath));
            ivHead.setImageBitmap(orc_bitmap);

            //Log.d("Aaron","base64==" + cursor.getString(2));
//            Log.d("Aaron","orc_bitmap==" + orc_bitmap);
//            Log.d("Aaron","ivHead==" + ivHead);

            //转Base64
            base64Pic = BitmapUtils.bitmapToBase64(orc_bitmap);

            SharedPreferences spfRecord = getSharedPreferences("spfRecord", MODE_PRIVATE);
            String username = spfRecord.getString("username", "");

//            AvatarDBHelper avatarDBHelper = new AvatarDBHelper(this, "avatars.db", null, 1);
//            SQLiteDatabase db = avatarDBHelper.getWritableDatabase();
//            ContentValues cValue = new ContentValues();
//            Cursor cursor = db.query("avatars", null, "username=?", new String[]{username}, null, null, null);
//            if(cursor.getCount() == 0){
//                cValue.put("username",username);
//                cValue.put("image_base64",base64Pic);
//                db.insert("avatars",null ,cValue);
//            }
//            db.close();
            final ByteArrayOutputStream os = new ByteArrayOutputStream();
            orc_bitmap.compress(Bitmap.CompressFormat.JPEG,100,os);
            UpLoadAvatarDBHelper upLoadAvatarDBHelper = new UpLoadAvatarDBHelper(this, "avatars.db", null, 1);
            SQLiteDatabase db = upLoadAvatarDBHelper.getWritableDatabase();
            ContentValues cValue = new ContentValues();
            Cursor cursor = db.query("avatars", null, "username=?", new String[]{username}, null, null, null);
            if(cursor.getCount() == 0){
                cValue.put("username",username);
                cValue.put("express_img",os.toByteArray());
                db.insert("avatars",null ,cValue);
            }
            db.close();



        } else {
            showMsg("图片获取失败");
        }
    }


    /**
     * Toast提示
     *
     * @param msg
     */
    private void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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
            //userInfoGet.setCookie("JSESSIONID=CF318C6CA9C070F3BAA33A86844071EA");
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

        if(userInfo.getErrorCode() == 0) {
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

            SharedPreferences spf = getSharedPreferences("spfRecord", MODE_PRIVATE);
            SharedPreferences.Editor edit = spf.edit();
            edit.putString("username", username);
            edit.apply();
        }

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

    //获取网络图片
    public Bitmap returnBitMap(String url){

        URL myFileUrl = null;

        Bitmap bitmap = null;

        try {

            myFileUrl = new URL(url);

        } catch (MalformedURLException e) {

            e.printStackTrace();

        }

        try {

            HttpURLConnection conn = (HttpURLConnection) myFileUrl

                    .openConnection();

            conn.setDoInput(true);

            conn.connect();

            InputStream is = conn.getInputStream();

            bitmap = BitmapFactory.decodeStream(is);

            is.close();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return bitmap;
    }

}














