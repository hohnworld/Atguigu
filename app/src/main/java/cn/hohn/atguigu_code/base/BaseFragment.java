package cn.hohn.atguigu_code.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作者：吴红华
 * 网站：www.hohn.cn
 * 微信：qq321988081
 * q q ： 292920487
 * o n ： 2018-03-01.
 *  作用：Fragment基类
 */
//有抽象的方法的类是抽象类
public abstract class BaseFragment extends Fragment{
    protected Context mContext;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }
    //抽象类，子类实现，渲染各自的布局视图
    protected abstract View initView();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }
    //初始化数据
    protected void initData() {
    }
}
