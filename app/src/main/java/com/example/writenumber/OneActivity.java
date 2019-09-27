package com.example.writenumber;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

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
    private LinearLayout linearLayout=null;     //线性布局，书写区域

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        initView();                             //初始化
    }

    private void initView() {
        iv_frame=(ImageView)findViewById(R.id.iv_frame);            //显示数字
        linearLayout=(LinearLayout)findViewById(R.id.LinearLayout1);//写数字区域布局
        LinearLayout write_layout=(LinearLayout)findViewById(R.id.LinearLayout_number);//整个书写界面
        write_layout.setBackgroundResource(R.drawable.bg1);
        //获取屏幕宽度
        widthPixels=this.getResources().getDisplayMetrics().widthPixels;
        //获取屏幕高度
        heightPixels=this.getResources().getDisplayMetrics().heightPixels;
        //因为图片资源是按1280*720，如果是其他分辨率，适应屏幕做准备
        scaleWight=((float)widthPixels/720);
        scaleHeight=((float)heightPixels/1280);

        try{
            //通过输入流打开第一张图片
            InputStream inputStream=getResources().getAssets().open("on1_1.png");
            //使用Bitmap解析图片
            arrdown= BitmapFactory.decodeStream(inputStream);
        }catch (IOException e){
            e.printStackTrace();
        }
        //获取布局的宽高信息
        LinearLayout.LayoutParams layoutParams=(LinearLayout.LayoutParams)iv_frame.getLayoutParams();
        //获取图片缩放后的宽度
        layoutParams.weight=(int)(arrdown.getWidth()*scaleWight);
        layoutParams.height=(int)(arrdown.getHeight()*scaleHeight);
        //根据图片缩放后的宽高，设置图片显示区域iv——frame的宽高
        iv_frame.setLayoutParams(layoutParams);
        loadImage(1);//加载第一张图片

        //为书写数字区域的布局添加手势事件
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {    //触摸事件的回调
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:       //手指按下那一刻
                        x1=motionEvent.getX();          //手指按下的坐标
                        y1=motionEvent.getY();
                        igvx=iv_frame.getLeft();        //手指按下图片的坐标
                        igvy=iv_frame.getRight();
                        //当手指按下的坐标大于图片的位置坐标，证明手指按住移动，开启书写
                        if (x1>=igvx && x1<= igvx+(int)(arrdown.getWidth()*scaleWight)
                                     && y1>=igvy & y1 <= igvy+(int)(arrdown.getWidth()*scaleWight)){
                            type=1;//开启书写
                        }else {
                            type=0;
                        }
                        break;

                    case MotionEvent.ACTION_MOVE:        //手指移动时
                        igvx=iv_frame.getLeft();         //获取图片的X坐标
                        igvy=iv_frame.getRight();       //获取图片的Y坐标
                        x2=motionEvent.getX();          //获取手指移动的坐标
                        y2=motionEvent.getY();
                        //根据笔画以及手势做图片的处理，滑到不同的位置加载不同的图片
                        if (type == 1) { 					// 如果书写开启
                            // 如果手指按下的X坐标大于等于图片的X坐标，或者小于等于缩放图片的X坐标时
                            if (x2 >= igvx && x2 <= igvx + (int) (arrdown.getWidth() * scaleWight)) {
                                // 如果当前手指按下的Y坐标小于等于缩放图片的Y坐标，或者大于等于图片的Y坐标时
                                if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 && y2 >= igvy) {
                                    loadImage(1);      		// 调用lodimagep()方法，加载第一张显示图片
                                }
                                // 如果当前手指按下的Y坐标小于等于缩放图片的Y坐标
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 2) {
                                    loadImage(2);            // 调用lodimagep()方法，加载第二张显示图片
                                }
                                // 如果当前手指按下的Y坐标小于等于缩放图片的Y坐标
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 3) {
                                    loadImage(3);            // 调用lodimagep()方法，加载第三张显示图片
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 4) {
                                    loadImage(4);            // 调用lodimagep()方法，加载第四张显示图片
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 5) {
                                    loadImage(5);            // 调用lodimagep()方法，加载第五张显示图片
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 6) {
                                    loadImage(6);            // 调用lodimagep()方法，加载第六张显示图片
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 7) {
                                    loadImage(7);            // 调用lodimagep()方法，加载第七张显示图片
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 8) {
                                    loadImage(8);            // 调用lodimagep()方法，加载第八张显示图片
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 9) {
                                    loadImage(9);            // 调用lodimagep()方法，加载第九张显示图片
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 10) {
                                    loadImage(10);
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 11) {
                                    loadImage(11);
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 12) {
                                    loadImage(12);
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 13) {
                                    loadImage(13);
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 14) {
                                    loadImage(14);
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 15) {
                                    loadImage(15);
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 16) {
                                    loadImage(16);
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 17) {
                                    loadImage(17);
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 18) {
                                    loadImage(18);
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 19) {
                                    loadImage(19);
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 20) {
                                    loadImage(20);
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 21) {
                                    loadImage(21);
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 22) {
                                    loadImage(22);
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 23) {
                                    loadImage(23);
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 24) {
                                    loadImage(24);   //加载最后一张图片时，将在lodimagep()方法中调用书写完成对话框
                                }
                                else {
                                    type = 0;         // 手指离开 设置书写关闭
                                }

                            }
                        }
                        break;

                    case MotionEvent.ACTION_UP:         //手指抬起动作
                        type=0;
                        //当手指离开
                        if (touchTimer!=null){
                            touchTimer.cancel();    //中断计时
                            touchTimer=null;
                        }
                        touchTimer=new Timer();
                        touchTimer.schedule(new TimerTask() {   //开启倒计时
                            @Override
                            public void run() {
                                new Thread(new Runnable() { //开启子线程，异步消息
                                    @Override
                                    public void run() {
                                        Message message=new Message();
                                        message.what=2; //消息为2
                                        mHandler.sendMessage(message);//发送消息
                                    }
                                }).start();
                            }
                        },300,200);
                        break;
                }
                return true;
            }
        });
    }

    public Handler mHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 2:
                    
            }
        }
    };

    //显示书写数字的第一张图片，并且实现手势移动时加载不同的图片
    private synchronized void loadImage(int j){
        i=j;
        if (i<25){
            String name="on1_"+i;       //当前图片的名字
            //获取图片资源id
            int imgid=getResources().getIdentifier(name,"drawable","com.example.writenumber");
            iv_frame.setBackgroundResource(imgid);  //更新图片
            i++;
        }
        if (j==24){     //书写完成
            if (typedialog){
                dialog();           //显示完成对话框
            }
        }
    }

    //书写完成对话框
    protected void dialog() {
        typedialog=false;           //修改对话框状态
        //实例化对话框
        AlertDialog.Builder builder=new AlertDialog.Builder(OneActivity.this);
        builder.setMessage("太棒了!书写完成"); //对话框内容
        builder.setTitle("提示:");
        //设置对话框完成点击事件
        builder.setPositiveButton("完成", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {   //点击PositiveButton按钮的回调
                dialogInterface.dismiss();      //取消对话框
                typedialog=true;                //修改对话框状态
                finish();                       //关闭当前Activity
            }
        });
        //设置对话框另一个按钮的点击事件
        builder.setNegativeButton("再来一次", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {   //点击NegativeButton的回调
                dialogInterface.dismiss();      //取消对话框
                typedialog=true;                //修改对话框状态
                i=1;
                loadImage(i);                   //调用加载第一张图片

            }
        });
        builder.create().show();                //显示对话框
    }

}
