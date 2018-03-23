package cs301.pong;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * PongMainActivity
 * 
 * This is the activity for the Pong game. It attaches a PongAnimator to
 * an AnimationSurface.
 * 
 * @author Andrew Nuxoll
 * @author Steven R. Vegdahl
 * @author Michael Quach
 * @version March 22, 2018
 * 
 */
public class PongMainActivity extends Activity {

	//creates a new instance of the test animator
	BallAnimator ballAnimator = new BallAnimator();

	/**
	 * creates an AnimationSurface containing a TestAnimator.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pong_main);

		// Connect the animation surface with the animator
		AnimationSurface mySurface = (AnimationSurface) this
				.findViewById(R.id.animationSurface);
		mySurface.setAnimator(ballAnimator);

		//Register buttons for player to select
		Button smallPaddle = (Button)findViewById(R.id.smallButton);
		Button largePaddle = (Button)findViewById(R.id.largeButton);

		//Connect buttons to On-click listeners
		smallPaddle.setOnClickListener(this);
		largePaddle.setOnClickListener(this);
	}

	/**
	 * on-Click listener to register button inputs
	 */
	@Override
	public void onClick(View v) {
		//Change paddle size to small if button pressed
		if(v.getId() == R.id.bigButton) {
			ballAnimator.setPaddleSmall();
		}

		//Change paddle size to large if button pressed
		else if(v.getId() == R.id.smallButton) {
			ballAnimator.setPaddleLarge();
		}
	}
}
