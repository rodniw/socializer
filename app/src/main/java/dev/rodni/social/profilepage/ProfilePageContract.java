package dev.rodni.social.profilepage;

import dev.rodni.social.BasePresenter;
import dev.rodni.social.BaseView;

public interface ProfilePageContract {

    interface View extends BaseView<Presenter> {
        void setPresenter(ProfilePageContract.Presenter presenter);

        void setName (String name);

        void setEmail (String email);

        void setBio (String bio);

        void setInterests (String interests);

        void setProfilePhotoURL (String profilePhotoURL);

        void setDefaultProfilePhoto ();

        void startPhotoGalleryActivity();

        void startProfileDetailActivity();

        void startProfileSettingsActivity();

        void showLogoutSnackbar ();

        void startLoginActivity();

        void setThumbnailLoadingIndicator(boolean show);

        void setDetailLoadingIndicators(boolean show);
    }

    interface Presenter extends BasePresenter {
        void onThumbnailClick();

        void onEditProfileClick();

        void onLogoutClick();

        void onLogoutConfirmed();

        void onAccountSettingsClick();

        void onThumbnailLoaded();
    }
}
