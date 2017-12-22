// The package statement for the Login class
package com.droidrocks.demos.helloui;

// These are the Classes we're importing from their respective packages
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hollisinman.helloui.R;

// Class Login extends abstract class AppCompatActivity and implements interface View.OnClickListener
public class Login extends AppCompatActivity implements View.OnClickListener{

    // Define the fields for our Class - Class Variables aka Instance Variables
    private EditText et_email;
    private EditText et_password;
    private Button btn_login;

    // This is the entry point for our Activity's lifecycle
    // Set up any resources we can before the application becomes visible to the user
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Find our Views so the corresponding objects we've declared can be inflated
        et_email = findViewById(R.id.email);
        et_password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);

        // Attach "this" OnClickListener - the Activity's implementation of View.OnClickListener
        et_email.setOnClickListener(this);
        et_password.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    // Called after directly after onCreate() or after onRestart() (In the event that the Activity was stopped and is restarting)
    @Override
    protected void onStart() {
        // To @Override, we have to make a call to the parent's (AppCompatActivity) method "onStart()"
        super.onStart();

        System.out.println(R.string.onStart);
    }

    // Called immediately before the Activity becomes visible
    // Use this method to update views (if necessary) when resuming the Activity, for instance, after onStop() was called
    @Override
    protected void onResume() {
        super.onResume();

        System.out.println(R.string.onResume);
    }

    // Called immediately before the UI is hidden
    // Use this opportunity to save or store any data in the Activity
    @Override
    protected void onPause() {
        super.onPause();

        System.out.println(R.string.onPause);
    }

    // Called immediately after onPause() - the Activity is no longer visible
    // *** WARNING *** NOT GUARANTEED TO BE CALLED
    // Often used to stop processes that may have been spawned by the Activity
    @Override
    protected void onStop() {
        super.onStop();

        System.out.println(R.string.onStop);
    }

    // The last lifecycle method to be called - final chance to clean up resources
    // *** WARNING *** NOT GUARANTEED TO BE CALLED
    @Override
    protected void onDestroy() {
        super.onDestroy();

        System.out.println(R.string.onDestroy);
    }

    // Determine whether or not an EditText contains text
    private boolean isEditTextEmpty(EditText editText) {
        return editText.getText().toString().isEmpty();
    }

    // Login's implementation of View.OnClickListener
    @Override
    public void onClick(View view) {
        // We'll switch on the Resource identifier (of type int)
        // We can't switch on the type "View" directly because it is not an acceptable argument for switch()
        switch (view.getId()) {
            // Since we're not doing anything when these views are clicked, we have them here simply to show they can be accessed
            case R.id.email:
                // Do nothing
                break; // ends the case

            case R.id.password:
                // Do nothing
                break;

            // Navigate to Homepage only if both et_email and et_password are non-blank (they contain text)
            case R.id.btn_login:
                // Create local variables for our conditional statement
                boolean emailEmpty = isEditTextEmpty(et_email);
                boolean passwordEmpty = isEditTextEmpty(et_password);

                // Validate user input
                // Check the condition we're looking for first, then check to see what's wrong if our condition isn't met
                if (!emailEmpty && !passwordEmpty) {
                    // Create a Toast message (pop-up) that tells the user we're launching the Homepage
                    Toast.makeText(getApplicationContext(), "Launching HomePage Activity", Toast.LENGTH_SHORT).show();

                    // Create a new Intent and provide this (Login) as the Context and HomePage.class as the destination Activity
                    Intent homepage = new Intent(this, HomePage.class);
//                    // Start an Activity by passing an Intent
                    startActivity(homepage);

                } else if (emailEmpty) { // et_email is empty
                    Toast.makeText(getApplicationContext(), "Your email field is blank", Toast.LENGTH_SHORT).show();
                } else if (passwordEmpty) { // et_password is empty
                    Toast.makeText(getApplicationContext(), "Your password field is blank", Toast.LENGTH_SHORT).show();
                } else { // Unreachable - impossible for one of the conditions above to not have been met
                    Toast.makeText(getApplicationContext(), "This will never be executed", Toast.LENGTH_SHORT).show();
                }
                break;
        } // Closes switch(view.getId()
    } // Closes onClick(View view)
} // Closes Login
