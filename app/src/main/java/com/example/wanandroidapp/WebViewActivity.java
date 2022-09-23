package com.example.wanandroidapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wanandroidapp.bean.Article;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class WebViewActivity extends AppCompatActivity {

    //声明引用
    private WebView mWVmhtml;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_test);

        ArrayList<String> articleLinkList = new ArrayList<String>();
        articleLinkList = parseJsonData();

        //获取控件对象
        mWVmhtml = (WebView) findViewById(R.id.wv_id);

        //加载本地html文件
        // mWVmhtml.loadUrl("file:///android_asset/hello.html");
        //加载网络URL
        //mWVmhtml.loadUrl("https://blog.csdn.net/qq_36243942/article/details/82187204");
        //设置JavaScrip
        mWVmhtml.getSettings().setJavaScriptEnabled(true);
        Bundle bundle = getIntent().getExtras();
        int number = bundle.getInt("number");
        //访问网址
        mWVmhtml.loadUrl(articleLinkList.get(number));

        mWVmhtml.setWebViewClient(new MyWebViewClient());


    }


    class MyWebViewClient extends WebViewClient {
        @Override  //WebView代表是当前的WebView
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            //表示在当前的WebView继续打开网页
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.d("WebView", "开始访问网页");
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.d("WebView", "访问网页结束");
        }
    }


    public ArrayList<String> parseJsonData() {
        //文章Json数据
        Gson gson = new Gson();
                                                                                                                                                                                        String str1 = "{\"data\":{\"curPage\":2,\"datas\":[{\"apkLink\":\"\",\"audit\":1,\"author\":\"\",\"canEdit\":false,\"chapterId\":502,\"chapterName\":\"自助\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23863,\"link\":\"https://www.jianshu.com/p/2f0ecf6ca08c\",\"niceDate\":\"1天前\",\"niceShareDate\":\"1天前\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1660137080000,\"realSuperChapterId\":493,\"selfVisible\":0,\"shareDate\":1660137059000,\"shareUser\":\"鸿洋\",\"superChapterId\":494,\"superChapterName\":\"广场Tab\",\"tags\":[],\"title\":\"Android 重学系列 有趣的工具--智能指针与智能锁\",\"type\":0,\"userId\":2,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"\",\"canEdit\":false,\"chapterId\":502,\"chapterName\":\"自助\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23853,\"link\":\"https://juejin.cn/post/7130144715063689253\",\"niceDate\":\"2天前\",\"niceShareDate\":\"2天前\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1660120326000,\"realSuperChapterId\":493,\"selfVisible\":0,\"shareDate\":1660120326000,\"shareUser\":\"彭旭锐\",\"superChapterId\":494,\"superChapterName\":\"广场Tab\",\"tags\":[],\"title\":\"飞书前端提到的竞态问题，在 Android 上怎么解决？\",\"type\":0,\"userId\":30587,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"张鸿洋\",\"canEdit\":false,\"chapterId\":543,\"chapterName\":\"Android技术周报\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23850,\"link\":\"https://www.wanandroid.com/blog/show/3401\",\"niceDate\":\"2天前\",\"niceShareDate\":\"2天前\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1660060800000,\"realSuperChapterId\":542,\"selfVisible\":0,\"shareDate\":1660061400000,\"shareUser\":\"\",\"superChapterId\":543,\"superChapterName\":\"技术周报\",\"tags\":[],\"title\":\"Android 技术周刊 （2022-08-03 ~ 2022-08-10）\",\"type\":0,\"userId\":-1,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"鸿洋\",\"canEdit\":false,\"chapterId\":408,\"chapterName\":\"鸿洋\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23864,\"link\":\"https://mp.weixin.qq.com/s/GJJHjbg9QK93kD4YzHnrAQ\",\"niceDate\":\"2天前\",\"niceShareDate\":\"1天前\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1660060800000,\"realSuperChapterId\":407,\"selfVisible\":0,\"shareDate\":1660143814000,\"shareUser\":\"\",\"superChapterId\":408,\"superChapterName\":\"公众号\",\"tags\":[{\"name\":\"公众号\",\"url\":\"/wxarticle/list/408/1\"}],\"title\":\"WebView遇到的各种问题解决方案分享\",\"type\":0,\"userId\":-1,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"\",\"canEdit\":false,\"chapterId\":502,\"chapterName\":\"自助\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23849,\"link\":\"https://blog.csdn.net/chong_lai/article/details/126217107\",\"niceDate\":\"2天前\",\"niceShareDate\":\"2天前\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1660057874000,\"realSuperChapterId\":493,\"selfVisible\":0,\"shareDate\":1660057856000,\"shareUser\":\"usagisang\",\"superChapterId\":494,\"superChapterName\":\"广场Tab\",\"tags\":[],\"title\":\"Compose LazyGrid 相关介绍\",\"type\":0,\"userId\":126508,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"\",\"canEdit\":false,\"chapterId\":502,\"chapterName\":\"自助\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23843,\"link\":\"https://juejin.cn/post/7129419242088169485\",\"niceDate\":\"2022-08-09 08:58\",\"niceShareDate\":\"2022-08-09 08:58\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1660006699000,\"realSuperChapterId\":493,\"selfVisible\":0,\"shareDate\":1660006699000,\"shareUser\":\"equationl\",\"superChapterId\":494,\"superChapterName\":\"广场Tab\",\"tags\":[],\"title\":\"使用 compose 的 Canvas 自定义绘制实现 LCD 显示数字效果\",\"type\":0,\"userId\":87590,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"\",\"canEdit\":false,\"chapterId\":502,\"chapterName\":\"自助\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23834,\"link\":\"https://juejin.cn/post/7129306281650683935\",\"niceDate\":\"2022-08-09 00:38\",\"niceShareDate\":\"2022-08-09 00:35\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1659976695000,\"realSuperChapterId\":493,\"selfVisible\":0,\"shareDate\":1659976531000,\"shareUser\":\"鸿洋\",\"superChapterId\":494,\"superChapterName\":\"广场Tab\",\"tags\":[],\"title\":\"裸辞-疫情-闭关-复习-大厂offer（二）\",\"type\":0,\"userId\":2,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"\",\"canEdit\":false,\"chapterId\":502,\"chapterName\":\"自助\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23835,\"link\":\"https://juejin.cn/post/7129239231473385503\",\"niceDate\":\"2022-08-09 00:38\",\"niceShareDate\":\"2022-08-09 00:35\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1659976693000,\"realSuperChapterId\":493,\"selfVisible\":0,\"shareDate\":1659976537000,\"shareUser\":\"鸿洋\",\"superChapterId\":494,\"superChapterName\":\"广场Tab\",\"tags\":[],\"title\":\"Flutter 实现 &ldquo;真&rdquo; 3D 动画效果，用纯代码实现立体 Dash 和 3D 掘金 Logo\",\"type\":0,\"userId\":2,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"\",\"canEdit\":false,\"chapterId\":502,\"chapterName\":\"自助\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23836,\"link\":\"https://juejin.cn/post/7129157665732689934\",\"niceDate\":\"2022-08-09 00:38\",\"niceShareDate\":\"2022-08-09 00:35\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1659976690000,\"realSuperChapterId\":493,\"selfVisible\":0,\"shareDate\":1659976541000,\"shareUser\":\"鸿洋\",\"superChapterId\":494,\"superChapterName\":\"广场Tab\",\"tags\":[],\"title\":\"&ldquo;终于懂了&rdquo; 系列：组件化框架 ARouter 完全解析（二）APT技术\",\"type\":0,\"userId\":2,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"\",\"canEdit\":false,\"chapterId\":502,\"chapterName\":\"自助\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23837,\"link\":\"https://juejin.cn/post/7129063704829804580\",\"niceDate\":\"2022-08-09 00:38\",\"niceShareDate\":\"2022-08-09 00:36\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1659976688000,\"realSuperChapterId\":493,\"selfVisible\":0,\"shareDate\":1659976563000,\"shareUser\":\"鸿洋\",\"superChapterId\":494,\"superChapterName\":\"广场Tab\",\"tags\":[],\"title\":\"音视频开发之旅（66) - 音频变速不变调的原理\",\"type\":0,\"userId\":2,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"\",\"canEdit\":false,\"chapterId\":502,\"chapterName\":\"自助\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23838,\"link\":\"https://juejin.cn/post/7128947531471388709\",\"niceDate\":\"2022-08-09 00:38\",\"niceShareDate\":\"2022-08-09 00:36\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1659976687000,\"realSuperChapterId\":493,\"selfVisible\":0,\"shareDate\":1659976567000,\"shareUser\":\"鸿洋\",\"superChapterId\":494,\"superChapterName\":\"广场Tab\",\"tags\":[],\"title\":\"Android实现倒计时的几种方案汇总\",\"type\":0,\"userId\":2,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"\",\"canEdit\":false,\"chapterId\":502,\"chapterName\":\"自助\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23839,\"link\":\"https://juejin.cn/post/7128779284503592991\",\"niceDate\":\"2022-08-09 00:38\",\"niceShareDate\":\"2022-08-09 00:36\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1659976685000,\"realSuperChapterId\":493,\"selfVisible\":0,\"shareDate\":1659976579000,\"shareUser\":\"鸿洋\",\"superChapterId\":494,\"superChapterName\":\"广场Tab\",\"tags\":[],\"title\":\"软件绘制 &amp; 硬件加速绘制 【DisplayList &amp; RenderNode】\",\"type\":0,\"userId\":2,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"\",\"canEdit\":false,\"chapterId\":502,\"chapterName\":\"自助\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23840,\"link\":\"https://www.jianshu.com/p/22a398d615e3\",\"niceDate\":\"2022-08-09 00:38\",\"niceShareDate\":\"2022-08-09 00:37\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1659976683000,\"realSuperChapterId\":493,\"selfVisible\":0,\"shareDate\":1659976649000,\"shareUser\":\"鸿洋\",\"superChapterId\":494,\"superChapterName\":\"广场Tab\",\"tags\":[],\"title\":\"深入理解Android Runtime\",\"type\":0,\"userId\":2,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"\",\"canEdit\":false,\"chapterId\":502,\"chapterName\":\"自助\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23841,\"link\":\"https://www.jianshu.com/p/92fdc538bde4\",\"niceDate\":\"2022-08-09 00:38\",\"niceShareDate\":\"2022-08-09 00:37\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1659976681000,\"realSuperChapterId\":493,\"selfVisible\":0,\"shareDate\":1659976653000,\"shareUser\":\"鸿洋\",\"superChapterId\":494,\"superChapterName\":\"广场Tab\",\"tags\":[],\"title\":\"Android多用户的一些坑\",\"type\":0,\"userId\":2,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"鸿洋\",\"canEdit\":false,\"chapterId\":408,\"chapterName\":\"鸿洋\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23847,\"link\":\"https://mp.weixin.qq.com/s/ZpoPqFT8PtRiFHVpim8Xgw\",\"niceDate\":\"2022-08-09 00:00\",\"niceShareDate\":\"2天前\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1659974400000,\"realSuperChapterId\":407,\"selfVisible\":0,\"shareDate\":1660057617000,\"shareUser\":\"\",\"superChapterId\":408,\"superChapterName\":\"公众号\",\"tags\":[{\"name\":\"公众号\",\"url\":\"/wxarticle/list/408/1\"}],\"title\":\"Android 技术周刊（第1期）：38篇技术文章\",\"type\":0,\"userId\":-1,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"郭霖\",\"canEdit\":false,\"chapterId\":409,\"chapterName\":\"郭霖\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23848,\"link\":\"https://mp.weixin.qq.com/s/Oxqa-MQi0-woqlB2EEMHZQ\",\"niceDate\":\"2022-08-09 00:00\",\"niceShareDate\":\"2天前\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1659974400000,\"realSuperChapterId\":407,\"selfVisible\":0,\"shareDate\":1660057635000,\"shareUser\":\"\",\"superChapterId\":408,\"superChapterName\":\"公众号\",\"tags\":[{\"name\":\"公众号\",\"url\":\"/wxarticle/list/409/1\"}],\"title\":\"从XML到View显示在屏幕上，都发生了什么？\",\"type\":0,\"userId\":-1,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"\",\"canEdit\":false,\"chapterId\":502,\"chapterName\":\"自助\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23827,\"link\":\"https://mp.weixin.qq.com/s/LWDOaAQIkRnst2GLCAeAsw\",\"niceDate\":\"2022-08-08 08:54\",\"niceShareDate\":\"2022-08-08 08:54\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1659920045000,\"realSuperChapterId\":493,\"selfVisible\":0,\"shareDate\":1659920045000,\"shareUser\":\"JsonChao\",\"superChapterId\":494,\"superChapterName\":\"广场Tab\",\"tags\":[],\"title\":\"三个值得深入思考的 Android 问答分享（第 2 期）\",\"type\":0,\"userId\":611,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"Android群英传\",\"canEdit\":false,\"chapterId\":413,\"chapterName\":\"Android群英传\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":23831,\"link\":\"https://mp.weixin.qq.com/s/HKpmfz1vM7ojhZNHPy-cHg\",\"niceDate\":\"2022-08-08 00:00\",\"niceShareDate\":\"2022-08-08 23:06\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1659888000000,\"realSuperChapterId\":407,\"selfVisible\":0,\"shareDate\":1659971175000,\"shareUser\":\"\",\"superChapterId\":408,\"superChapterName\":\"公众号\",\"tags\":[{\"name\":\"公众号\",\"url\":\"/wxarticle/list/413/1\"}],\"title\":\"Flutter混编工程之打通纹理之路\",\"type\":0,\"userId\":-1,\"visible\":1,\"zan\":0},{\"apkLink\":\"\",\"audit\":1,\"author\":\"RuffianZhong\",\"canEdit\":false,\"chapterId\":539,\"chapterName\":\"未分类\",\"collect\":false,\"courseId\":13,\"desc\":\"垃圾Flutter没有生命周期？\",\"descMd\":\"\",\"envelopePic\":\"https://www.wanandroid.com/resources/image/pc/default_project_img.jpg\",\"fresh\":false,\"host\":\"\",\"id\":23826,\"link\":\"https://www.wanandroid.com/blog/show/3400\",\"niceDate\":\"2022-08-07 22:23\",\"niceShareDate\":\"2022-08-07 22:23\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"https://github.com/RuffianZhong/flutter_lifecycle\",\"publishTime\":1659882235000,\"realSuperChapterId\":293,\"selfVisible\":0,\"shareDate\":1659882235000,\"shareUser\":\"\",\"superChapterId\":294,\"superChapterName\":\"开源项目主Tab\",\"tags\":[{\"name\":\"项目\",\"url\":\"/project/list/1?cid=539\"}],\"title\":\"垃圾Flutter没有生命周期？\",\"type\":0,\"userId\":-1,\"visible\":1,\"zan\":0}],\"offset\":20,\"over\":false,\"pageCount\":643,\"size\":20,\"total\":12844},\"errorCode\":0,\"errorMsg\":\"\"}";
        //String str1 = getWebText("https://www.wanandroid.com/article/list/1/json");
        ArrayList<String> articleList = new ArrayList<String>();


        Article article = gson.fromJson(str1, Article.class);
        List<Article.DataDTO.DatasDTO> datasDTOSList = article.getData().getDatas();
        for (int i = 0; i < datasDTOSList.size(); i++) {
            Article.DataDTO.DatasDTO datasDTO = datasDTOSList.get(i);
            String link = datasDTO.getLink();

            articleList.add(link);

            //Log.d("Aaron","Link==" + link );

        }
        return articleList;
    }
}




