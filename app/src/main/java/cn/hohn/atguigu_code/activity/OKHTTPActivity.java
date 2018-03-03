package cn.hohn.atguigu_code.activity;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import cn.hohn.atguigu_code.R;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OKHTTPActivity extends Activity implements View.OnClickListener {
    private static final int GET = 1;
    private static final int POST = 2;
    private Button btn_get_post;
    private TextView tv_result;
    private OkHttpClient client=new OkHttpClient();
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GET:
                    tv_result.setText((String) msg.obj);
                    break;
                case POST:
                    tv_result.setText((String)msg.obj);
                    break;

            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        //初始化按钮文本显示
        btn_get_post = (Button) findViewById(R.id.btn_get_post);
        tv_result=(TextView) findViewById(R.id.tv_result);
        //按钮设置点击事件
        btn_get_post.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            //okhttp原生get、post请求
            case R.id.btn_get_post:
                //按钮btn_get_post点击执行代码
//                getDataFromGet();
                getDataFromPost();
                break;
        }
    }

    private void getDataFromPost() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                //post请求的第二个参数是json字符串，没有数据提交使用空字符串
                try {
                    String str=post("http://api.m.mtime.cn/PageSubArea/TrailerList.api","");
                    //获取Message对象
                    Message message=Message.obtain();
                    //标识message
                    message.what=POST;
                    //message消息体是对象
                    message.obj=str;
                    //发送消息
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void getDataFromGet() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    String res=get("http://api.m.mtime.cn/PageSubArea/TrailerList.api");
                    Log.e("TAG###########",res);
                    Message message=Message.obtain();
                    message.what=GET;
                    message.obj=res;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //OKHTTP的原生get请求方法
    public String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return  response.body().string();
    }
    //OKHTTP的原生post请求方法
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    public String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();

    }
}
