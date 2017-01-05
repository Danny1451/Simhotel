package com.real.simhotel.internal.dl.modules;

import android.app.Application;
import android.content.Context;

import com.real.simhotel.MainApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by liudan on 2016/12/7.
 */
@Module
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    protected Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    protected MainApplication provideApplication() {
        return (MainApplication)this.application;
    }

}
