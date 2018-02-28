package com.example.manueltenorio.androidskyrunner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class DecisionPage extends AppCompatActivity {

    private Random rm = new Random();
    private int counter = 0;
    private int numOfPlayers = 0;
    private int currentPlayer = rm.nextInt(2) + 1;
    TextView playerTextView = null;
    TextView resultTextView = null;
    Button btnAddOne = null;
    Button btnAddTwo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decision_page);

        if (getIntent().hasExtra("com.example.manueltenorio.androidskyrunner.numPlayers")) {
            numOfPlayers = getIntent().getExtras().getInt("com.example.manueltenorio.androidskyrunner.numPlayers");
        }

        playerTextView = (TextView) findViewById(R.id.playerTextView);

        switch (numOfPlayers) {
            case 0:
                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainIntent);
                break;
            case 1:
                onePlayerGame();
                break;
            case 2:
                twoPlayerGame();
                break;
        }
    }

    public void onePlayerGame() {
        btnAddOne = (Button) findViewById(R.id.btnAddOne);
        btnAddTwo = (Button) findViewById(R.id.btnAddTwo);

        btnAddOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOne();
            }
        });
        btnAddTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTwo();
            }
        });

    }

    public void twoPlayerGame() {
        playerTextView.setText("player" + currentPlayer);

        btnAddOne = (Button) findViewById(R.id.btnAddOne);
        btnAddTwo = (Button) findViewById(R.id.btnAddTwo);

        btnAddOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOne();
                checkWinner();
            }
        });
        btnAddTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTwo();
                checkWinner();
            }
        });
    }

    public void addOne() {
        counter+=1;
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        resultTextView.setText(counter + "");
    }

    public void addTwo() {
        counter+=2;
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        resultTextView.setText(counter + "");
    }

    public void switchPlayers() {
        if (currentPlayer == 1) {
            currentPlayer = 2;
        } else {
            currentPlayer = 1;
        }
        playerTextView.setText("Player" + currentPlayer);
    }

    public void checkWinner() {
        if (counter < 21) {
            switchPlayers();
        } else{
            resultTextView.setText("Player " + currentPlayer + " wins!!!");
            btnAddOne.setEnabled(false);
            btnAddTwo.setEnabled(false);
        }
    }
}
