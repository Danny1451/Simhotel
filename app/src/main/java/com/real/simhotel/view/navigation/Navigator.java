package com.real.simhotel.view.navigation;

import android.content.Context;
import android.content.Intent;

import com.real.simhotel.config.Constants;
import com.real.simhotel.view.activity.LoginActivity;
import com.real.simhotel.view.activity.TrainingChooseActivity;
import com.real.simhotel.view.activity.teacher.TeacherMainActivity;

import javax.inject.Inject;

/**
 * Created by liudan on 2016/12/7.
 */
public class Navigator {
    @Inject
    public Navigator() {
    }

    public void toStudentMainActivity(Context context){

    }

    public void toTeacherMainActivity(Context context){
        Intent intent = new Intent(context, TeacherMainActivity.class);
        context.startActivity(intent);
    }


    public void toTrainingDetailActivity(Context context){

        Intent intent = new Intent(context, TrainingChooseActivity.class);

        intent.putExtra("user_type", Constants.USER_TYPE_TEACHER);
        context.startActivity(intent);
    }
    /**
     * 调整登录
     * @param context
     */
    public void toLoginActivity(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
