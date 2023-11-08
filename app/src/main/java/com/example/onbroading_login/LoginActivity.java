package com.example.onbroading_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType; // Để sử dụng InputType
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextLogInEmail, editTextLogInPassWord;
    private Button buttonLogInLogIn;

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


        ImageView passwordVisibilityToggle = findViewById(R.id.img_passwordVisibility);
        EditText passwordEditText = findViewById(R.id.edt_loginPassword);

        final boolean[] passwordVisible = {false};

        passwordVisibilityToggle.setOnClickListener(v -> {
            passwordVisible[0] = !passwordVisible[0];

            if (passwordVisible[0]) {
                passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                passwordVisibilityToggle.setImageResource(R.drawable.visible); // Thay đổi biểu tượng hiện mật khẩu
            } else {
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                passwordVisibilityToggle.setImageResource(R.drawable.invisible); // Thay đổi biểu tượng ẩn mật khẩu
            }

            // Đảm bảo rằng văn bản trong EditText không bị mất
            passwordEditText.setSelection(passwordEditText.length());
        });

        buttonLogInLogIn.setOnClickListener(view -> {
            if (!validateEmail() | !validatePassWord() ) {

            }
            else {
                login();
            }
        });
    }

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
        Call<HelperClass> call = authService.login("openremote", username, password, "password");
        call.enqueue(new Callback<HelperClass>() {
            @Override
            public void onResponse(Call<HelperClass> call, Response<HelperClass> response) {
                if (response.isSuccessful()) {
                    HelperClass loginResponse = response.body();
                    String accessToken = loginResponse.getAccess_token();
                    Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                } else {
                    // Authentication failed
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HelperClass> call, Throwable t) {
                // Handle network or API errors
                Toast.makeText(LoginActivity.this, "Login Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
