package com.manoloTech.skyrunner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by manueltenorio on 3/4/18.
 */

public class GameView extends SurfaceView implements Runnable{

    //boolean variable to track if the game is playing or not
    volatile boolean playing;

    //the game thread
    private Thread gameThread = null;

    //adding the player to this class
    private Player player;

    //These objects will be used for drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    //adding a stars list
    private ArrayList<Star> stars = new ArrayList<>();

    //adding enemies object array
    private Enemy[] enemies;

    //adding 3 enemies
    private int enemyCount = 3;

    //defining a boom object to display blast
    private Boom boom;

    //created a reference of the class Friend
    private Friend friend;

    //a screenX holder
    int screenX;

    //to count the number of Misses
    int countMisses;

    //indicator that the enemy has just entered the game screen
    boolean flag ;

    //an indicator if the game is Over
    private boolean isGameOver ;

    //the score holder
    int score;

    //the high Scores Holder
    int highScore[] = new int[4];

    //Shared Prefernces to store the High Scores
    SharedPreferences sharedPreferences;

    //Class constructor
    public GameView(Context context, int screenX, int screenY) {

        super(context);

        //initializing player object
        //this time also passing screen size to player constructor
        player = new Player(context, screenX, screenY);

        //initializing drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();

        //adding 100 stars
        int starNums = 100;

        for(int i = 0; i < starNums; i++){
            Star s = new Star(screenX, screenY);
            stars.add(s);
        }

        //initializing the enemy object array
        enemies = new Enemy[enemyCount];

        for(int i = 0; i < enemyCount; i++){
            enemies[i] = new Enemy(context, screenX, screenY);
        }

        //initializing the boom object
        boom = new Boom(context);

        //initializing the Friend class object
        friend = new Friend(context, screenX, screenY);

        this.screenX = screenX;

        countMisses = 0;

        isGameOver = false;

        //setting the score to 0 initially
        score = 0;

        sharedPreferences = context.getSharedPreferences("SHAR_PREF_NAME",Context.MODE_PRIVATE);

        //initializing the array high scores with the previous values
        highScore[0] = sharedPreferences.getInt("score1",0);
        highScore[1] = sharedPreferences.getInt("score2",0);
        highScore[2] = sharedPreferences.getInt("score3",0);
        highScore[3] = sharedPreferences.getInt("score4",0);
    }

    /**
     * Separate thread runs the game
     */
    @Override
    public void run() {

        //while loop runs the game
        while (playing) {

            //to update the frame
            update();

            //to draw the frame
            draw();

            //to control
            control();
        }

    }

    /**
     * Looks for touch input and acts accordingly
     * A press boosts the ship up and no action will boost the ship down
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_UP:

                //stopping the boosting when screen is released
                player.stopBoosting();

                break;
            case MotionEvent.ACTION_DOWN:

                //boosting the space jet when screen is pressed
                player.setBoosting();
                break;
        }
        return true;
    }

    /**
     * Here we will update the coordinate of our characters.
     */
    private void update() {

        score++;

        this.player.update();

        boom.setX(-300);
        boom.setX(-300);

        //updating the stars with the player speed
        for(Star s: stars){
            s.update(player.getSpeed());
        }


        //updating the enemy coordinate with respect to player speed
        for(int i = 0; i < enemyCount; i++){

            //setting the flag true when the enemy just enters the screen
            if(enemies[i].getX()==screenX){
                flag = true;
            }

            enemies[i].update(player.getSpeed());

            //if collision occurrs with player
            if (Rect.intersects(player.getDetectCollision(), enemies[i].getDetectCollision())) {

                //displaying boom at that location
                boom.setX(enemies[i].getX());
                boom.setY(enemies[i].getY());

                //moving enemy outside the left edge
                enemies[i].setX(-300);

            }else{

                if(flag){

                    //if player's x coordinate is more than the enemies's x coordinate.i.e. enemy has just passed across the player
                    if(player.getDetectCollision().exactCenterX() >= enemies[i].getDetectCollision().exactCenterX()){

                        //increment countMisses
                        countMisses++;

                        //setting the flag false so that the else part is executed only when new enemy enters the screen
                        flag = false;

                        //if no of Misses is equal to 3, then game is over.
                        if(countMisses==3){

                            //setting playing false to stop the game.
                            playing = false;
                            isGameOver = true;
                        }

                        //Assigning the scores to the high score integer array
                        for(int h = 0 ; h < 4; h++){
                            if(highScore[h] < score){

                                final int finalI = h;
                                highScore[h] = score;
                                break;
                            }
                        }

                        //storing the scores through shared Preferences
                        SharedPreferences.Editor e = sharedPreferences.edit();

                        for(int k = 0; k < 4; k++){

                            int j = k+1;

                            e.putInt("score"+j,highScore[k]);
                        }
                        e.apply();
                    }
                }
            }
        }

        //updating the friend ships coordinates
        friend.update(player.getSpeed());

        //checking for a collision between player and a friend
        if(Rect.intersects(player.getDetectCollision(),friend.getDetectCollision())){

            //displaying the boom at the collision
            boom.setX(friend.getX());
            boom.setY(friend.getY());

            //setting playing false to stop the game
            playing = false;

            //setting the isGameOver true as the game is over
            isGameOver = true;

            //Assigning the scores to the high score integer array
            for(int i = 0 ; i < 4; i++){
                if(highScore[i] < score){

                    final int finalI = i;
                    highScore[i] = score;
                    break;
                }
            }

            //storing the scores through shared Preferences
            SharedPreferences.Editor e = sharedPreferences.edit();

            for(int i=0; i < 4; i++){

                int j = i+1;

                e.putInt("score"+j,highScore[i]);
            }
            e.apply();

        }

    }

    /**
     * Here we will draw the characters to the canvas.
     */
    private void draw() {

        //checking if surface is valid
        if (surfaceHolder.getSurface().isValid()) {

            //locking the canvas
            canvas = surfaceHolder.lockCanvas();
            //drawing a background color for canvas
            canvas.drawColor(Color.BLACK);

            //setting the paint color to white to draw the stars
            paint.setColor(Color.WHITE);

            //drawing all the stars
            for(Star s : stars){

                paint.setStrokeWidth(s.getStarWidth());
                canvas.drawPoint(s.getX(),s.getY(), paint);
            }

            //drawing the score on the game screen
            paint.setTextSize(80);
            canvas.drawText("Score: "+score,1250,90,paint);

            //Drawing the player
            canvas.drawBitmap(
                    player.getBitmap(),
                    player.getX(),
                    player.getY(),
                    paint);

            //Drawing the enemies
            for (int i = 0; i < enemyCount; i++) {
                canvas.drawBitmap(
                        enemies[i].getBitmap(),
                        enemies[i].getX(),
                        enemies[i].getY(),
                        paint
                );
            }

            //drawing boom image
            canvas.drawBitmap(
                    boom.getBitmap(),
                    boom.getX(),
                    boom.getY(),
                    paint
            );

            //drawing friends image
            canvas.drawBitmap(

                    friend.getBitmap(),
                    friend.getX(),
                    friend.getY(),
                    paint
            );

            //draw game Over when the game is over
            if(isGameOver){

                paint.setTextSize(150);
                paint.setTextAlign(Paint.Align.CENTER);

                int yPos=(int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
                canvas.drawText("Game Over",canvas.getWidth()/2,yPos,paint);
            }

            //Unlocking the canvas
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    /**
     * Pauses the game thread for 17 milliseconds
     * This method will control the frames per seconds drawn.
     * Here we are calling the delay method of Thread. And this is actually making our frame rate to aroud 60fps.
     * After these we have two more methods.
     */
    private void control() {

        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pause the game
     * To pause the game, we are stopping the gameThread here.
     */
    public void pause() {

        //when the game is paused
        //setting the variable to false
        playing = false;
        try {
            //stopping the thread
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    /**
     * Resume game play
     */
    public void resume() {
        //when the game is resumed
        //starting the thread again
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
}
