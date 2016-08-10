package com.backendlessstringconverter.components;

import android.app.ProgressDialog;
import android.content.Context;

import com.backendlessstringconverter.R;

/**
 * Created by lfqdt on 26.07.2016.
 */
public class ProgressDialogHelper {
    private static ProgressDialog pd;

    public static void showProgress(Context context) {
        pd = new ProgressDialog(context, R.style.TransparentProgressBarStyle);
        pd.setCancelable(false);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.show();
    }

    public static void dismissProgress() {
        pd.dismiss();
    }
}
