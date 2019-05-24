package dev.rodni.social.data.photos;

import android.content.Context;
import android.support.annotation.NonNull;

import dev.rodni.social.data.database.DatabaseSource;
import dev.rodni.social.data.database.FirebaseDatabaseService;

public class PhotoInjection {

    public static PhotoSource providePhotoSource() {
        return PhotoService.getInstance();
    }
}
