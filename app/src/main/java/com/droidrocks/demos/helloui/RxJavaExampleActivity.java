package com.droidrocks.demos.helloui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hollisinman.helloui.R;
import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class RxJavaExampleActivity extends AppCompatActivity {

    EditText editText;
    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_example);

        editText = findViewById(R.id.my_edit_text);
        textView = findViewById(R.id.my_text_view);
        button = findViewById(R.id.submit_button);
    }

    @Override
    protected void onResume() {
        super.onResume();

        RxView.clicks(button)
                .map(new Function<Object, String>() {
                    @Override
                    public String apply(Object o) throws Exception {
                        return editText.getText().toString();
                    }
                })
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String returnVal) throws Exception {
                        textView.setText(returnVal);
                    }
                })
                .subscribe();
    }
}
