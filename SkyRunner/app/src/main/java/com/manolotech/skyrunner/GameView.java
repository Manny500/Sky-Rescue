package com.manolotech.skyrunner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

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

        this.player.update();

        //updating the stars with the player speed
        for(Star s: stars){
            s.update(player.getSpeed());
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

            //Drawing the player
            canvas.drawBitmap(
                    player.getBitmap(),
                    player.getX(),
                    player.getY(),
                    paint);

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
