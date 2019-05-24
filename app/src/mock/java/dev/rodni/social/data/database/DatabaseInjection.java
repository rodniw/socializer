package dev.rodni.social.data.database;


public class DatabaseInjection {
    public static DatabaseSource provideDatabaseSource() {
        return FakeDatabaseService.getInstance();
    }
}
