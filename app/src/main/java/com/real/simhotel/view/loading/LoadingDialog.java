package com.real.simhotel.view.loading;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.TextView;

import com.real.simhotel.R;

/**
 * Created by liudan on 2017/1/12.
 */
public class LoadingDialog extends Dialog {

    TextView infoTV;

    public LoadingDialog(Context context){
        super(context);

        /**设置对话框背景透明*/
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_loading);
        infoTV = (TextView) findViewById(R.id.tv_text);
        setCanceledOnTouchOutside(false);

    }

    public LoadingDialog setMessage(String message) {
        infoTV.setText(message);
        return this;
    }
}
