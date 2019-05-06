package com.example.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.ui.ChapterFragment;
import com.example.android.ui.PageArticleFragment;
import com.example.android.ui.ProjectFragment;
import com.example.android.ui.SystemFragment;


public class MainActivity extends BaseActivity implements View.OnClickListener{

    private int currentPosition;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private FloatingActionButton btn_scroll_top;
    private Button[] btns;
    private TextView mNameView;
    private ImageView mAvatarView;
    private int index;
    private Fragment[] fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragments();
        initViews();
    }

    @Override
    protected boolean initToolbar() {   //重写父类方法ToolBar()
        mToolbar.setTitle(R.string.app_name);
        mToolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        return true;
    }

    @Override
    protected void initViews() {
        mDrawerLayout =  findViewById(R.id.drawerLayout);
        mNavigationView =  findViewById(R.id.navigation_view);
        btn_scroll_top = findViewById(R.id.btn_scroll_top);
        btns = new Button[4];
        btns[0] =  findViewById(R.id.btn_main);
        btns[1] =  findViewById(R.id.btn_system);
        btns[2] =  findViewById(R.id.btn_chapter);
        btns[3] =  findViewById(R.id.btn_project);
        btns[0].setSelected(true);         //默认首页

        for (int i = 0; i < btns.length; i++) {
            btns[i].setOnClickListener(this);
            if (i != currentPosition) {
                btns[i].setScaleX(0.9f);
                btns[i].setScaleY(0.9f);
            }
        }
        mNavigationView.setNavigationItemSelectedListener(onNavigationItemSelectedListener);
//        btn_scroll_top.setOnClickListener(onScrollTopListener);
    }

    private void initFragments() {
        fragments = new Fragment[]{new ChapterFragment(),new ProjectFragment(), new SystemFragment(),new PageArticleFragment()};
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.container, fragments[0]).show(fragments[0]).commitAllowingStateLoss();
    }

    //创建Menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_setting, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Menu点击事件
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                startActivity(new Intent(this, SearchActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //侧滑菜单点击
    private NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
//                case R.id.menu_collect_article: {
//                    if (!UserInfoManager.isLogin()) {
//                        IntentUtils.goLogin(MainActivity.this);
//                    } else {
//                       startActivity(new Intent(MainActivity.this, CollectArticleActivity.class));
//                    }
//                }
//                break;
                case R.id.menu_about:
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                    break;
                case R.id.user_exit:
//                    exitToLogin();
                    break;
            }
            return true;
        }
    };
//    private View.OnClickListener onScrollTopListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            String action="";
//            switch (currentPosition) {
//                case 0:
//                    action = Const.EVENT_ACTION.HOME;
//
//                    break;
//                case 1:
//                    action = Const.EVENT_ACTION.SYSTEM;
//                    break;
//                case 2:
//                //    ((ChaptersFragment)fragments[2]).scrollToTop();
//                    return;
//                case 3:
//               //     ((ProjectFragment)fragments[3]).scrollToTop();
//                    return;
//
//            }
//          //  RxEvent.getInstance().postEvent(action,new UsageEvents.Event(UsageEvents.Event.Type.SCROLL_TOP));
//        }
//    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_main:
                index = 0;
                Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_system:
                index = 1;
                break;
            case R.id.btn_chapter:
                index = 2;
                break;
            case R.id.btn_project:
                index = 3;
                break;
            default:
        }
        showCurrentFragment(index);
    }


    /**
     * 切换显示当前Fragment
     *
     * @param index
     */
    private void showCurrentFragment(int index) {
        if (currentPosition != index) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(fragments[currentPosition]);
            if (!fragments[index].isAdded()) {
                ft.add(R.id.container, fragments[index]);
            }
            ft.show(fragments[index]).commitAllowingStateLoss();
            btns[currentPosition].setSelected(false);
            btns[index].setSelected(true);
            scaleView();
            currentPosition = index;
            setCurrentTitle();
        }
    }

    //转换标题栏标题
    public void setCurrentTitle() {
        if (currentPosition == 0)
            mToolbar.setTitle(R.string.app_name);
        else if (currentPosition == 1)
            mToolbar.setTitle(R.string.system);
        else if(currentPosition == 2)
            mToolbar.setTitle(R.string.chapter);
        else if (currentPosition == 3)
            mToolbar.setTitle(R.string.project);
    }

    /**
     * view放大缩小
     */
    private void scaleView() {
        btns[currentPosition].animate().scaleX(0.9f).scaleY(0.9f)
                .setDuration(150).start();
        btns[index].animate().scaleX(1.0f).scaleY(1.0f)
                .setDuration(150).start();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
    @Override
    protected void onNavigationClick() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }



}
