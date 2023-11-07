package com.example.onbroading_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
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

    private void login() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://uiot.ixxc.dev/auth/realms/master/protocol/openid-connect/") // Replace with the base URL of your API
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API_Interface authService = retrofit.create(API_Interface.class);
        String username = editTextLogInEmail.getText().toString();
        String password = editTextLogInPassWord.getText().toString();
        Call<HelperClass> call = authService.login("openremote",username,password,"password");
        call.enqueue(new Callback<HelperClass>() {
            @Override
            public void onResponse(Call<HelperClass> call, Response<HelperClass> response) {
                if (response.isSuccessful()) {
                    HelperClass loginResponse = response.body();
                    String accessToken = loginResponse.getAccess_token();
                    Log.d("token",accessToken);
                    Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }
                else {
                    // Authentication failed
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<HelperClass> call, Throwable t) {
                // Handle network or API errors
                Log.d("API CALL", t.getMessage().toString());
                Toast.makeText(LoginActivity.this, "Login Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageView backHomeFromLogin = findViewById(R.id.img_loginBack);
        backHomeFromLogin.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        });

        TextView SignUptoLogin = findViewById(R.id.txt_toSignUp);
        SignUptoLogin.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        TextView LogintoForgotPassword = findViewById(R.id.txt_toForgotPassword);
        LogintoForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
            startActivity(intent);
        });

        editTextLogInEmail = findViewById(R.id.edt_loginEmail);
        editTextLogInPassWord = findViewById(R.id.edt_loginPassword);
        buttonLogInLogIn = findViewById(R.id.btn_loginLogIn);
        buttonLogInLogIn.setOnClickListener(view -> {
            if (!validateEmail() | !validatePassWord() ) {
            }
            else {
                login();
            }
        });
    }
}