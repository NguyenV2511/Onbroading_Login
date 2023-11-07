package com.example.onbroading_login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextSignUpUserName, editTextSignUpEmail, editTextSignUpPassWord, editTextSignUpConfirmPassword;
    private Button buttonSignUpCreateAccount;
    private ProgressBar progressbar;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    private Boolean validateUserName() {
        String val = editTextSignUpUserName.getText().toString();
        if (val.isEmpty()) {
            editTextSignUpUserName.setError("Field cannot be empty");
            return false;
        }
        else if (val.length() >= 20) {
            editTextSignUpUserName.setError("Username too long");
            return false;
        }
        else {
            editTextSignUpUserName.setError(null);
            return true;
        }
    }
    private Boolean validateEmail() {
        String val = editTextSignUpEmail.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            editTextSignUpEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            editTextSignUpEmail.setError("Invalid email address");
            return false;
        } else {
            editTextSignUpEmail.setError(null);
            return true;
        }
    }
    private Boolean validatePassword() {
        String val = editTextSignUpPassWord.getText().toString();
//        String passwordVal = "^" +
//                //"(?=.*[0-9])" +         //at least 1 digit
//                //"(?=.*[a-z])" +         //at least 1 lower case letter
//                //"(?=.*[A-Z])" +         //at least 1 upper case letter
//                "(?=.*[a-zA-Z])" +      //any letter
//                "(?=.*[@#$%^&+=])" +    //at least 1 special character
//                "(?=\\S+$)" +           //no white spaces
//                ".{8,}" +               //at least 4 characters
//                "$";
        if (val.isEmpty()) {
            editTextSignUpPassWord.setError("Field cannot be empty");
            return false;
        }
        else if (val.length() < 8) {
            editTextSignUpPassWord.setError("At least 8 characters");
            return false;
//        } else if (!val.matches(passwordVal)) {
//            editTextSignUpPassWord.setError("Password is too weak");
//            return false;
        } else {
            editTextSignUpPassWord.setError(null);
            return true;
        }
    }
    private Boolean validateConfirmPassword() {
        String val = editTextSignUpConfirmPassword.getText().toString();
        String password = editTextSignUpPassWord.getText().toString();

        if (val.isEmpty()) {
            editTextSignUpConfirmPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.equals(password)) {
            editTextSignUpConfirmPassword.setError("Passwords do not match");
            return false;
        } else {
            editTextSignUpConfirmPassword.setError(null);
            return true;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ImageView backHomeFromSignUp = findViewById(R.id.img_signupBack);
        backHomeFromSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        TextView SignUptoLogin = findViewById(R.id.txt_toLogin);
        SignUptoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        editTextSignUpUserName = findViewById(R.id.edt_signupUserName);
        editTextSignUpEmail = findViewById(R.id.edt_signupEmail);
        editTextSignUpPassWord = findViewById(R.id.edt_signupPassword);
        editTextSignUpConfirmPassword = findViewById(R.id.edt_sigupConfirmPassword);
        buttonSignUpCreateAccount = findViewById(R.id.btn_signupCreateAccount);


        buttonSignUpCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateUserName() | !validateEmail() | !validatePassword() | !validateConfirmPassword()) {
                    return;
                }
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");


                String username = editTextSignUpUserName.getText().toString();
                String email = editTextSignUpEmail.getText().toString();
                String password = editTextSignUpPassWord.getText().toString();
                String confirmpassword = editTextSignUpConfirmPassword.getText().toString();
                //HelperClass helperClass = new HelperClass(username, email, password, confirmpassword);
               // reference.child(username).setValue(helperClass);

                Toast.makeText(SignUpActivity.this, "You have signup successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);

            }

        });
    }
}
