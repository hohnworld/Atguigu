package cn.hohn.atguigu_code.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 作者：吴红华
 * 网站：www.hohn.cn
 * 微信：qq321988081
 * q q ： 292920487
 * o n ： 2018-03-02.
 */

public class CommonFrameFragmentAdapter extends BaseAdapter{
    private final Context mContext;
    private final String[] datas;

    public CommonFrameFragmentAdapter(Context context, String[] datas) {
        this.mContext=context;
        this.datas=datas;
    }

    @Override
    public int getCount() {
        return datas.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView textView=new TextView(mContext);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(20);
        textView.setPadding(10,10,0,10);
        textView.setText(datas[i]);
//        final int n=i;
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(mContext, "data="+datas[n], Toast.LENGTH_SHORT).show();
//            }
//        });
        return textView;
    }
}
