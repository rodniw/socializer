package dev.rodni.social.data.photos;

import android.content.ContentResolver;

import java.util.List;

import io.reactivex.Maybe;

public interface PhotoSource {
    Maybe<List<Photo>> getThumbnails(ContentResolver resolver);

    void setReturnFail(boolean bool);
}
