package com.real.simhotel.view.navigation;

import android.content.Context;
import android.content.Intent;

import com.real.simhotel.config.Constants;
import com.real.simhotel.view.activity.LoginActivity;
import com.real.simhotel.view.activity.TrainingChooseActivity;
import com.real.simhotel.view.activity.student.StudentMainActivity;
import com.real.simhotel.view.activity.teacher.TeacherControlActivity;
import com.real.simhotel.view.activity.teacher.TeacherHRManagerActivity;
import com.real.simhotel.view.activity.teacher.TeacherTraningInitActivity;

import javax.inject.Inject;

/**
 * Created by liudan on 2016/12/7.
 */
public class Navigator {
    @Inject
    public Navigator() {
    }

    public void toStudentMainActivity(Context context, int role){

        Intent intent =new Intent(context, StudentMainActivity.class);
        intent.putExtra("user_role",role);
        context.startActivity(intent);

    }



    public void toTeacherTrainingDetailActivity(Context context){

        Intent intent = new Intent(context, TrainingChooseActivity.class);

        intent.putExtra("user_type", Constants.USER_TYPE_TEACHER);
        context.startActivity(intent);
    }

    public void toStudentTrainingDetailActivity(Context context){

        Intent intent = new Intent(context, TrainingChooseActivity.class);

        intent.putExtra("user_type", Constants.USER_TYPE_STUDENT);
        context.startActivity(intent);
    }

    public void toTrainingControlActivity(Context context){
        Intent intent = new Intent(context, TeacherControlActivity.class);
        context.startActivity(intent);
    }



    public void toTrainingInitActivity(Context context){
        Intent intent = new Intent(context, TeacherTraningInitActivity.class);
        context.startActivity(intent);
    }

    public void toHRManagerActivity(Context context){
        Intent intent = new Intent(context, TeacherHRManagerActivity.class);
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
