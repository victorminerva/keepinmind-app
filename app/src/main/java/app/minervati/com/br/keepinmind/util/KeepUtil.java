package app.minervati.com.br.keepinmind.util;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

/**
 * Created by victorminerva on 14/08/2016.
 */
public class KeepUtil {

    public static Boolean isNull(Object obj){
        Boolean isNull = false;
        if(null == obj)
            isNull = true;

        return isNull;
    }

    public static Boolean isNullOrEmpty(Object obj){
        Boolean isNull = false;
        if(null == obj || "".equals(obj))
            isNull = true;

        return isNull;
    }

    public static int getColor(@ColorRes int colorRes, Activity mActivity) {
        return ContextCompat.getColor(mActivity, colorRes);
    }

    public static void setFontToTextView(Activity activity, TextView textView, String font){
        Typeface typeFace = Typeface.createFromAsset(activity.getAssets(), font);
        textView.setTypeface(typeFace);
    }
}
