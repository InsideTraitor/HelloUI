package com.example.hollisinman.helloui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private EditText et_email;
    private EditText et_password;
    private Button btn_login;

    private String foo = "foo";
    private String bar = "bar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_email = findViewById(R.id.email);
        et_password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);

        et_email.setOnClickListener(this);
        et_password.setOnClickListener(this);
        btn_login.setOnClickListener(this);


    }

    private boolean isEditTextEmpty(EditText editText) {
        return editText.getText().toString().isEmpty();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.email:
                // Do nothing
                break;
            case R.id.password:
                // Do nothing
                break;
            case R.id.btn_login:
                boolean emailEmpty = isEditTextEmpty(et_email);
                boolean passwordEmpty = isEditTextEmpty(et_password);

                // Validate user input
                if (!emailEmpty && !passwordEmpty) {
                    Toast.makeText(getApplicationContext(), "Launching HomePage Activity", Toast.LENGTH_SHORT).show();
                    Intent homepage = new Intent(this, HomePage.class);
                    startActivity(homepage);
                } else if (emailEmpty) {
                    Toast.makeText(getApplicationContext(), "Your email field is blank", Toast.LENGTH_SHORT).show();
                } else if (passwordEmpty) {
                    Toast.makeText(getApplicationContext(), "Your password field is blank", Toast.LENGTH_SHORT).show();
                } else { // Unreachable
                    Toast.makeText(getApplicationContext(), "Couldn't figure out what fields were empty", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
