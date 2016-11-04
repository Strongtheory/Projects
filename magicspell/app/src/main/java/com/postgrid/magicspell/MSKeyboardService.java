package com.postgrid.magicspell;

import android.inputmethodservice.InputMethodService;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;


/**
 * Created by connor on 8/26/16.
 */
public class MSKeyboardService extends InputMethodService {

    private MSKeyboardView view;
    private boolean caps = false;
    private static final String TAG = "MSKeyboardService";

    @Override
    public View onCreateInputView() {
        view = (MSKeyboardView) getLayoutInflater().inflate(R.layout.single_view_layout, null);
        view.setService(this);
        //keyboard = new Keyboard(this, R.xml.qwerty);
        return view;
    }
    public void singlePressed(MSButton button) {
        Log.v(TAG, "Single Pressed: " + button);
        InputConnection ic = getCurrentInputConnection();
        if(!caps){
            switch(button) {
                case BUTTON_BACKSPACE:
                    ic.deleteSurroundingText(1, 0);
                    break;
                case BUTTON_SHIFT:
                    caps = !caps;
                    //keyboard.setShifted(caps);
                    //view.invalidateAllKeys();
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
                default:
                    /*char code = (char) i;
                    if(Character.isLetter(code) && caps) {
                        code = Character.toUpperCase(code);
                    }
                    ic.commitText(String.valueOf(code), 1);*/
                    break;
            }
        }else{
            switch(button) {
                case BUTTON_BACKSPACE:
                    ic.deleteSurroundingText(1, 0);
                    break;
                case BUTTON_SHIFT:
                    caps = !caps;
                    //keyboard.setShifted(caps);
                    //view.invalidateAllKeys();
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
        }else{
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

}
