package com.example.palyandroid.activitys.otherActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.palyandroid.R;
import com.example.palyandroid.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegristActivity extends AppCompatActivity {

    @BindView(R.id.bt_back_r)
    Button btBackR;
    @BindView(R.id.et_name_re)
    EditText etNameRe;
    @BindView(R.id.et_password_re)
    EditText etPasswordRe;
    @BindView(R.id.et_password_re_two)
    EditText etPasswordReTwo;
    @BindView(R.id.bt_regrist)
    Button btRegrist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regrist);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_back_r, R.id.bt_regrist})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_back_r:
                finish();
                ToastUtil.showShort("取消注册");
                break;
            case R.id.bt_regrist:
                String userName = etNameRe.getText().toString();
                String userPassword = etPasswordRe.getText().toString();
                String twoPassword = etPasswordReTwo.getText().toString();
                if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userPassword) && !TextUtils.isEmpty(twoPassword)) {
                    if (userPassword.equals(twoPassword)) {
                        Intent intent = new Intent(RegristActivity.this, LoginActivity.class);
                        intent.putExtra("username",etNameRe.getText().toString());
                        intent.putExtra("password",etPasswordRe.getText().toString());
                        startActivity(intent);
                        finish();
                    }else {
                        ToastUtil.showShort("两次密码不一致");
                    }
                }else {
                    ToastUtil.showShort("用户名/密码不能为空");
                }

                break;
        }
    }
}
