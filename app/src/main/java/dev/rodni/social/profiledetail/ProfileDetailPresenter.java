package dev.rodni.social.profiledetail;

import dev.rodni.social.R;
import dev.rodni.social.data.auth.AuthSource;
import dev.rodni.social.data.auth.User;
import dev.rodni.social.data.database.DatabaseSource;
import dev.rodni.social.data.database.Profile;
import dev.rodni.social.util.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;

public class ProfileDetailPresenter implements ProfileDetailContract.Presenter {

    private AuthSource auth;
    private DatabaseSource database;
    private ProfileDetailContract.View view;
    private CompositeDisposable subscriptions;
    private BaseSchedulerProvider schedulerProvider;
    private Profile currentProfile;

    public ProfileDetailPresenter(AuthSource auth,
                                  ProfileDetailContract.View view,
                                  DatabaseSource database,
                                  BaseSchedulerProvider schedulerProvider
    ) {
        this.auth = auth;
        this.database = database;
        this.view = view;
        //this.rootRef = FirebaseDatabase.getInstance().getReference();
        this.subscriptions = new CompositeDisposable();
        this.schedulerProvider = schedulerProvider;
        view.setPresenter(this);


    }

    @Override
    public void onBackButtonClick() {
        view.startProfilePageActivity();
    }

    @Override
    public void onDoneButtonClick() {
        currentProfile.setBio(view.getBio());
        currentProfile.setInterests(view.getInterests());

        subscriptions.add(
                database.updateProfile(currentProfile)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        view.startProfilePageActivity();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.makeToast(e.getMessage());
                    }
                })
        );
    }

    private void getActiveUser(){
        subscriptions.add(auth.getUser()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(new DisposableMaybeObserver<User>() {
                    @Override
                    public void onSuccess(User user) {
                        getUserProfile(user.getUserId());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.makeToast(R.string.error_no_data_found);
                    }

                    @Override
                    public void onComplete() {
                        view.startProfilePageActivity();
                    }
                })

        );
    }

    private void getUserProfile(String uid) {
        subscriptions.add(database.getProfile(uid)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(new DisposableMaybeObserver<Profile>() {
                    @Override
                    public void onSuccess(Profile profile) {
                        ProfileDetailPresenter.this.currentProfile = profile;
                        view.setBioText(profile.getBio());
                        view.setInterestsText(profile.getInterests());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.makeToast(R.string.error_no_data_found);
                    }

                    @Override
                    public void onComplete() {
                        view.startProfilePageActivity();
                    }
                })

        );
    }

    @Override
    public void subscribe() {
        getActiveUser();
    }

    @Override
    public void unsubscribe() {
        subscriptions.clear();
    }


}
