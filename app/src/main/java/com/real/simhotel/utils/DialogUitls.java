package com.real.simhotel.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by liudan on 2017/2/4.
 */
public class DialogUitls {

    public static void showConfirmDialog(Context context,
                                         String title,
                                         String postiveTitle,
                                         DialogInterface.OnClickListener postiveListener,
                                         String negativeTitle,
                                         DialogInterface.OnClickListener negativeListener){
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setPositiveButton(postiveTitle,postiveListener)
                .setNegativeButton(negativeTitle,negativeListener)
                .show();

    }

    public static void showConfirmDialog(Context context,
                                         String title,
                                         DialogInterface.OnClickListener postiveListener,
                                         DialogInterface.OnClickListener negativeListener){
        showConfirmDialog(context,title,"确认",postiveListener,"取消",negativeListener);

    }

    public static void showConfirmDialog(Context context,
                                         String title,
                                         DialogInterface.OnClickListener postiveListener){
        showConfirmDialog(context,title,"确认",postiveListener,"取消",null);

    }
}
