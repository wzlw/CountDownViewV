package com.zl.countdownview.utils;

import android.content.Context;

/**
 * Created by zl on 2016/10/13.
 */

public class SizeUtils {

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
