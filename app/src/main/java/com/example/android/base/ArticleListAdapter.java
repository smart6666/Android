package com.example.android.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.R;
import com.example.android.json.Article;
import com.example.android.util.DateUtils;

import java.util.List;

/**
 * 文章列表
         */

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ViewHolder> {

    private Context mContext;
    private List<Article> mArticleList;


    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView tv_tag;
        TextView tv_author;
        TextView tv_title;
        TextView tv_time;
        TextView tv_type;
        ImageView img_collect;

        public ViewHolder(View view){
            super(view);
            cardView = (CardView) view;
            tv_tag = view.findViewById(R.id.tv_tag);
            tv_author = view.findViewById(R.id.tv_author);
            tv_title = view.findViewById(R.id.tv_title);
            tv_time = view.findViewById(R.id.tv_time);
            tv_type = view.findViewById(R.id.tv_type);
            img_collect = view.findViewById(R.id.img_collect);
        }
    }

    public ArticleListAdapter( List<Article> articlelist){
        mArticleList = articlelist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.article_list,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article article = mArticleList.get(position);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {   //判断API是否大于17
            holder.tv_title.setText(Html.fromHtml(article.getTitle(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            holder.tv_title.setText(Html.fromHtml(article.getTitle()));
        }
        holder.tv_author.setText(article.getAuthor());
        holder.tv_time.setText(DateUtils.parseTime(article.getPublishTime()));
        holder.tv_type.setText(String.format("%1$s / %2$s",article.getSuperChapterName(), article.getChapterName()));
        holder.tv_tag.setVisibility(View.VISIBLE);
        if (article.isFresh()){
            holder.tv_tag.setText("新");
            holder.tv_tag.setTextColor(Color.RED);
            holder.tv_tag.setActivated(true);
        }else if (article.getTags().size() > 0){
            holder.tv_tag.setActivated(false);
            holder.tv_tag.setText(article.getTags().get(0).getName());
//            holder.tv_tag.setTextColor(ContextCompat.getColor(MyApplication.getContext(),R.color._009a61));
        }else  {
            holder.tv_tag.setVisibility(View.GONE);
        }
    }



    @Override
    public int getItemCount() {
        return mArticleList.size();
    }
}