package cn.hohn.atguigu_code.fragment;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import cn.hohn.atguigu_code.R;
import cn.hohn.atguigu_code.base.BaseFragment;

/**
 * 作者：吴红华
 * 网站：www.hohn.cn
 * 微信：qq321988081
 * q q ： 292920487
 * o n ： 2018-03-01.
 */

public class ThirdPartyFragment extends BaseFragment{
    private static final String TAG = ThirdPartyFragment.class.getSimpleName();
    private ImageView imageView;
    @Override
    protected View initView() {
        Log.e(TAG,"第三方页面初始化了");
        //通过父类的上下文Context对象mContext创建ImageView实例
        imageView=new ImageView(mContext);

        return imageView;
    }

    @Override
    protected void initData() {
        Log.e(TAG,"第三方数据初始化了");
        super.initData();
        imageView.setImageResource(R.drawable.ic_tab_audio);
    }
}
