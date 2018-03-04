package cn.hohn.atguigu_code;

import android.app.Application;

import org.xutils.x;

/**
 * 作者：吴红华
 * 网站：www.hohn.cn
 * 微信：qq321988081
 * q q ： 292920487
 * o n ： 2018-03-04.
 */

public class AtguiguApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化xUtils3
        x.Ext.init(this);
        //是否输出debug日志，开启debug会影响性能，发布前注释
        x.Ext.setDebug(true);
    }
}
