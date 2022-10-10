package com.example.wanandroidapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wanandroidapp.Tool.DBHelper;
import com.example.wanandroidapp.Tool.GetPostData;
import com.google.gson.Gson;


public class UserInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        //getSupportActionBar().hide();//隐藏标题栏
        setContentView(R.layout.activity_userinfo);
        //Log.d("Aaron","个人信息数据是==+++" + userInfoJsonData());
        ImageView imageView = (ImageView)findViewById(R.id.iv_userinfo_back); //创建Button控件
        imageView.setOnClickListener(new View.OnClickListener() {  //给Button注册单击事件监听器
            @Override
            public void onClick(View v) {  //重写监听器中的onClick()方法
                //do some thing
                Intent it = new Intent(UserInfoActivity.this,MainActivity.class);
                startActivity(it);
                //Log.d("Aaron","点击");
                finish();
            }
        });

    }

    //获取个人信息数据
//    public String userInfoJsonData() {
//        Gson gson = new Gson();
//
//        //ArrayList<String> userInfoList = new ArrayList<String>();
//
//        GetPostData userInfoGet = new GetPostData();
//        DBHelper dbHelper = new DBHelper(this, "cookies.db", null, 1);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        Cursor cursor = db.query("cookies", null, null, null, null, null, null);
//
//        // 将游标移到开头
//        cursor.moveToFirst();
//
//        while (!cursor.isAfterLast()) { // 游标只要不是在最后一行之后，就一直循环
//
//            int id=cursor.getInt(0);
//            //Log.d("Aaron","id==" + id);
//            String cookie=cursor.getString(1);
//
//            userInfoGet.setCookie(cookie);
//            //Log.d("Aaron","cookie==" + cookie);
//            // 将游标移到下一行
//            cursor.moveToNext();
//
//        }
//        userInfoGet.setUrl("https://wanandroid.com//user/lg/userinfo/json");
//        userInfoGet.start();
//        while (userInfoGet.getContent() == null) {
//            try {
//                Thread.sleep(100);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        db.close();
//
//
//        //Log.d("Aaron", "数据是==" + userInfoGet.getContent());
//        return userInfoGet.getContent();
//    }

//    public void onClick(View v) {
//        switch (v.getId()) {
//
//            case R.id.iv_userinfo_back:
//                Intent it = new Intent(UserInfoActivity.this,MainActivity.class);
//                startActivity(it);
//                Log.d("Aaron","点击");
//                finish();
//                break;
//        }
//
//    }


}

