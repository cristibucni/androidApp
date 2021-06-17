package com.exam.AndroidApp.main;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.exam.AndroidApp.animator.AnimatorActivity;
import com.exam.AndroidApp.camera.CameraActivity;
import com.exam.AndroidApp.nav.ProfileFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    String NameHolder;
    TextView Name;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private FrameLayout frameLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        Intent intent = getIntent();
        // Receiving User username Send By MainActivity.
        this.NameHolder = intent.getStringExtra(MainActivity.userName);

        // Init widgets
        initializeViews();
        toggleDrawer();
        // Default fragment, dashboard
        initializeDefaultFragment(savedInstanceState,0);
    }


    private void initializeViews() {
        /**
         * Initialize all widgets
         */
        toolbar = findViewById(R.id.toolbar_id);
        toolbar.setTitle(R.string.toolbar_title);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout_id);
        frameLayout = findViewById(R.id.framelayout_id);
        navigationView = findViewById(R.id.navigationview_id);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        Name = (headerView).findViewById(R.id.username);
        Name.setText(Name.getText().toString()+ NameHolder);

    }

    private void toggleDrawer() {
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }


    private void initializeDefaultFragment(Bundle savedInstanceState, int itemIndex){
        /**
         * Checks if the savedInstanceState is null - onCreate() is ran
         * If so, display fragment of navigation drawer menu at position itemIndex and
         * set checked status as true
         * @param savedInstanceState
         * @param itemIndex
         */
        if (savedInstanceState == null){
            MenuItem menuItem = navigationView.getMenu().getItem(itemIndex).setChecked(true);
            onNavigationItemSelected(menuItem);

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_profile_id:
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new ProfileFragment())
                        .commit();
                closeDrawer();
                break;
            case R.id.image_taker_id:
                Intent intent = new Intent(DashboardActivity.this, CameraActivity.class);
                startActivity(intent);
                closeDrawer();
                break;
            case R.id.nav_animator_id:
                Intent animatorIntent = new Intent(DashboardActivity.this, AnimatorActivity.class);
                startActivity(animatorIntent);
                closeDrawer();
                break;
            case R.id.logout_id:
                this.logout();
                break;
        }
        return true;
    }

    private void closeDrawer(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void logout(){
        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();

        Toast.makeText(DashboardActivity.this,"Successfully logged out", Toast.LENGTH_LONG).show();
    }

}