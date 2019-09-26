package com.example.writenumber;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    static boolean isPlay=true;         //设置音乐播放的状态变量
    MediaPlayer mediaPlayer;            //音乐播放对象
    Button music_btn;                   //音乐播放按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取控件
        music_btn=(Button)findViewById(R.id.btn_music);
        PlayMusic();

    }

    //播放音乐背景方法
    private void PlayMusic() {
        mediaPlayer=MediaPlayer.create(this,R.raw.main_music);
        mediaPlayer.setLooping(true);       //循环播放
        mediaPlayer.start();                //播放
    }

    public void OnPlay(View view){//单击事件，进入数字选择界面
        startActivity(new Intent(MainActivity.this,SelectActivity.class));

    }

    public void OnAbout(View view){//单击事件，进入关于选择界面
        startActivity(new Intent(MainActivity.this,AboutActivity.class));

    }

    public void OnMusic(View view){//单击事件，点击按钮，播放/停止音乐
        if (isPlay==true){
            if (mediaPlayer!=null){     //音乐播放器不为空时
                mediaPlayer.stop();     //停止播放
                //更改按钮背景
                music_btn.setBackgroundResource(R.drawable.btn_music2);
                isPlay=false;           //更改播放状态
            }
        }else {
            PlayMusic();
            //更改annuity背景
            music_btn.setBackgroundResource(R.drawable.btn_music1);
            isPlay=true;                //更改播放状态
        }
    }

    //当此界面Activity停止时，停止音乐播放
    @Override
    protected void onStop() {
        super.onStop();
            if (mediaPlayer !=null){
                mediaPlayer.stop();
            }
    }

    //Activity被销毁
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //当前界面被销毁时,释放资源
        if (mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }

    //Activity从新启动，背景音乐从新播放
    @Override
    protected void onRestart() {
        super.onRestart();
        if (isPlay==true){
            PlayMusic();        //播放音乐
        }
    }
}
