package com.droidrocks.demos.helloui.authentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hollisinman.helloui.R;

public class Registration extends AppCompatActivity implements View.OnClickListener{

    EditText username;
    EditText password;
    EditText confirmPassword;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Find views
        username = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);
        confirmPassword = findViewById(R.id.et_confirm_password);
        register = findViewById(R.id.btn_register);

        // Register OnClickListener with our views
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                // If username, password and confirmPassword are not empty
                if (!username.getText().toString().isEmpty() && !password.getText().toString().isEmpty() && !confirmPassword.getText().toString().isEmpty()) {
                    // If user is NOT on banned HashSet
                    if (!AppAccess.getInstance().getBanned().contains(username.getText().toString())) {
                        // If user is NOT on allowed HashMap
                        if (!AppAccess.getInstance().getAllowed().containsKey(username.getText().toString())) {
                            // If password and confirmPassword match
                            if (password.getText().toString().equals(confirmPassword.getText().toString())) {
                                // Register user
                                if (checkPasswordLength(password) && checkPasswordLength(confirmPassword)) {
                                    AppAccess.getInstance().getAllowed().put(username.getText().toString(), password.getText().toString());
                                    Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.registration_success), Toast.LENGTH_SHORT).show();
                                    // Return newly registered user back to Login
                                    startActivity(new Intent(getApplicationContext(), Login.class));
                                }
                            // User's passwords do not match
                            } else {
                                Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.registration_password_mismatch), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Alert user they have already registered (they are on the allowed HashMap)
                            Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.registration_already_registered), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Alert user they are not allowed to register as their email already appears on the banned HashSet
                        Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.registration_email_banned), Toast.LENGTH_SHORT).show();
                    }
                } else if (username.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.registration_username_empty), Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.registration_password_empty), Toast.LENGTH_SHORT).show();
                } else if (confirmPassword.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.registration_confirm_password_empty), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private boolean checkPasswordLength(EditText passwordField) {
        int minLength = getApplicationContext().getResources().getInteger(R.integer.minPasswordLength);
        int maxLength = getApplicationContext().getResources().getInteger(R.integer.maxPasswordLength);
        boolean pass = false;

        if (minLength > passwordField.getText().toString().length()) {
            Toast.makeText(this, getApplicationContext().getResources().getString(R.string.registration_password_short), Toast.LENGTH_SHORT).show();
        } else if (maxLength < passwordField.getText().toString().length()) {
            Toast.makeText(this, getApplicationContext().getResources().getString(R.string.registration_password_long), Toast.LENGTH_SHORT).show();
        } else {
            pass = true;
        }
        return pass;
    }
}
