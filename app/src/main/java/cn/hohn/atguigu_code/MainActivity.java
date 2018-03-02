package cn.hohn.atguigu_code;

        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentActivity;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentTransaction;
        import android.widget.RadioGroup;

        import java.util.ArrayList;
        import java.util.List;

        import cn.hohn.atguigu_code.base.BaseFragment;
        import cn.hohn.atguigu_code.fragment.CommonFrameFragment;
        import cn.hohn.atguigu_code.fragment.CustomFragment;
        import cn.hohn.atguigu_code.fragment.OtherFragment;
        import cn.hohn.atguigu_code.fragment.ThirdPartyFragment;

/**
 * Created by xuequ on 2018-03-01.
 */

public class MainActivity extends FragmentActivity{
    private RadioGroup radioGroup;
    //Fragment集合(四个Fragment页面)
    private List<BaseFragment> mBaseFragment;
    //RadioGroup选中位置
    private int position;
    //上次的Fragment
    private Fragment mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //1初始化View
        initView();
        //2初始化Fragment
        initFragment();
        //3RadioGroup设置监听：RadioButton切换
        setListener();
    }

    private void setListener() {
        radioGroup.setOnCheckedChangeListener(new MyCheckListener());
        //默认选择第一个RadioButon常用框架
        radioGroup.check(R.id.rb_common_frame);
    }
    //实现RadioButton选中接口内部类
    private class MyCheckListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (i)
            {
                case R.id.rb_common_frame:
                    position=0;
                    break;
                case R.id.rb_thirdparty:
                    position=1;
                    break;
                case R.id.rb_custom:
                    position=2;
                    break;
                case R.id.rb_other:
                    position=3;
                    break;

                default:
                    position=0;
                    break;
            }
            //Fragment替换activity_main中间的相对布局区域R.id.fl_content
            //根据上面的position得到要显示的Fragment
            BaseFragment to=getFragment();
            //to替换现有的mContext
            switchFragment(mContext,to);
        }
    }

    //切换Fragment
//    private void switchFragment(BaseFragment fragment) {
//        //1得到FragmentManager:v4包的FragmentManager
//        FragmentManager fragmentManager=getSupportFragmentManager();
//        //2开启事务
//        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
//        //3替换
//        fragmentTransaction.replace(R.id.fl_content,fragment);
//        //4提交事务
//        fragmentTransaction.commit();
//    }

    //优化切换Fragment
    ///

    private void switchFragment(Fragment from,Fragment to) {
        //from是当前Fragment视图，to是下一个Fragment视图
        //1判断不是同一个Fragment视图
        if(from!=to){
            //不同可以切换,当前替换成下一个视图
            mContext=to;
            //到FragmentManager
            FragmentManager fragmentManager=getSupportFragmentManager();
            //得到事务
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();

            //判断to是否添加过
            if(!to.isAdded()){
                //to没添加
                //from隐藏
                if(from!=null){
                    fragmentTransaction.hide(from);
                }
                //添加to
                if(to!=null){
                    fragmentTransaction.add(R.id.fl_content,to).commit();
                }
            }else{
                //to已添加
                //from隐藏
                if(from!=null){
                    fragmentTransaction.hide(from);
                }
                //to显示
                if(to!=null){
                    fragmentTransaction.show(to).commit();
                }
            }
        }else{
            //相同，不操作
            return;
        }
    }

    //根据position得到
    private BaseFragment getFragment() {
        return mBaseFragment.get(position);
    }

    private void initFragment() {
        mBaseFragment=new ArrayList<>();
        mBaseFragment.add(new CommonFrameFragment());
        mBaseFragment.add(new ThirdPartyFragment());
        mBaseFragment.add(new CustomFragment());
        mBaseFragment.add(new OtherFragment());
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        radioGroup=(RadioGroup)findViewById(R.id.rg_main);
    }
}
