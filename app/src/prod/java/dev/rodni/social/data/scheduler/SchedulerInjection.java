package dev.rodni.social.data.scheduler;

import dev.rodni.social.util.BaseSchedulerProvider;
import dev.rodni.social.util.SchedulerProvider;

public class SchedulerInjection {
    public static BaseSchedulerProvider provideSchedulerProvider(){
        return SchedulerProvider.getInstance();
    }
}
