package com.if4a.cashflow.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.if4a.cashflow.R;
import com.if4a.cashflow.fragment.HomeFragment;
import com.if4a.cashflow.fragment.StatistikFragment;
import com.if4a.cashflow.fragment.UserFragment;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView navbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navbar = findViewById(R.id.navbar);

        bukafragment(new HomeFragment());

        navbar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId= item.getItemId();

                if (itemId == R.id.navHome){
                    bukafragment(new HomeFragment());
                }
                else if (itemId== R.id.navStatistik){
                    bukafragment(new StatistikFragment());
                }
                else if (itemId == R.id.navUser){
                    bukafragment(new UserFragment());
                }
                else {
                    return false;
                }
                return false;
            }

        });
    }

    private void bukafragment(Fragment fragment) {
        FragmentManager FM = getSupportFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();
        FT.replace(R.id.fl_container, fragment);
        FT.commit();
    }
}