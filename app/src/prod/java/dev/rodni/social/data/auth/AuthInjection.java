package dev.rodni.social.data.auth;


public class AuthInjection {

    public static AuthSource provideAuthSource(){
        return FirebaseAuthService.getInstance();
    }

}