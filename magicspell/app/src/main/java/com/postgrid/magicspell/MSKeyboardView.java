package com.postgrid.magicspell;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by connor on 8/31/16.
 */
public class MSKeyboardView extends View implements SharedPreferences.OnSharedPreferenceChangeListener {
    private int myWidth;
    private int myHeight;
    private ShapeDrawable[] circles;
    private MSKeyboardService service;
    private static final String TAG = "CustomButtonsView";
    private static final int TIMEOUT = 200;
    private int heightPercent;
    Context mContext;
    private GestureDetector gestureDetector;

    private SharedPreferences sharedPref;


    boolean composingChord;
    long lastPointerReleaseTime;
    Point[] chordCoords;
    int[] pointerIdx;

    private static final MSButton[] LETTER_BUTTONS = {
            MSButton.BUTTON_1,
            MSButton.BUTTON_2,
            MSButton.BUTTON_3,
            MSButton.BUTTON_4,
            MSButton.BUTTON_5,
            MSButton.BUTTON_6,
            MSButton.BUTTON_7,
            MSButton.BUTTON_8,
            MSButton.BUTTON_9,
            MSButton.BUTTON_10,
            MSButton.BUTTON_SHIFT,
            MSButton.BUTTON_COMMA,
            MSButton.BUTTON_PERIOD,
            MSButton.BUTTON_SPACE,
            MSButton.BUTTON_BACKSPACE,
    };


    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public MSKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);

        myHeight = 0;
        myWidth = 0;

        mContext = context;
        circles = new ShapeDrawable[15];
        for (int i = 0; i < circles.length; i++) {
            circles[i] = new ShapeDrawable(new OvalShape());
        }

        service = null;
        sharedPref =  PreferenceManager.getDefaultSharedPreferences(mContext);
        heightPercent = 50;
        gestureDetector = new GestureDetector(mContext,new MyGestureDetector());

        composingChord = false;
        lastPointerReleaseTime = 0;
        chordCoords = new Point[2];
        pointerIdx = new int[2];


    }

    @Override
    public void onDraw (Canvas canvas) {
        super.onDraw(canvas);
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();

        Paint blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);
        double alpha = 255.0 * sharedPref.getInt("seekBar", 100) / 100.0;
        //we would get the positions of butttons here
        blackPaint.setAlpha((int)alpha);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), blackPaint);

        int totalWidth = metrics.widthPixels;
        int widthForCircles = (int)(totalWidth * 0.6f);
        int circleWidth = (int)(widthForCircles / 10.0f);
        int boxWidth = (int)(totalWidth / 10);
        int spaceWidth = circleWidth*3;
        int shiftWidth = circleWidth*2;
        int constantForRaise = 5;

        //buttons 0 to 9
        for(int i = 0; i < circles.length; i++){
            setButtonPosition(i);
        }

        int y = myHeight - constantForRaise;
        boxWidth = totalWidth / 11;
        //space - 13
        int x = totalWidth/2 - spaceWidth/2;
        circles[13].setBounds(x,y - circleWidth,x + spaceWidth, y);

        //Shift - 10
        x = boxWidth - circleWidth;
        circles[10].setBounds(x,y - circleWidth,x + shiftWidth, y);

        //Comma - 11
        x = (int) (boxWidth * 2 + boxWidth * 0.5f) - (circleWidth / 2);
        circles[11].setBounds(x,y - circleWidth,x + circleWidth, y);

        //period - 12
        x = (int) (boxWidth * 3 + boxWidth * 0.5f) - (circleWidth / 2);
        circles[12].setBounds(x,y - circleWidth,x + circleWidth, y);

        //backspace - 14
        x = (int) (boxWidth * 7 + boxWidth * 0.5f) - (circleWidth / 2);
        circles[14].setBounds(x,y - circleWidth,x + shiftWidth, y);

        for (int i = 0; i < circles.length; i++) {
            //int midPoint = (int)(boxWidth * i + boxWidth * 0.5f);
            //int x = midPoint - (circleWidth / 2);
            //int y = (myHeight / 2) - (circleWidth / 2);
            //circles[i].setBounds(x,y,x + circleWidth, y + circleWidth);
            circles[i].getPaint().setColor(Color.BLUE);
            circles[i].getPaint().setAlpha((int)alpha);
            circles[i].draw(canvas);
        }
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        myWidth = MeasureSpec.getSize(widthMeasureSpec);
        myHeight = (int)(metrics.heightPixels * heightPercent / 100.0f);
        setMeasuredDimension(myWidth, myHeight);
    }

    public void setService(MSKeyboardService service) {
        this.service = service;
    }
    public void setHeightPercentage(int newHeightPercentage) {
        heightPercent = newHeightPercentage;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event)) {
            return true;
        }
        int action = event.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN) {
            //Log.v(TAG, "ACTION_DOWN");
            return true;
        } else if (action == MotionEvent.ACTION_UP) {
            //Log.v(TAG, "ACTION_UP");
            chordCoords[0] = new Point((int)event.getX(), (int)event.getY());
            if (System.currentTimeMillis() - lastPointerReleaseTime < TIMEOUT) {
                //Log.v(TAG, "Close together!");
                checkChord();
            } else {
                //Log.v(TAG, "Not close together!");
                checkSingle();
            }
            return true;
        } else if (action == MotionEvent.ACTION_POINTER_DOWN){
            //Log.v(TAG, "ACTION_POINTER_DOWN: " + event.getActionIndex());
            return true;
        } else if(action == MotionEvent.ACTION_POINTER_UP) {
            //Log.v(TAG, "ACTION_POINTER_UP: " + event.getActionIndex());
            lastPointerReleaseTime = System.currentTimeMillis();
            int index = event.getActionIndex();
            chordCoords[1] = new Point((int)event.getX(index), (int)event.getY(index));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d(TAG, "Some preference changed somewhere");
        if(key.equals("seekBar")) {
            Log.d(TAG, "Seekbar value changed!");
        }
    }

    private class MyGestureDetector extends android.view.GestureDetector.SimpleOnGestureListener {
    }
    private void checkChord() {
        int[] indeces = new int[2];
        indeces[0] = -1;
        indeces[1] = -1;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < circles.length; j++) {
                if (circles[j].getBounds().contains(chordCoords[i].x, chordCoords[i].y)) {
                    indeces[i] = j;
                    break;
                }
            }
        }
        if (indeces[0] >= 0 && indeces[1] >= 0) {
            if (indeces[1] > indeces[0])
                service.chordPressed(LETTER_BUTTONS[indeces[0]], LETTER_BUTTONS[indeces[1]]);
            else
                service.chordPressed(LETTER_BUTTONS[indeces[1]], LETTER_BUTTONS[indeces[0]]);
        } else {
            Log.e(TAG, "Chord missing one touch");
        }
    }
    private void checkSingle() {
        int index = -1;
        for (int i = 0; i < circles.length; i++) {
            if (circles[i].getBounds().contains(chordCoords[0].x, chordCoords[0].y)) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            service.singlePressed(LETTER_BUTTONS[index]);
        }
    }
    private int dpsToPixels(int dps) {
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        return (int)(dps * metrics.density);
    }
    private void setButtonPosition(int button){
        if(button >=0 || button <= 9) {
            int widthForCircles = (int) (myWidth * 0.6f);
            int circleWidth = (int) (widthForCircles / 10.0f);
            int boxWidth = (int) (myWidth / 10);
            int hightDifference = 6;
            int spaceWidth = circleWidth * 3;
            int shiftWidth = circleWidth * 2;
            int constantForRaise = 5;
            int hightModifier = 0;
            switch (button) {
                case 0: case 9:
                    hightModifier = 0;
                    break;
                case 1: case 8: case 3: case 6:
                    hightModifier = -1;
                    break;
                case 2: case 7:
                    hightModifier = -2;
                    break;
                case 4:case 5:
                    hightModifier = 1;
                    break;
            }


            int midPoint = (int) (boxWidth * button + boxWidth * 0.5f);
            int x = midPoint - (circleWidth / 2);
            int y = (myHeight / 2) - (circleWidth / 2) + hightModifier*myHeight / hightDifference;
            circles[button].setBounds(x, y, x + circleWidth, y + circleWidth);
        }
    }
}
