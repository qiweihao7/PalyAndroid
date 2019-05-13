package com.example.palyandroid.activitys.collectionActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.palyandroid.R;
import com.example.palyandroid.activitys.MainActivity;
import com.example.palyandroid.activitys.otherActivity.LoginActivity;
import com.example.palyandroid.adapters.collectionAdapter.CollectionAdapter;
import com.example.palyandroid.base.BaseActivity;
import com.example.palyandroid.net.Constants;
import com.example.palyandroid.presenters.mainPresenter.MainPresenter;
import com.example.palyandroid.sql.DaoUtils;
import com.example.palyandroid.sql.MyDb;
import com.example.palyandroid.utils.SpUtil;
import com.example.palyandroid.utils.ToastUtil;
import com.example.palyandroid.utils.UIModeUtil;
import com.example.palyandroid.views.mainView.MainView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectionActivity extends BaseActivity<MainPresenter, MainView> implements MainView {

    @BindView(R.id.toolBar_collection)
    Toolbar toolBarCollection;
    @BindView(R.id.rlv_collection)
    RecyclerView rlvCollection;
    @BindView(R.id.toolBar_tv_collection)
    TextView toolBarTvCollection;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.img_coll)
    ImageView imgColl;
    private int type;
    private CollectionAdapter collectionAdapter;
    private int mListenterPosition;
    private List<MyDb> select;

    private int mLongListenerPosition;

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_collection;
    }

    @Override
    protected void initView() {

        // 初始化用户名称
        String userName = initSmall();

        // 初始化SqlView数据
        initSqlView();

        // 初始化ToolBar
        initToolBar(userName);



    }

    @Override
    protected void initListener() {

        //点击进入WebView
        ListenerWebView();

        // 长按删除数据
         //LongListenerRemoveList();

    }

    private void LongListenerRemoveList() {
        collectionAdapter.setOnLongItemClickListener(new CollectionAdapter.OnLongItemClickListener() {
            @Override
            public void OnLongItemClick(int position) {

                mLongListenerPosition = position;

                DaoUtils.getDaoUtils().delete(select.get(mLongListenerPosition).getId());
                select.remove(mLongListenerPosition);
                collectionAdapter.notifyDataSetChanged();
                ToastUtil.showShort("删除成功");
            }
        });
    }

    private void ListenerWebView() {
        collectionAdapter.setOnItemClickListener(new CollectionAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {

                mListenterPosition = position;

                Intent intent = new Intent(CollectionActivity.this, CollectionWebActivity.class);
                //WebView用的数据
                intent.putExtra("link", select.get(mListenterPosition).getUrl());
                intent.putExtra("listTitle", select.get(mListenterPosition).getTitle());
                startActivity(intent);
                ToastUtil.showShort("收藏界面WebView");
            }
        });
    }

    private void initSqlView() {
        select = DaoUtils.getDaoUtils().select();
        rlvCollection.setLayoutManager(new LinearLayoutManager(this));
        collectionAdapter = new CollectionAdapter(this, select);
        rlvCollection.setAdapter(collectionAdapter);
    }

    private String initSmall() {

        //type = (int) SpUtil.getParam(Constants.DAY_NIGHT_FRAGMENT_POS, MainActivity.TYPE_SETTING);

        String userName = (String) SpUtil.getParam(Constants.DATA, "");
        String userImage = (String) SpUtil.getParam(Constants.IMG, "");
        tvUsername.setText(userName);
        if (userImage.equals("")) {
            Glide.with(CollectionActivity.this).load(getResources().getDrawable(R.mipmap.ic_launcher)).into(imgColl);
        }else {
            Glide.with(CollectionActivity.this).load(userImage).into(imgColl);
        }

        /*if (type == 5) {
            UIModeUtil.setActModeUseMode(CollectionActivity.this, AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            UIModeUtil.setActModeUseMode(CollectionActivity.this, AppCompatDelegate.MODE_NIGHT_YES);
        }*/
        return userName;
    }

    private void initToolBar(String userName) {
        toolBarCollection.setTitle("");
        toolBarTvCollection.setText("收藏");
        toolBarCollection.setNavigationIcon(getResources().getDrawable(R.drawable.zjt));
        setSupportActionBar(toolBarCollection);
        toolBarCollection.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (type == 5) {
                    SpUtil.setParam(Constants.DAY_NIGHT_FRAGMENT_POS, 5);
                } else {
                    SpUtil.setParam(Constants.DAY_NIGHT_FRAGMENT_POS, 0);
                }*/
                startActivity(new Intent(CollectionActivity.this, MainActivity.class));
                SpUtil.setParam(Constants.DATA, userName);
                finish();
                ToastUtil.showShort("退出收藏界面");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 1, "退出登陆");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                SpUtil.setParam(Constants.DATA, "");
                SpUtil.setParam(Constants.IMG, "");
                startActivity(new Intent(CollectionActivity.this, MainActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
