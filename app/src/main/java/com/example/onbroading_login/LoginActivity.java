package com.example.onbroading_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextLogInEmail, editTextLogInPassWord;
    private Button buttonLogInLogIn;

    private Boolean validateEmail() {
        String val = editTextLogInEmail.getText().toString();
        if (val.isEmpty()) {
            editTextLogInEmail.setError("Email cannot be empty");
            return false;
        } else {
            editTextLogInEmail.setError(null);
            return true;
        }
    }

    private Boolean validatePassWord() {
        String val = editTextLogInPassWord.getText().toString();
        if (val.isEmpty()) {
            editTextLogInPassWord.setError("Password cannot be empty");
            return false;
        } else {
            editTextLogInPassWord.setError(null);
            return true;
        }
    }

    public void checkUser() {
        final String loginEmail = editTextLogInEmail.getText().toString().trim();
        final String loginPassword = editTextLogInPassWord.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("email").equalTo(loginEmail);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    DataSnapshot userSnapshot = snapshot.getChildren().iterator().next();
                    String passwordFromDB = userSnapshot.child("password").getValue(String.class);

                    if (passwordFromDB != null && passwordFromDB.equals(loginPassword)) {
                        editTextLogInEmail.setError(null);

                        Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
                        intent.putExtra("email", loginEmail);
                        intent.putExtra("password", loginPassword);
                        startActivity(intent);
                    } else {
                        editTextLogInPassWord.setError("Wrong password");
                        editTextLogInPassWord.requestFocus();
                    }
                } else {
                    editTextLogInEmail.setError("User does not exist or email name is wrong");
                    editTextLogInEmail.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi nếu cần
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageView backHomeFromLogin = findViewById(R.id.img_loginBack);
        backHomeFromLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        TextView SignUptoLogin = findViewById(R.id.txt_toSignUp);
        SignUptoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        TextView LogintoForgotPassword = findViewById(R.id.txt_toForgotPassword);
        LogintoForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
                startActivity(intent);
            }
        });

        editTextLogInEmail = findViewById(R.id.edt_loginEmail);
        editTextLogInPassWord = findViewById(R.id.edt_loginPassword);
        buttonLogInLogIn = findViewById(R.id.btn_loginLogIn);
        buttonLogInLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateEmail() | !validatePassWord()) {
                    return;
                } else {
                    checkUser();
                }
            }
        });
    }
}