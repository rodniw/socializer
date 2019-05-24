package dev.rodni.social.createaccount;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import dev.rodni.social.R;
import dev.rodni.social.util.ActivityUtils;

public class CreateAccountActivity extends AppCompatActivity {
    private static final String CREATE_FRAGMENT = "CREATE_FRAGMENT";

    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        manager = this.getFragmentManager();

        //set up fragment
        CreateAccountFragment fragment = (CreateAccountFragment)
                manager.findFragmentByTag(CREATE_FRAGMENT);

        if (fragment == null){
            fragment = CreateAccountFragment.newInstance();
        }

        ActivityUtils.addFragmentToActivity(manager,
                fragment,
                R.id.cont_create_fragment,
                CREATE_FRAGMENT
        );

    }
}
