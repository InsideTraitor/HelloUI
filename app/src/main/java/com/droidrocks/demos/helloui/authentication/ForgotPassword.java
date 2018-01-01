package com.droidrocks.demos.helloui.authentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hollisinman.helloui.R;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener{

    EditText et_email;
    Button btn_send_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        et_email = findViewById(R.id.et_email);
        btn_send_password = findViewById(R.id.btn_send_password);

        btn_send_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (!et_email.getText().toString().isEmpty()) {
            if (AppAccess.getInstance().getAllowed().containsKey(et_email.getText().toString())) {
                emailImplicitIntent(et_email.getText().toString(), "Droid Rocks Password Reminder", "Your password is: " + AppAccess.getInstance().getAllowed().get(et_email.getText().toString()), "Choose app to send your password:");
//                Toast.makeText(getApplicationContext(), "Your password is: " + AppAccess.getInstance().getAllowed().get(et_email.getText().toString()), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Sorry, that email is not registered", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "You must enter an email to retrieve your password", Toast.LENGTH_SHORT).show();
        }
    }

    private void emailImplicitIntent(String toEmailAddress, String subject, String body, String intentTitle) {
        Intent sendPasswordReminder = new Intent(Intent.ACTION_SEND);
        sendPasswordReminder.setType("text/html");
        sendPasswordReminder.putExtra(Intent.EXTRA_EMAIL, toEmailAddress);
        sendPasswordReminder.putExtra(Intent.EXTRA_SUBJECT, subject);
        sendPasswordReminder.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(Intent.createChooser(sendPasswordReminder, intentTitle));
    }
}
