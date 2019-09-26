package com.example.writenumber;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SelectActivity extends Activity {

    MediaPlayer mediaPlayer;        //定义音乐播放器对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        if (MainActivity.isPlay==true){     //不是静音模式下
            playMusic();

        }
    }

    private void playMusic(){       //播放音乐
        //实例化此音乐资源R.raw.number_music的播放器
        mediaPlayer=MediaPlayer.create(this,R.raw.number_music);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    //此activity生命停止时，停止音乐
    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer!=null){
            mediaPlayer.stop();
        }
    }

    //此Activity被销毁时，清空资源
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }

    //此Activity重新被激活
    @Override
    protected void onRestart() {
        super.onRestart();
        if (MainActivity.isPlay==true){ //不是静音模式下，才可以自动播放音乐
            playMusic();

        }
    }

    /*各个图片的点击事件 */
    public void OnOne(View view){
        //当前界面跳转到数字1的书写界面
        startActivity(new Intent(SelectActivity.this,OneActivity.class));
    }

}
