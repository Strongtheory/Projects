package game.framework;

import game.skeleton.Input;

import java.util.List;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;


public class AndroidInput implements Input {

	AccelerometerHandler handler;
	KeyBoardHandler keyHandler;
	TouchHandler touchHandler;
	
	@SuppressWarnings("deprecation")
	public AndroidInput(Context context, View view, float scaleX, float scaleY) {
		handler = new AccelerometerHandler(context);
		keyHandler = new KeyBoardHandler(view);
		if (Integer.parseInt(VERSION.SDK) < 5)
			touchHandler = new SingleTouchHandler(view, scaleX, scaleY);
		else
			touchHandler = new MultiTouchHandler(view, scaleX, scaleY);
	}
	
	@Override
	public boolean isKeyPressed(int keyCode) {
		return keyHandler.isKeyPressed(keyCode);
	}

	@Override
	public boolean isTouchDown(int pointer) {
		return touchHandler.isTouchDown(pointer);
	}

	@Override
	public int getTouchX(int pointer) {
		return touchHandler.getTouchX(pointer);
	}

	@Override
	public int getTouchY(int pointer) {
		return touchHandler.getTouchY(pointer);
	}

	@Override
	public float getAccelX() {
		return handler.getAccelX();
	}

	@Override
	public float getAccelY() {
		return handler.getAccelY();
	}

	@Override
	public float getAccelZ() {
		return handler.getAccelZ();
	}

	@Override
	public List<KeyEvent> getKeyEvents() {
		return keyHandler.getKeyEvents();
	}

	@Override
	public List<TouchEvent> getTouchEvents() {
		return touchHandler.getTouchEvents();
	}

}
