package com.example.palyandroid.activitys.otherActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.example.palyandroid.R;
import com.example.palyandroid.activitys.MainActivity;
import com.example.palyandroid.sql.DaoUtils;
import com.example.palyandroid.sql.MyDb;
import com.example.palyandroid.utils.CircularAnimUtil;
import com.example.palyandroid.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends AppCompatActivity {

    @BindView(R.id.tv_web_project)
    TextView tv;
    @BindView(R.id.toolBar_project)
    Toolbar toolBar;
    @BindView(R.id.web)
    WebView web;
    private String url;
    private String title;
    private String anthor;
    private String all;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_web);
        ButterKnife.bind(this);

        initView();

    }

    private void initView() {

        Intent intent = getIntent();
        url = intent.getStringExtra("link");
        title = intent.getStringExtra("listTitle");
        anthor = intent.getStringExtra("anthor");
        all = intent.getStringExtra("all");
        time = intent.getStringExtra("time");

        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        //获取设置对象
        WebSettings settings = web.getSettings();
        //支持JS代码
        settings.setJavaScriptEnabled(true);
        web.loadUrl(url);

        toolBar.setTitle("");
        toolBar.setNavigationIcon(R.drawable.zjt);

        tv.setText(title);
        setSupportActionBar(toolBar);

        // 监听事件必须写在 setSupportActionBar(toolBar) 后面
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);
                finish();*/
                //Intent intent = new Intent();
                finish();
                //CircularAnimUtil.startActivity(WebActivity.this, intent, toolBar, R.color.org);
                Toast.makeText(WebActivity.this, "退出", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.web_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_collection:
                item.setIcon(R.drawable.follow);
                MyDb myDb = new MyDb(null, url, title, anthor, all, time);
                DaoUtils.getDaoUtils().insert_one(myDb);
                ToastUtil.showShort("收藏成功");
                break;
            case R.id.action_give:
                fenxiang();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fenxiang() {
        //分享文本
        Intent textIntent = new Intent(Intent.ACTION_SEND);
        textIntent.setType("text/plain");
        textIntent.putExtra(Intent.EXTRA_TEXT, url);
        startActivity(Intent.createChooser(textIntent, "分享"));
        //分享图片
/*String path = bean.getEnvelopePic();
Intent imageIntent = new Intent(Intent.ACTION_SEND);
imageIntent.setType("image/png");
imageIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
startActivity(Intent.createChooser(imageIntent, "分享"));*/

    }
}
