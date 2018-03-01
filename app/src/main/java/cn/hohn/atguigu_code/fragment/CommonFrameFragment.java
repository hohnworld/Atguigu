package cn.hohn.atguigu_code.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import cn.hohn.atguigu_code.base.BaseFragment;

/**
 * 作者：吴红华
 * 网站：www.hohn.cn
 * 微信：qq321988081
 * q q ： 292920487
 * o n ： 2018-03-01.
 * 作用： 初始化第一个页面CommonFrameFragment及其数据
 */

public class CommonFrameFragment extends BaseFragment{
    //得到CommonFrameFragment(跟随修改的类名)
    private static final String TAG = CommonFrameFragment.class.getSimpleName();
    private TextView textView;
    @Override
    protected View initView() {
        //输出创建视图日志
        Log.e(TAG,"常用框架页面初始化了");
        //使用父类的上下文mContext创建简单的TextView视图
        textView=new TextView(mContext);
        //设置textView属性
        textView.setTextSize(20);
        textView.setTextColor(Color.RED);
        //返回textView，至此完成一个Fragment的onCreate、onCreateView
        return textView;
    }

    @Override
    protected void initData() {
        super.initData();
        Log.e(TAG,"常用框架数据初始化了");
        //设置数据(譬如网络请求的数据)
        textView.setText("这是常用框架页面");
    }
}
