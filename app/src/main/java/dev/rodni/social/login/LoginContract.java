package dev.rodni.social.login;

import android.support.annotation.StringRes;

import dev.rodni.social.BasePresenter;
import dev.rodni.social.BaseView;

public interface LoginContract {
    interface View extends BaseView<LoginContract.Presenter> {
        void makeToast(@StringRes int stringId);

        void makeToast(String message);

        String getEmail();

        String getPassword();

        void startProfileActivity();

        void startCreateAccountActivity();

        void setPresenter(LoginContract.Presenter presenter);

        void showProgressIndicator(boolean show);
    }

    interface Presenter extends BasePresenter {
        void onLogInClick();

        void onCreateClick();

    }
}
