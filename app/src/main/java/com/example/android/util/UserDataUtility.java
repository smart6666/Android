package com.example.android.util;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.json.User;
import com.example.android.json.UserData;

import org.json.JSONObject;

public class UserDataUtility {

    public static User handleInfoResponse(String response){
        User user = new User();
        UserData userData = new UserData();
        if(!TextUtils.isEmpty(response)){
            try {
                JSONObject UserJsonObject = new JSONObject(response);
                user.setErrorCode(UserJsonObject.getInt("errorCode"));
                user.setErrorMsg(UserJsonObject.getString("errorMsg"));
                if(UserJsonObject.getString("data") .equals("")){
                    userData = null;
                }
                else{
                    JSONObject UserDataJsonObject = UserJsonObject.getJSONObject("data");
                    userData.setEmail(UserDataJsonObject.getString("email"));
                    userData.setIcon(UserDataJsonObject.getString("icon"));
                    userData.setId(UserDataJsonObject.getInt("id"));
                    userData.setPassword(UserDataJsonObject.getString("password"));
                    userData.setToken(UserDataJsonObject.getString("token"));
                    userData.setType(UserDataJsonObject.getInt("type"));
                    userData.setUsername(UserDataJsonObject.getString("username"));
                    userData.setCollectIds(null);
                    userData.setChapterTops(null);
                    user.setUserInfo(userData);
                }
            }catch(Exception e){
                Log.e("Utility","异常出现");
                e.printStackTrace();
            }
            return user;
        }
        return null;
    }
}
