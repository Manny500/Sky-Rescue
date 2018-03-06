package com.manoloTech.skyrunner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    //Private Variables
    private InterstitialAd mInterstitialAd;

    //declaring gameview
    private GameView gameView;

    private Button playAgainBtn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        playAgainBtn = (Button) findViewById(R.id.playAgainBtn);

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

        //Initializing game view object
        gameView = new GameView(this, size.x, size.y);

        //adding it to content view
        setContentView(gameView);

        playAgainBtn.setVisibility(View.VISIBLE);

        playAgainBtn.setOnClickListener(this);

    }//end of onCreate

    // the onclick methods
    @Override
    public void onClick(View v) {

        if (v == playAgainBtn) {
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