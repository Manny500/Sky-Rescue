package com.manoloTech.twentyOne;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnOnePLayer = (Button) findViewById(R.id.btnOnePlayer);
        Button btnTwoPLayer = (Button) findViewById(R.id.btnTwoPlayer);
        TextView btnHowToPlay = (TextView) findViewById(R.id.btnHowtoplay);

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

        btnHowToPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent howToIntent = new Intent(getApplicationContext(), HowToPlay.class);
                startActivity(howToIntent);
            }
        });
    }

    public Intent getPlayers(int value){
        Intent playerIntent = new Intent(getApplicationContext(), DecisionPage.class);
        playerIntent.putExtra("com.manoloTech.twentyOne.numPlayers", value);
        return playerIntent;
    }
}
