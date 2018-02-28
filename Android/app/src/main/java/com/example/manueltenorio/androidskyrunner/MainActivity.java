package com.example.manueltenorio.androidskyrunner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Reference a button that we are going to search for
        Button addBtn = (Button) findViewById(R.id.addBtn);

        //assign an on click method
        addBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                EditText firstNumText = (EditText) findViewById(R.id.firstNumText);
                EditText secondNumText = (EditText) findViewById(R.id.secondNumText);

                TextView resultTextView = (TextView) findViewById(R.id.resultTextView);

                int num1 = Integer.parseInt(firstNumText.getText().toString());
                int num2 = Integer.parseInt(secondNumText.getText().toString());
                int result = num1 + num2;

                resultTextView.setText(result + "");

            }
        });

    }
}
