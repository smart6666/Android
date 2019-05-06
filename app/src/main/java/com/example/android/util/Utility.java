package com.example.android.util;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.json.Article;
import com.example.android.json.BaseBean;
import com.example.android.json.Data;
import com.example.android.json.TagsBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Utility {

    /**
     * 将返回的json数据解析成Base实体类
     */
    public static BaseBean handleBasebeanResponse(String response){
        BaseBean basebean = new BaseBean();
        List<TagsBean> tagsBeanList = new ArrayList<>();
        List<Article> articleList = new ArrayList<>();
        if(!TextUtils.isEmpty(response)){
            try{
                JSONObject baseJsonObject = new JSONObject(response);
                basebean.setErrorCode(baseJsonObject.getInt("errorCode"));
                basebean.setErrorMsg(baseJsonObject.getString("errorMsg"));

                JSONObject dataJsonObject = baseJsonObject.getJSONObject("data");
                Data data = new Data();

                data.setCurPage(dataJsonObject.getInt("curPage"));
                data.setOffset(dataJsonObject.getInt("offset"));
                data.setOver(dataJsonObject.getBoolean("over"));
                data.setPageCount(dataJsonObject.getInt("pageCount"));
                data.setSize(dataJsonObject.getInt("size"));
                data.setTotal(dataJsonObject.getInt("total"));

                JSONArray datasJsonArray = dataJsonObject.getJSONArray("datas");

                for(int i = 0; i < datasJsonArray.length(); i++){
                    Article article = new Article();
                    JSONObject articleJsonObject = datasJsonArray.getJSONObject(i);

                    article.setApkLink(articleJsonObject.getString("apkLink"));

                    article.setAuthor(articleJsonObject.getString("author"));

                    article.setChapterId(articleJsonObject.getInt("chapterId"));
                    article.setChapterName(articleJsonObject.getString("chapterName"));
                    article.setCollect(articleJsonObject.getBoolean("collect"));
                    article.setCourseId(articleJsonObject.getInt("courseId"));
                    article.setDesc(articleJsonObject.getString("desc"));

                    article.setEnvelopePic(articleJsonObject.getString("envelopePic"));
                    article.setFresh(articleJsonObject.getBoolean("fresh"));
                    article.setId(articleJsonObject.getInt("id"));

                    article.setLink(articleJsonObject.getString("link"));

                    article.setNiceDate(articleJsonObject.getString("niceDate"));

                    article.setOrigin(articleJsonObject.getString("origin"));
                    article.setPrefix(articleJsonObject.getString("prefix"));
                    article.setProjectLink(articleJsonObject.getString("projectLink"));
                    article.setPublishTime(articleJsonObject.getLong("publishTime"));
                    article.setSuperChapterId(articleJsonObject.getInt("superChapterId"));
                    article.setSuperChapterName(articleJsonObject.getString("superChapterName"));
                    article.setTitle(articleJsonObject.getString("title"));
                    article.setType(articleJsonObject.getInt("type"));
                    article.setUserId(articleJsonObject.getInt("userId"));
                    article.setVisible(articleJsonObject.getInt("visible"));
                    article.setZan(articleJsonObject.getInt("zan"));
                    JSONArray tagsJsonArray = articleJsonObject.getJSONArray("tags");
                    if(tagsJsonArray.length() == 0){
                        TagsBean tagsBean = new TagsBean();
                        tagsBean.setName("");
                        tagsBean.setUrl("");
                        tagsBeanList.add(tagsBean);
                    }
                    for(int j = 0; j < tagsJsonArray.length(); j++){
                        JSONObject tagJsonObject = tagsJsonArray.getJSONObject(j);
                        TagsBean tagsBean = new TagsBean();
                        tagsBean.setName(tagJsonObject.getString("name"));
                        tagsBean.setUrl(tagJsonObject.getString("url"));
                        tagsBeanList.add(tagsBean);
                    }
                    article.setTags(tagsBeanList);
                    articleList.add(article);
                }
                data.setDatas(articleList);
                basebean.setData(data);
                return basebean;
            }catch(Exception e){
                Log.e("Utility","异常出现");
                e.printStackTrace();
            }
        }
        Log.e("Utility", "zifuchuangweikong");
        return null;
    }
}