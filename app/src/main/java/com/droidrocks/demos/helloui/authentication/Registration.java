package com.droidrocks.demos.helloui.authentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hollisinman.helloui.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registration extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG_REGISTRATION = "TAG_REGISTRATION";

    EditText username;
    EditText password;
    EditText confirmPassword;
    Button register;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();

        // Find views
        username = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);
        confirmPassword = findViewById(R.id.et_confirm_password);
        register = findViewById(R.id.btn_register);

        // Register OnClickListener with our views
        register.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    // Creates a new user account with supplied email and password
    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            Log.i(TAG_REGISTRATION, task.getResult().toString());
                            Log.d(TAG_REGISTRATION, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Registration.this, R.string.registration_success,
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Registration.this, Login.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG_REGISTRATION, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Registration.this, R.string.registration_failure,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                // If username, password and confirmPassword are not empty
                if (!username.getText().toString().isEmpty() && !password.getText().toString().isEmpty() && !confirmPassword.getText().toString().isEmpty()) {
                    // If password and confirmPassword match
                    if (password.getText().toString().equals(confirmPassword.getText().toString())) {
                        // Register user
                        if (checkPasswordLength(password) && checkPasswordLength(confirmPassword)) {
                            createAccount(username.getText().toString(), password.getText().toString());
                            Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.registration_success), Toast.LENGTH_SHORT).show();
                            // Return newly registered user back to Login
                            startActivity(new Intent(getApplicationContext(), Login.class));
                        }
                        // User's passwords do not match
                    } else {
                        Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.registration_password_mismatch), Toast.LENGTH_SHORT).show();
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
}
