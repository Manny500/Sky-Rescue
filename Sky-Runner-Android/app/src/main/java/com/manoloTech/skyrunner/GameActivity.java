package com.manoloTech.skyrunner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    //Private Variables
    private InterstitialAd mInterstitialAd;

    //declaring gameview
    private GameView gameView;

    private Button endGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        //banner ad
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //interstitial ad
        mInterstitialAd = new InterstitialAd(this);
        //replace id with your own
        mInterstitialAd.setAdUnitId("ca-app-pub-7093564868978624~1313063741");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                finish();
            }
        });

        //////////Logic//////////////////

        //Getting display object
        Display display = getWindowManager().getDefaultDisplay();

        //Getting the screen resolution into point object
        Point size = new Point();
        display.getSize(size);

        FrameLayout game = new FrameLayout(this);

        RelativeLayout gameWidgets = new RelativeLayout (this);

        endGameButton = new Button(this);
        TextView myText = new TextView(this);

        endGameButton.setWidth(300);
        endGameButton.setX(10);
        endGameButton.setY(10);
        endGameButton.setText("Play Again");
        //endGameButton.setVisibility(View.INVISIBLE);
        myText.setText("rIZ..i");

        gameWidgets.addView(myText);
        gameWidgets.addView(endGameButton);

        //Initializing game view object
        gameView = new GameView(this, size.x, size.y);

        game.addView(gameView);
        game.addView(gameWidgets);

        //adding it to content view
        setContentView(game);
        //endGameButton.setVisibility(View.VISIBLE);
        endGameButton.setOnClickListener(this);

    }//end of onCreate

    // the onclick methods
    @Override
    public void onClick(View v) {

        if (v == endGameButton) {
            //the transition from MainActivity to GameActivity
            startActivity(new Intent(GameActivity.this, MainActivity.class));
        }

    }

    //pausing the game when activity is paused
    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    //running the game when activity is resumed
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    /**
     * In order to show the ads
     */
    public void showInterstitial() {

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            finish();
        }
    }

    /**
     * In order to exit the ad
     */
    @Override
    public void onBackPressed() {
        //show ad of app exit

    }
}