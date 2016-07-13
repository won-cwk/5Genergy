package teameleven.smartbells2;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TabHost;
import android.widget.TextView;

import teameleven.smartbells2.dashboardfragmenttabs.Exercises_Fragment;
import teameleven.smartbells2.dashboardfragmenttabs.Routines_Fragment;
import teameleven.smartbells2.dashboardfragmenttabs.Workouts_Fragment;

/**
 * Created by: Jarret Holden
 *             Jordan Medwid
 * This class treats the dashboard page that is the fisr page of the smartbells after login.
 */
public class Dashboard extends Fragment {
    /**
     * FragmentTabHost of the dashboard tab host
     */
    private FragmentTabHost dashboardTabHost;
    /**
     * A number of tab page
     */
    private int checkTabPage;
    /**
     * FloatingActionButton
     */
    private FloatingActionButton fab;

    /**
     * Create the view and setup the tab host to view
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState Bundle
     * @return view for dashborad of smartbells main
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        /**
         * View for the first main page of dashboard
         */
        View rootView = inflater.inflate(R.layout.tab_fragment_dashboard, container, false);
        /**
         * Set the tabhost(title) at the dashboard tab host
         */
        dashboardTabHost = (FragmentTabHost) rootView.findViewById(android.R.id.tabhost);
        /**
         * Set the contents at the dashboard tab host
         */
        dashboardTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

        /**
         * Show the FloatingActionButton
         */
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();

        /**
         * Creates a listener, and handles fragment manager to swap between list fragments
         */
        TabHost.OnTabChangeListener tabChangeListener = new TabHost.OnTabChangeListener() {
            //Inner method to control tab functions
            @Override
            public void onTabChanged(String tabId) {

                android.support.v4.app.FragmentManager fragManager = getActivity().getSupportFragmentManager();

                Workouts_Fragment workout_Fragment = (Workouts_Fragment) fragManager.findFragmentByTag("workouts");
                Routines_Fragment routine_Fragment = (Routines_Fragment) fragManager.findFragmentByTag("routines");
                Exercises_Fragment exercises_Fragment = (Exercises_Fragment) fragManager.findFragmentByTag("exercises");


                android.support.v4.app.FragmentTransaction fragTransaction = fragManager.beginTransaction();

                //Detach Workout_Fragment if it exists
                if (workout_Fragment != null) {
                    fragTransaction.detach(workout_Fragment);
                }

                //Detach Routine_Fragment if it exists
                if (routine_Fragment != null) {
                    fragTransaction.detach(routine_Fragment);
                }

                //Detach Achievement_Fragment if it exists
                if (exercises_Fragment != null) {
                    fragTransaction.detach(exercises_Fragment);
                }

                //If the current tab is on workouts, show only workouts
                if (tabId.equalsIgnoreCase("workouts")) {
                    setCheckTabPage(0);
                    if (workout_Fragment == null) {
                        if(savedInstanceState != null) {
                            fragTransaction.add(R.id.realtabcontent, new Workouts_Fragment(), "workouts");
                            return;
                        }
                    } else {
                        fragTransaction.attach(workout_Fragment);
                    }
                }

                //If the current tab is on routines, show only routines
                if (tabId.equalsIgnoreCase("routines")) {
                    setCheckTabPage(1);
                    if (routine_Fragment == null) {
                        if(savedInstanceState != null) {
                            fragTransaction.add(R.id.realtabcontent, new Routines_Fragment(), "routines");
                            return;
                        }
                    } else {
                        fragTransaction.attach(routine_Fragment);
                    }
                }

                //If the current tab is on achievements show only achieves
                if (tabId.equalsIgnoreCase("exercises")) {
                    setCheckTabPage(2);
                    if (exercises_Fragment == null) {
                        if(savedInstanceState != null) {
                            fragTransaction.add(R.id.realtabcontent, new Exercises_Fragment(), "exercises");
                            return;
                        }
                    } else {
                        fragTransaction.attach(exercises_Fragment);
                    }
                }
                fragTransaction.commit();
            }
        };

        /**
         * Setting up the tab change listener
         */
        dashboardTabHost.setOnTabChangedListener(tabChangeListener);
        /**
         * Setting up builders for each tabs
         * Workout Tab creation
         */
        dashboardTabHost.addTab(dashboardTabHost.newTabSpec("workouts").setIndicator("Workouts"), Workouts_Fragment.class, null);
        /**
         * Routine Tab creation
         */
        dashboardTabHost.addTab(dashboardTabHost.newTabSpec("routines").setIndicator("Routines"), Routines_Fragment.class, null);
        /**
         * Achievements Tab creation
         */
        dashboardTabHost.addTab(dashboardTabHost.newTabSpec("exercises").setIndicator("Exercises"), Exercises_Fragment.class, null);

        /**
         * Adjusting the text within the tabs to properly show titles
         */
        for (int i = 0; i < 3; i++)
        {
            TextView x = (TextView) dashboardTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            if (i == 2)
            {
                x.setTextSize(14);
            } else {x.setTextSize(14);}
        }
        //Return the view
        return rootView;
    }

    /**
     * Get the tab page number
     * @return a number of tab page
     */
    public int getCheckTabPage() {
        return checkTabPage;
    }

    /**
     * Set the page number of the tab
     * @param checkTabPage a number of the tab page
     */
    public void setCheckTabPage(int checkTabPage) {
        this.checkTabPage = checkTabPage;
        //Use for debugging - Want to make sure variable is changing with tab clicks.
        System.err.print(checkTabPage);
    }


}