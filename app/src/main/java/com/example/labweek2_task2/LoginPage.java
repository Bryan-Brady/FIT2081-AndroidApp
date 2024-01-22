package com.example.labweek2_task2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginPage extends AppCompatActivity {

    private SharedPreferences sP;

    EditText email;
    EditText password;

    String getEmailSP;
    String getPassSP;

    String emailCheck;
    String passCheck;

    String EMAIL_KEY = "EMAIL";
    String PASSWORD_KEY = "PWD";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);

        sP = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        getEmailSP = sP.getString(EMAIL_KEY, "");
        getPassSP = sP.getString(PASSWORD_KEY, "");

        emailCheck = getEmailSP;
        passCheck = getPassSP;



        Log.d("week_12", "Email: " + emailCheck + ", Password: " + passCheck);
        Log.d("week_12", "Login Email: " + getLoginEmail() + ", Login Password: " + getLoginPassword());




    }

    public void onRegisterClick(View view){
        Intent intent = new Intent(LoginPage.this, RegisterPage.class);
        LoginPage.this.startActivity(intent);
    }
    //getLoginEmail() == emailCheck && getLoginPassword() == passCheck

    public void onLoginClick(View view) {

        if(getLoginEmail() == emailCheck && getLoginPassword() == passCheck){
            Intent intent = new Intent(LoginPage.this, MainActivity.class);
            LoginPage.this.startActivity(intent);
        } else{
            Log.d("week_12", "Login Email: " + getLoginEmail() + ", Password: " + getLoginPassword());
            Toast.makeText(getApplicationContext(), "Your input email or password is invalid, Try again", Toast.LENGTH_SHORT).show();
        }

    }

    public String getLoginEmail(){
        String enteredEmail = email.getText().toString();
        return enteredEmail;
    }
    public String getLoginPassword(){
        String enteredPassword = password.getText().toString();
        return enteredPassword;
    }
}
