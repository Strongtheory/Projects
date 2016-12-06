package com.postgrid.magicspell;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * <h1>MSResizingView</h1>
 * This class extends view class and displays resizing
 * view of the application setting.
 * <p>
 * This class is responsbile for displaying keyboard resizing
 * and terminates on resizing action.
 *
 * @author Connor
 * @version 0.1
 * @since 11/9/2016
 */
public class MSResizingView extends View {

    private Context mContext;
    MSKeyboardService service;
    private static final String TAG = "MSResizingView";
    private SharedPreferences sharedPref;
    private int myHeight;
    private ShapeDrawable[] circles;
    private Drawable kl_backspace;
    private Drawable kl_space;
    private Drawable kl_period;
    private Drawable kl_shift;
    private Drawable kl_comma;
    private Drawable kl_resize;
    private Drawable kl_numbers;

    Paint blackPaint;


    public MSResizingView(Context context, AttributeSet attr) {
        super(context, attr);
        mContext = context;
        circles = new ShapeDrawable[17];
        for (int i = 10; i < circles.length; i++) {
            circles[i] = new ShapeDrawable(new OvalShape());
        }

        blackPaint = new Paint();

        kl_backspace = ContextCompat.getDrawable(mContext, R.drawable.keylabel_backspace);
        kl_period = ContextCompat.getDrawable(mContext, R.drawable.keylabel_periodex);
        kl_space = ContextCompat.getDrawable(mContext, R.drawable.keylabel_space);
        kl_shift = ContextCompat.getDrawable(mContext, R.drawable.keylabel_shift);
        kl_comma = ContextCompat.getDrawable(mContext, R.drawable.keylabel_commaq);
        kl_resize = ContextCompat.getDrawable(mContext, R.drawable.keylabel_resize);
        kl_numbers = ContextCompat.getDrawable(mContext, R.drawable.keylabel_numbers);

    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        int myWidth = MeasureSpec.getSize(widthMeasureSpec);
        myHeight = metrics.heightPixels;
        setMeasuredDimension(myWidth, myHeight);
    }
    public void setService(MSKeyboardService service) {
        this.service = service;
        sharedPref =  PreferenceManager.getDefaultSharedPreferences(service);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN) {
            Log.d(TAG, "Touch: " + event.getX() + ", " + event.getY());
            return true;
        } else if (action == MotionEvent.ACTION_POINTER_DOWN){
            int count = event.getPointerCount();
            if (count == 10) {
                Point[] points = new Point[10];
                for (int i = 0; i < 10; i++) {
                    points[i] = new Point((int)event.getX(i), (int)event.getY(i));
                }
                service.setSize(points);
            }
            return true;
        }
        return false;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        blackPaint.setColor(Color.DKGRAY);
        double alpha = 255.0 * sharedPref.getInt("seekBar", 100) / 100.0;
        blackPaint.setAlpha((int)alpha);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), blackPaint);


        int totalWidth = metrics.widthPixels;
        int widthForCircles = (int)(totalWidth * 0.6f);
        int circleWidth = (int)(widthForCircles / 10.0f);
        int boxWidth;
        int spaceWidth = circleWidth*3;
        int shiftWidth = circleWidth*2;

        int constantForRaise = 35;
        int y = myHeight - constantForRaise;
        Log.d(TAG, "myHeight: " + myHeight);
        Log.d(TAG, "myHeight - circleWidth: " + (myHeight - circleWidth));


        boxWidth = totalWidth / 11;
        //space - 13
        int x = totalWidth/2 - spaceWidth/2;
        circles[13].setBounds(x,y - circleWidth,x + spaceWidth, y);
        kl_space.setBounds(x+((int)(spaceWidth*.2)),y-circleWidth,x+((int)(spaceWidth*.8)),y);

        //Shift - 10
        x = boxWidth - circleWidth;
        circles[10].setBounds(x,y - circleWidth,x + shiftWidth, y);
        kl_shift.setBounds(x+((int)(shiftWidth*.1)),y-((int)(circleWidth*.9)),x+((int)(shiftWidth*.9)),y-((int)(circleWidth*.1)));

        //Comma - 11
        x = (int) (boxWidth * 2 + boxWidth * 0.5f) - (circleWidth / 2);
        circles[11].setBounds(x,y - circleWidth,x + circleWidth, y);
        kl_comma.setBounds(x+((int)(circleWidth*.1)),y-((int)(circleWidth*.9)),x+((int)(circleWidth*.9)),y-((int)(circleWidth*.1)));

        //period - 12
        x = (int) (boxWidth * 3 + boxWidth * 0.5f) - (circleWidth / 2);
        circles[12].setBounds(x,y - circleWidth,x + circleWidth, y);
        kl_period.setBounds(x+((int)(circleWidth*.1)),y-((int)(circleWidth*.9)),x+((int)(circleWidth*.9)),y-((int)(circleWidth*.1)));

        //backspace - 14
        x = (int) (boxWidth * 7 + boxWidth * 0.5f) - (circleWidth / 2);
        circles[14].setBounds(x,y - circleWidth,x + shiftWidth, y);
        kl_backspace.setBounds(x+((int)(shiftWidth*.1)),y-((int)(circleWidth*.9)),x+((int)(shiftWidth*.9)),y-((int)(circleWidth*.1)));

        //numbers - 15
        x = (int) (boxWidth*9 + boxWidth * 0.5f) - (circleWidth / 2);
        circles[15].setBounds(x,y-circleWidth,x+circleWidth,y);
        kl_numbers.setBounds(x+((int)(circleWidth*.1)),y-((int)(circleWidth*.9)),x+((int)(circleWidth*.9)),y-((int)(circleWidth*.1)));


        //resize - 16
        x = (int) (boxWidth * 10 + boxWidth * 0.5f) - (circleWidth / 2);
        circles[16].setBounds(x,y-circleWidth,x+circleWidth,y);
        kl_resize.setBounds(x+((int)(circleWidth*.1)),y-((int)(circleWidth*.9)),x+((int)(circleWidth*.9)),y-((int)(circleWidth*.1)));

        for (int i = 10; i < circles.length; i++) {
            circles[i].getPaint().setColor(Color.LTGRAY);
            circles[i].getPaint().setAlpha((int)alpha);
            circles[i].draw(canvas);
        }
        kl_backspace.draw(canvas);
        kl_period.draw(canvas);
        kl_space.draw(canvas);
        kl_shift.draw(canvas);
        kl_comma.draw(canvas);
        kl_resize.draw(canvas);
        kl_numbers.draw(canvas);
    }
}
