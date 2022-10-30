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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends Activity implements View.OnClickListener {
    static AccountManager am;
    Authenticator au;
    EditText username;
    EditText password;
    Button login;
    Button register;

    // Sets up the LoginActivity and necessary variables and listeners
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        am = AccountManager.get(getBaseContext());
        au = new Authenticator(getBaseContext());
        //get all component
        username = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.userPassword);
        login = (Button) findViewById(R.id.buttonLogin);
        login.setOnClickListener(this);
        register = (Button) findViewById(R.id.buttonRegister);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        //Hide the keyboard after click login
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(login.getWindowToken(), 0);

        if (view.getId() == R.id.buttonLogin) {
            attemptLogin();
        } else {
            attemptRegister();
        }
    }

    /*
    * Attempt to log in with credentials
    * if success, start MainActivity
    * */
    private void attemptLogin() {
        if (username.getText().toString().equals("") || password.getText().toString().equals("")) {
            // If the input is mal-formatted
            Toast.makeText(LoginActivity.this, "Username or password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Account account = findAccountByUsername(username);
        if (account == null) {
            Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
            return;
        }

        // if account exists, attempt to log in
        String authToken = au.logIn(account, password.getText().toString());
        if (authToken == null) {
            Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
        } else {
            // if authenticated, set authToken and switch to main activity page
            Toast.makeText(LoginActivity.this, "Welcome back " + username.getText().toString(), Toast.LENGTH_SHORT).show();
            am.setAuthToken(account, "com.team7", authToken);
            Intent mainActivityIntent = new Intent(this, MainActivity.class);
            mainActivityIntent.putExtra("username",username.getText().toString());
            startActivity(mainActivityIntent);
        }
    }

    /*
    * if account exist, display error
    * else, create user and register to account manager
    * */
    private void attemptRegister() {
        if (username.getText().toString().equals("") || password.getText().toString().equals("")) {
            // If the input is mal-formatted
            Toast.makeText(LoginActivity.this, "Username or password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        Account account = findAccountByUsername(username);
        if (account != null) {
            Toast.makeText(LoginActivity.this, "Account already existed " + username.getText().toString(), Toast.LENGTH_SHORT).show();
            return;
        }

        // if account doesn't exist, create an account
        account = new Account(username.getText().toString(), "com.team7");
        am.addAccountExplicitly(account, password.getText().toString(), null);
        Toast.makeText(LoginActivity.this, "Account registered " + username.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    /*
    * Find account by username
    * if no match, return null
    * */
    private Account findAccountByUsername(EditText username) {
        Account[] accounts = am.getAccountsByType("com.team7");
        for(Account account: accounts) {
            if(account.name.equals(username.getText().toString().trim())) {
                return account;
            }
        }
        return null;
    }

}
