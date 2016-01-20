package deo.gl;

public class Animation {
	public static final int LOOPING = 0;
	public static final int NONLOOPING = 1;
	final TextureRegions[] keyFrames;
	final float frameDuration;
	
	public Animation(float frameDuration, TextureRegions ... keyFrames){
		this.frameDuration = frameDuration;
		this.keyFrames = keyFrames;
	}
	
	public TextureRegions getKeyFrame(float state, int mode){
		int frameNumber = (int)(state / frameDuration);
		if(mode == NONLOOPING){
			frameNumber = Math.min(keyFrames.length - 1, frameNumber);
		} else {
			frameNumber = frameNumber % keyFrames.length;
		}
		return keyFrames[frameNumber];
	}
}
