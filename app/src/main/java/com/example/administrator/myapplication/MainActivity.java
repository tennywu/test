package com.example.administrator.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.bean.FirstEvent;
import com.example.administrator.myapplication.bean.User;
import com.orhanobut.logger.AndroidLogTool;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.hello_text)
    TextView helloText;
    @Bind(R.id.test_image)
    ImageView textImage;
    @Bind(R.id.hi_text)
    TextView hiTest;
    @Bind(R.id.jumpBtn)Button jumpBtn;
    @Bind(R.id.event_text) TextView eventText;
    @Bind(R.id.toGreendaoBtn) Button toGreendaoBtn;
    @Bind(R.id.toRefreshBtn) Button toRefreshBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //注解ButterKnife
        ButterKnife.bind(this);
        //日志Logger
        Logger
                .init()                 // default PRETTYLOGGER or use just init()
                .methodCount(3)                 // default 2
                .hideThreadInfo()               // default shown
                .methodOffset(2)                // default 0
                .logTool(new AndroidLogTool()); // custom log tool, optional

        //响应式函数编程rxjava
        Observable.just("美刀")
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s + "牛刀";
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        helloText.setText(s);
                    }
                });
//        helloText.setText("牛刀");
        Logger.e("hello");

        //图片加载Picasso
        Picasso.with(this)
                .load("http://p4.so.qhimg.com/t01fcee4bf056436009.jpg")
                .into(textImage);
//        网络请求okhttp
//        TestOkHttp();
        //网络请求retrofit+okhttp
//        TestRetrofit();
        //测试EventBus消息传递
        EventBus.getDefault().register(this);
        jumpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
        //跳转GreenDaoActivity
        toGreendaoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GreendaoActivity.class);
                startActivity(intent);
            }
        });
        //跳转RefreshActivity
        toRefreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RefreshActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onEventMainThread(FirstEvent event){
        String msg = "onEventMainThread收到了消息：" + event.getMsg();
        eventText.setText(msg);
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    public void TestOkHttp() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://api.ontech.com.cn/home/banner_list").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String res = response.body().string();
                helloText.setText(res);
            }
        });
    }

    public interface APIService {

        @GET("banner_list")
        Call<Void> getBanner();
    }

//    public void TestRetrofit() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://api.ontech.com.cn/home/")
//                .build();
//
//        APIService service = retrofit.create(APIService.class);
//        Call<Void> call = service.getBanner();
//        call.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(retrofit2.Response<Void> response) {
//                String res = response.body();
//                helloText.setText(res);
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//
//            }
//        });
//    }

}
