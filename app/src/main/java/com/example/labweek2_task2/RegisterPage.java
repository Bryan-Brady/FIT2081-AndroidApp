package com.example.labweek2_task2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterPage extends AppCompatActivity {

    EditText email;
    EditText password;

    String EMAIL_KEY = "EMAIL";
    String PASSWORD_KEY = "PWD";

    private SharedPreferences sP;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        sP = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

    }

    public void onRegisterButton(View view){
        SharedPreferences.Editor editor = sP.edit();

        editor.putString(EMAIL_KEY, getEmail());
        editor.putString(PASSWORD_KEY, getPassword());

        editor.apply();

        Intent intent = new Intent(RegisterPage.this, LoginPage.class);
        RegisterPage.this.startActivity(intent);
    }

    public String getEmail(){
        String enteredEmail = email.getText().toString();
        return enteredEmail;
    }

    public String getPassword(){
        String enteredPassword = password.getText().toString();
        return enteredPassword;
    }
}
