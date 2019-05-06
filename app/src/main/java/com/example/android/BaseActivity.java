package com.example.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Activity 基类
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Toolbar mToolbar;
    protected FrameLayout mContainerLayout;

    @Override
    protected void onCreate(Bundle bundle) {
        if (bundle != null) {
            //如果系统回收Activity,但是系统却保留了Fragment,当Activity被重新初始化,此时,系统保存的Fragment 的getActivity为空，
            //所以要移除旧的Fragment,重新初始化新的Fragment
            String FRAGMENTS_TAG = "android:support:fragments";
            bundle.remove(FRAGMENTS_TAG);
        }
        super.onCreate(bundle);
        setContentView(R.layout.activity_base);
        Intent intent = getIntent();
        if (intent != null)
            getIntent(intent);
        mToolbar = findViewById(R.id.toolbar);
        mContainerLayout = findViewById(R.id.frameLayout);
        //初始化ToolBar
        boolean isToolbar = initToolbar();
        if (isToolbar) {
            setSupportActionBar(mToolbar);          //将Toolbar的实例传入
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);//得到ActionBar的实例，并让导航键显示出来
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                //必须要在setSupportActionBar之后,不然不起作用
                @Override
                public void onClick(View v) {
                    onNavigationClick();
                }
            });
        } else {
            mToolbar.setVisibility(View.GONE);
        }
        //初始化Content
        initContent(getLayoutId());
    }

    private void initContent(int layoutId) {
        if (layoutId != 0) {
            View contentView = LayoutInflater.from(this).inflate(layoutId, mContainerLayout, false);
            mContainerLayout.addView(contentView);
            initViews();
        }
    }
    protected abstract int getLayoutId();

    protected void onNavigationClick() {
        finish();
    }

    protected boolean initToolbar(){
        return false;
    }

    protected void getIntent(Intent intent){ }

    protected abstract void initViews();

}
