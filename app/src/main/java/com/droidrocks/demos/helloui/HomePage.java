package com.droidrocks.demos.helloui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.droidrocks.demos.helloui.Login;
import com.example.hollisinman.helloui.R;

public class HomePage extends AppCompatActivity implements View.OnClickListener{

    TextView welcome;
    Button goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        welcome = findViewById(R.id.txt_welcome);
        goBack = findViewById(R.id.btn_go_back);

        goBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_go_back:
                Intent loginPage = new Intent(this, Login.class);
                startActivity(loginPage);
                // No "break" is necessary when we only have one case
        }
    }
}
