package com.droidrocks.demos.helloui.general;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hollisinman.helloui.R;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxJava2 extends AppCompatActivity {

    private final String TAG = RxJava2.this.getClass().getSimpleName();

    TextView txt_rx_output;
    Button btn_rx_java_2;


    final Observable<Integer> serverDownloadObservable = Observable.create(emitter -> {
        SystemClock.sleep(1000); // simulate delay
        emitter.onNext(5);
        emitter.onComplete();
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java2);

        txt_rx_output = findViewById(R.id.txt_rx_output);
        btn_rx_java_2 = findViewById(R.id.btn_rx_java_2);

        btn_rx_java_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runIntegerObservable(serverDownloadObservable);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        rxHelloWorld();
        rxSchedulerDemo();
        rxSequentialSquares();
        rxParallelSquares();
        rxParallelOperator();
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
            Thread.sleep(5000); //  imitate expensive computation

            return "Done";
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(System.out::println, Throwable::printStackTrace);
    }

    void rxSequentialSquares() {
        Flowable.range(1, 10)
                .observeOn(Schedulers.computation())
                .map(v -> performSquare(v))
                .blockingSubscribe(System.out::println);
    }

    void rxParallelSquares() {
        Flowable.range(1, 10)
                .flatMap(v ->
                        Flowable.just(v)
                                .subscribeOn(Schedulers.computation())
                                .map(w -> w * w)
                )
                .blockingSubscribe(System.out::println);
    }

    void rxParallelOperator() {
        Flowable.range(1, 10)
                .parallel()
                .runOn(Schedulers.computation())
                .map(v -> v * v)
                .sequential()
                .blockingSubscribe(System.out::println);
    }

    void runIntegerObservable(Observable<Integer> integerObservable) {
        integerObservable.
                observeOn(AndroidSchedulers.mainThread()).
                subscribeOn(Schedulers.io()).
                subscribe(integer -> {
                    updateTheUserInterface(integer); // this methods updates the ui
                    btn_rx_java_2.setEnabled(true); // enables it again
                });
    }

    // Generate a computationally expensive operation using a large enough square
    void loopThroughSquares(float count) {
        for (int i = 0; i < count; i++) {
            System.out.println(i*i);
        }
    }

    int performSquare(int numberToSquare) {
        return numberToSquare * numberToSquare;
    }

    void updateTheUserInterface(Integer integer) {
        txt_rx_output.setText(String.valueOf(integer));
    }
}
