package teameleven.smartbells2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.TabHost;
import android.widget.TextView;

import teameleven.smartbells2.dashboardfragmenttabs.Achievement_Fragment;
import teameleven.smartbells2.dashboardfragmenttabs.Records_Fragment;

/**
 * This class is the screen for the AchievementDashboard
 * Created by Jarret on 2015-11-17.
 * @Author Jordan Medwid
 */
public class AchievementDashboard extends Fragment {
    /**
     * FragmentTabHost
     */
    private FragmentTabHost dashboardTabHost;
    /**
     * Tabl page number
     */
    private int checkTabPage;
    /**
     * FloatingActionButton
     */
    private FloatingActionButton fab;

    private Achievement_Fragment achievement_Fragment;
    private Records_Fragment records_Fragment;

    private android.support.v4.app.FragmentManager fragManager;
    private android.support.v4.app.FragmentTransaction fragTransaction;

    /**
     * Create the view and setup the tab host to view
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState Bundle
     * @return  view for display the page tab_achievement_dashboard
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         * tab_achievement_dashboard page
         */
        View rootView = inflater.inflate(R.layout.tab_achievement_dashboard, container, false);

        //Hide the FAB
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.animate().translationY(fab.getHeight() + 50).setInterpolator(
                new AccelerateInterpolator(2)).start();

        dashboardTabHost = (FragmentTabHost) rootView.findViewById(android.R.id.tabhost);
        dashboardTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

        //Creates a listener, and handles fragment manager to swap between list fragments
        TabHost.OnTabChangeListener tabChangeListener = new TabHost.OnTabChangeListener() {
            /**
             * Inner method to control tab functions
             * @param tabId Tab id
             */
            @Override
            public void onTabChanged(String tabId) {

                fragManager = getActivity().getSupportFragmentManager();

                achievement_Fragment = (Achievement_Fragment) fragManager.findFragmentByTag("achievements");
                records_Fragment = (Records_Fragment) fragManager.findFragmentByTag("records");

                fragTransaction = fragManager.beginTransaction();

                //Detach Achievement_Fragment if it exists
                if (achievement_Fragment != null) {
                    fragTransaction.detach(achievement_Fragment);
                }

                //Detach Records_Fragment if it exists
                if (records_Fragment != null) {
                    fragTransaction.detach(records_Fragment);
                }

                //If the current tab is on achievements show only achieves
                if (tabId.equalsIgnoreCase("achievements")) {
                    setCheckTabPage(1);
                    if (achievement_Fragment == null) {
                        fragTransaction.add(R.id.realtabcontent, new Achievement_Fragment(), "achievements");
                    } else {
                        fragTransaction.attach(achievement_Fragment);
                    }
                }

                //If the current tab is on records show only records
                if (tabId.equalsIgnoreCase("records")) {
                    setCheckTabPage(2);
                    if (records_Fragment == null) {
                        fragTransaction.add(R.id.realtabcontent, new Records_Fragment(), "records");
                    } else {
                        fragTransaction.attach(records_Fragment);
                    }
                }

                fragTransaction.commit();

            }
        };

        /**
         * Setting up the tab change listener
         */
        dashboardTabHost.setOnTabChangedListener(tabChangeListener);

        //Setting up builders for each tabs
        /**
         * Achievements Tab creation
         */
        dashboardTabHost.addTab(dashboardTabHost.newTabSpec("achievements").setIndicator("Achievements"), Achievement_Fragment.class, null);

        /**
         * Records Tab creation
         */
        dashboardTabHost.addTab(dashboardTabHost.newTabSpec("records").setIndicator("Records"), Records_Fragment.class, null);

        /**
         * Adjusting the text within the tabs to properly show titles
         */
        for (int i = 0; i < 2; i++)
        {
            TextView x = (TextView) dashboardTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            if (i == 2)
            {
                x.setTextSize(15);
            } else {x.setTextSize(15);}
        }

        //Return the view
        return rootView;
    }

    /**
     * Get the tab page
     * @return the tab page number
     */
    public int getCheckTabPage() {
        return checkTabPage;
    }

    /**
     * Set the tab page number
     * @param checkTabPage the tab page number
     */
    public void setCheckTabPage(int checkTabPage) {
        this.checkTabPage = checkTabPage;
        //Use for debugging - Want to make sure variable is changing with tab clicks.
        System.err.print(checkTabPage);
    }


}
