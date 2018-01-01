package com.droidrocks.demos.helloui.general;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hollisinman.helloui.R;

public class Calculator extends AppCompatActivity implements View.OnClickListener{

    Button btn_add;
    Button btn_subtract;
    Button btn_multiply;
    Button btn_divide;

    EditText et_num1;
    EditText et_num2;
    TextView txt_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        btn_add = findViewById(R.id.btn_add);
        btn_subtract = findViewById(R.id.btn_subtract);
        btn_multiply = findViewById(R.id.btn_multiply);
        btn_divide = findViewById(R.id.btn_divide);

        et_num1 = findViewById(R.id.et_num1);
        et_num2 = findViewById(R.id.et_num2);
        txt_result = findViewById(R.id.txt_result);

        btn_add.setOnClickListener(this);
        btn_subtract.setOnClickListener(this);
        btn_multiply.setOnClickListener(this);
        btn_divide.setOnClickListener(this);

    }

    private Double convertTextViewToDouble(TextView textView) {
        return Double.valueOf(textView.getText().toString());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                // Add
                txt_result.setText(String.valueOf(Arithmetic.add(convertTextViewToDouble(et_num1), convertTextViewToDouble(et_num2))));
                break;
            case R.id.btn_subtract:
                // Subtract
                txt_result.setText(String.valueOf(Arithmetic.subtract(convertTextViewToDouble(et_num1), convertTextViewToDouble(et_num2))));
                break;
            case R.id.btn_multiply:
                // Multiply
                txt_result.setText(String.valueOf(Arithmetic.multiply(convertTextViewToDouble(et_num1), convertTextViewToDouble(et_num2))));
                break;
            case R.id.btn_divide:
                // Divide
                txt_result.setText(String.valueOf(Arithmetic.divide(convertTextViewToDouble(et_num1), convertTextViewToDouble(et_num2))));
                break;
        }
    }
}
