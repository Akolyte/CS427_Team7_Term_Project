package edu.uiuc.cs427app;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends Activity implements View.OnClickListener {
    EditText username;
    EditText password;
    Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //get all component
        username = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.userPassword);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        //Hide the keyboard after click login
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(login.getWindowToken(), 0);
        if (username.getText().toString().equals("") || password.getText().toString().equals("")) {
            Toast.makeText(LoginActivity.this, "username or password cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (username.getText().toString().equals("666") || password.getText().toString().equals("666")) {
            Toast.makeText(LoginActivity.this, "Hello "+username.getText().toString(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(LoginActivity.this, "incorrect username or password", Toast.LENGTH_SHORT).show();
        }
    }
}
