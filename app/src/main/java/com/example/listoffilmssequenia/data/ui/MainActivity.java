package com.example.listoffilmssequenia.data.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.listoffilmssequenia.R;
import com.example.listoffilmssequenia.data.ui.films.ListFilmsFragment;

public class MainActivity extends AppCompatActivity implements OnBackClickListener {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ListFilmsFragment listFilmsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("dd", "onCreate: MA");

        initToolbar();
        startFragment();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_fragment_details);
        setSupportActionBar(toolbar);
    }

    private void startFragment() {
        fragmentManager = getSupportFragmentManager();
        listFilmsFragment = (ListFilmsFragment) fragmentManager.findFragmentByTag("ListFilmsFragment");

        if (listFilmsFragment == null) {
            fragmentTransaction = fragmentManager.beginTransaction();
            listFilmsFragment = new ListFilmsFragment();
            fragmentTransaction.replace(R.id.frame_layout_container, listFilmsFragment, "ListFilmsFragment");
            fragmentTransaction.commit();
        }
        listFilmsFragment.setListener(this);
    }

    public void onBackPressedFromDetailFragment(View view) {
        onBackPressed();

        listFilmsFragment = (ListFilmsFragment) fragmentManager.findFragmentByTag("ListFilmsFragment");
        if (listFilmsFragment != null) {
            listFilmsFragment.setPosition();
        }
    }

    @Override
    public void onBackClick() {
        listFilmsFragment = (ListFilmsFragment) fragmentManager.findFragmentByTag("ListFilmsFragment");
        if (listFilmsFragment != null) {
            listFilmsFragment.setPosition();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("dd", "onDestroy: MA ");
    }
}
