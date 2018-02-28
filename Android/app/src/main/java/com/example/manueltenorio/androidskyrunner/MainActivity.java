package com.example.manueltenorio.androidskyrunner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnOnePLayer = (Button) findViewById(R.id.btnOnePlayer);
        Button btnTwoPLayer = (Button) findViewById(R.id.btnTwoPlayer);

        btnTwoPLayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(getPlayers(2));
            }
        });

        btnOnePLayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(getPlayers(1));
            }
        });
    }

    public Intent getPlayers(int value){
        Intent PlayerIntent = new Intent(getApplicationContext(), DecisionPage.class);
        PlayerIntent.putExtra("com.example.manueltenorio.androidskyrunner.numPlayers", value);
        return PlayerIntent;
    }
}
