package dev.rodni.social.profilepage;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import dev.rodni.social.R;

public class ProfilePageActivity extends AppCompatActivity {
    private static final String PAGE_FRAGMENT = "PAGE_FRAGMENT";

    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        manager = this.getFragmentManager();

        ProfilePageFragment fragment = (ProfilePageFragment) manager.findFragmentByTag(PAGE_FRAGMENT);

        if (fragment == null){
            fragment = ProfilePageFragment.newInstance();
        }

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.cont_profile_page_fragment, fragment, PAGE_FRAGMENT)
                .commit();

    }
}
