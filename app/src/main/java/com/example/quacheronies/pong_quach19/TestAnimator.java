package cs301.pong;

import android.graphics.*;
import android.view.MotionEvent;
import java.util.Random;


/**
 * class that animates a ball repeatedly moving on a
 * simple background
 *
 * The biggest issue I had with this project was my version of Android
 * Studio not syncing correcting, preventing me from actually testing
 * the game out. I had to go on campus to test it out on the computers.
 * 
 * @author Steve Vegdahl
 * @author Andrew Nuxoll
 * @author Michael Quach
 * @version March 22, 2018
 */
public class BallAnimator implements Animator {

	//instance variables
	private final int ballRadius = 30; //radius of the ball ball

	private float x; //create a variable for the x position of the ball
	private float y; //create a variable for the y position of the ball
	private float diffX; //create a variable for the difference in x of the ball
	private float diffY; //create a variable for the difference in y of the ball
	private float velX; //create a variable for the x velocity of the ball
	private float velY; //create a variable for the x velocity of the ball

	private Paint paint = new Paint(); //create a paint command for the layout

	private boolean ballOut; //create a variable to check if the ball is
	//currently in play
	private boolean paddleSize; //create a variable to check if the
	//paddle is small or large.

	private String question = "Would you like to try again?"

	private Random gen = new Random(); //create a random number generator

	public BallAnimator(){
		//Set a randomized coordinate for the ball to be placed
		x = gen.nextInt(500);
		y = gen.nextInt(500);
		randomize(); //set a random direction and speed for ball to follow
		paint.setColor(Color.BLACK); //set the ball color to black
	}

	/**
	 * The background color: a light blue.
	 *
	 * @return the background color onto which we will draw the image.
	 */
	public int backgroundColor() {
		// create/return the background color
		return Color.rgb(180, 200, 255);
	}

	/**
	 * Set a random velocity and speed for the ball
	 * to travel at
	 *
	 */
	public void randomize(){

		//Pick a random initial velocity for the ball between 0 and 10
		velX = gen.nextInt(10);
		velY = gen.nextInt(10);

		//Set a specific y direction
		if(gen.nextInt(1) == 0){
			diffY = velY;
		}
		else {
			diffY = -velY;
		}

		//Set a specific x direction
		if(gen.nextInt(1) == 0){
			diffX = velX;
		}
		else {
			diffX = -velX;
		}
	}

	/**
	 * Paint the walls of the game on the Android layout
	 *
	 * @param canvas layout to be painted on
	 */
	public void drawWall(Canvas canvas) {
		canvas.drawRect(0.0f, 0.0f, (float)canvas.getWidth(), 100.0f, paint);
		canvas.drawRect(0.0f, 0.0f, 100.0f, (float)canvas.getHeight(), paint);
		canvas.drawRect((float)(canvas.getHeight() - 100), 0.0f,
				canvas.getWidth(), canvas.getHeight(), paint);
	}

	/**
	 * Paint the paddle used to control the ball
	 *
	 * @param canvas layout to be painted on
	 */
	public void drawPaddle(Canvas canvas) {
		//check to see if the paddle size is small
		if(paddleSize == true) {
			canvas.drawRect((float)(canvas.getWidth()-80),
					(float)((canvas.getHeight()/2)-160),
					(float)(canvas.getWidth()),
					(float)((canvas.getHeight()/2)+160), paint);
		}
		//if the paddle size is not small, paint the large one instead
		else {
			canvas.drawRect((float)(canvas.getWidth()-160),
					(float)((canvas.getHeight()/2)-320),
					(float)(canvas.getWidth()),
					(float)((canvas.getHeight()/2)+320), paint);
		}
	}

	/**
	 * Set the paddle to large
	 *
	 */
	public void setPaddleLarge() {
		paddleSize = true;
	}

	/**
	 * Set the paddle to small
	 *
	 */
	public void setPaddleSmall() {
		paddleSize = false;
	}

	/**
	 * Adds motion and animation to the ball when
	 * it collides on wall or paddle
	 *
	 * @param canvas canvas to be drawn on
	 */
	public void ballAnimation(Canvas canvas) {
		//Allow ball to move
		x += diffX;
		y += diffY;

		//if the ball collides with right wall, move it in opposite direction
		if((x - ballRadius) < 80) {
			diffX = -10 * 0.99f;
		}

		//If the ball collides with top wall, move it in opposite direction
		if((y - ballRadius) < 80) {
			diffY = 10 * 0.99f;
		}
		//If the ball collides with bottom wall, move it in opposite direction
		if((y + ballRadius) > canvas.getHeight() - 80) {
			diffY = -10 * 0.99f;

		}

		//Check the paddle size and launch the ball in the opposite direction
		//when collided
		if(paddleSize == true) { //small paddle
			if((x + ballRadius) > canvas.getWidth() - 80) {
				if((y >= (canvas.getHeight()/2) - 160)
						&& (y <= (canvas.getHeight()/2) + 160)) {
					diffX = 10 * 0.99f;
				}
			}
		}
		else if(paddleSize == false) { //large paddle
			if((x + ballRadius) > canvas.getWidth() - 150) {
				if((y >= (canvas.getHeight()/2) - 300)
						&& (y <= (canvas.getHeight()/2) + 300)) {
					diffX = 10 * 0.99f;
				}
			}
		}

		// place the ball at a random position if out-of-bounds
		if(x > canvas.getWidth()){
			ballOut = true;
			x = gen.nextInt(500) + 500;
			y = gen.nextInt(500) + 500;
			randomize();
		}

		//Confirm with player if they want to try again after loss
		if(ballOut) {
			paint.setTextSize(100.0f); //set the text size
			//ask if player wants to try again
			canvas.drawText(question, (canvas.getWidth()/2),
					canvas.getHeight()/2, paint);
		}
		//draw the ball if it is still in bounds
		else if (ballOut == false) {
			canvas.drawCircle(x, y, ballRadius, paint);
		}


	}

	/**
	 * Tells that we never pause.
	 *
	 * @return indication of whether to pause
	 */
	@Override
	public boolean doPause() {
		return false;
	}

	/**
	 * Tells that we never stop the animation
	 *
	 * @return indication of whether to quit
	 */
	@Override
	public boolean doQuit() {
		return false;
	}

	/**
	 * Draw all elements on the layout canvas
	 *
	 * @param canvas canvas to be drawn on
	 */
	@Override
	public void tick(Canvas canvas) {

		drawWall(canvas); //paint the three walls
		drawPaddle(canvas); //paint the paddle
		ballAnimation(canvas); //paint the ball and its animations

	}

	 /**
	 * Allow the user to tap to retry if ball goes out-of-bounds
	 *
	 * @param event allows user interaction through touch
	 */
	@Override
	public void onTouch(MotionEvent event) {
		ballOut = true; //if the ball goes out-of-bounds
	}

	/**
	 External Citation
	 Date: 22 March 2018
	 Problem: Did not remember how to include touch events
	 Resource:
	 https://stackoverflow.com/questions/3142670
	 /how-do-i-detect-touch-input-on-the-android
	 Solution: I used the example code from this post.
	 */

}//class BallAnimator
