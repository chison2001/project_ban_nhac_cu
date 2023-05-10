package com.example.projectbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.projectbanhang.R;
import com.example.projectbanhang.database.UserDBHelper;
import com.example.projectbanhang.model.User;
import com.example.projectbanhang.utils.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]{3,16}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^.{6,20}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(0|\\+84)[3|5|7|8|9]\\d{8}$");


    EditText email , pass, repass, phone, username;
    AppCompatButton btnregis;
    UserDBHelper userDBHelper;
    ImageView logo;
    TextView returnlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getView();
        initControl();
    }

    private void initControl() {
        btnregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAccount();
            }
        });
        returnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(login);
            }
        });
    }

    private void registerAccount() {
        String str_email = email.getText().toString().trim();
        String str_pass = pass.getText().toString().trim();
        String str_repass = repass.getText().toString().trim();
        String str_phone = phone.getText().toString().trim();
        String str_username = username.getText().toString().trim();
        Matcher matcher_email = EMAIL_PATTERN.matcher(str_email);
        Matcher matcher_username = USERNAME_PATTERN.matcher(str_username);
        Matcher matcher_password = PASSWORD_PATTERN.matcher(str_pass);
        Matcher matcher_phonenumber = PHONE_PATTERN.matcher(str_phone);
        userDBHelper = new UserDBHelper(RegisterActivity.this);
        if(TextUtils.isEmpty(str_username)){
            Toast.makeText(getApplicationContext(), "bạn chưa nhập Username", Toast.LENGTH_SHORT).show();
        }else if(!matcher_username.matches()) {
            Toast.makeText(getApplicationContext(), "user name từ 3 đến 16 kí tự", Toast.LENGTH_SHORT).show();
        }else if(userDBHelper.checkUserName(str_username)) {
            Toast.makeText(getApplicationContext(), "Username đã tồn tại", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(str_email)){
            Toast.makeText(getApplicationContext(), "bạn chưa nhập Email", Toast.LENGTH_SHORT).show();
        }else if(!matcher_email.matches()) {
            Toast.makeText(getApplicationContext(), "Định dạng mail không đúng", Toast.LENGTH_SHORT).show();
        }else if(userDBHelper.checkUserEmail(str_email)) {
            Toast.makeText(getApplicationContext(), "Email đã đăng kí", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(str_pass)){
            Toast.makeText(getApplicationContext(), "bạn chưa nhập pass", Toast.LENGTH_SHORT).show();
        }else if(!matcher_password.matches()){
            Toast.makeText(getApplicationContext(), "Password từ 6 đến 20 kí tự", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(str_repass)){
            Toast.makeText(getApplicationContext(), "bạn chưa nhập Confirm pass", Toast.LENGTH_SHORT).show();
        }else if(!str_repass.equals(str_pass)){
            Toast.makeText(getApplicationContext(), "Confirm pass phải giống với pass word", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(str_phone)){
            Toast.makeText(getApplicationContext(), "bạn chưa nhập số điện thoại", Toast.LENGTH_SHORT).show();
        }else if(!matcher_phonenumber.matches()){
            Toast.makeText(getApplicationContext(), "Số điện thoại gồm 10 chữ số và bắt đầu bằng 0 hoặc +84", Toast.LENGTH_SHORT).show();
        }else if(userDBHelper.checkUserPhone(str_phone)){
            Toast.makeText(getApplicationContext(), "Số điện thoại đã được đăng kí", Toast.LENGTH_SHORT).show();
        } else {
            if(str_pass.equals(str_repass)){
                userDBHelper.onCreate(SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.projectbanhang/databases/bannhaccu.db", null));
                User user = new User(str_username, str_email, str_pass, str_phone, "user");
                userDBHelper.insertUser(user);
                userDBHelper.close();
                Utils.user_current = user;
                savingPreferences(user);
                Intent  loginuser=  new Intent(getApplicationContext(), MainActivity.class);
                startActivity(loginuser);
                finish();
            }else {
                Toast.makeText(getApplicationContext(), "Confirm pass chưa khớp", Toast.LENGTH_SHORT).show();
            }
        }

    }
    private void savingPreferences(User user) {
        SharedPreferences pre = getSharedPreferences("user",MODE_PRIVATE);
        SharedPreferences.Editor editor= pre.edit();
        editor.putInt("id", user.getId());
        editor.commit();
    }
    private void getView() {
        email = findViewById(R.id.emailregis);
        pass = findViewById(R.id.passregis);
        repass = findViewById(R.id.repassregis);
        btnregis = findViewById(R.id.btnRegis);
        phone = findViewById(R.id.phone);
        username = findViewById(R.id.username);
        logo = findViewById(R.id.imglogoregis);
        returnlogin = findViewById(R.id.tvreturnlogin);
        Glide.with(getApplicationContext()).load("https://res.cloudinary.com/dwbnrx0mg/image/upload/v1683003034/projectbannhaccu/Imusic_w2jujj.png").into(logo);
    }


}