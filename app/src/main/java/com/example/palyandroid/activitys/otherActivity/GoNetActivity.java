package com.example.palyandroid.activitys.otherActivity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.palyandroid.R;
import com.example.palyandroid.activitys.MainActivity;
import com.example.palyandroid.beans.otherBean.NetBean;
import com.example.palyandroid.net.MyServer;
import com.example.palyandroid.utils.BaseObserver;
import com.example.palyandroid.utils.CircularAnimUtil;
import com.example.palyandroid.utils.FlowLayout;
import com.example.palyandroid.utils.HttpUtils;
import com.example.palyandroid.utils.RxUtils;
import com.example.palyandroid.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Random;

import io.reactivex.disposables.Disposable;

public class GoNetActivity extends AppCompatActivity implements View.OnClickListener{

    /**
     * 常用网站
     */
    private TextView mTv;
    private FlowLayout mFla;
    private ArrayList<NetBean.DataBean> mList;
    private TextView mTlushi;
    private ImageView mIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_net);
        initView();
        initData();
    }

    private void initData() {

         // 横向布局管理器 new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false)
         //瀑布流管理器 //StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
          //网格 //GridLayoutManager manager = new GridLayoutManager(this,5);
          //线性 LinearLayoutManager manager = new LinearLayoutManager(this);

        HttpUtils.getInstance().getApiserver(MyServer.wanurl, MyServer.class)
                .netData()
                .compose(RxUtils.rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<NetBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NetBean netBean) {
                        Log.d("NNN", "onNext: " + netBean.toString());
                        mList.addAll(netBean.getData());


                        for (int i = 0; i < mList.size(); i++) {

                            View inflate = LayoutInflater.from(GoNetActivity.this).inflate(R.layout.layout_flow, null);
                            mTlushi = inflate.findViewById(R.id.tvtt);

                            String name = mList.get(i).getName();
                            mTlushi.setText(name);

                            String[] colors = {"#91CED5", "#F88F55", "#90C5F0", "#C0AFD0", "#FE8542", "#A2398D"};
                            Random random = new Random();
                            int c = random.nextInt(5);
                            mTlushi.setTextColor(getResources().getColor(R.color.white));
                            mTlushi.setBackgroundColor(Color.parseColor(colors[c]));


                            int finalI = i;
                            mTlushi.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.putExtra("link", mList.get(finalI).getLink());
                                    intent.putExtra("listTitle", mList.get(finalI).getName());
                                    intent.setClass(GoNetActivity.this,WebActivity.class);
                                    CircularAnimUtil.startActivity(GoNetActivity.this, intent, mTv, R.color.org);
                                }
                            });

                            mFla.addView(inflate);
                        }

                    }
                });
    }

    private void initView() {
        mTv = (TextView) findViewById(R.id.tv);
        mFla = (FlowLayout) findViewById(R.id.fla);
        mIv = (ImageView) findViewById(R.id.iv);
        mIv.setOnClickListener(this);
        mList = new ArrayList<>();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.iv:
                finish();
                Toast.makeText(GoNetActivity.this, "退出", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
