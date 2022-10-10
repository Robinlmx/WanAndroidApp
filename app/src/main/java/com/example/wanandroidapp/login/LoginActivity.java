package com.example.wanandroidapp.login;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wanandroidapp.MainActivity;
import com.example.wanandroidapp.R;
import com.example.wanandroidapp.Tool.DBHelper;
import com.example.wanandroidapp.Tool.UserLoginPost;
import com.example.wanandroidapp.bean.ResponseLogin;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;


/**
 * 此类 implements View.OnClickListener 之后，
 * 就可以把onClick事件写到onCreate()方法之外
 * 这样，onCreate()方法中的代码就不会显得很冗余
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private DBOpenHelper mDBOpenHelper;
    private EditText et_User, et_Psw;
    private CheckBox cb_rmbPsw;
    private String userName;
    private String cookieStr;

    public String getCookieStr() {
        return cookieStr;
    }

    public void setCookieStr(String cookieStr) {
        this.cookieStr = cookieStr;
    }

    private SharedPreferences.Editor editor;

    /**
     * 创建 Activity 时先来重写 onCreate() 方法
     * 保存实例状态
     * super.onCreate(savedInstanceState);
     * 设置视图内容的配置文件
     * setContentView(R.layout.activity_login);
     * 上面这行代码真正实现了把视图层 View 也就是 layout 的内容放到 Activity 中进行显示
     * 初始化视图中的控件对象 initView()
     * 实例化 DBOpenHelper，待会进行登录验证的时候要用来进行数据查询
     * mDBOpenHelper = new DBOpenHelper(this);
     */

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        Objects.requireNonNull(getSupportActionBar()).hide();//隐藏标题栏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);//禁止横屏
        setContentView(R.layout.activity_login);



        initView();//初始化界面
        mDBOpenHelper = new DBOpenHelper(this);

        SharedPreferences sp = getSharedPreferences("user_mes", MODE_PRIVATE);
        editor = sp.edit();
        if(sp.getBoolean("flag",false)){
            String user_read = sp.getString("user","");
            String psw_read = sp.getString("psw","");
            et_User.setText(user_read);
            et_Psw.setText(psw_read);
        }


    }

    private void initView() {
        //初始化控件
        et_User = findViewById(R.id.et_User);
        et_Psw = findViewById(R.id.et_Psw);
        cb_rmbPsw = findViewById(R.id.cb_rmbPsw);
        Button btn_Login = findViewById(R.id.btn_Login);
        TextView tv_register = findViewById(R.id.tv_Register);
        et_User.setText("zhou7433");
        et_Psw.setText("123456");
        //设置点击事件监听器
        btn_Login.setOnClickListener(this);
        tv_register.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_Register: //注册
                Intent intent = new Intent(LoginActivity.this, RegisteredActivity.class);//跳转到注册界面
                startActivity(intent);
                finish();
                break;
            /**
             * 登录验证：
             *
             * 从EditText的对象上获取文本编辑框输入的数据，并把左右两边的空格去掉
             *  String name = mEtLoginactivityUsername.getText().toString().trim();
             *  String password = mEtLoginactivityPassword.getText().toString().trim();
             *  进行匹配验证,先判断一下用户名密码是否为空，
             *  if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password))
             *  再进而for循环判断是否与数据库中的数据相匹配
             *  if (name.equals(user.getName()) && password.equals(user.getPassword()))
             *  一旦匹配，立即将match = true；break；
             *  否则 一直匹配到结束 match = false；
             *
             *  登录成功之后，进行页面跳转：
             *
             *  Intent intent = new Intent(this, MainActivity.class);
             *  startActivity(intent);
             *  finish();//销毁此Activity
             */
            case R.id.btn_Login:
                String name = et_User.getText().toString().trim();
                String password = et_Psw.getText().toString().trim();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                    ArrayList<User> data = mDBOpenHelper.getAllData();
                    boolean match = false;
                    boolean match2 = false;
                    for (int i = 0; i < data.size(); i++) {
                        User user = data.get(i);
                        if ((name.equals(user.getName()) && password.equals(user.getPassword()))||
                                (name.equals(user.getEmail())&&password.equals(user.getPassword()))||
                                (name.equals(user.getPhonenum())&&password.equals(user.getPassword()))) {
                            userName = user.getName();
                            match = true;
                            if(cb_rmbPsw.isChecked()){
                                editor.putBoolean("flag",true);
                                editor.putString("user",user.getName());
                                editor.putString("psw",user.getPassword());
                                Log.d("Aaron","user.getName == " + user.getName());
                                Log.d("Aaron","user.getPassword == " + user.getPassword());
                                editor.apply();
                                match2 = true;
                            }else {
                                editor.putString("user",user.getName());
                                editor.putString("psw","");
//                                editor.clear();
                                editor.apply();
                                match2 = false;
                            }
                            break;
                        } else {
                            match = false;
                        }
                    }

                    UserLoginPost userLoginPost = new UserLoginPost();
                    userLoginPost.setUrl("https://www.wanandroid.com/user/login");
                    userLoginPost.setUsername(name);
                    userLoginPost.setPassword(password);
                    userLoginPost.start();

                    //设置此函数用来确保多线程里的Content收到参数为止
                    while (userLoginPost.getUserlogin_json() == null){
                        try{
                            Thread.sleep(100);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    while (userLoginPost.getCookiestr() == null){
                        try{
                            Thread.sleep(100);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    DBHelper dbHelper = new DBHelper(this, "cookies.db", null, 1);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.delete("cookies","u_id = 1",null);
                    ContentValues cValue = new ContentValues();
                    cValue.put("u_id",1);
                    cValue.put("cookie",userLoginPost.getCookiestr());
                    db.insert("cookies",null ,cValue);
                    db.close();

                    Gson gson = new Gson();
                    String user_login_json = userLoginPost.getUserlogin_json();
                    //Log.d("Aaron","json == " + user_login_json);
                    ResponseLogin responseLogin = gson.fromJson(user_login_json,ResponseLogin.class);
                    responseLogin.getErrorCode();
                    //Log.d("Aaron","cookiestr == " + userLoginPost.getCookiestr());
                    //Log.d("Aaron","cookiestr == " + userLoginPost.getCookiestr());
                    //Bundle bundle = new Bundle();
                    //this.cookieStr = userLoginPost.getCookiestr();
                    //Log.d("Aaron","在Login里面---cookiestr == " + userLoginPost.getCookiestr());
                    //Log.d("Aaron","userLoginPost.getDatalist().get(0) == " + userLoginPost.getDatalist().get(0));
//                    String user_login_json = userLoginPost.getDatalist().get(0);
//                    Log.d("Aaron","json == " + user_login_json);
//                    //Log.d("Aaron","json == " + user_login_json);
//                    ResponseLogin responseLogin = gson.fromJson(user_login_json,ResponseLogin.class);
//                    responseLogin.getErrorCode();


//                    Log.d("Aaron","name == " + name);
//                    Log.d("Aaron","password == " + password);
//                    Log.d("Aaron","ErrorCode == " + responseLogin.getErrorCode());

                    if (responseLogin.getErrorCode() == 0) {
                        if(match2){
                            Toast.makeText(this, "成功记住密码", Toast.LENGTH_SHORT).show();
                            //Log.d("Aaron","记住密码");
                            cb_rmbPsw.setChecked(true);
                        }
                        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();

                        Runnable target;
                        //用线程启动

                        Thread thread = new Thread(){
                            @Override
                            public void run(){
                                try {
                                    sleep(100);//0.1秒 模拟登录时间
                                    String user_name = userName;
                                    Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);//设置自己跳转到成功的界面

                                    //intent1.putExtra("bundle",bundle);

                                    startActivity(intent1);
                                    finish();
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        };
                        thread.start();//打开线程
                    } else {
                        Toast.makeText(this, "用户名或密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "请输入你的用户名或密码", Toast.LENGTH_SHORT).show();
                }
//                    if (match) {
//                        if(match2){
//                            Toast.makeText(this, "成功记住密码", Toast.LENGTH_SHORT).show();
//                            cb_rmbPsw.setChecked(true);
//                        }
//                        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
//                        Runnable target;
//                        //用线程启动
//                        Thread thread = new Thread(){
//                            @Override
//                            public void run(){
//                                try {
//                                    sleep(2000);//2秒 模拟登录时间
//                                    String user_name = userName;
//                                    Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);//设置自己跳转到成功的界面
//
//                                    //intent1.putExtra("user_name",user_name);
//                                    startActivity(intent1);
//                                    finish();
//                                }catch (Exception e){
//                                    e.printStackTrace();
//                                }
//                            }
//                        };
//                        thread.start();//打开线程
//                    } else {
//                        Toast.makeText(this, "用户名或密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(this, "请输入你的用户名或密码", Toast.LENGTH_SHORT).show();
//                }
                break;
        }
    }
}

