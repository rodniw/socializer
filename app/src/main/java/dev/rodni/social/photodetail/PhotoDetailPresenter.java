package dev.rodni.social.photodetail;

import dev.rodni.social.R;
import dev.rodni.social.data.auth.AuthSource;
import dev.rodni.social.data.auth.User;
import dev.rodni.social.data.database.DatabaseSource;
import dev.rodni.social.data.database.Profile;
import dev.rodni.social.util.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;

public class PhotoDetailPresenter implements PhotoDetailContract.Presenter {

    private BaseSchedulerProvider schedulerProvider;
    private AuthSource auth;
    private PhotoDetailContract.View view;
    private DatabaseSource database;
    private CompositeDisposable subscriptions;
    private Profile currentProfile;

    public PhotoDetailPresenter(AuthSource auth,
                                DatabaseSource database,
                                PhotoDetailContract.View view,
                                BaseSchedulerProvider schedulerProvider
                                ) {
        this.auth = auth;
        this.database = database;
        this.view = view;
        this.schedulerProvider = schedulerProvider;
        this.subscriptions = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void onBackButtonPress() {
        view.startPhotoGalleryActivity();
    }

    @Override
    public void onDoneButtonPress() {
        view.showProgressIndicator(true);
        currentProfile.setPhotoURL(view.getPhotoURL());

        subscriptions.add(database.updateProfile(currentProfile)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        view.startProfilePageActivity();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showProgressIndicator(false);
                        view.makeToast(e.getMessage());
                    }

                })

        );
    }

    @Override
    public void onImageLoaded() {
        view.showProgressIndicator(false);
    }

    @Override
    public void onImageLoadFailure() {
        view.makeToast(R.string.error_loading_image);
        view.startPhotoGalleryActivity();
    }

    private void getCurrentProfile(String uid) {
        subscriptions.add(database.getProfile(uid)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(new DisposableMaybeObserver<Profile>() {
                    @Override
                    public void onComplete() {
                        view.startProfilePageActivity();
                    }

                    @Override
                    public void onSuccess(Profile profile) {
                        PhotoDetailPresenter.this.currentProfile = profile;
                        view.setBitmap();
                        view.showProgressIndicator(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showProgressIndicator(false);
                        view.makeToast(e.getMessage());
                    }

                })

        );
    }

    @Override
    public void subscribe() {
        getCurrentUser();
    }

    private void getCurrentUser() {
        view.showProgressIndicator(true);
        subscriptions.add(auth.getUser()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(new DisposableMaybeObserver<User>() {
                    @Override
                    public void onComplete() {
                        view.startPhotoGalleryActivity();
                    }

                    @Override
                    public void onSuccess(User user) {
                        getCurrentProfile(user.getUserId());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.startPhotoGalleryActivity();
                        view.makeToast(e.getMessage());
                    }
                })

        );
    }


    @Override
    public void unsubscribe() {
        subscriptions.clear();
    }


}
