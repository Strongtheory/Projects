package deo.droid;

import javax.microedition.khronos.opengles.GL10;

import game.framework.GLGame;
import game.skeleton.Screen;

public class Droid extends GLGame {
	boolean first = true;
	
	public Screen getStartScreen(){
		return new Main(this);
	}
	
	public void onSurfaceCreated(GL10 gl, javax.microedition.
			khronos.egl.EGLConfig config){
		super.onSurfaceCreated(gl, config);
		if(first){
			Setting.load(getFileIO());
			Assets.load(this);
			first = false;
		} else {
			Assets.reload();
		}
	}
	
	public void onPause(){
		super.onPause();
		if(Setting.soundEnabled)
			Assets.music.pause();
	}
	
}