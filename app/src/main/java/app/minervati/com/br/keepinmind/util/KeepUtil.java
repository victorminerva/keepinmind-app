package app.minervati.com.br.keepinmind.util;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import app.minervati.com.br.keepinmind.R;

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

    public static String dateToStringFormat(Date date, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String getStringFromResources(Fragment fragment, Integer string){
        return fragment.getResources().getString(string);
    }
}
