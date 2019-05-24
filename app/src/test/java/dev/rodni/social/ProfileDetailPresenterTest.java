package dev.rodni.social;

import com.wiseass.profiler.R;

import dev.rodni.social.data.auth.AuthInjection;
import dev.rodni.social.data.auth.AuthSource;
import dev.rodni.social.data.database.DatabaseInjection;
import dev.rodni.social.data.database.DatabaseSource;
import dev.rodni.social.data.scheduler.SchedulerInjection;
import dev.rodni.social.profiledetail.ProfileDetailContract;
import dev.rodni.social.profiledetail.ProfileDetailPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Responsible for Displaying a User's bio and interests based on their profile. The user may then
 * change those fields. The result is then written to the Database.
 * Created by Ryan on 13/01/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProfileDetailPresenterTest {

    private AuthSource authSource;

    private DatabaseSource databaseSource;

    private ProfileDetailPresenter presenter;

    @Mock
    private ProfileDetailContract.View view;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        authSource = AuthInjection.provideAuthSource();
        databaseSource = DatabaseInjection.provideDatabaseSource();
        presenter = new ProfileDetailPresenter(
                authSource,
                view,
                databaseSource,
                SchedulerInjection.provideSchedulerProvider()
        );
    }


    @Test
    public void whenGetUserFails(){
        authSource.setReturnFail(true);
        presenter.subscribe();

        Mockito.verify(view).makeToast(R.string.error_no_data_found);
    }

    @Test
    public void whenProfileUpdateSuccesful(){
        presenter.subscribe();
        presenter.onDoneButtonClick();

        Mockito.verify(view).startProfilePageActivity();
    }

    @Test
    public void whenProfileUpdateFails(){
        presenter.subscribe();
        databaseSource.setReturnFail(true);
        presenter.onDoneButtonClick();

        Mockito.verify(view).makeToast(Mockito.anyString());
    }

    @Test
    public void onBackButtonPress(){
        presenter.subscribe();
        presenter.onBackButtonClick();

        Mockito.verify(view).startProfilePageActivity();
    }

}
