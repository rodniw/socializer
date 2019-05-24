package dev.rodni.social;


import dev.rodni.social.data.auth.AuthInjection;
import dev.rodni.social.data.auth.AuthSource;
import dev.rodni.social.data.database.DatabaseInjection;
import dev.rodni.social.data.database.DatabaseSource;
import dev.rodni.social.data.scheduler.SchedulerInjection;
import dev.rodni.social.profilepage.ProfilePageContract;
import dev.rodni.social.profilepage.ProfilePagePresenter;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;


/**
 * Responsible for displaying all of a given user's profile details.
 * Created by Ryan on 13/01/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProfilePagePresenterTest {

    private AuthSource authSource;

    private DatabaseSource databaseSource;

    @Mock
    private ProfilePageContract.View view;

    private ProfilePagePresenter presenter;

    @Before
    public void setUp() throws Exception {
       MockitoAnnotations.initMocks(this);
        authSource = AuthInjection.provideAuthSource();
        databaseSource = DatabaseInjection.provideDatabaseSource();
        presenter = new ProfilePagePresenter(
                authSource,
                view,
                databaseSource,
                SchedulerInjection.provideSchedulerProvider()
        );
    }

    @Test
    public void whenGetUserSucceeds (){
        presenter.subscribe();

        Mockito.verify(view).setName(Mockito.anyString());
        Mockito.verify(view).setEmail(Mockito.anyString());

    }

    @Test
    public void whenGetUserFails (){
        authSource.setReturnFail(true);

        presenter.subscribe();

        Mockito.verify(view).startLoginActivity();
    }

    @Test
    public void whenGetProfileSucceeds (){
        presenter.subscribe();

        Mockito.verify(view).setBio(Mockito.anyString());
        Mockito.verify(view).setInterests(Mockito.anyString());
    }

    @Test
    public void whenGetProfileFails (){
        databaseSource.setReturnFail(true);
        presenter.subscribe();

        Mockito.verify(view).startLoginActivity();


    }

    @Test
    public void whenNoUserProfileFoundForGivenUid(){
        presenter.subscribe();
        databaseSource.setReturnEmpty(true);

        Mockito.verify(view).setBio(Mockito.anyString());
        Mockito.verify(view).setInterests(Mockito.anyString());
    }

    @Test
    public void whenUserClicksProfileSettings(){
        presenter.subscribe();

        presenter.onAccountSettingsClick();

        Mockito.verify(view).startProfileSettingsActivity();
    }
    

    @Test
    public void whenLogoutSuccessful(){
        presenter.subscribe();

        presenter.onLogoutClick();
        presenter.onLogoutConfirmed();

        Mockito.verify(view).startLoginActivity();
    }

    @Test
    public void whenLogoutUnsuccessful(){
        authSource.setReturnFail(true);

        presenter.subscribe();

        presenter.onLogoutClick();
        presenter.onLogoutConfirmed();
        Mockito.verify(view).makeToast(Mockito.anyString());
    }
}
