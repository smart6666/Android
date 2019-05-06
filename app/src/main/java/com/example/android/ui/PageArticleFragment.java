package com.example.android.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.R;
import com.example.android.WebViewActivity;
import com.example.android.base.ArticleListAdapter;
import com.example.android.common.Const;
import com.example.android.json.Article;
import com.example.android.json.BaseBean;
import com.example.android.util.HttpUtil;
import com.example.android.util.Utility;

import java.util.ArrayList;
import java.util.List;


public class PageArticleFragment extends Fragment {

    final String address = "https://www.wanandroid.com/article/list/0/json";
    private List<Article> artileList;
    private RecyclerView articleListRecyclerView;
    private LinearLayoutManager layoutManager;
    private ArticleListAdapter adapter;
    private int id;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_list, container, false);
        articleListRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        articleListRecyclerView.setLayoutManager(layoutManager);
        artileList = new ArrayList<>();
        adapter = new ArticleListAdapter(artileList);
        articleListRecyclerView.setAdapter(adapter);
        loadWebData(address);
        return view;
    }

    private void loadWebData(final String address) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
//                执行网络请求
                String s = HttpUtil.sendHttpRequest(address);
                return s;
            }

            @Override
            protected void onPostExecute(String s) {
                if (s != null && !s.isEmpty()) {
//                    解析数据 ：解析成功之后要什么，解析之后的数据给RecyclerView来显示
                    BaseBean bean = Utility.handleBasebeanResponse(s);
//                  获取到网络数据，并且解析，添加数据到数据源当中
                    artileList.addAll(bean.getData().getDatas());
//                    提示adapter更新数据
                    adapter.notifyDataSetChanged();
                }
            }
        }.execute();
    }


    public int getArticleId() {
        return id;
    }



    //进入详情
    public void onItemClick(int position, Article bean) {
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.BUNDLE_KEY.OBJ, bean);
        bundle.putString(Const.BUNDLE_KEY.TYPE, Const.EVENT_ACTION.HOME);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
