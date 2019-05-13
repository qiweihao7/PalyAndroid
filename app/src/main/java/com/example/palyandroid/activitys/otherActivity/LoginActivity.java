package com.example.palyandroid.activitys.otherActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.palyandroid.R;
import com.example.palyandroid.activitys.MainActivity;
import com.example.palyandroid.activitys.collectionActivity.CollectionActivity;
import com.example.palyandroid.activitys.collectionActivity.CollectionWebActivity;
import com.example.palyandroid.base.BaseActivity;
import com.example.palyandroid.net.Constants;
import com.example.palyandroid.presenters.mainPresenter.MainPresenter;
import com.example.palyandroid.utils.SpUtil;
import com.example.palyandroid.utils.ToastUtil;
import com.example.palyandroid.utils.UIModeUtil;
import com.example.palyandroid.views.mainView.MainView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<MainPresenter, MainView> implements MainView {

    @BindView(R.id.bt_back)
    Button btBack;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.bt_regrist)
    Button btRegrist;
    @BindView(R.id.bt_Um_qq)
    Button btUmQq;
    @BindView(R.id.bt_Um_wx)
    Button btUmWx;
    @BindView(R.id.bt_Um_wb)
    Button btUmWb;
    private int type;
    private String username;
    private String password;
    private String loginName;
    private String logingPassword;

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

        //注册界面传值
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");

        etName.setText(username);
        etPassword.setText(password);

        /*type = (int) SpUtil.getParam(Constants.DAY_NIGHT_FRAGMENT_POS, MainActivity.TYPE_SETTING);

        if (type == 5) {
            UIModeUtil.setActModeUseMode(LoginActivity.this, AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            UIModeUtil.setActModeUseMode(LoginActivity.this, AppCompatDelegate.MODE_NIGHT_YES);
        }*/

        initPer();

    }

    private void initPer() {
        String[] per = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(this, per, 100);
    }

    // 分享登陆
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private void loginAll(SHARE_MEDIA type) {

        UMShareAPI umShareAPI = UMShareAPI.get(this);
        umShareAPI.getPlatformInfo(LoginActivity.this, type, authListener);

    }

    UMAuthListener authListener = new UMAuthListener() {

        private String value;
        private String key;
        private String img;
        private String name;

        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {


            for (Map.Entry<String, String> entry : data.entrySet()) {

                key = entry.getKey();
                value = entry.getValue();
                // Log.d(TAG, "key: "+key+",value:"+value);

                if (key.equals("screen_name")) {
                    name = entry.getValue();
                }
                if (key.equals("iconurl")) {
                    img = entry.getValue();
                }

            }

            SpUtil.setParam(Constants.DATA, name);
            SpUtil.setParam(Constants.IMG, img);
            Intent intent = new Intent(LoginActivity.this, CollectionActivity.class);
            startActivity(intent);

            Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_LONG).show();

        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {

            Toast.makeText(LoginActivity.this, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(LoginActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };

    @OnClick({R.id.bt_back,R.id.bt_login, R.id.bt_regrist, R.id.bt_Um_qq, R.id.bt_Um_wx, R.id.bt_Um_wb})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_back:
                if (type == 5) {
                    SpUtil.setParam(Constants.DAY_NIGHT_FRAGMENT_POS, 5);
                } else {
                    SpUtil.setParam(Constants.DAY_NIGHT_FRAGMENT_POS, 0);
                }
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                Toast.makeText(this, "取消登陆", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.bt_login:
                loginName = etName.getText().toString();
                logingPassword = etPassword.getText().toString();
                if (!TextUtils.isEmpty(loginName) && !TextUtils.isEmpty(logingPassword)) {
                    SpUtil.setParam(Constants.DATA, loginName);
                    SpUtil.setParam(Constants.IMG, "http://ww1.sinaimg.cn/large/0065oQSqly1g2pquqlp0nj30n00yiq8u.jpg");
                    startActivity(new Intent(LoginActivity.this, CollectionActivity.class));
                    finish();
                    ToastUtil.showShort("登陆成功");
                } else {
                    ToastUtil.showLong("账号或密码不能为空");
                }
                break;
            case R.id.bt_regrist:
                startActivity(new Intent(LoginActivity.this, RegristActivity.class));
                Toast.makeText(this, "注册界面", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_Um_qq:
                loginAll(SHARE_MEDIA.QQ);
                break;
            case R.id.bt_Um_wx:
                loginAll(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.bt_Um_wb:
                loginAll(SHARE_MEDIA.SINA);
                break;

        }
    }
}
