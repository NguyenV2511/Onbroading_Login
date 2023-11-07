package com.example.onbroading_login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        // Lấy thông tin từ Intent
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String password = intent.getStringExtra("password");

        // Bây giờ bạn có thể sử dụng biến "email" và "password" trong MainPageActivity.
        // Ví dụ:
        TextView emailTextView = findViewById(R.id.txt_appname);
        emailTextView.setText("Email: " + email);
    }
}