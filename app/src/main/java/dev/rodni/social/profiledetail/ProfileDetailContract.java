package dev.rodni.social.profiledetail;

import android.support.annotation.StringRes;

import dev.rodni.social.BasePresenter;
import dev.rodni.social.BaseView;

public interface ProfileDetailContract {

    interface View extends BaseView<Presenter> {

        void setBioText(String bio);

        void setInterestsText(String interests);

        String getInterests();

        String getBio();

        void startProfilePageActivity();

        void setPresenter(Presenter presenter);

        void makeToast(@StringRes int message);
    }

    interface Presenter extends BasePresenter {
        void onBackButtonClick();

        void onDoneButtonClick();
    }
}
