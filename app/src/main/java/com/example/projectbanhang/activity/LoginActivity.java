package com.example.projectbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.example.projectbanhang.R;
import com.example.projectbanhang.database.UserDBHelper;
import com.example.projectbanhang.model.User;
import com.example.projectbanhang.utils.Utils;

public class LoginActivity extends AppCompatActivity {
    TextView txtregis;
    EditText username , pass;
    AppCompatButton btnlogin;
    UserDBHelper userDBHelper;
    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getViews();
        initControl();
    }

    private void initControl() {
        txtregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class );
                startActivity(intent);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_username = username.getText().toString().trim();
                String str_pass = pass.getText().toString().trim();
                userDBHelper = new UserDBHelper(getApplicationContext());
                User user = userDBHelper.getUserByusername(str_username);
                if (TextUtils.isEmpty(str_username)){
                    Toast.makeText(getApplicationContext(), "bạn chưa nhập username", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(str_pass)){
                    Toast.makeText(getApplicationContext(), "bạn chưa nhập pass", Toast.LENGTH_SHORT).show();
                }else if(user == null){
                    Toast.makeText(getApplicationContext(), "Bạn chưa có tài khoản vui lòng đăng kí", Toast.LENGTH_SHORT).show();
                }else {
                    if (!str_pass.equals(user.getPass())){
                        Toast.makeText(getApplicationContext(), "sai mat khau", Toast.LENGTH_SHORT).show();
                    }else {
                        Utils.user_current = user;
                        savingPreferences(user);
                        switch (user.getRole()){
                            case "Admin":
                                Intent loginadmin = new Intent(getApplicationContext(), ManageActivity.class);
                                startActivity(loginadmin);
                                finish();
                                break;
                            case "user":
                                Intent  loginuser=  new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(loginuser);
                                finish();
                                break;
                        }
                    }
                }
            }
        });
    }

    private void savingPreferences(User user) {
        SharedPreferences pre = getSharedPreferences("user",MODE_PRIVATE);
        SharedPreferences.Editor editor= pre.edit();
        editor.putInt("id", user.getId());
        editor.commit();
    }

    private void getViews() {
        txtregis = findViewById(R.id.txtregis);
        username = findViewById(R.id.loginusername);
        pass = findViewById(R.id.loginpass);
        btnlogin = findViewById(R.id.btnLogin);
        logo = findViewById(R.id.imglogo);
        Glide.with(getApplicationContext()).load("https://res.cloudinary.com/dwbnrx0mg/image/upload/v1683003034/projectbannhaccu/Imusic_w2jujj.png").into(logo);
    }

}