package edu.uiuc.cs427app;


import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountManager;
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
        Intent intent = null;
        boolean isExsit=false;
        //Hide the keyboard after click login
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(login.getWindowToken(), 0);
        AccountManager mAccountManager = AccountManager.get(getBaseContext());
        Account[] accounts = mAccountManager.getAccountsByType(null);

        for(Account account : accounts) {
            if(!isExsit){
                if(account.name.equals(username.getText().toString().trim())) {
                    isExsit=true;
                }
            }
        }

        if (username.getText().toString().equals("") || password.getText().toString().equals("")) {
            Toast.makeText(LoginActivity.this, "username or password cannot be empty", Toast.LENGTH_SHORT).show();
        }
        else if (isExsit) {
            //Login
            Toast.makeText(LoginActivity.this, "Welcome back "+username.getText().toString(), Toast.LENGTH_SHORT).show();
        } else {
            //Create an account
            final Account account = new Account(username.getText().toString(), "com.foo");

            mAccountManager.addAccountExplicitly(account, password.getText().toString(), null);
            Toast.makeText(LoginActivity.this, "Welcome back "+username.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
