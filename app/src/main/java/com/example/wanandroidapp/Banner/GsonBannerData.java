package com.example.wanandroidapp.Banner;


import com.example.wanandroidapp.Thread.GetNetDataThread;
import com.example.wanandroidapp.bean.BannerData;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class GsonBannerData {
    //轮播图的数据
    public ArrayList<String> parseBannerData() {     //設置一个str用來返回不同的值

        Gson gson = new Gson();

        GetNetDataThread getNetDataThread = new GetNetDataThread();
        getNetDataThread.setUrl("https://www.wanandroid.com/banner/json");
        getNetDataThread.start();
        //设置此函数用来确保多线程里的Content收到参数为止
        while (getNetDataThread.getContent() == null){
            try{
                Thread.sleep(100);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        String bannerJson = getNetDataThread.getContent();

        ArrayList<String> imagePathList = new ArrayList<String>();
        ArrayList<String> urlList = new ArrayList<String>();

        BannerData bannerData = gson.fromJson(bannerJson,BannerData.class);
        //List<Article.DataDTO.DatasDTO> datasDTOSList = article.getData().getDatas();
        List<BannerData.DataDTO> datasDTOSList = bannerData.getData();

        for (int i = 0; i < datasDTOSList.size(); i++) {
            BannerData.DataDTO dataDTO = datasDTOSList.get(i);
            String imagePath = dataDTO.getImagePath();
            String url = dataDTO.getUrl();
            imagePathList.add(imagePath);
            urlList.add(url);
            //Log.d("Aaron","Title==" + title );

        }

        return imagePathList;

    }

}
