package com.example.wanandroidapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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
import com.example.wanandroidapp.bean.Article;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class MainActivity extends AppCompatActivity implements GetViewPagerItemView{

    private ViewPager mViewPager;
    private ViewPager mViewBannerPager;
    private RadioGroup mRadioGroup;
    private RadioButton tab1, tab2, tab3, tab4;
    private List<View> mViews;   //存放视图
    private TextView textView;
    public static String jsonStr = ""; //静态变量
    //private BannerActivity bannerActivity;


    private Banner mBanner;

    private final int[] mDrawableIds = {R.mipmap.icon_true, R.mipmap.icon_true, R.mipmap.icon_true, R.mipmap.icon_true, R.mipmap.icon_true};




    private List<String> mItemViews = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();//初始化数据

        //初始化轮播图数据
        initBannerViews();
        initBannerDatas();
//        IdCardFragment frontFragment =
//                (IdCardFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_card_front);

        //对单选按钮进行监听，选中、未选中
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

        mViews = new ArrayList<View>();//加载，添加视图


        View viewHome = LayoutInflater.from(this).inflate(R.layout.home, null);

        ListView listView = viewHome.findViewById(R.id.listView);

        ArrayList<HashMap<String, Object>> listItem = new ArrayList<>();
        ArrayList<String> articleLinkList = new ArrayList<String>();
        ArrayList<String> titleList = new ArrayList<String>();
        articleLinkList = parseJsonData("articleList");
        titleList = parseJsonData("titleList");
        //parseJsonData();


        for (int i = 0; i < articleLinkList.size(); i++) {
            HashMap<String, Object> map = new HashMap<>();
            //加入图片
            textView = (TextView) findViewById(R.id.textView);
            //map.put("ItemImage", R.drawable.me);
//            map.put("ItemText", "这是第" + i + "行");
            map.put("ItemText", titleList.get(i));
            listItem.add(map);
            //Log.d("Aaron", "i==" + i);
        }


        SimpleAdapter adapter = new SimpleAdapter(this,
                //绑定的数据
                listItem,
                //每一行的布局
                R.layout.item,
                //动态数组中的数据源的键映射到布局文件对应的控件中
//                new String[]{"ItemImage", "ItemText"},
//                new int[]{R.id.imageView, R.id.textView});
                new String[]{"ItemText"},
                new int[]{R.id.textView});

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
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
        });

        mViews.add(viewHome);
        mViews.add(LayoutInflater.from(this).inflate(R.layout.contact, null));
        mViews.add(LayoutInflater.from(this).inflate(R.layout.popular_sites, null));
        mViews.add(LayoutInflater.from(this).inflate(R.layout.me, null));

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

    public ArrayList<String> parseJsonData(String param) {     //設置一个str用來返回不同的值
        //文章Json数据
        Gson gson = new Gson();

        //String str1 = "{\"data\":{\"curPage\":2,\"datas\":[{\"apkLink\":\"\",\"audit\":1,\"author\":\"\",\"canEdit\":false,\"chapterId\":502,\"chapterName\":\"自助\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23863,\"link\":\"https://www.jianshu.com/p/2f0ecf6ca08c\",\"niceDate\":\"1天前\",\"niceShareDate\":\"1天前\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1660137080000,\"realSuperChapterId\":493,\"selfVisible\":0,\"shareDate\":1660137059000,\"shareUser\":\"鸿洋\",\"superChapterId\":494,\"superChapterName\":\"广场Tab\",\"tags\":[],\"title\":\"Android 重学系列 有趣的工具--智能指针与智能锁\",\"type\":0,\"userId\":2,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"\",\"canEdit\":false,\"chapterId\":502,\"chapterName\":\"自助\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23853,\"link\":\"https://juejin.cn/post/7130144715063689253\",\"niceDate\":\"2天前\",\"niceShareDate\":\"2天前\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1660120326000,\"realSuperChapterId\":493,\"selfVisible\":0,\"shareDate\":1660120326000,\"shareUser\":\"彭旭锐\",\"superChapterId\":494,\"superChapterName\":\"广场Tab\",\"tags\":[],\"title\":\"飞书前端提到的竞态问题，在 Android 上怎么解决？\",\"type\":0,\"userId\":30587,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"张鸿洋\",\"canEdit\":false,\"chapterId\":543,\"chapterName\":\"Android技术周报\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23850,\"link\":\"https://www.wanandroid.com/blog/show/3401\",\"niceDate\":\"2天前\",\"niceShareDate\":\"2天前\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1660060800000,\"realSuperChapterId\":542,\"selfVisible\":0,\"shareDate\":1660061400000,\"shareUser\":\"\",\"superChapterId\":543,\"superChapterName\":\"技术周报\",\"tags\":[],\"title\":\"Android 技术周刊 （2022-08-03 ~ 2022-08-10）\",\"type\":0,\"userId\":-1,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"鸿洋\",\"canEdit\":false,\"chapterId\":408,\"chapterName\":\"鸿洋\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23864,\"link\":\"https://mp.weixin.qq.com/s/GJJHjbg9QK93kD4YzHnrAQ\",\"niceDate\":\"2天前\",\"niceShareDate\":\"1天前\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1660060800000,\"realSuperChapterId\":407,\"selfVisible\":0,\"shareDate\":1660143814000,\"shareUser\":\"\",\"superChapterId\":408,\"superChapterName\":\"公众号\",\"tags\":[{\"name\":\"公众号\",\"url\":\"/wxarticle/list/408/1\"}],\"title\":\"WebView遇到的各种问题解决方案分享\",\"type\":0,\"userId\":-1,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"\",\"canEdit\":false,\"chapterId\":502,\"chapterName\":\"自助\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23849,\"link\":\"https://blog.csdn.net/chong_lai/article/details/126217107\",\"niceDate\":\"2天前\",\"niceShareDate\":\"2天前\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1660057874000,\"realSuperChapterId\":493,\"selfVisible\":0,\"shareDate\":1660057856000,\"shareUser\":\"usagisang\",\"superChapterId\":494,\"superChapterName\":\"广场Tab\",\"tags\":[],\"title\":\"Compose LazyGrid 相关介绍\",\"type\":0,\"userId\":126508,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"\",\"canEdit\":false,\"chapterId\":502,\"chapterName\":\"自助\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23843,\"link\":\"https://juejin.cn/post/7129419242088169485\",\"niceDate\":\"2022-08-09 08:58\",\"niceShareDate\":\"2022-08-09 08:58\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1660006699000,\"realSuperChapterId\":493,\"selfVisible\":0,\"shareDate\":1660006699000,\"shareUser\":\"equationl\",\"superChapterId\":494,\"superChapterName\":\"广场Tab\",\"tags\":[],\"title\":\"使用 compose 的 Canvas 自定义绘制实现 LCD 显示数字效果\",\"type\":0,\"userId\":87590,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"\",\"canEdit\":false,\"chapterId\":502,\"chapterName\":\"自助\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23834,\"link\":\"https://juejin.cn/post/7129306281650683935\",\"niceDate\":\"2022-08-09 00:38\",\"niceShareDate\":\"2022-08-09 00:35\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1659976695000,\"realSuperChapterId\":493,\"selfVisible\":0,\"shareDate\":1659976531000,\"shareUser\":\"鸿洋\",\"superChapterId\":494,\"superChapterName\":\"广场Tab\",\"tags\":[],\"title\":\"裸辞-疫情-闭关-复习-大厂offer（二）\",\"type\":0,\"userId\":2,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"\",\"canEdit\":false,\"chapterId\":502,\"chapterName\":\"自助\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23835,\"link\":\"https://juejin.cn/post/7129239231473385503\",\"niceDate\":\"2022-08-09 00:38\",\"niceShareDate\":\"2022-08-09 00:35\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1659976693000,\"realSuperChapterId\":493,\"selfVisible\":0,\"shareDate\":1659976537000,\"shareUser\":\"鸿洋\",\"superChapterId\":494,\"superChapterName\":\"广场Tab\",\"tags\":[],\"title\":\"Flutter 实现 &ldquo;真&rdquo; 3D 动画效果，用纯代码实现立体 Dash 和 3D 掘金 Logo\",\"type\":0,\"userId\":2,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"\",\"canEdit\":false,\"chapterId\":502,\"chapterName\":\"自助\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23836,\"link\":\"https://juejin.cn/post/7129157665732689934\",\"niceDate\":\"2022-08-09 00:38\",\"niceShareDate\":\"2022-08-09 00:35\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1659976690000,\"realSuperChapterId\":493,\"selfVisible\":0,\"shareDate\":1659976541000,\"shareUser\":\"鸿洋\",\"superChapterId\":494,\"superChapterName\":\"广场Tab\",\"tags\":[],\"title\":\"&ldquo;终于懂了&rdquo; 系列：组件化框架 ARouter 完全解析（二）APT技术\",\"type\":0,\"userId\":2,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"\",\"canEdit\":false,\"chapterId\":502,\"chapterName\":\"自助\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23837,\"link\":\"https://juejin.cn/post/7129063704829804580\",\"niceDate\":\"2022-08-09 00:38\",\"niceShareDate\":\"2022-08-09 00:36\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1659976688000,\"realSuperChapterId\":493,\"selfVisible\":0,\"shareDate\":1659976563000,\"shareUser\":\"鸿洋\",\"superChapterId\":494,\"superChapterName\":\"广场Tab\",\"tags\":[],\"title\":\"音视频开发之旅（66) - 音频变速不变调的原理\",\"type\":0,\"userId\":2,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"\",\"canEdit\":false,\"chapterId\":502,\"chapterName\":\"自助\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23838,\"link\":\"https://juejin.cn/post/7128947531471388709\",\"niceDate\":\"2022-08-09 00:38\",\"niceShareDate\":\"2022-08-09 00:36\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1659976687000,\"realSuperChapterId\":493,\"selfVisible\":0,\"shareDate\":1659976567000,\"shareUser\":\"鸿洋\",\"superChapterId\":494,\"superChapterName\":\"广场Tab\",\"tags\":[],\"title\":\"Android实现倒计时的几种方案汇总\",\"type\":0,\"userId\":2,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"\",\"canEdit\":false,\"chapterId\":502,\"chapterName\":\"自助\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23839,\"link\":\"https://juejin.cn/post/7128779284503592991\",\"niceDate\":\"2022-08-09 00:38\",\"niceShareDate\":\"2022-08-09 00:36\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1659976685000,\"realSuperChapterId\":493,\"selfVisible\":0,\"shareDate\":1659976579000,\"shareUser\":\"鸿洋\",\"superChapterId\":494,\"superChapterName\":\"广场Tab\",\"tags\":[],\"title\":\"软件绘制 &amp; 硬件加速绘制 【DisplayList &amp; RenderNode】\",\"type\":0,\"userId\":2,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"\",\"canEdit\":false,\"chapterId\":502,\"chapterName\":\"自助\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23840,\"link\":\"https://www.jianshu.com/p/22a398d615e3\",\"niceDate\":\"2022-08-09 00:38\",\"niceShareDate\":\"2022-08-09 00:37\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1659976683000,\"realSuperChapterId\":493,\"selfVisible\":0,\"shareDate\":1659976649000,\"shareUser\":\"鸿洋\",\"superChapterId\":494,\"superChapterName\":\"广场Tab\",\"tags\":[],\"title\":\"深入理解Android Runtime\",\"type\":0,\"userId\":2,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"\",\"canEdit\":false,\"chapterId\":502,\"chapterName\":\"自助\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23841,\"link\":\"https://www.jianshu.com/p/92fdc538bde4\",\"niceDate\":\"2022-08-09 00:38\",\"niceShareDate\":\"2022-08-09 00:37\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1659976681000,\"realSuperChapterId\":493,\"selfVisible\":0,\"shareDate\":1659976653000,\"shareUser\":\"鸿洋\",\"superChapterId\":494,\"superChapterName\":\"广场Tab\",\"tags\":[],\"title\":\"Android多用户的一些坑\",\"type\":0,\"userId\":2,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"鸿洋\",\"canEdit\":false,\"chapterId\":408,\"chapterName\":\"鸿洋\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23847,\"link\":\"https://mp.weixin.qq.com/s/ZpoPqFT8PtRiFHVpim8Xgw\",\"niceDate\":\"2022-08-09 00:00\",\"niceShareDate\":\"2天前\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1659974400000,\"realSuperChapterId\":407,\"selfVisible\":0,\"shareDate\":1660057617000,\"shareUser\":\"\",\"superChapterId\":408,\"superChapterName\":\"公众号\",\"tags\":[{\"name\":\"公众号\",\"url\":\"/wxarticle/list/408/1\"}],\"title\":\"Android 技术周刊（第1期）：38篇技术文章\",\"type\":0,\"userId\":-1,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"郭霖\",\"canEdit\":false,\"chapterId\":409,\"chapterName\":\"郭霖\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23848,\"link\":\"https://mp.weixin.qq.com/s/Oxqa-MQi0-woqlB2EEMHZQ\",\"niceDate\":\"2022-08-09 00:00\",\"niceShareDate\":\"2天前\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1659974400000,\"realSuperChapterId\":407,\"selfVisible\":0,\"shareDate\":1660057635000,\"shareUser\":\"\",\"superChapterId\":408,\"superChapterName\":\"公众号\",\"tags\":[{\"name\":\"公众号\",\"url\":\"/wxarticle/list/409/1\"}],\"title\":\"从XML到View显示在屏幕上，都发生了什么？\",\"type\":0,\"userId\":-1,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"\",\"canEdit\":false,\"chapterId\":502,\"chapterName\":\"自助\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23827,\"link\":\"https://mp.weixin.qq.com/s/LWDOaAQIkRnst2GLCAeAsw\",\"niceDate\":\"2022-08-08 08:54\",\"niceShareDate\":\"2022-08-08 08:54\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1659920045000,\"realSuperChapterId\":493,\"selfVisible\":0,\"shareDate\":1659920045000,\"shareUser\":\"JsonChao\",\"superChapterId\":494,\"superChapterName\":\"广场Tab\",\"tags\":[],\"title\":\"三个值得深入思考的 Android 问答分享（第 2 期）\",\"type\":0,\"userId\":611,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"Android群英传\",\"canEdit\":false,\"chapterId\":413,\"chapterName\":\"Android群英传\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23831,\"link\":\"https://mp.weixin.qq.com/s/HKpmfz1vM7ojhZNHPy-cHg\",\"niceDate\":\"2022-08-08 00:00\",\"niceShareDate\":\"2022-08-08 23:06\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1659888000000,\"realSuperChapterId\":407,\"selfVisible\":0,\"shareDate\":1659971175000,\"shareUser\":\"\",\"superChapterId\":408,\"superChapterName\":\"公众号\",\"tags\":[{\"name\":\"公众号\",\"url\":\"/wxarticle/list/413/1\"}],\"title\":\"Flutter混编工程之打通纹理之路\",\"type\":0,\"userId\":-1,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"RuffianZhong\",\"canEdit\":false,\"chapterId\":539,\"chapterName\":\"未分类\",\"collect\":false,\"courseId\":13,\"desc\":\"垃圾Flutter没有生命周期？\",\"descMd\":\"\",\"envelopePic\":\"https://www.wanandroid.com/resources/image/pc/default_project_img.jpg\",\"fresh\":false,\"host\":\"\",\"id\":23826,\"link\":\"https://www.wanandroid.com/blog/show/3400\",\"niceDate\":\"2022-08-07 22:23\",\"niceShareDate\":\"2022-08-07 22:23\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"https://github.com/RuffianZhong/flutter_lifecycle\",\"publishTime\":1659882235000,\"realSuperChapterId\":293,\"selfVisible\":0,\"shareDate\":1659882235000,\"shareUser\":\"\",\"superChapterId\":294,\"superChapterName\":\"开源项目主Tab\",\"tags\":[{\"name\":\"项目\",\"url\":\"/project/list/1?cid=539\"}],\"title\":\"垃圾Flutter没有生命周期？\",\"type\":0,\"userId\":-1,\"visible\":1,\"zan\":0}],\"offset\":20,\"over\":false,\"pageCount\":643,\"size\":20,\"total\":12844},\"errorCode\":0,\"errorMsg\":\"\"}";
        String str2 = "";
        GetNetDataThread getNetDataThread = new GetNetDataThread();
        getNetDataThread.setUrl("https://www.wanandroid.com/article/list/1/json");
        getNetDataThread.start();
        //设置此函数用来确保多线程里的Content收到参数为止
        while (getNetDataThread.getContent() == null){
            try{
                Thread.sleep(100);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        String str1 = getNetDataThread.getContent();

        ArrayList<String> articleList = new ArrayList<String>();
        ArrayList<String> titleList = new ArrayList<String>();
        Article article = gson.fromJson(str1,Article.class);
        List<Article.DataDTO.DatasDTO> datasDTOSList = article.getData().getDatas();

        //getNetDataByOkHttp();
        for (int i = 0; i < datasDTOSList.size(); i++) {
            Article.DataDTO.DatasDTO datasDTO = datasDTOSList.get(i);
            String link = datasDTO.getLink();
            String title = datasDTO.getTitle();
            articleList.add(link);
            titleList.add(title);
            //Log.d("Aaron","Title==" + title );

        }
        if(param == "articleList") {
            return articleList;
        }
        else if(param == "titleList"){
            return titleList;
        }
        else
            return articleList;

    }





    public static String getJsonStr() {
        return jsonStr;
    }

    public static void setJsonStr(String jsonStr) {
        MainActivity.jsonStr = jsonStr;
    }









}




