package com.example.palyandroid.activitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.palyandroid.R;
import com.example.palyandroid.activitys.otherActivity.AboutActivity;
import com.example.palyandroid.activitys.collectionActivity.CollectionActivity;
import com.example.palyandroid.activitys.otherActivity.GoNetActivity;
import com.example.palyandroid.activitys.otherActivity.LoginActivity;
import com.example.palyandroid.activitys.otherActivity.SerachActivity;
import com.example.palyandroid.activitys.otherActivity.WebActivity;
import com.example.palyandroid.adapters.otherAdapter.RlvAdapterPop;
import com.example.palyandroid.base.BaseActivity;
import com.example.palyandroid.fragments.homeFragments.HomeFragment;
import com.example.palyandroid.fragments.knowLagerFragments.KnowLagerFragment;
import com.example.palyandroid.fragments.projectfragments.ProjectFragment;
import com.example.palyandroid.fragments.safariFragments.SafariFragment;
import com.example.palyandroid.fragments.settingFragments.SettingFragment;
import com.example.palyandroid.fragments.wechatFragments.WeChatFragment;
import com.example.palyandroid.net.Constants;
import com.example.palyandroid.presenters.mainPresenter.MainPresenter;
import com.example.palyandroid.utils.CircularAnimUtil;
import com.example.palyandroid.utils.FlowLayout;
import com.example.palyandroid.utils.SpUtil;
import com.example.palyandroid.utils.ToastUtil;
import com.example.palyandroid.views.mainView.MainView;

import java.util.ArrayList;
import java.util.Random;

import javax.microedition.khronos.opengles.GL;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter, MainView> implements MainView {

    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.nv)
    NavigationView nv;
    @BindView(R.id.dl)
    DrawerLayout dl;
    @BindView(R.id.toolBar_tv)
    TextView toolBarTv;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private ArrayList<Fragment> mFragments;
    private ArrayList<String> mTitles;
    private FragmentManager mManager;
    private int mLastFragmentPosition;
    private final int TYPE_WanAndroid = 0;
    private final int TYPE_KNOWLAGER = 1;
    private final int TYPE_WECHAT = 2;
    private final int TYPE_SAFARI = 3;
    private final int TYPE_PROJECT = 4;
    //    private final int TYPE_COLLECTION = 5;
    public static final int TYPE_SETTING = 5;
    private ImageView tabImg;
    private TextView tabTv;
    private boolean isBottom;
    private int sptype;
    private boolean noImag;
    private String loginIma;
    private ImageView heardIma;

    public TabLayout getTabLayout() {
        return tabLayout;
    }

    public FloatingActionButton getFab() {
        return fab;
    }

    private ArrayList<String> mList;
    private ArrayList<String> mTitless;
    private RlvAdapterPop mAdapterPop;
    private String loginName;

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    // 视图布局
    @Override
    protected void initView() {

        mManager = getSupportFragmentManager();

        //初始化ToolBar
        initToolBar();
        // 初始化Fragment
        initFragments();
        // 初始化文字
        initTitles();
        // 默认显示一个界面
        LinxFragment();
        //初始化Tab栏
        initTabLayou();
        // 只显示字体
       /* tabLayout.addTab(tabLayout.newTab().setText(mTitles.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(mTitles.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(mTitles.get(2)));
        tabLayout.addTab(tabLayout.newTab().setText(mTitles.get(3)));
        tabLayout.addTab(tabLayout.newTab().setText(mTitles.get(4)));*/

    }

    private void initTabLayou() {
        // 显示字体加图片 --- Tab栏动态添加
        tabLayout.addTab(tabLayout.newTab().setCustomView(getlayoutTitle(TYPE_WanAndroid)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(getlayoutTitle(TYPE_KNOWLAGER)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(getlayoutTitle(TYPE_WECHAT)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(getlayoutTitle(TYPE_SAFARI)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(getlayoutTitle(TYPE_PROJECT)));
    }

    private void initToolBar() {

        toolBar.setTitle("");
        toolBarTv.setTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolBar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, dl, toolBar, R.string.Safari, R.string.Safari) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //获取mDrawerLayout中的第一个子布局，也就是布局中的RelativeLayout
                //获取抽屉的view
                View mContent = dl.getChildAt(0);
                float scale = 1 - slideOffset;
                float endScale = 0.8f + scale * 0.2f;
                float startScale = 1 - 0.3f * scale;

                //设置左边菜单滑动后的占据屏幕大小
                drawerView.setScaleX(startScale);
                drawerView.setScaleY(startScale);
                //设置菜单透明度
                drawerView.setAlpha(0.6f + 0.4f * (1 - scale));

                //设置内容界面水平和垂直方向偏转量
                //在滑动时内容界面的宽度为 屏幕宽度减去菜单界面所占宽度
                mContent.setTranslationX(drawerView.getMeasuredWidth() * (1 - scale));
                //设置内容界面操作无效（比如有button就会点击无效）
                mContent.invalidate();
                //设置右边菜单滑动后的占据屏幕大小
                mContent.setScaleX(endScale);
                mContent.setScaleY(endScale);
            }
        };
        toggle.syncState();
        dl.addDrawerListener(toggle);

       /* //设置挤压界面  视图效果
        dl.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                ll.setX(nv.getWidth() * slideOffset);
            }
        });*/
    }

    private View getlayoutTitle(int i) {

        View inflate = LayoutInflater.from(this).inflate(R.layout.item, null, false);
        tabImg = inflate.findViewById(R.id.iv);
        tabTv = inflate.findViewById(R.id.tv);

        // private final int TYPE_WanAndroid = 0;
        // private final int TYPE_KNOWLAGER = 1;
        // private final int TYPE_WECHAT = 2;
        // private final int TYPE_SAFARI = 3;
        // private final int TYPE_PROJECT = 4;

        if (i == TYPE_WanAndroid) {
            tabImg.setBackground(getResources().getDrawable(R.drawable.select_home));
            tabTv.setText(mTitles.get(0));
        } else if (i == TYPE_KNOWLAGER) {
            tabImg.setBackground(getResources().getDrawable(R.drawable.select_knowlager));
            tabTv.setText(mTitles.get(1));
        } else if (i == TYPE_WECHAT) {
            tabImg.setBackground(getResources().getDrawable(R.drawable.select_wechat));
            tabTv.setText(mTitles.get(2));
        } else if (i == TYPE_SAFARI) {
            tabImg.setBackground(getResources().getDrawable(R.drawable.select_safari));
            tabTv.setText(mTitles.get(3));
        } else if (i == TYPE_PROJECT) {
            tabImg.setBackground(getResources().getDrawable(R.drawable.select_project));
            tabTv.setText(mTitles.get(4));
        }

        return inflate;
    }

    // 所有的点击事件
    @Override
    protected void initListener() {

        // 抽屉的点击事件
        NavigationListener();

        // Tab栏点击事件
        TabLayouListener();

        /***
         * -----设置屏幕最小高度-----
         * DisplayMetrics displayMetrics = new DisplayMetrics();
         getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
         int screenHeight = displayMetrics.heightPixels;
         int actionBarHeight = 0;
         TypedValue typedValue = new TypedValue();
         if (getTheme().resolveAttribute(android.R.attr.actionBarSize, typedValue, true)) {
         actionBarHeight = TypedValue.complexToDimensionPixelSize(typedValue.data,getResources().getDisplayMetrics());
         }
         fragmentContainer.setMinimumHeight(screenHeight - actionBarHeight);
         *
         */


       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fragmentContainer.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    ToastUtil.showShort("正在滑动");

                    // 上滑 并且 正在显示底部栏
                    if (scrollY - oldScrollY > 0 && isBottom) {
                        isBottom = false;
                        //将Y属性变为底部栏高度  (相当于隐藏了)
                        tabLayout.animate().translationY(tabLayout.getHeight());
                    } else if (scrollY - oldScrollY < 0 && !isBottom) {
                        isBottom = true;
                        tabLayout.animate().translationY(0);
                    }
                }
            });
        }*/

    }

    // 初始化名字
    private void initTitles() {
        mTitles = new ArrayList<>();
        mTitles.add((String) getResources().getText(R.string.home));
        mTitles.add((String) getResources().getText(R.string.knowLager));
        mTitles.add((String) getResources().getText(R.string.WeChat));
        mTitles.add((String) getResources().getText(R.string.Safari));
        mTitles.add((String) getResources().getText(R.string.Project));
    }

    // 初始化Fragment
    private void initFragments() {
        mFragments = new ArrayList<>();
        mFragments.add(new HomeFragment()); // 0
        mFragments.add(new KnowLagerFragment()); // 1
        mFragments.add(new WeChatFragment()); // 2
        mFragments.add(new SafariFragment()); // 3
        mFragments.add(new ProjectFragment()); // 4
        mFragments.add(new SettingFragment()); // 5
    }


    // 默认显示哪个界面
    private void LinxFragment() {


        sptype = (int) SpUtil.getParam(Constants.DAY_NIGHT_FRAGMENT_POS, TYPE_SETTING);

        if (sptype == TYPE_SETTING) {
            tabLayout.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
            toolBarTv.setText(getResources().getString(R.string.setting));
            FragmentTransaction transaction = mManager.beginTransaction();
            transaction.add(R.id.fragment_container, mFragments.get(5));
            transaction.commit();
        }

        FragmentTransaction transaction = mManager.beginTransaction();
        transaction.add(R.id.fragment_container, mFragments.get(0));
        transaction.commit();
        tabLayout.setVisibility(View.VISIBLE);
        fab.setVisibility(View.VISIBLE);
        toolBarTv.setText(getResources().getString(R.string.home));


    }

    //设置点击后的toolBar
    private void switchToolBar(int position) {

        // private final int TYPE_WanAndroid = 0;
        // private final int TYPE_KNOWLAGER = 1;
        // private final int TYPE_WECHAT = 2;
        // private final int TYPE_SAFARI = 3;
        // private final int TYPE_PROJECT = 4;
        if (position == TYPE_WanAndroid) {
            toolBarTv.setText(getResources().getString(R.string.home));
        } else if (position == TYPE_KNOWLAGER) {
            toolBarTv.setText(getResources().getString(R.string.knowLager));
        } else if (position == TYPE_WECHAT) {
            toolBarTv.setText(getResources().getString(R.string.WeChat));
            fab.setVisibility(View.GONE);
        } else if (position == TYPE_SAFARI) {
            toolBarTv.setText(getResources().getString(R.string.Safari));
        } else if (position == TYPE_PROJECT) {
            fab.setVisibility(View.GONE);
            toolBarTv.setText(getResources().getString(R.string.Project));
        }
    }

    // 点击选择显示某一个界面 默认显示Fragment
    private void switchFragment(int type) {

        //显示一个fragmnet,隐藏一个Fragment
        Fragment fragment = mFragments.get(type);

        //需要隐藏
        Fragment hideFragment = mFragments.get(mLastFragmentPosition);
        FragmentTransaction transaction = mManager.beginTransaction();
        if (!fragment.isAdded()) {
            transaction.add(R.id.fragment_container, fragment);
        }

        transaction.hide(hideFragment);
        transaction.show(fragment);
        transaction.commit();

        mLastFragmentPosition = type;

    }

    // Tab栏点击事件 显示Fragment
    private void TabLayouListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switchFragment(position);
                switchToolBar(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    // 抽屉的点击事件 显示Fragment
    private void NavigationListener() {

        TextView heardName = nv.getHeaderView(0).findViewById(R.id.textView);
        heardIma = nv.getHeaderView(0).findViewById(R.id.header_img);

        loginName = (String) SpUtil.getParam(Constants.DATA, "");
        loginIma = (String) SpUtil.getParam(Constants.IMG, "");

        if (!loginName.equals("") && !loginIma.equals("")) {
            heardName.setText(loginName);
            Glide.with(MainActivity.this).load(loginIma).into(heardIma);
            heardName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, CollectionActivity.class);
                    CircularAnimUtil.startActivity(MainActivity.this, intent, nv, R.color.green);
                    ToastUtil.showShort("收藏界面");
                }
            });
        }else {
            heardName.setText("未登录");
            heardName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    Glide.with(MainActivity.this).load(getResources().getDrawable(R.drawable.logo )).into(heardIma);
                    intent.setClass(MainActivity.this, LoginActivity.class);
                    CircularAnimUtil.startActivity(MainActivity.this, intent, nv, R.color.bule);
                    ToastUtil.showShort("登陆界面");
                }
            });
        }

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                item.setChecked(true);
                switch (itemId) {
                    case R.id.wanandroid:
                        switchFragment(TYPE_WanAndroid);
                        tabLayout.setVisibility(View.VISIBLE);
                        fab.setVisibility(View.VISIBLE);
                        toolBarTv.setText(getResources().getString(R.string.home));
                        break;
                    case R.id.collection:
                        String userName = (String) SpUtil.getParam(Constants.DATA, "");
                       /* if (sptype == TYPE_SETTING) {
                            sptype = 5;
                            SpUtil.setParam(Constants.DAY_NIGHT_FRAGMENT_POS, sptype);
                        } else {
                            sptype = 0;
                            SpUtil.setParam(Constants.DAY_NIGHT_FRAGMENT_POS, sptype);
                        }*/
                        if (!userName.equals("")) {
                            //SpUtil.setParam(Constants.DAY_NIGHT_FRAGMENT_POS,sptype);
                            SpUtil.setParam(Constants.DATA,userName);
                            startActivity(new Intent(MainActivity.this, CollectionActivity.class));
                            ToastUtil.showShort("收藏界面");
                            finish();
                        }else {
                            Intent intent = new Intent();
                            //SpUtil.setParam(Constants.DAY_NIGHT_FRAGMENT_POS,sptype);
                            intent.setClass(MainActivity.this, LoginActivity.class);
                            CircularAnimUtil.startActivity(MainActivity.this, intent, nv, R.color.black);
                            ToastUtil.showShort("WanAndroid:请先登录~~");
                        }
                        break;
                    case R.id.setting:
                        switchFragment(TYPE_SETTING);
                        tabLayout.setVisibility(View.GONE);
                        fab.setVisibility(View.GONE);
                        toolBarTv.setText(getResources().getString(R.string.setting));
                        break;
                    case R.id.about:
                       /* if (sptype == TYPE_SETTING) {
                            Log.e("----sptype----", "onNavigationItemSelected: " + sptype);
                            sptype = 5;
                            SpUtil.setParam(Constants.DAY_NIGHT_FRAGMENT_POS, sptype);
                        } else {
                            sptype = 0;
                            SpUtil.setParam(Constants.DAY_NIGHT_FRAGMENT_POS, sptype);
                        }*/
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, AboutActivity.class);
                        CircularAnimUtil.startActivity(MainActivity.this, intent, nv, R.color.white);
                        ToastUtil.showShort("产品介绍目录");
                        //startActivity(new Intent(MainActivity.this, AboutActivity.class));
                        break;
                }
                dl.closeDrawer(Gravity.LEFT);
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_:
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, GoNetActivity.class);
                CircularAnimUtil.startActivity(this, intent,fragmentContainer, R.color.pink);
                break;
            case R.id.action_search:
                popupWindow();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // 热搜
    private void popupWindow() {
        final PopupWindow popupWindow = new PopupWindow();
        View inflate = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_popuwindow, null);
        inflate.findViewById(R.id.iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        FlowLayout flow = inflate.findViewById(R.id.flow);
        final EditText et = inflate.findViewById(R.id.et_share);
        final RecyclerView rlv = inflate.findViewById(R.id.rlv);
        ImageView clear = inflate.findViewById(R.id.iv_clear);
        Button bt = inflate.findViewById(R.id.search_1);

        String text = et.getText().toString();
        et.setSelection(text.length());

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SerachActivity.class);
                intent.putExtra("query",et.getText().toString());
                startActivity(intent);
                ToastUtil.showShort("搜素界面");

            }
        });


        /*et.getQuery().toString()  ;
        final String text = et.getText().toString();
        et.setSelection(text.length());*/
        mList = new ArrayList<>();
        initLiu();
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setHint("发现更多干货");
                mAdapterPop.mText.clear();
                mAdapterPop.notifyDataSetChanged();
            }
        });

        for (int i = 0; i < mTitless.size(); i++) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_flow, null);
            TextView mTv = view.findViewById(R.id.tvtt);
            final String data = mTitless.get(i);
            mTv.setText(data);
            String[] colors = {"#91CED5", "#F88F55", "#90C5F0", "#C0AFD0", "#FE8542", "#A2398D"};
            Random random = new Random();
            int c = random.nextInt(5);
            mTv.setBackgroundColor(Color.parseColor(colors[c]));

            flow.addView(view);
            mTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    et.setText(data);
                    popData(rlv, data);
                }
            });
        }
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setContentView(inflate);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(fragmentContainer, Gravity.NO_GRAVITY, 0, 0);
    }

    @NonNull
    private RlvAdapterPop popData(RecyclerView rlv, String data) {
        mList.add(data);
        mAdapterPop = new RlvAdapterPop(mList);
        rlv.setAdapter(mAdapterPop);
        rlv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mAdapterPop.notifyDataSetChanged();
        return mAdapterPop;
    }

    private void initLiu() {
        mTitless = new ArrayList<>();
        mTitless.add("面试");
        mTitless.add("Studio3");
        mTitless.add("动画");
        mTitless.add("自定义View");
        mTitless.add("性能优化 速度");
        mTitless.add("gradle");
        mTitless.add("Camera 相机");
        mTitless.add("代码混淆 安全");
        mTitless.add("逆向加固");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SpUtil.setParam(Constants.DAY_NIGHT_FRAGMENT_POS,TYPE_WanAndroid);
    }


    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //点击返回键
        if(keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN){
            if (System.currentTimeMillis()-exitTime>2000){
                //声明弹出对象并初始化
                Snackbar.make(dl, "再按一次退出WanAndroid", Snackbar.LENGTH_SHORT)
                        .setAction("知道了", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .show();
                //Toast.makeText(this,"再按一次退出程序！(???)",Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            }else {
                finish();
            }
            return true;

        }
        return super.onKeyDown(keyCode,event);
    }


   /* //点击返回按钮 退出项目
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("确定退出PalyAndroid吗？");

            //设置确定按钮
            builder.setNegativeButton("取消", null);
            //设置取消按钮
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (sptype == 5) {
                        SpUtil.setParam(Constants.DAY_NIGHT_FRAGMENT_POS,TYPE_SETTING);
                    }else {
                        SpUtil.setParam(Constants.DAY_NIGHT_FRAGMENT_POS,TYPE_WanAndroid);
                    }
                    finish();
                }
            });
            //显示提示框
            builder.show();
        }

        return super.onKeyDown(keyCode, event);
    }*/



}
