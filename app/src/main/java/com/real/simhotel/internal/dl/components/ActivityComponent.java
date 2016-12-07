package com.real.simhotel.internal.dl.components;

import android.app.Activity;

import com.real.simhotel.internal.dl.PerActivity;
import com.real.simhotel.internal.dl.modules.ActivityModule;

import dagger.Component;

/**
 * Created by liudan on 2016/12/7.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity activity();
}
