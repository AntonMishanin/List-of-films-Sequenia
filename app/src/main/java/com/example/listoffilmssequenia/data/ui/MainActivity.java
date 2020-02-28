package com.example.listoffilmssequenia.data.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.listoffilmssequenia.R;
import com.example.listoffilmssequenia.data.ui.films.ListFragment;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        startFragment();
    }

    private void initToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar_fragment_details);
        setSupportActionBar(toolbar);
    }

    private void startFragment(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        ListFragment listFragment = new ListFragment();
        fragmentTransaction.replace(R.id.frame_layout_container, listFragment);
        fragmentTransaction.commit();
    }

    public void onBackPressedFromDetailFragment(View view) {
        onBackPressed();
    }
}
