package cn.hohn.atguigu_code;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

public class LauncherActivity extends Activity {
    Handler handler;
    public boolean isStartedMainActivity=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //在主线程中执行
                startMainActivity();
                //关闭当前LauncherActivity
                finish();
            }
        },2000);
    }

    //启动MainActivity
    private void startMainActivity() {
        if(!isStartedMainActivity){
            isStartedMainActivity=true;
            startActivity(new Intent(LauncherActivity.this,MainActivity.class));
        }
    }

    //触摸屏幕事件(触摸可能响应多次)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //触摸屏幕输出多次
        Log.i("onTouchEvent",Thread.currentThread().getName());
        startMainActivity();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        //应用销毁handler队列消息也清除：不再执行postDelayed()
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
