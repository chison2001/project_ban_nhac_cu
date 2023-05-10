package com.example.projectbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.projectbanhang.R;
import com.example.projectbanhang.database.UserDBHelper;
import com.example.projectbanhang.model.User;
import com.example.projectbanhang.utils.Utils;

public class SplashActivity extends AppCompatActivity {
    ImageView imageView;
    UserDBHelper userDBHelper;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getView();
        Glide.with(getApplicationContext()).load("https://res.cloudinary.com/dwbnrx0mg/image/upload/v1683003034/projectbannhaccu/Imusic_w2jujj.png").into(imageView);
        Thread thread= new Thread(){
            public void run() {
                try {
                    sleep(1500);
                }catch (Exception ex){

                }finally {
                    restoringPreferences();
                    if (user == null){
                        Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(login);
                        finish();
                    }else {
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
        };
        thread.start();
    }

    private void restoringPreferences() {
        SharedPreferences pre = getSharedPreferences("user",MODE_PRIVATE);
        int id =pre.getInt("id", -1);
        if (id == -1){
            user = null;
        }else {
            userDBHelper = new UserDBHelper(getApplicationContext());
            user = userDBHelper.getUserById(id);
            userDBHelper.close();
        }
    }


    private void getView() {
        imageView= findViewById(R.id.splashimg);
    }
}