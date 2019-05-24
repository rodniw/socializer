package dev.rodni.social.data.photos;

public class PhotoInjection {

    public static PhotoSource providePhotoSource() {
        return FakePhotoService.getInstance();
    }

}
