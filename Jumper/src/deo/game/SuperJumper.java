package deo.game;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.egl.EGLConfig;

import deo.framework.GLGame;
import deo.skeleton.Screen;

public class SuperJumper extends GLGame {
	boolean firstTimeCreate = true;
	
	public Screen getStartScreen(){
		return new MainMenu(this);
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config){
		super.onSurfaceCreated(gl, config);
		if(firstTimeCreate){
			Settings.load(getFileIO());
			Assets.load(this);
			firstTimeCreate = false;
		} else {
			Assets.reload();
		}
	}
	
	@Override
	public void onPause(){
		super.onPause();
		if(Settings.soundEnabled)
			Assets.music.pause();
	}
}
