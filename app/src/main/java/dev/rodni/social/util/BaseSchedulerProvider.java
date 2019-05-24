package dev.rodni.social.util;

import io.reactivex.Scheduler;


public interface BaseSchedulerProvider {

    Scheduler computation();

    Scheduler io();

    Scheduler ui();
}
