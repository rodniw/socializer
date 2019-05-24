package dev.rodni.social.photogallery;

import android.app.Activity;
import android.support.annotation.StringRes;

import dev.rodni.social.BasePresenter;
import dev.rodni.social.BaseView;
import dev.rodni.social.data.photos.Photo;

import java.util.List;

public interface PhotoGalleryContract {

    interface View extends BaseView<Presenter> {
        void setAdapterData(List<Photo> photos);

        void setNoListDataFound();

        Activity getActivityContext();

        void makeToast(@StringRes int message);

        void setPresenter(Presenter presenter);

        void startPhotoDetailActivity(String photoURL);

        void showProgressIndicator(boolean show);

    }

    interface Presenter extends BasePresenter {
        void onPhotoItemClick(int itemPosition);
    }
}
