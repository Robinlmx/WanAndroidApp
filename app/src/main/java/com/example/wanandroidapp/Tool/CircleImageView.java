package com.example.wanandroidapp.Tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.wanandroidapp.MainActivity;
import com.example.wanandroidapp.R;

import java.util.ArrayList;

public class CircleImageView extends View {

    private Paint paint;
    MainActivity mainActivity = new MainActivity();

    public CircleImageView(Context context) {

        super(context);

        paint=new Paint();

    }

    public CircleImageView(Context context, AttributeSet attrs) {

        super(context, attrs);

        paint=new Paint();

    }

    @Override

    protected void onDraw(Canvas canvas) {

        /*

         * 方法 说明 drawCircle 绘制圆形 drawRect 绘制矩形 drawOval 绘制椭圆 drawPosText绘制文字 drawPath 绘制任意多边形

         * drawLine 绘制直线 drawPoin 绘制点

         */

        paint.setAntiAlias(true); // 是否抗锯齿

        paint.setAlpha(50); // 设置alpha不透明度，范围为0~255

        paint.setColor(Color.RED);// 给画笔设置颜色

// 设置画笔属性

        paint.setStyle(Paint.Style.FILL);//画笔属性是实心圆

// paint.setStyle(Paint.Style.STROKE);//画笔属性是空心圆

        paint.setStrokeWidth(4);//设置画笔粗细

        //Dcircle(canvas);//drawCircle 绘制圆形

        //Drect(canvas);//drawRect 绘制矩形

        //Doval(canvas);//drawOval 绘制椭圆

        //DposText(canvas);//drawPosText 按照既定点 绘制文本内容

        //Pic(canvas);//绘制图片

    }

// drawCircle 绘制圆形

    public void Dcircle(Canvas canvas){

/*四个参数：

参数一：圆心的x坐标

参数二：圆心的y坐标

参数三：圆的半径

参数四：定义好的画笔

*/

        canvas.drawCircle(60, 60, 50, paint);

    }

//drawRect 绘制矩形

    public void Drect(Canvas canvas){

/*五个参数：

参数一：矩形距离父view左边距离

参数二：矩形距离父view上边距离

参数三：矩形距离父view左边距离

参数四：矩形距离父view上边距离

参数五：定义好的画笔

*/

        canvas.drawRect(120, 10, 220, 110, paint);// drawRect 绘制矩形

    }

//drawOval 绘制椭圆

    public void Doval(Canvas canvas){

/*四个参数：

参数一：矩形距离父view左边距离

参数二：矩形距离父view上边距离

参数三：矩形距离父view左边距离

参数四：矩形距离父view上边距离

*/

//定义一个矩形区域

        RectF oval = new RectF(70, 30, 380, 110);

//矩形区域内切椭圆

        canvas.drawOval(oval, paint);// drawOval 绘制椭圆

    }

//drawPosText绘制文字

    public void DposText(Canvas canvas){
        ArrayList<String> navigationNameList = mainActivity.navigationJsonData("navigationName");
        Log.d("Aaron","测试一下==" + mainActivity.navigationJsonData("navigationName"));
        Log.d("Aaron","测试一下==");
        int x_offset = 65;
        int y_offset = 100;

        for(int i = 0 ; i < 3 ; i++){
            canvas.drawPosText(navigationNameList.get(i), new float[]

                    {10 + i * x_offset,65 + i * y_offset,//第一个字母在坐标10,10

                            65 + i * x_offset,65 + i * y_offset,//第二个字母在坐标20,20

                            120 + i * x_offset,65 + i * y_offset, //....

                            175 + i * x_offset,65 + i * y_offset

                    }, paint);
        }

//        canvas.drawPosText("常用网站", new float[]
//
//                {10,65,//第一个字母在坐标10,10
//
//                        65,65,//第二个字母在坐标20,20
//
//                        120,65, //....
//
//                        175,65
//
//                }, paint);
//
//        canvas.drawPosText("常用网站", new float[]
//
//                {10,165,//第一个字母在坐标10,10
//
//                        65,165,//第二个字母在坐标20,20
//
//                        120,165, //....
//
//                        175,165
//
//                }, paint);

        paint.setTextSize(45);

    }

//绘制图片

    public void Pic(Canvas canvas){

        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_back);

// 将画布坐标系移动到画布中央

        canvas.translate(getWidth()/2,getHeight()/2); // 指定图片绘制区域(左上角的四分之一)

        //Rect src = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight()); // 指定图片在屏幕上显示的区域 图片 >>原矩形

        Rect dst = new Rect(0,0,200,200); // 绘制图片 屏幕 >>目标矩形

        //canvas.drawBitmap(bitmap, src,dst, paint);

// canvas.drawBitmap(bitmap, 50,160, paint);

    }

}
