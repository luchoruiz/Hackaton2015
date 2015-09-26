package com.android.desafioaudionews.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.android.desafioaudionews.R;
import com.android.desafioaudionews.adapters.CustomTabPagerAdapter;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.contentViews)
     ViewPager viewContainer;
    @InjectView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @InjectView(R.id.navigation_view)
    NavigationView view;
    @InjectView(R.id.tabLayout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.inject(this);
        setToolbar();
        setDrawerMenu();
        setupViewPager();
    }

    private void setDrawerMenu() {
        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                Intent toShare = new Intent(getApplicationContext(),ScrShare.class);
                toShare.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(toShare);
                drawerLayout.closeDrawers();
                return true;
            }
        });

    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupViewPager() {

        String[] categoriesList = getResources().getStringArray(R.array.string_categories);
        CustomTabPagerAdapter adapter = new CustomTabPagerAdapter(getSupportFragmentManager(),categoriesList);
        /*for (String category : categoriesList) {
            CategoryFragment fragment = new CategoryFragment();
            adapter.addFrag(fragment);
        }*/
        viewContainer.setAdapter(adapter);
        //this method is used to prevent that the ViewPager destroy the off-screen views
        viewContainer.setOffscreenPageLimit(categoriesList.length);
        //disable scrolling into viwePager
        tabLayout.setupWithViewPager(viewContainer);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}