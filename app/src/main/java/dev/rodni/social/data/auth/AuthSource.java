package dev.rodni.social.data.auth;

import io.reactivex.Completable;
import io.reactivex.Maybe;

public interface AuthSource {

    Completable createAccount(Credentials cred);

    Completable attemptLogin(Credentials cred);

    Completable deleteUser();

    Maybe<User> getUser();

    Completable logUserOut();

    Completable reauthenticateUser(String password);

    void setReturnFail(boolean bool);
}
