package com.postgrid.magicspell;

/**
 * credit to afarber on github
 * https://github.com/afarber/android-newbie/tree/master/MyPrefs
 */

import android.preference.Preference;
import android.widget.SeekBar;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

/**
 * <h1>SeekBarPreference</h1>
 * This class which extends Preference and implements SeekBar listener, handles
 * the opacity setting of the keyboard.
 * <p>
 * This class is responsible for setting the opacity of the keyboard on the display.
 * The view of the seekbar is separate from the keyboard and the value is persisted throughout
 * the lifecycle of the application until changed again.
 *
 * @author Alex
 * @version 0.1
 * @since 9/22/2016
 */
public class SeekBarPreference extends Preference implements SeekBar.OnSeekBarChangeListener {
    private int mProgress;


    public SeekBarPreference(Context context) {
        this(context, null, 0);
    }

    public SeekBarPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     *
     * @param context of the seekbar
     * @param attrs associated with seekbar tag
     * @param defStyle default style
     */
    private SeekBarPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayoutResource(R.layout.preference_seekbar);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        SeekBar mSeekBar = (SeekBar) view.findViewById(R.id.seekbar);
        mSeekBar.setProgress(mProgress);
        mSeekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!fromUser)
            return;

        setValue(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // Unused
        //throw new UnsupportedOperationException();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // Unused
        //throw new UnsupportedOperationException();
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        setValue(restoreValue ? getPersistedInt(mProgress) : (Integer) defaultValue);
    }

    /**
     * Sets the opacity value and persists
     * Notifies whole application of change
     *
     * @param value to set for opacity
     */
    private void setValue(int value) {
        if (shouldPersist()) {
            persistInt(value);
        }

        if (value != mProgress) {
            mProgress = value;
            notifyChanged();
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getInt(index, 0);
    }
}
