package com.real.simhotel.internal.dl.modules;

import android.app.Activity;

import com.real.simhotel.internal.dl.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by liudan on 2016/12/7.
 */
@PerActivity
@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    protected Activity provideActivity() {
        return this.activity;
    }
}
