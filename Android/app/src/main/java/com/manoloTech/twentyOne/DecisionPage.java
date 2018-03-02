package com.manoloTech.twentyOne;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Random;

public class DecisionPage extends AppCompatActivity {

    private Random rm = new Random();
    private int counter = 0;
    private int numOfPlayers = 0;
    private int currentPlayer = rm.nextInt(2) + 1;
    private boolean computerEnabled = false;
    private String playerStr = "";
    private TextView playerTextView = null;
    private TextView resultTextView = null;
    private Button btnAddOne = null;
    private Button btnAddTwo = null;
    private Button btnPlayAgain = null;

    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decision_page);

        //banner ad
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //interstitial ad
        mInterstitialAd = new InterstitialAd(this);
        //replace id with your own
        mInterstitialAd.setAdUnitId("ca-app-pub-7093564868978624~7042310253");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed(){
                super.onAdClosed();
                finish();
            }
        });


        if (getIntent().hasExtra("com.manoloTech.twentyOne.numPlayers")) {
            numOfPlayers = getIntent().getExtras().getInt("com.manoloTech.twentyOne.numPlayers");
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

    public void showInterstitial(){

        if(mInterstitialAd.isLoaded()){
            mInterstitialAd.show();
        }else{
            finish();
        }
    }

    @Override
    public void onBackPressed(){
        //show ad of app exit
        showInterstitial();
    }

    public void onePlayerGame() {
        computerEnabled = true;

        btnAddOne = (Button) findViewById(R.id.btnAddOne);
        btnAddTwo = (Button) findViewById(R.id.btnAddTwo);

        btnAddOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOne();
                checkWin();
            }
        });
        btnAddTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTwo();
                checkWin();
            }
        });
    }

    public void twoPlayerGame() {
        playerTextView.setText("Player " + currentPlayer);

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

    public void aiMove(){
        int aiChoice = (rm.nextInt(2) + 1);
        if(aiChoice == 1){
            addOne();

        }else{
            addTwo();

        }
        checkWin();
    }
    public void switchTurns(){
        if (currentPlayer == 1) {
            currentPlayer = 2;
            playerTextView.setText("Player: AI");
            playerStr = "AI";
            aiMove();

        } else {
            currentPlayer = 1;
            playerTextView.setText("Player: 1");
            playerStr = "Player: 1";
        }
    }
    public void switchPlayers() {
        if (currentPlayer == 1) {
            currentPlayer = 2;
        } else {
            currentPlayer = 1;
        }
        playerTextView.setText("Player " + currentPlayer);
    }

    public void checkWinner() {
        btnPlayAgain = (Button) findViewById(R.id.btnPlayAgain);
        if (counter < 21) {
            switchPlayers();
        } else{
            resultTextView.setText("Player " + currentPlayer + " wins!!!");
            btnAddOne.setEnabled(false);
            btnAddTwo.setEnabled(false);
            btnPlayAgain.setVisibility(View.VISIBLE);

            btnPlayAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent MainIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(MainIntent);
                }
            });
        }
    }

    public void checkWin() {
        btnPlayAgain = (Button) findViewById(R.id.btnPlayAgain);
        if (counter < 21) {
            switchTurns();
        } else{
            resultTextView.setText(playerStr+ " wins!!!");
            btnAddOne.setEnabled(false);
            btnAddTwo.setEnabled(false);
            btnPlayAgain.setVisibility(View.VISIBLE);

            btnPlayAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent MainIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(MainIntent);
                }
            });
        }
    }
}
