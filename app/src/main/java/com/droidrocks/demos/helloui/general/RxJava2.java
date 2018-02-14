package com.droidrocks.demos.helloui.general;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.hollisinman.helloui.R;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxJava2 extends AppCompatActivity {

    private final String TAG = RxJava2.this.getClass().getSimpleName();

    TextView txt_rx_output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java2);

        txt_rx_output = findViewById(R.id.txt_rx_output);
    }

    @Override
    protected void onResume() {
        super.onResume();

        rxHelloWorld();
    }

    void rxHelloWorld() {
        Flowable.just("Hello world")
                .subscribe(new Consumer<String>() {
                    @Override public void accept(String s) {
                        txt_rx_output.setText(s);
                    }
                });
    }

    void rxSchedulerDemo() {
        Flowable.fromCallable(() -> {
            Thread.sleep(1000); //  imitate expensive computation
            return "Done";
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(System.out::println, Throwable::printStackTrace);

        try {
            Thread.sleep(2000); // <--- wait for the flow to finish
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
