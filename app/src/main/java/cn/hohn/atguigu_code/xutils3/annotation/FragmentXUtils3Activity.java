package cn.hohn.atguigu_code.xutils3.annotation;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.hohn.atguigu_code.R;
@ContentView(R.layout.activity_fragment_xutils3)
public class FragmentXUtils3Activity extends FragmentActivity {
    @ViewInject(R.id.tv_title)
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_fragment_xutils3);
        x.view().inject(this);
        textView.setText("在Fragment中使用注解");
        //Fragment管理员
        FragmentManager fragmentManager=getFragmentManager();
        //Fragment事务=Fragment管理员开始事务
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_content,new DemoFragment()).commit();
    }
}
