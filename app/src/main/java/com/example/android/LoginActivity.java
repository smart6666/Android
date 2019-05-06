package com.example.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.json.User;
import com.example.android.util.LoginHttpUtil;
import com.example.android.util.UserDataUtility;

public class LoginActivity extends AppCompatActivity {

    private EditText userNameEt;
    private EditText userPwdEt;
    private Button loginBtn;
    private Button registerBtn;
    private CheckBox rememberPass;
    private SharedPreferences infoPref;
    private SharedPreferences.Editor editor;
    private final String address = "https://www.wanandroid.com/user/login";
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        infoPref = PreferenceManager.getDefaultSharedPreferences(this);
        userNameEt = (EditText) findViewById(R.id.et_userName);
        userPwdEt = (EditText) findViewById(R.id.et_userpassword);
        loginBtn = (Button) findViewById(R.id.bt_login);
        registerBtn = (Button) findViewById(R.id.bt_register);
        rememberPass = (CheckBox) findViewById(R.id.remember_pwd);
        boolean isRemember = infoPref.getBoolean("remember_password", false);
        if (isRemember) {
            String userName = infoPref.getString("username", "");
            String userPwd = infoPref.getString("userpwd", "");
            userNameEt.setText(userName);
            userPwdEt.setText(userPwd);
            rememberPass.setChecked(true);
        }
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = userNameEt.getText().toString().trim();
                String pwd = userPwdEt.getText().toString().trim();
                loadWebData("address","name","pwd",null);


            }
        });
    }

        private void loadWebData(final String address, final String name, final String pwd, final String repwd) {
            new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... params) {
//                执行网络请求
                    String s = LoginHttpUtil.sendHttpRequest(address, name, pwd, repwd);
                    return s;
                }

                @Override
                protected void onPostExecute(String s) {
                    if (s != null && !s.isEmpty()) {
                        user = UserDataUtility.handleInfoResponse(s);
                        if(user.getErrorCode() == 0){
                            Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                            editor = infoPref.edit();
                            if(rememberPass.isChecked()){   //检查复选框是否被选中
                                editor.putBoolean("remember_password",true);
                                editor.putString("username", name);
                                editor.putString("userpwd",pwd);
                            }else{
                                editor.clear();
                            }
                            editor.apply();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this,user.getErrorMsg(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }.execute();
        }


}
