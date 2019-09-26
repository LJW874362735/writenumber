package com.example.writenumber;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Timer;

public class OneActivity extends Activity {
    private ImageView iv_frame;                 //显示书写数字的控件
    int i=1;                                    //图片展示到第几张的标记

    float x1;                                   //屏幕按下时的x值
    float y1;                                   //屏幕按下时的y值

    float x2;                                   //屏幕离开时的x值
    float y2;                                   //屏幕离开时的y值

    float x3;                                   //移动中的坐标x值
    float y3;                                   //移动中的坐标y值

    int igvx;                                   //图片的x坐标
    int igvy;                                   //图片的y坐标

    int type=0;                                 //是否可以书写的标识：1 =开启。，0 = 关闭
    int widthPixels;                            //屏幕的宽度
    int heightPixels;                           //屏幕的高度
    float scaleWight;                           //宽度的伸缩比例
    float scaleHeight;                          //高度的伸缩比例
    Timer touchTimer;                           //单击在虚拟按钮上后用于连续动作的计时器
    Bitmap arrdown;                             //Bitmap图像处理
    boolean typedialog=true;                    //dialog对话框状态
    private LinearLayout linearLayout=null;     //线性布局

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        initView();                             //初始化
    }

    private void initView() {
        
    }


}
