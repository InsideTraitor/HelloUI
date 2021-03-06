// The package statement for the Login class
package com.droidrocks.demos.helloui.authentication;

// These are the Classes we're importing from their respective packages

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.droidrocks.demos.helloui.demos.ReferenceDataTypes;
import com.droidrocks.demos.helloui.general.NavDrawer;
import com.example.hollisinman.helloui.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// Class Login extends abstract class AppCompatActivity and implements interface View.OnClickListener
public class Login extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG_LOGIN = "TAG_LOGIN";

    // Define the fields for our Class - Class Variables aka Instance Variables
    private EditText et_email;
    private EditText et_password;

    private FirebaseAuth mAuth;

    // This is the entry point for our Activity's lifecycle - this always comes before "onStart()"
    // Set up any resources we can before the application becomes visible to the user
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ConstraintLayout constraintLayout = findViewById(R.id.constraint_layout);
        TextView txt_forgot_password;
        TextView txt_register;
        Button btn_login;

        mAuth = FirebaseAuth.getInstance();

        // Find our Views so the corresponding objects we've declared can be inflated
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        txt_forgot_password = findViewById(R.id.txt_forgot_password);
        txt_register = findViewById(R.id.txt_register);

        // Attach "this" OnClickListener - the Activity's implementation of View.OnClickListener
        et_email.setOnClickListener(this);
        et_password.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        txt_forgot_password.setOnClickListener(this);
        txt_register.setOnClickListener(this);
        constraintLayout.setOnClickListener(this);
    }

    // Called after directly after onCreate() or after onRestart() (In the event that the Activity was stopped and is restarting)
    @Override
    protected void onStart() {
        // To @Override, we have to make a call to the parent's (AppCompatActivity) method "onStart()"
        super.onStart();

        // Check if user is signed in redirect to NavDrawer if so.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(Login.this, NavDrawer.class));
        }

        System.out.println(R.string.onStart);
    }

    // Called immediately before the Activity becomes visible
    // Use this method to update views (if necessary) when resuming the Activity, for instance, after onStop() was called
    @Override
    protected void onResume() {
        super.onResume();

//        RxView.clicks(btn_login)
//                .map(new Function<Object, HashMap<String, String>>() {
//
//                    @Override
//                    public HashMap<String, String> apply(Object o) throws Exception {
//                        HashMap<String, String> usernameAndPassword = new HashMap<>();
//                        if (!isEditTextEmpty(et_email) && !isEditTextEmpty(et_password)) {
//                            usernameAndPassword.put(et_email.getText().toString(), et_password.getText().toString());
//                        } else if (isEditTextEmpty(et_email)) { // et_email is empty
//                            throw new IllegalArgumentException("Your email field is blank");
//                        } else if (isEditTextEmpty(et_password)) { // et_password is empty
//                            throw new IllegalArgumentException("Your password field is blank");
//                        }
//                        return usernameAndPassword;
//                    }
//                })
//        .doOnNext(new Consumer<HashMap<String, String>>() {
//            @Override
//            public void accept(HashMap<String, String> hashMap) throws Exception {
//                mAuth.signInWithEmailAndPassword(et_email.getText().toString(), et_password.getText().toString())
//                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    // Sign in success, update UI with the signed-in user's information
//                                    Log.d(TAG_LOGIN, "signInWithEmail:success");
//                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    updateUI(user);
//                                } else {
//                                    // If sign in fails, display a message to the user.
////                                    Log.w(TAG_LOGIN, "signInWithEmail:failure", task.getException());
////                                    Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
////                                            Toast.LENGTH_SHORT).show();
//                                    updateUI(null);
//                                }
//
//                                // ...
//                            }
//                        });
//            }
//        })
//        .doOnError(new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        })
//        .subscribe(
//                new Observer<HashMap<String, String>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.i(TAG_LOGIN, "In Observer onSubscribe, value of Disposable d: " + d.toString());
//                    }
//
//                    @Override
//                    public void onNext(HashMap<String, String> hashMap) {
//                        Log.i(TAG_LOGIN, "In Observer onNext, value of String s: ");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.i(TAG_LOGIN, "In Observer onError, value of e:" + e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.i(TAG_LOGIN, "In Observer onComplete");
//                    }
//                }
//        );

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

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), "Authentication succeed.",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this, NavDrawer.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG_LOGIN, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validateInput(EditText email, EditText password) {

        boolean emailEmpty = isEditTextEmpty(email);
        boolean passwordEmpty = isEditTextEmpty(password);

        // Validate user input
        if (emailEmpty) { // et_email is empty
            Toast.makeText(getApplicationContext(), "Your email field is blank", Toast.LENGTH_SHORT).show();
            return false;
        } else if (passwordEmpty) { // et_password is empty
            Toast.makeText(getApplicationContext(), "Your password field is blank", Toast.LENGTH_SHORT).show();
            return false;
        } else { // both fields have user input
            return true;
        }
    }

    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        if (imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    // Login's implementation of View.OnClickListener
    @Override
    public void onClick(View view) {
        // We'll switch on the Resource identifier (of type int)
        // We can't switch on the type "View" directly because it is not an acceptable argument for switch()
        switch (view.getId()) {
            // Since we're not doing anything when these views are clicked, we have them here simply to show they can be accessed
            case R.id.et_email:
                // Do nothing
                break; // ends the case
            case R.id.et_password:
                // Do nothing
                break;
            // Navigate to Homepage only if both et_email and et_password are non-blank (they contain text)
            case R.id.btn_login:
                if (validateInput(et_email, et_password)) {
                    signIn(et_email.getText().toString(), et_password.getText().toString());
                }
                hideSoftKeyBoard();
                break;
            case R.id.txt_forgot_password:
                Intent forgotPassword = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(forgotPassword);
                break;
            case R.id.txt_register:
                startActivity(new Intent(getApplicationContext(), Registration.class));
                break;
            case R.id.constraint_layout:
                hideSoftKeyBoard();
                break;
        } // Closes switch(view.getId())
    } // Closes onClick(View view)
} // Closes Login
