package com.example.palyandroid.activitys.collectionActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.palyandroid.activitys.otherActivity.WebActivity;
import com.example.palyandroid.base.BaseActivity;
import com.example.palyandroid.presenters.mainPresenter.MainPresenter;
import com.example.palyandroid.sql.DaoUtils;
import com.example.palyandroid.sql.MyDb;
import com.example.palyandroid.utils.CircularAnimUtil;
import com.example.palyandroid.utils.ToastUtil;
import com.example.palyandroid.views.mainView.MainView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectionWebActivity extends BaseActivity<MainPresenter, MainView> implements MainView {

    @BindView(R.id.tv_web_coll)
    TextView tv;
    @BindView(R.id.toolBar_coll)
    Toolbar toolBar;
    @BindView(R.id.web_coll)
    WebView web;
    private String url;
    private String title;

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_collection_web;
    }


    @Override
    protected void initData() {
        Intent intent = getIntent();
        url = intent.getStringExtra("link");
        title = intent.getStringExtra("listTitle");

    }

    @Override
    protected void initView() {

        initWeb();

        initToolBar();

    }

    @Override
    protected void initListener() {
        ToolBarListener();
    }

    private void ToolBarListener() {
        // 监听事件必须写在 setSupportActionBar(toolBar) 后面
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);
                finish();*/
                Intent intent = new Intent();
                intent.setClass(CollectionWebActivity.this, CollectionActivity.class);
                CircularAnimUtil.startActivity(CollectionWebActivity.this, intent, toolBar, R.color.org);
                Toast.makeText(CollectionWebActivity.this, "退出", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initToolBar() {
        toolBar.setTitle("");
        toolBar.setNavigationIcon(R.drawable.zjt);

        tv.setText(title);
        setSupportActionBar(toolBar);
    }

    private void initWeb() {
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.collection_web_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_collection:
                ToastUtil.showShort("这里取消不了,去外面删除8.");
                break;
            case R.id.action_give:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
