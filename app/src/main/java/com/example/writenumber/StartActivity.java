package com.example.writenumber;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Timer timer=new Timer();    //创建Timer对象，用于启动界面的的显示时间
        //创建TimerTask对象，用于实现启动界面，倒计时的跳转
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() { //倒计时结束后的回调
                //从启动界面跳转到 游戏主界面
                Intent intent=new Intent(StartActivity.this,MainActivity.class);
                startActivity(intent);
                finish();//关闭当前活动
            }
        };
        timer.schedule(timerTask,2000);//设置2秒后的定时
    }
}
