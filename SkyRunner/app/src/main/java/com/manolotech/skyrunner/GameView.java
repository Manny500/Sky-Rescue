package com.manolotech.skyrunner;

import android.content.Context;
import android.view.SurfaceView;

/**
 * Created by manueltenorio on 3/4/18.
 */

public class GameView extends SurfaceView implements Runnable{

    //boolean variable to track if the game is playing or not
    volatile boolean playing;

    //the game thread
    private Thread gameThread = null;

    //Class constructor
    public GameView(Context context) {
        super(context);
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
     * Here we will update the coordinate of our characters.
     */
    private void update() {

    }

    /**
     * Here we will draw the characters to the canvas.
     */
    private void draw() {

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
