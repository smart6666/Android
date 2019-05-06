package com.example.android.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginHttpUtil {
    public static String sendHttpRequest(final String address, final String name, final String pwd, final String repwd){
        HttpURLConnection connection = null;
        try{
            URL url = new URL(address);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes("username=" + name + "&password=" + pwd);
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder response = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null){
                response.append(line);
            }
            return response.toString();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(connection != null)
                connection.disconnect();
        }
        return null;
    }
}
