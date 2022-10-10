package com.example.wanandroidapp.login;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wanandroidapp.R;
import com.example.wanandroidapp.Tool.UserRegisteredPost;
import com.example.wanandroidapp.bean.ResponseLogin;
import com.example.wanandroidapp.bean.ResponseRegistered;
import com.google.gson.Gson;

public class RegisteredActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_rgsName, et_rgsEmail, et_rgsPhoneNum, et_rgsPsw1, et_rgsPsw2;
    private DBOpenHelper mDBOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);//禁止横屏
        setContentView(R.layout.activity_registered);
        setTitle("用户注册");//顶部标题改成用户注册

        initView();//初始化界面
        mDBOpenHelper = new DBOpenHelper(this);
    }

    private void initView() {
        et_rgsName = findViewById(R.id.et_rgsName);
        //et_rgsEmail = findViewById(R.id.et_rgsEmail);
        //et_rgsPhoneNum = findViewById(R.id.et_rgsPhoneNum);
        et_rgsPsw1 = findViewById(R.id.et_rgsPsw1);
        et_rgsPsw2 = findViewById(R.id.et_rgsPsw2);

        Button btn_register = findViewById(R.id.btn_rgs);
        ImageView iv_back = findViewById(R.id.iv_back);
        /**
         * 注册页面能点击的就三个地方
         * top处返回箭头、刷新验证码图片、注册按钮
         */
        iv_back.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back://返回登录界面
                Intent intent = new Intent(RegisteredActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_rgs://注册按钮
                //获取用户输入的用户名、密码、验证码
                String username = et_rgsName.getText().toString().trim();
                String password = et_rgsPsw1.getText().toString().trim();
                String repassword = et_rgsPsw2.getText().toString().trim();
                //String email = et_rgsEmail.getText().toString().trim();
                //String phonenum = et_rgsPhoneNum.getText().toString().trim();
                int errorcode;
                String errormsg;
                UserRegisteredPost userRegisteredPost = new UserRegisteredPost();
                Gson gson = new Gson();

                userRegisteredPost.setUrl("https://www.wanandroid.com/user/register");
                userRegisteredPost.setUsername(username);
                userRegisteredPost.setPassword(password);
                userRegisteredPost.setRepassword(repassword);
                userRegisteredPost.start();

                //设置此函数用来确保多线程里的Content收到参数为止
                while (userRegisteredPost.getUser_registered_json() == null){
                    try{
                        Thread.sleep(100);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                String user_registered_json = userRegisteredPost.getUser_registered_json();
                //Log.d("Aaron","json == " + user_login_json);

                ResponseRegistered responseRegistered = gson.fromJson(user_registered_json,ResponseRegistered.class);
                Log.d("Aaron","注册json == " + user_registered_json);
                errorcode = responseRegistered.getErrorCode();
                errormsg = responseRegistered.getErrorMsg();

                Log.d("Aaron","errcode == " + responseRegistered.getErrorCode());
                Log.d("Aaron","errmsg == " + responseRegistered.getErrorMsg());
                //注册验证
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(repassword)) {
                    //判断两次密码是否一致
                    if (errorcode == 0) {
                        //将用户名和密码加入到数据库中
                        //mDBOpenHelper.add(username, password, "email", "phonenum");
                        Intent intent1 = new Intent(RegisteredActivity.this, LoginActivity.class);
                        startActivity(intent1);
                        finish();
                        Toast.makeText(this, "验证通过，注册成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, errormsg, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "注册信息不完善,注册失败", Toast.LENGTH_SHORT).show();
                }
//                //注册验证
//                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password1) && !TextUtils.isEmpty(password2)) {
//                    //判断两次密码是否一致
//                    if (password1.equals(password2)) {
//                        //将用户名和密码加入到数据库中
//                        //mDBOpenHelper.add(username, password2, email, phonenum);
//                        Intent intent1 = new Intent(RegisteredActivity.this, LoginActivity.class);
//                        startActivity(intent1);
//                        finish();
//                        Toast.makeText(this, "验证通过，注册成功", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(this, "两次密码不一致,注册失败", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(this, "注册信息不完善,注册失败", Toast.LENGTH_SHORT).show();
//                }
                break;
        }
    }
}

