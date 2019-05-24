package dev.rodni.social.profilesettings;

import dev.rodni.social.R;
import dev.rodni.social.data.auth.AuthSource;
import dev.rodni.social.data.auth.User;
import dev.rodni.social.data.database.DatabaseSource;
import dev.rodni.social.util.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;

public class ProfileSettingsPresenter implements ProfileSettingsContract.Presenter {
    private final AuthSource auth;
    private final ProfileSettingsContract.View view;
    private final BaseSchedulerProvider schedulerProvider;
    private final CompositeDisposable disposableSubscriptions;
    private final DatabaseSource database;
    private String uid;

    //TODO: Account data for users isn't being deleted properly from RealtimeDatabase. Fix It.

    public ProfileSettingsPresenter(AuthSource auth,
                                    DatabaseSource database,
                                    ProfileSettingsContract.View view,
                                    BaseSchedulerProvider schedulerProvider) {
        this.auth = auth;
        this.database = database;
        this.view = view;
        this.schedulerProvider = schedulerProvider;
        this.disposableSubscriptions = new CompositeDisposable();
    }

    @Override
    public void subscribe() {
        getCurrentUser();
    }

    @Override
    public void unsubscribe() {
        disposableSubscriptions.clear();
    }

    @Override
    public void onDeleteAccountPress() {
        view.showAuthCard(true);
    }

    @Override
    public void onDeleteAccountConfirmed(final String password) {
        view.showAuthCard(false);
        view.showProgressIndicator(true);
        disposableSubscriptions.add(
                auth.reauthenticateUser(password)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {

                                deleteProfileFromDatabase();
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.showProgressIndicator(false);
                                view.makeToast(R.string.error_authenticating_credentails);
                            }
                        })
        );
    }

    private void deleteUser() {
        disposableSubscriptions.add(
                auth.deleteUser()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                                view.showProgressIndicator(false);
                                view.startLogInActivity();
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.showProgressIndicator(false);
                                view.makeToast(e.getMessage());
                            }
                        })
        );
    }

    private void getCurrentUser() {
        view.showProgressIndicator(true);
        disposableSubscriptions.add(
                auth.getUser()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribeWith(
                                new DisposableMaybeObserver<User>() {

                                    @Override
                                    public void onError(Throwable e) {
                                        view.makeToast(R.string.error_retrieving_data);
                                        view.startLogInActivity();
                                    }

                                    @Override
                                    public void onComplete() {
                                        view.showProgressIndicator(false);
                                    }

                                    @Override
                                    public void onSuccess(User user) {
                                        view.showProgressIndicator(false);
                                        ProfileSettingsPresenter.this.uid = user.getUserId();
                                    }
                                }
                        )
        );
    }

    private void deleteProfileFromDatabase() {
        disposableSubscriptions.add(
                database.deleteProfile(uid)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                                deleteUser();
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.showProgressIndicator(false);
                                view.makeToast(e.getMessage());
                            }
                        })
        );
    }
}
