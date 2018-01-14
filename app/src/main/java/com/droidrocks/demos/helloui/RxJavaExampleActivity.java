package com.droidrocks.demos.helloui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hollisinman.helloui.R;
import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class RxJavaExampleActivity extends AppCompatActivity {

    EditText passwordEditText1;
    EditText passwordEditText2;
    EditText usernameEditText;
    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_example);

        passwordEditText1 = findViewById(R.id.password_edit_text1);
        passwordEditText2 = findViewById(R.id.password_edit_text2);
        usernameEditText = findViewById(R.id.username_edit_text);

        textView = findViewById(R.id.my_text_view);
        button = findViewById(R.id.submit_button);
    }

    @Override
    protected void onResume() {
        super.onResume();

        RxView.clicks(button)
                .flatMap(new Function<Object, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Object o) throws Exception {
                        String pass1 = passwordEditText1.getText().toString();
                        String pass2 = passwordEditText2.getText().toString();
                        if(pass1.equals(pass2)){
                            return Observable.just(pass1);
                        } else {
                            return Observable.error(new IllegalArgumentException("Passwords do not match"));
                        }
                    }
                })
                .map(new Function<String, String>() {
                         @Override
                         public String apply(String pass1) throws Exception {
                             return pass1 + " " + usernameEditText.getText().toString();
                         }
                     }
                )
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String returnVal) throws Exception {
                        textView.setText(returnVal);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        textView.setText("Could not validate: " + throwable.getMessage());
                    }
                })
                .subscribe();
    }
}









