package fallingpuzzle.sprite;

import javafx.scene.image.Image;

public class Animation {
	
	private Image[] animationBuffer;
	private int currentFrame;
	private int totFrames;
	private boolean playing;
	private int rightDelay;
	private int actualDelay;
	private int select;
	private Image frame;
	
	private static final int PLAY = 0;
	private static final int PLAY_ONCE = 1;
	private static final int PLAY_REVERSE = 2;
	private static final int PLAY_ONCE_REVERSE = 3;
	
	
	private Animation() {
		playing = false;
		rightDelay = 5; //frames
		currentFrame = 0;
		actualDelay = 0;
	}
	
	
	public Animation( Animation animation ) {
		this.animationBuffer = animation.animationBuffer;
		this.totFrames = animation.totFrames;
		this.rightDelay = animation.rightDelay;
		this.currentFrame = 0;
		this.actualDelay = 0;
	}


	public void setupAnimation( int totFrames, Image[] frames, int start ) {
		this.totFrames = totFrames;
		animationBuffer = new Image[ totFrames ];
		for( int i = 0; i < totFrames; i++, start++ )
			animationBuffer[ i ] = frames[ start ];
		currentFrame = 0;
	}
	
	public void play() {
		if( playing ) return;
		setPlayingBool( PLAY );
		playing = true;
	}
	
	public void playOnce( int lastFrame ) {
		if( playing ) return;
		if( lastFrame <= 0 || lastFrame >= totFrames ) lastFrame = 0;
		setPlayingBool( PLAY_ONCE );
		currentFrame = lastFrame;
		playing = true;
	}
	
	public void playReverse() {
		if( playing ) return;
		setPlayingBool( PLAY_REVERSE );
		currentFrame = totFrames - 1;
		playing = true;
	}
	
	public void playOnceReverse( int lastFrame ) {
		if( playing ) return;
		if( lastFrame <= 0 || lastFrame >= totFrames ) lastFrame = totFrames - 1;
		setPlayingBool( PLAY_ONCE_REVERSE );
		currentFrame = lastFrame;
		playing = true;
	}
	
	public int getLastFrame() {
		return currentFrame;
	}
	
	public void reset() { 
		currentFrame = 0;
		playing = false;
	}
	
	public void stop() { 
		playing = false;
	}
	
	private void setPlayingBool( int selection ) {
		select = selection;
	}
	
	public Image getFrame() { 
		return frame;
	}
	
	public boolean isPlaying() { return playing; }
	
	public Image nextFrame() {
		
		++actualDelay;
		
		//infinite playing forward
		if( select == PLAY && actualDelay >= rightDelay ) {
			++currentFrame;
			if( currentFrame >= totFrames ) currentFrame = 0;
			actualDelay = 0;
		}
		
		//infinite playing backward
		else if( select == PLAY_REVERSE && actualDelay >= rightDelay ) {
			--currentFrame;
			if( currentFrame < 0 ) currentFrame = totFrames - 1;
			actualDelay = 0;
		}
		
		//plaing once forward
		else if( select == PLAY_ONCE && actualDelay >= rightDelay && currentFrame < totFrames - 1 ) {
			++currentFrame;
			actualDelay = 0;
		}
		
		//playing once backward
		else if( select == PLAY_ONCE_REVERSE && actualDelay >= rightDelay && currentFrame > 0 ) {
			--currentFrame;
			actualDelay = 0;
		}
				
		frame = animationBuffer[ currentFrame ];
				
		if( ( select == PLAY_ONCE ) && ( currentFrame >= totFrames - 1 ) )
			playing = false;
		
		else if( ( select == PLAY_ONCE_REVERSE ) && ( currentFrame <= 0 ) )
			playing = false;
				
		return frame;
	}

	
	public static Animation createAnimation( int totFrames, Image[] frames, int start, int delay ) {
		Animation animation = new Animation();
		animation.rightDelay = delay;
		animation.setupAnimation( totFrames, frames, start );
		return animation;
	}
	
}
