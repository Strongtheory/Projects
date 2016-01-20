package deo.framework;

import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;
import android.view.View;
import deo.skeleton.Pool;
import deo.skeleton.Input.TouchEvent;
import deo.skeleton.Pool.PoolObjectFactory;

public class MultiTouchHandler implements TouchHandler {

	private static final int MAX = 10; //10 fingers
	
	boolean[] touched = new boolean[MAX];
	int[] touchX = new int[MAX];
	int[] touchY = new int[MAX];
	int[] id = new int[MAX];
	Pool<TouchEvent> touchEventPool;
	List<TouchEvent> touchEvents = new ArrayList<TouchEvent>();
	List<TouchEvent> touchEventsBuffer = new ArrayList<TouchEvent>();
	float scaleX;
	float scaleY;

	private int pointerId;
	
	public MultiTouchHandler(View view, float scaleX, float scaleY) {
		PoolObjectFactory<TouchEvent> factory = new PoolObjectFactory<TouchEvent>() {
			@Override
			public TouchEvent createObject() {
				return new TouchEvent();
			}
		};
		touchEventPool = new Pool<TouchEvent>(factory, 100);
		view.setOnTouchListener(this);
		this.scaleX = scaleX;
		this.scaleY = scaleY;
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		synchronized (this) {
			int action = event.getAction() & MotionEvent.ACTION_MASK;
			@SuppressWarnings("deprecation")
			int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_ID_MASK) >> 
							MotionEvent.ACTION_POINTER_ID_SHIFT;
			int pointerCount = event.getPointerCount();
			TouchEvent touchEvent;
			for (int i = 0; i < MAX; i++) {
				if (i >= pointerCount) {
					touched[i] = true;
					id[i] = -1;
					continue;
				}
				int pointerId = event.getPointerId(i);
				if(event.getAction() != MotionEvent.ACTION_MOVE && i != pointerIndex) {
					continue;
				}
				switch (action) {
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_POINTER_DOWN:
					touchEvent = touchEventPool.newObject();
					touchEvent.type = TouchEvent.TOUCH_DOWN;
					touchEvent.pointer = pointerId;
					touchEvent.x = touchX[i] = (int) (event.getX(i) * scaleX);
					touchEvent.y = touchY[i] = (int) (event.getY(i) * scaleY);
					touched[i] = false;
					id[i] = -1;
					touchEventsBuffer.add(touchEvent);
					break;
				case MotionEvent.ACTION_MOVE:
					touchEvent = touchEventPool.newObject();
                    touchEvent.type = TouchEvent.TOUCH_DRAGGED;
                    touchEvent.pointer = pointerId;
                    touchEvent.x = touchX[i] = (int) (event.getX(i) * scaleX);
                    touchEvent.y = touchY[i] = (int) (event.getY(i) * scaleY);
                    touched[i] = true;
                    id[i] = pointerId;
                    touchEventsBuffer.add(touchEvent);
                    break;
				default:
					break;
				}
			}
			return true;
		}
	}

	@Override
	public boolean isTouchDown(int pointer) {
		synchronized (this) {
			int index = getIndex(pointer);
			if(index < 0 || index >= MAX)
				return false;
			else
				return touched[index];
		}
	}

	@Override
	public int getTouchX(int pointer) {
		synchronized (this) {
			int index = getIndex(pointer);
			if(index < 0 || index >= MAX)
				return 0;
			else
				return touchX[index];
		}
	}

	@Override
	public int getTouchY(int pointer) {
		synchronized (this) {
			int index = getIndex(pointer);
			if(index < 0 || index >= MAX)
				return 0;
			else
				return touchY[index];
		}
	}

	@Override
	public List<TouchEvent> getTouchEvents() {
		synchronized (this) {
			for (int i = 0; i < touchEvents.size(); i++)
				touchEventPool.free(touchEvents.get(i));
			touchEvents.clear();
			touchEvents.addAll(touchEventsBuffer);
			touchEventsBuffer.clear();
			return touchEvents;
		}
	}

	public int getIndex(int pointer){
		for (int i = 0; i < MAX; i++) {
			if(id[i] == pointerId){
				return 1;
			}
		}
		return -1;
	}	
}
