package cn.hohn.atguigu_code.xutils3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.hohn.atguigu_code.R;
import cn.hohn.atguigu_code.xutils3.annotation.FragmentXUtils3Activity;

@ContentView(R.layout.activity_xutils3_main)
public class XUtils3MainActivity extends Activity {
    //3注解一个按钮事件(按钮一并初始化了，方法名任意，参数一个View),把R.id.btn_annotation赋值给value
    @Event(value = R.id.btn_annotation)
    private void getMy(View view) {
        startActivity(new Intent(XUtils3MainActivity.this, FragmentXUtils3Activity.class));
    }

    //4注解一组按钮,使用对象包括，逗号分隔，把这个对象赋值个value
    @Event(value ={R.id.btn_net,R.id.btn_image,R.id.btn_image_list})
    private void getEvent(View view){
        switch (view.getId()){
            case R.id.btn_net:

                break;
            case R.id.btn_image:
                Toast.makeText(this, "btn_image", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_image_list:
                Toast.makeText(this, "btn_image_list", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    //2注解一个控件
    @ViewInject(R.id.tv_title)
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_xutils3_main);
        //1注解布局(类定义前添加@ContentView(R.layout.activity_xutils3_main))
        x.view().inject(this);
        textView.setText("通过注解初始化的");
    }


}
