package dev.rodni.social.data.database;

import android.content.Context;
import android.support.annotation.NonNull;

import dev.rodni.social.data.auth.AuthSource;
import dev.rodni.social.data.auth.FirebaseAuthService;


public class DatabaseInjection {
    public static DatabaseSource provideDatabaseSource() {
        return FirebaseDatabaseService.getInstance();
    }
}
