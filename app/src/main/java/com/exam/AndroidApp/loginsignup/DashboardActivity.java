package com.exam.AndroidApp.loginsignup;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.FrameLayout;

import com.exam.AndroidApp.animator.AnimatorActivity;
import com.exam.AndroidApp.camera.CameraActivity;
import com.exam.AndroidApp.nav.ProfileFragment;
import com.google.android.material.navigation.NavigationView;


public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    // Login properties
    String NameHolder;
    TextView Name;
    Button LogOUT ;
    //
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private FrameLayout frameLayout;
    private NavigationView navigationView;
    private SwitchCompat darkModeSwitch;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        Intent intent = getIntent();
        // Receiving User username Send By MainActivity.
        this.NameHolder = intent.getStringExtra(MainActivity.userName);



//        // Adding click listener to Log Out button.
//        LogOUT.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //Finishing current DashBoard activity on button click.
//                finish();
//
//                Toast.makeText(DashboardActivity.this,"Log out successfuly", Toast.LENGTH_LONG).show();
//
//            }
//        });

        // Init widgets
        initializeViews();
        toggleDrawer();
        // Default fragment, dashboard
        initializeDefaultFragment(savedInstanceState,0);
        setDarkModeSwitchListener();

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
        darkModeSwitch = (SwitchCompat)navigationView.getMenu().findItem(R.id.nav_darkmode_id).getActionView();

        View headerView = navigationView.getHeaderView(0);
        Name = (headerView).findViewById(R.id.username);
        Name.setText(Name.getText().toString()+ NameHolder);

    }

    private void toggleDrawer() {
        /**
         * Creates an instance of the ActionBarDrawerToggle class:
         * 1) Handles opening and closing the navigation drawer
         * 2) Creates a hamburger icon in the toolbar
         * 3) Attaches listener to open/close drawer on icon clicked and rotates the icon
         */
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


    private void setDarkModeSwitchListener(){
        /**
         * Attach setOnCheckedChangeListener to the dark mode switch
         */
        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked){
                    Toast.makeText(DashboardActivity.this, "Dark Mode Turn Off", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(DashboardActivity.this, "Dark Mode Turn On", Toast.LENGTH_SHORT).show();
                }
            }
        });
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

                // Going to Dashboard activity after login success message.
                Intent intent = new Intent(DashboardActivity.this, CameraActivity.class);
                startActivity(intent);
                closeDrawer();
                break;
            case R.id.nav_animator_id:
                Intent animatorIntent = new Intent(DashboardActivity.this, AnimatorActivity.class);
                startActivity(animatorIntent);
                closeDrawer();
                break;
          /*  case R.id.nav_send_id:
                Toast.makeText(this, "Send Pressed", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_share_id:
                Toast.makeText(this, "Share Pressed", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_photos_id:
                Toast.makeText(this, "Photos Pressed", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_trash_id:
                Toast.makeText(this, "Trash Pressed", Toast.LENGTH_SHORT).show();
                break;*/
            case R.id.logout_id:
                // log the user out
                this.logout();
                break;
        }
        return true;
    }


    private void deSelectCheckedState(){
        /**
         * Iterates through all the items in the navigation menu and deselects them:
         * removes the selection color
         */
        int noOfItems = navigationView.getMenu().size();
        for (int i=0; i<noOfItems;i++){
            navigationView.getMenu().getItem(i).setChecked(false);
        }
    }

    private void closeDrawer(){
        /**
         * Checks if the navigation drawer is open - if so, close it
         */
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void logout(){
        finish();
        Toast.makeText(DashboardActivity.this,"Log out successfully", Toast.LENGTH_LONG).show();
    }

}