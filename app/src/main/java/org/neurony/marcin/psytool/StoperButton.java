package org.neurony.marcin.psytool;

import android.content.Context;
import android.widget.Button;
import android.util.*;

/**
 * Created by Marcin on 2016-03-13.
 */
public class StoperButton extends Button {

    private static final int[] STATE_STANDBY = {R.attr.state_standby};
    private boolean mIsStandby = true;

    public StoperButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setStandby(boolean isStandby) {
        mIsStandby = isStandby;
        refreshDrawableState();
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 2);
        if (mIsStandby) {
            mergeDrawableStates(drawableState, STATE_STANDBY);
        }

        return drawableState;
    }
}
