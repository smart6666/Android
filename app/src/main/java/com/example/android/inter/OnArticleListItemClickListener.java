package com.example.android.inter;


import com.example.android.json.Article;

/**
 * 文章列表接口
 */

public interface OnArticleListItemClickListener extends OnItemClickListener<Article>{
    void onDeleteCollectClick(int position, int id, int originId);
    void onCollectClick(int position, int id);
}
