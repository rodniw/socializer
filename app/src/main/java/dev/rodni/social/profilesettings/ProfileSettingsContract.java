package dev.rodni.social.profilesettings;

import dev.rodni.social.BasePresenter;
import dev.rodni.social.BaseView;

public interface ProfileSettingsContract {
    interface View extends BaseView<Presenter> {
        void startLogInActivity();

        void showAuthCard(boolean show);

        void showProgressIndicator(boolean show);
    }

    interface Presenter extends BasePresenter {
        void onDeleteAccountPress();

        void onDeleteAccountConfirmed(String password);
    }
}
