package dev.rodni.social.data.scheduler;

import dev.rodni.social.util.BaseSchedulerProvider;
import dev.rodni.social.util.ImmediateSchedulerProvider;


public class SchedulerInjection {

    public static BaseSchedulerProvider provideSchedulerProvider(){
        return ImmediateSchedulerProvider.getInstance();
    }

}
