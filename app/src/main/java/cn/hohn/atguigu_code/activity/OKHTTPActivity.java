package cn.hohn.atguigu_code.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.IOException;

import cn.hohn.atguigu_code.MainActivity;
import cn.hohn.atguigu_code.R;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class OKHTTPActivity extends Activity implements View.OnClickListener {
    private static final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "water.mp4";
    File file = new File(path);
    //1android6.0权限问题
    private static final int REQUEST_CODE = 1;
    private boolean isDownloading = false;
    private int downloadProgressing = 0;
    private static final String TAG = OKHTTPActivity.class.getSimpleName();
    private static final int GET = 1;
    private static final int POST = 2;
    private Button btn_get_post;
    private TextView tv_result;
    private ProgressBar progressBar;
    private OkHttpClient client = new OkHttpClient();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET:
                    tv_result.setText((String) msg.obj);
                    break;
                case POST:
                    tv_result.setText((String) msg.obj);
                    break;

            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        Log.e(TAG, path);
        //初始化按钮文本显示
        btn_get_post = (Button) findViewById(R.id.btn_get_post);
        tv_result = (TextView) findViewById(R.id.tv_result);
        //按钮设置点击事件
        btn_get_post.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //okhttp原生get、post请求
            case R.id.btn_get_post:

                //按钮btn_get_post点击执行代码
//                getDataFromGet();
//                getDataFromPost();
//                getDataByOkhttpUtils();
//                postDataByOkhttpUtils();
//                postDataByOkhttpUtilsWith();
//                okhttpUtilsDownload();
                //判断文件是否存在
//                if (file.exists()) {
//
//                    Log.e(TAG, "当前的AlertDialog线程###############" + Thread.currentThread().getName());
//                    //Toast.makeText(this, "存在无需下载", Toast.LENGTH_SHORT).show();
//                    //用户确认存在还要下载
//                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
//                    alert.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            //继续执行，无需操作，关闭窗口
//                            dialogInterface.dismiss();
//                        }
//                    });
//                    alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            return;
//                        }
//                    });
//                    alert.setTitle("提示：");
//                    alert.setMessage("文件已经存在，是否重新下载");
//                    alert.show();
//                }
                //避免重复点击下载，每次都去下载
                if (downloadProgressing > 0) {
                    Log.e(TAG, "当前的downloadProgressing线程###############" + Thread.currentThread().getName());
                    Toast.makeText(this, "下载中...", Toast.LENGTH_SHORT).show();
                    return;
                }
                //②判断是否版本是否大于等于6.0
                if (Build.VERSION.SDK_INT >= 23) {
                    //③判断是否有权限：应用程序权限(状态：未申请、允许、拒绝)
                    if (ContextCompat.checkSelfPermission(OKHTTPActivity.this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(OKHTTPActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            //状态：拒绝
                            Toast.makeText(OKHTTPActivity.this, "请授权", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        } else {
                            //状态：未申请
                            ActivityCompat.requestPermissions(OKHTTPActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
                        }
                    }
                    //状态：允许
                    else {
                        if (okhttpUtilsDownload()) {
                            Toast.makeText(OKHTTPActivity.this, "写入文件成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(OKHTTPActivity.this, "写入文件失败1", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                //android6.0以下
                else {
                    if (okhttpUtilsDownload()) {
                        Toast.makeText(OKHTTPActivity.this, "写入文件成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(OKHTTPActivity.this, "写入文件失败2", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
        }
    }

    //okhttp-Utils下载文件
    public boolean okhttpUtilsDownload() {
        Log.e(TAG, "当前的线程###############" + Thread.currentThread().getName());
        try {
            String url = "http://vfx.mtime.cn/Video/2016/07/24/mp4/160724055620533327_480.mp4";
            OkHttpUtils//
                    .get()//
                    .url(url)//
                    .build()//
                    .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "water.mp4")//
                    {

                        @Override
                        public void onBefore(Request request, int id) {
                        }

                        @Override
                        public void inProgress(float progress, long total, int id) {
                            progressBar.setProgress((int) (100 * progress));
                            //避免重复点击下载
                            downloadProgressing = (int) (progress * 100);
                            Log.e(TAG, "当前的inProgress线程###############" + Thread.currentThread().getName());
                            //Log.e(TAG, "inProgress :" + (int) (100 * progress));
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.e(TAG, "onError :" + e.getMessage());
                        }

                        @Override
                        public void onResponse(File file, int id) {
                            Log.e(TAG, "onResponse :" + file.getAbsolutePath());
                        }

                        @Override
                        public void onAfter(int id) {
                            super.onAfter(id);
                            downloadProgressing = 0;
                            progressBar.setProgress(0);
                            Toast.makeText(OKHTTPActivity.this, "下载完成", Toast.LENGTH_SHORT).show();
                        }
                    });
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    //post请求携带简单数据
    public void postDataByOkhttpUtilsWith() {

        String url = "http://www.hohn.cn/crh123/test/test.php";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("par", "你妹")
                .build()
                .execute(new MyStringCallback());


    }

    //post不携带数据请求
    public void postDataByOkhttpUtils() {
        String url = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";
        OkHttpUtils
                .post()
                .url(url)
                .id(100)
                .build()
                .execute(new MyStringCallback());
        Log.e(TAG, "post请求完成");
    }

    //get请求
    public void getDataByOkhttpUtils() {
        String url = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
            setTitle("loading...");
        }

        @Override
        public void onAfter(int id) {
            setTitle("Sample-okHttp");
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
            tv_result.setText("onError:" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e(TAG, "onResponse：complete");
            tv_result.setText("onResponse:" + response);

            switch (id) {
                case 100:
                    Toast.makeText(OKHTTPActivity.this, "http", Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    Toast.makeText(OKHTTPActivity.this, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            Log.e(TAG, "inProgress:" + progress);
// mProgressBar.setProgress((int) (100 * progress));
        }
    }

    private void getDataFromPost() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                //post请求的第二个参数是json字符串，没有数据提交使用空字符串
                try {
                    String str = post("http://api.m.mtime.cn/PageSubArea/TrailerList.api", "");
                    //获取Message对象
                    Message message = Message.obtain();
                    //标识message
                    message.what = POST;
                    //message消息体是对象
                    message.obj = str;
                    //发送消息
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void getDataFromGet() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String res = get("http://api.m.mtime.cn/PageSubArea/TrailerList.api");
                    Log.e("TAG###########", res);
                    Message message = Message.obtain();
                    message.what = GET;
                    message.obj = res;
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
        return response.body().string();
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

    //用户允许权限后，下次不提示
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //提示用户授权时允许才执行(只执行一次)
            switch (permissions[0]) {
                case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                    if (okhttpUtilsDownload()) {
                        Toast.makeText(this, "写入成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "写入失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Manifest.permission.CALL_PHONE:
                    okhttpUtilsDownload();
                    break;
            }
        } else {
            Toast.makeText(OKHTTPActivity.this, "用户授权拒绝", Toast.LENGTH_SHORT).show();
        }
    }

}
