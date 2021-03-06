package com.postgrid.magicspell;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Point;
import android.inputmethodservice.InputMethodService;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

import java.util.Arrays;
import java.util.Comparator;


/**
 * <h1>MSKeyboardService</h1>
 * MSKeyboardService extends {@link android.inputmethodservice.InputMethodService}
 * to provide the control handler for input on the keyboard. Methods handle combinations
 * of keys and functionality for modifier keys.
 * <p>
 * This class uses a single touch and multi-touch approach to distinguish
 * between combination letters and single letters. In addition, this class provides
 * a method to set the size of the keys.
 *
 * @author  connor
 * @version 0.1
 * @since   8/26/2016
 */
public class MSKeyboardService extends InputMethodService {

    /**
     *  caps: caps-lock switch (boolean)
     *  numbers: numbers view toggle (boolean)
     *  TAG: Class Tag for logging (String)
     *  resizing: resizing view toggle (boolean)
     */
    private boolean caps = false;
    private boolean numbers = false;
    private static final String TAG = "MSKeyboardService";
    private boolean resizing = false;

    /**
     * This method creates the input view. It handles the base case
     * if the view is in resizing mode as well. If it is, the view
     * then becomes the resizing view then the predefined keyboard view.
     *
     * @return keyboardView object
     */
    @Override
    public View onCreateInputView() {
        if (resizing) {
            @SuppressLint("InflateParams")
            MSResizingView resizingView = (MSResizingView) getLayoutInflater().
                    inflate(R.layout.resizing_view_layout, null);
            resizingView.setService(this);
            return resizingView;
        }
        @SuppressLint("InflateParams")
        MSKeyboardView keyboardView = (MSKeyboardView) getLayoutInflater().
                inflate(R.layout.keyboard_view_layout, null);
        keyboardView.setService(this);
        return keyboardView;
    }

    /**
     * Using a switch statement, this method
     * is responsible for single press touches
     * on the keyboard. The first ten letters
     * of the alphabet (A-J) and toggle keys such as the numbers
     * key and resizing. In addition, it includes functionality
     * for all the modifier keys.
     *
     * @param button pressed (Single press)
     */
    public void singlePressed(MSButton button) {
        Log.v(TAG, "Single Pressed: " + button);
        InputConnection ic = getCurrentInputConnection();
        if(numbers){
            switch(button) {
                case BUTTON_BACKSPACE:
                    ic.deleteSurroundingText(1, 0);
                    break;
                case BUTTON_SHIFT:
                    caps = !caps;
                    //keyboard.setShifted(caps);
                    //keyboardView.invalidateAllKeys();
                    break;
                case BUTTON_NUMBERS:
                    numbers = !numbers;
                    break;
                case BUTTON_DONE:
                    ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                    break;
                case BUTTON_1:
                    ic.commitText("1", 1);
                    break;
                case BUTTON_2:
                    ic.commitText("2", 1);
                    break;
                case BUTTON_3:
                    ic.commitText("3", 1);
                    break;
                case BUTTON_4:
                    ic.commitText("4", 1);
                    break;
                case BUTTON_5:
                    ic.commitText("5", 1);
                    break;
                case BUTTON_6:
                    ic.commitText("6", 1);
                    break;
                case BUTTON_7:
                    ic.commitText("7", 1);
                    break;
                case BUTTON_8:
                    ic.commitText("8", 1);
                    break;
                case BUTTON_9:
                    ic.commitText("9", 1);
                    break;
                case BUTTON_10:
                    ic.commitText("0", 1);
                    break;
                case BUTTON_SPACE:
                    ic.commitText(" ", 1);
                    break;
                case BUTTON_PERIOD:
                    ic.commitText(".", 1);
                    break;
                case BUTTON_COMMA:
                    ic.commitText(",", 1);
                    break;
                case BUTTON_RESIZE:
                    resizing = !resizing;
                    setInputView(onCreateInputView());
                    break;
                default:
                    /*char code = (char) i;
                    if(Character.isLetter(code) && caps) {
                        code = Character.toUpperCase(code);
                    }
                    ic.commitText(String.valueOf(code), 1);*/
                    break;
            }
        } else if (!caps) {
            switch(button) {
                case BUTTON_BACKSPACE:
                    ic.deleteSurroundingText(1, 0);
                    break;
                case BUTTON_SHIFT:
                    caps = !caps;
                    //keyboard.setShifted(caps);
                    //keyboardView.invalidateAllKeys();
                    break;
                case BUTTON_NUMBERS:
                    numbers = !numbers;
                    break;
                case BUTTON_DONE:
                    ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                    break;
                case BUTTON_1:
                    ic.commitText("a", 1);
                    break;
                case BUTTON_2:
                    ic.commitText("b", 1);
                    break;
                case BUTTON_3:
                    ic.commitText("c", 1);
                    break;
                case BUTTON_4:
                    ic.commitText("d", 1);
                    break;
                case BUTTON_5:
                    ic.commitText("e", 1);
                    break;
                case BUTTON_6:
                    ic.commitText("f", 1);
                    break;
                case BUTTON_7:
                    ic.commitText("g", 1);
                    break;
                case BUTTON_8:
                    ic.commitText("h", 1);
                    break;
                case BUTTON_9:
                    ic.commitText("i", 1);
                    break;
                case BUTTON_10:
                    ic.commitText("j", 1);
                    break;
                case BUTTON_SPACE:
                    ic.commitText(" ", 1);
                    break;
                case BUTTON_PERIOD:
                    ic.commitText(".", 1);
                    break;
                case BUTTON_COMMA:
                    ic.commitText(",", 1);
                    break;
                case BUTTON_RESIZE:
                    resizing = !resizing;
                    setInputView(onCreateInputView());
                    break;
                default:
                    /*char code = (char) i;
                    if(Character.isLetter(code) && caps) {
                        code = Character.toUpperCase(code);
                    }
                    ic.commitText(String.valueOf(code), 1);*/
                    break;
            }
        } else {
            switch(button) {
                case BUTTON_BACKSPACE:
                    ic.deleteSurroundingText(1, 0);
                    break;
                case BUTTON_SHIFT:
                    caps = !caps;
                    //keyboard.setShifted(caps);
                    //keyboardView.invalidateAllKeys();
                    break;
                case BUTTON_NUMBERS:
                    numbers = !numbers;
                    break;
                case BUTTON_DONE:
                    ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                    break;
                case BUTTON_1:
                    ic.commitText("A", 1);
                    break;
                case BUTTON_2:
                    ic.commitText("B", 1);
                    break;
                case BUTTON_3:
                    ic.commitText("C", 1);
                    break;
                case BUTTON_4:
                    ic.commitText("D", 1);
                    break;
                case BUTTON_5:
                    ic.commitText("E", 1);
                    break;
                case BUTTON_6:
                    ic.commitText("F", 1);
                    break;
                case BUTTON_7:
                    ic.commitText("G", 1);
                    break;
                case BUTTON_8:
                    ic.commitText("H", 1);
                    break;
                case BUTTON_9:
                    ic.commitText("I", 1);
                    break;
                case BUTTON_10:
                    ic.commitText("J", 1);
                    break;
                case BUTTON_SPACE:
                    ic.commitText(" ", 1);
                    break;
                case BUTTON_PERIOD:
                    ic.commitText("!", 1);
                    break;
                case BUTTON_COMMA:
                    ic.commitText("?", 1);
                    break;
                default:
                    /*char code = (char) i;
                    if(Character.isLetter(code) && caps) {
                        code = Character.toUpperCase(code);
                    }
                    ic.commitText(String.valueOf(code), 1);*/
                    break;
            }
        }
    }

    /**
     * Using a switch statement, this method
     * is responsible for multi press touches
     * on the keyboard. This method handles the input
     * of the remaining letters of the alphabet as
     * pair combinations of the keys.
     *
     * @param button1 pressed (first button of combination)
     * @param button2 pressed (second button of combination)
     */
    public void chordPressed(MSButton button1, MSButton button2) {
        Log.v(TAG, "Chord Pressed: " + button1 + ", " + button2);
        InputConnection ic = getCurrentInputConnection();
        if(!caps) {
            switch (button1) {
                case BUTTON_1:
                    switch (button2) {
                        case BUTTON_10:
                            ic.commitText("k", 1);
                            break;
                        case BUTTON_5:
                            ic.commitText("p", 1);
                            break;
                        case BUTTON_2:
                            ic.commitText("x", 1);
                            break;
                    }
                    break;
                case BUTTON_2:
                    switch (button2) {
                        case BUTTON_9:
                            ic.commitText("l", 1);
                            break;
                        case BUTTON_5:
                            ic.commitText("q", 1);
                            break;
                    }
                    break;
                case BUTTON_3:
                    switch (button2) {
                        case BUTTON_8:
                            ic.commitText("m", 1);
                            break;
                        case BUTTON_5:
                            ic.commitText("r", 1);
                            break;
                        case BUTTON_4:
                            ic.commitText("y", 1);
                            break;
                    }
                    break;
                case BUTTON_4:
                    switch (button2) {
                        case BUTTON_7:
                            ic.commitText("n", 1);
                            break;
                        case BUTTON_5:
                            ic.commitText("s", 1);
                            break;
                    }
                    break;
                case BUTTON_5:
                    switch (button2) {
                        case BUTTON_6:
                            ic.commitText("o", 1);
                            break;
                    }
                    break;
                case BUTTON_6:
                    switch (button2) {
                        case BUTTON_7:
                            ic.commitText("t", 1);
                            break;
                        case BUTTON_8:
                            ic.commitText("u", 1);
                            break;
                        case BUTTON_9:
                            ic.commitText("v", 1);
                            break;
                        case BUTTON_10:
                            ic.commitText("w", 1);
                            break;
                    }
                    break;
                case BUTTON_7:
                    switch (button2) {
                        case BUTTON_8:
                            ic.commitText("z", 1);
                            break;
                    }
                    break;
            }
        } else {
            switch(button1) {
                case BUTTON_1:
                    switch (button2) {
                        case BUTTON_10:
                            ic.commitText("K",1);
                            break;
                        case BUTTON_5:
                            ic.commitText("P",1);
                            break;
                        case BUTTON_2:
                            ic.commitText("X",1);
                            break;
                    }
                    break;
                case BUTTON_2:
                    switch(button2) {
                        case BUTTON_9:
                            ic.commitText("L",1);
                            break;
                        case BUTTON_5:
                            ic.commitText("Q",1);
                            break;
                    }
                    break;
                case BUTTON_3:
                    switch (button2) {
                        case BUTTON_8:
                            ic.commitText("M",1);
                            break;
                        case BUTTON_5:
                            ic.commitText("R",1);
                            break;
                        case BUTTON_4:
                            ic.commitText("Y",1);
                            break;
                    }
                    break;
                case BUTTON_4:
                    switch (button2){
                        case BUTTON_7:
                            ic.commitText("N",1);
                            break;
                        case BUTTON_5:
                            ic.commitText("S",1);
                            break;
                    }
                    break;
                case BUTTON_5:
                    switch (button2) {
                        case BUTTON_6:
                            ic.commitText("O",1);
                            break;
                    }
                    break;
                case BUTTON_6:
                    switch (button2) {
                        case BUTTON_7:
                            ic.commitText("T",1);
                            break;
                        case BUTTON_8:
                            ic.commitText("U",1);
                            break;
                        case BUTTON_9:
                            ic.commitText("V",1);
                            break;
                        case BUTTON_10:
                            ic.commitText("W",1);
                            break;
                    }
                    break;
                case BUTTON_7:
                    switch (button2) {
                        case BUTTON_8:
                            ic.commitText("Z",1);
                            break;
                    }
                    break;
            }
        }
    }

    /**
     * Use on MSResizingView, this method sets the appropriate
     * size of the letter keys for the resizing functionality.
     *
     * @param fingers array
     */
    public void setSize(Point[] fingers) {
        PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Arrays.sort(fingers, new Comparator<Point>(){
            public int compare(Point a, Point b){
                if(a.x > b.x)
                    return 1;
                if(a.x < b.x)
                    return -1;
                else{
                    if(a.y < b.y)
                        return 1;
                    if(a.y > b.y)
                        return -1;
                    else
                        return 0;
                }
            }
        });

        int maxY = fingers[0].y;
        int minY = fingers[0].y;
        for (Point finger : fingers) {
            if (finger.y > maxY)
                maxY = finger.y;
            if (finger.y < minY)
                minY = finger.y;
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            editor.putInt("pHeight", maxY - minY + (int)(getResources().getDisplayMetrics().heightPixels /5.0f) + 50);
            for (int i = 0; i < fingers.length/2; i++) {
                int j = fingers.length-1-i;
                Log.d(TAG, "Finger Location " + (0 + i) + ": " + fingers[i]);
                editor.putInt("pPoint" + i + "x", fingers[i].x);
                editor.putInt("pPoint" + j + "x", fingers[j].x);
                editor.putInt("pPoint" + i + "y", fingers[i].y - minY + 50);
                editor.putInt("pPoint" + j + "y", fingers[i].y - minY + 50);

            }
            editor.putBoolean("pResized", true);


        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            editor.putInt("lHeight", maxY - minY + (int)(getResources().getDisplayMetrics().heightPixels /5.0f) + 50);
            for (int i = 0; i < fingers.length/2; i++) {
                int j = fingers.length-1-i;
                Log.d(TAG, "Finger Location " + (0 + i) + ": " + fingers[i]);
                editor.putInt("lPoint" + i + "x", fingers[i].x);
                editor.putInt("lPoint" + j + "x", fingers[j].x);
                editor.putInt("lPoint" + i + "y", fingers[i].y - minY + 50);
                editor.putInt("lPoint" + j + "y", fingers[i].y - minY + 50);

            }
            editor.putBoolean("lResized", true);

        }

        editor.apply();
        resizing = false;
        setInputView(onCreateInputView());
    }
}
