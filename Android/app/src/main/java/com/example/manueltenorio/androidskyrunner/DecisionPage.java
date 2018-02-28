package com.example.manueltenorio.androidskyrunner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DecisionPage extends AppCompatActivity {

    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decision_page);

        Button btnAddOne = (Button) findViewById(R.id.btnAddOne);
        Button btnAddTwo = (Button) findViewById(R.id.btnAddTwo);


        btnAddOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter+=1;
                TextView resultTextView = (TextView) findViewById(R.id.resultTextView);
                resultTextView.setText(counter + "");
            }
        });

        btnAddTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter+=2;
                TextView resultTextView = (TextView) findViewById(R.id.resultTextView);
                resultTextView.setText(counter + "");
            }
        });
    }
}
