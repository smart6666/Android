package com.example.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.example.android.common.Const;
import com.example.android.json.Article;

import java.lang.reflect.Method;

/**
 * 文章详情
 */

public class WebViewActivity extends BaseActivity {
    private FrameLayout mContainer;
    private WebView webView;
    private Article bean;
    private String actionType;
    private int id;
    private String title = "";
    private String url = "";



    @Override
    protected boolean initToolbar() {
        mToolbar.setTitle(title);
        return true;
    }

    @Override
    protected void getIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        bean = (Article) bundle.getSerializable(Const.BUNDLE_KEY.OBJ);
        actionType = intent.getStringExtra(Const.BUNDLE_KEY.TYPE);
        if (bean != null) {
            id = bean.getId();
            title = bean.getTitle();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                title = Html.fromHtml(title, Html.FROM_HTML_MODE_LEGACY).toString();
            } else {
                title = Html.fromHtml(title).toString();
            }
            url = bean.getLink();
        }
    }


    @Override
    protected void initViews() {
        mContainer = findViewById(R.id.container);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_webview);
        webView=(WebView)this.findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.content_menu_setting, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.browser:
                openInBrowser();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    // 让菜单同时显示图标和文字
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    //打开浏览器
    private void openInBrowser() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    public int getArticleId() {
        return id;
    }

}
