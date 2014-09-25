package game.framework;

import game.skeleton.Audio;
import game.skeleton.FileIO;
import game.skeleton.Game;
import game.skeleton.Graphics;
import game.skeleton.Input;
import game.skeleton.Screen;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

public abstract class GLGame extends Activity implements Game, Renderer {

	enum GLGameState {
		Initialized,
		Running,
		Paused,
		Finished,
		Idle
	}
	
	GLSurfaceView glView;
	GLGraphics glGraphics;
	Input input;
	Audio audio;
	FileIO fileIO;
	Screen screen;
	GLGameState state = GLGameState.Initialized;
	Object stateChanged = new Object();
	long startTime = System.nanoTime();
	WakeLock wakeLock;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        glView = new GLSurfaceView(this);
        glView.setRenderer(this);
        setContentView(glView);
        PowerManager powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "Game");
	}
	
	@SuppressLint("Wakelock")
	@Override
	public void onResume(){
		super.onResume();
		glView.onResume();
		wakeLock.acquire();
	}
	
	public void onDrawFrame(GL10 arg0) {
		GLGameState state = null;
		
		synchronized (stateChanged) {
			state = this.state;
		}
		
		if(state == GLGameState.Running){
			float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
			startTime = System.nanoTime();
			screen.update(deltaTime);
			screen.present(deltaTime);
		}
		
		if(state == GLGameState.Paused){
			screen.pause();
			synchronized (stateChanged) {
				this.state = GLGameState.Idle;
				stateChanged.notifyAll();
			}
		}
		
		if(state == GLGameState.Finished){
			screen.pause();
			screen.dispose();
			synchronized (stateChanged) {
				this.state = GLGameState.Idle;
				stateChanged.notifyAll();
			}
		}
	}

	public void onSurfaceChanged(GL10 arg0, int arg1, int arg2) {
		
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		glGraphics.setGl(gl);
		synchronized (stateChanged) {
			if(state == GLGameState.Initialized)
				screen = getStartScreen();
			state = GLGameState.Running;
			screen.resume();
			startTime = System.nanoTime();
		}
	}

	public void onPuase(){
		synchronized (stateChanged) {
			if(isFinishing())
				state = GLGameState.Finished;
			else
				state = GLGameState.Paused;
			while(true){
				try {
					stateChanged.wait();
					break;
				} catch (InterruptedException e) {
				}
			}
		}
		wakeLock.release();
		glView.onPause();
		super.onPause();
	}
	
	public GLGraphics getGlGraphics(){
		return glGraphics;
	}
	
	public Graphics getGraphics(){
		throw new IllegalStateException("OpenGl");
	}
	
	public Input getInput() {
		return input;
	}

	public FileIO getFileIO() {
		return fileIO;
	}

	public Audio getAudio() {
		return audio;
	}

	public void setScreen(Screen newScreen) {
		if(newScreen == null)
			throw new IllegalArgumentException("Null Screen");
		this.screen.pause();
		this.screen.dispose();
		newScreen.resume();
		newScreen.update(0);
		this.screen = newScreen;
	}

	public Screen getCurrentScreen() {
		return screen;
	}

	public Screen getStartScreen() {
		return null;
	}

}
