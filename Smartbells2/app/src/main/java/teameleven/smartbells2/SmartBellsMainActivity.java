package teameleven.smartbells2;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import teameleven.smartbells2.businesslayer.SessionManager;
import teameleven.smartbells2.create.CreateExercise;
import teameleven.smartbells2.create.CreateRoutine;
import teameleven.smartbells2.create.CreateWorkout;
import teameleven.smartbells2.dashboardfragmenttabs.Achievement_Fragment;
import teameleven.smartbells2.dashboardfragmenttabs.New_Records_Fragment;

/**
 * This class is the Main Activity of SmartBells
 * Updated by Brian McMahon
 *            Jarret Holden
 *            Jordan Medwid
 */
public class SmartBellsMainActivity extends AppCompatActivity
                                    implements NavigationView.OnNavigationItemSelectedListener {
    private static final String AUTHORITY =
            "teameleven.smartbells2.businesslayer.synchronization.provider";
    /**
     * Account type of the smartbells
     */
    private static final String ACCOUNT_TYPE = "smart-bells-staging.herokuapp.com";
    /**
     * Dashboard of the tab menu
     */
    public static Dashboard dashboardTab = new Dashboard();
    /**
     * Display the beginworkout page at once
     */
    public static BeginWorkout bw2 = new BeginWorkout();
    /**
     * Fragment
     */
    private Fragment fragmentDashBoard = null;
    private Fragment fragment = null;
    /**
     * FragmentTransaction
     */
    private FragmentTransaction transaction;
    /**
     * FloatingActionButton
     */
    private FloatingActionButton fab;

    private SessionManager session;

    private int fragmentShowing = 0;

    /**
     * Create the view of main activity pages
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_bells_main);

        //Go to the Dashboard
        fragmentDashBoard = new Dashboard();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, fragmentDashBoard);
        transaction.commit();
        fragmentShowing = 0;

        //instantiate session
        session = new SessionManager(getApplicationContext());
        //Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
        session.checkLogin();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //*************************************FAB**************************************************

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* DASHBOARD TAB SELECTED */

                //Load Create a Workout on FAB click while on Workout Tab
                if(dashboardTab.getCheckTabPage() == 0) {
                    //hide the fab
                    fab.animate().translationY(fab.getHeight() + 50).setInterpolator(
                                                            new AccelerateInterpolator(2)).start();
                    fragment = new CreateWorkout();
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_main, fragment);
                    transaction.commit();
                }

                //Load Create a Routine on FAB click while on Routine Tab
                if(dashboardTab.getCheckTabPage() == 1) {
                    //hide the fab
                    fab.animate().translationY(fab.getHeight() + 50).setInterpolator(
                                                            new AccelerateInterpolator(2)).start();
                    fragment = new CreateRoutine();
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_main, fragment);
                    transaction.commit();
                }

                //Probably going to remove this in favour for a new Exercise Class
                if(dashboardTab.getCheckTabPage() == 2) {
                    //hide the fab
                    fab.animate().translationY(fab.getHeight() + 50).setInterpolator(
                                                            new AccelerateInterpolator(2)).start();
                    fragment = new CreateExercise();
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_main, fragment);
                    transaction.commit();
                }

                /* BEGIN WORKOUT FRAGMENT TAB SELECTED */

                //This might go all together? Will keep if we decide to add a new tab type.
                if( (dashboardTab.getCheckTabPage() == 4) || (dashboardTab.getCheckTabPage() == 5) ) {
                    fab.animate().translationY(fab.getHeight() + 50).setInterpolator(
                            new AccelerateInterpolator(2)).start();
                    fragment = new CreateWorkout();
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_main, fragment);
                    transaction.commit();
                }

            }
        });

        //*******************************DRAWER****************************************************
        /**
         * DrawerLayout of the main page
         */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }



    @Override
    public void onResume() {
        super.onResume();
        //when the activity is resumed we check to see if the user is still logged in
        session.checkLogin();
    }

    @Override
    public void onBackPressed() {
        //
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //If the drawer is open close the drawer
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if ( fragmentShowing == 0 ) {
            //if the dashboard is visible close the app
            //close app if back is pressed on login activity
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to leave the app?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            SmartBellsMainActivity.super.onBackPressed();

                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            startActivity(intent);
                            finish();
                        }
                    }).create().show();
        } else {
            //else go to the dashboard
            fragment = new Dashboard();
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_main, fragment);
            transaction.commit();
            fragmentShowing = 0;
        }
    }

    /**
     * Handle action bar item clicks here. The action bar will
     * automatically handle clicks on the Home/Up button, so long
     * as you specify a parent activity in AndroidManifest.xml
     * @param item : Item of the mene
     * @return super.onOptionsItemSelected(item);
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /**
         * Item id
         */
        int id = item.getItemId();
        /**
         * noinspection SimplifiableIfStatement
         */
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Navigates when an item selected
     * @param item : An item of the menu
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //Drawer Selected Items
        //Handle the Actions
        if (id == R.id.nav_dashboard) {
            fragmentShowing = 0;
            fragment = new Dashboard();
        } else if (id == R.id.nav_beginworkout) {
            fragmentShowing = 1;
            //dashboardTab.setCheckTabPage(4);
            fragment = new BeginWorkout();
        } else if (id == R.id.nav_achievements) {


        //start of github overwrite
            fragment = new Achievement_Fragment();
        } else if (id == R.id.nav_records){
            fragment = new New_Records_Fragment();
        }
        else if (id == R.id.nav_about) {
        /* github overwrite - leaving in case of error
            fragmentShowing = 2;
            fragment = new AchievementDashboard();
        } else if (id == R.id.nav_about) {
            //View about page
            fragmentShowing = 3;
        origin/master*/




            fragment = new About();
        } else if (id == R.id.nav_profile) {
            //View and edit profile
            fragmentShowing = 4;
            fragment = new ViewProfile();
        }else if (id == R.id.nav_requestsync){//this should be removed - temporarily here for testing
            Bundle sync = new Bundle();
            sync.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
            ContentResolver.requestSync(LoginActivity.getAccount(), AUTHORITY, sync);

        }
        else if (id == R.id.nav_logout) {
            //Confirm logout
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to logout?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            SmartBellsMainActivity.super.onBackPressed();
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            session.logoutUser();
                        }
                    }).create().show();

        }

        if (fragment != null) {
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_main, fragment);
            transaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
