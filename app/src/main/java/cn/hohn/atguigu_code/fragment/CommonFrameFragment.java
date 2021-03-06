package cn.hohn.atguigu_code.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import cn.hohn.atguigu_code.R;
import cn.hohn.atguigu_code.activity.OKHTTPActivity;
import cn.hohn.atguigu_code.adapter.CommonFrameFragmentAdapter;
import cn.hohn.atguigu_code.base.BaseFragment;
import cn.hohn.atguigu_code.xutils3.XUtils3MainActivity;

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
    private ListView mListView;
    private String datas[];
    private TextView textView;
    private CommonFrameFragmentAdapter commonFrameFragmentAdapter;
    @Override
    protected View initView() {
        //输出创建视图日志
        Log.e(TAG,"常用框架页面初始化了");
        View view=View.inflate(mContext, R.layout.fragment_common_frame,null);
        mListView = view.findViewById(R.id.listview);
        //返回view，至此完成一个Fragment的onCreate、onCreateView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(mContext, "data="+datas[i], Toast.LENGTH_SHORT).show();
                //获取当前点击的listview条目
                String item=datas[i];
                //点击的是第一个okhttp
                if(item.trim().toLowerCase().toString().equals("okhttp")){
                    //打开okhttp的页面
                    mContext.startActivity(new Intent(mContext,OKHTTPActivity.class));
                }else if(item.trim().toLowerCase().toString().equals("xutils3")){
                    //打开xutils3的页面XUtils3MainActivity
                    mContext.startActivity(new Intent(mContext,XUtils3MainActivity.class));
                }else {
                    Toast.makeText(mContext, "居然都不匹配", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        Log.e(TAG,"常用框架数据初始化了");
        //设置数据(譬如网络请求的数据)
        //准备数据
        datas=new String[]{"OKHttp", "xUtils3","Retrofit2","Fresco","Glide","greenDao","RxJava","volley","Gson","FastJson","picasso","evenBus","jcvideoplayer","pulltorefresh","Expandablelistview","UniversalVideoView","....."};
        //设置适配器
        commonFrameFragmentAdapter=new CommonFrameFragmentAdapter(mContext,datas);
        mListView.setAdapter(commonFrameFragmentAdapter);
    }
}
