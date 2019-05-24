package dev.rodni.social;

import android.content.ContentResolver;

import dev.rodni.social.data.photos.Photo;
import dev.rodni.social.data.photos.PhotoInjection;
import dev.rodni.social.data.photos.PhotoSource;
import dev.rodni.social.data.scheduler.SchedulerInjection;
import dev.rodni.social.photogallery.PhotoGalleryContract;
import dev.rodni.social.photogallery.PhotoGalleryPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

/**
 * Responsible for Displaying a gallery of the user's device's images. When an Image is selected,
 * it forwards that image's URL to PhotoDetailActivity
 * Created by Ryan on 13/01/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class PhotoGalleryPresenterTest {

    @Mock
    private PhotoGalleryContract.View view;

    private PhotoSource photoSource;

    @Mock
    private ArrayList<Photo> photos;

    private PhotoGalleryPresenter presenter;

    @Mock
    ContentResolver resolver;

    public PhotoGalleryPresenterTest() {

    }

    @Before
    public void setUp() throws Exception {
        //Automatically Mocks fields with @Mock annotation
        MockitoAnnotations.initMocks(this);
        photoSource = PhotoInjection.providePhotoSource();

        presenter = new PhotoGalleryPresenter(
                view,
                photoSource,
                SchedulerInjection.provideSchedulerProvider(),
                resolver
        );
    }

    @Test
    public void whenLoadImageURLsReturnsData(){
        presenter.subscribe();

        Mockito.verify(view).setAdapterData(Mockito.anyList());

    }

    @Test
    public void whenLoadImageURLsReturnsNoDataOrFails(){
        photoSource.setReturnFail(true);

        presenter.subscribe();

        Mockito.verify(view).setNoListDataFound();
    }

    @Test
    public void whenUserSelectsAnImage (){

        presenter.subscribe();
        presenter.onPhotoItemClick(0);

       Mockito.verify(view).startPhotoDetailActivity(Mockito.anyString());
    }
}
