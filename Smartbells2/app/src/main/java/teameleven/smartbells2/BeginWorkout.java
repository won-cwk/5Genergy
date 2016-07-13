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

import teameleven.smartbells2.businesslayer.localdatabase.DatabaseAdapter;
import teameleven.smartbells2.dashboardfragmenttabs.MyRoutines_Fragment;
import teameleven.smartbells2.dashboardfragmenttabs.PublicRoutines_Fragment;

/**
 * Created by Jare on 2015-11-19.
 * Updated by Jordan on 2015-11-29.
 */
public class BeginWorkout extends Fragment {

    private FragmentTabHost dashboardTabHost;
    //value to be passed to RecordWorkout Activity
    public static final String ITEM_NAME = DatabaseAdapter.ROUTINE_NAME;
    private int checkTabPage;
    private FloatingActionButton fab;

    //Create the view and setup the tab host to view
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab_beginworkout_dashboard, container, false);

        dashboardTabHost = (FragmentTabHost) rootView.findViewById(android.R.id.tabhost);
        dashboardTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);


        //Show the FAB
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();

        //Creates a listener, and handles fragment manager to swap between list fragments
        TabHost.OnTabChangeListener tabChangeListener = new TabHost.OnTabChangeListener() {
            //Inner method to control tab functions
            @Override
            public void onTabChanged(String tabId) {

                android.support.v4.app.FragmentManager fragManager = getActivity().getSupportFragmentManager();

                MyRoutines_Fragment myroutines_fragment = (MyRoutines_Fragment) fragManager.findFragmentByTag("myroutines");
                PublicRoutines_Fragment publicroutines_fragment = (PublicRoutines_Fragment) fragManager.findFragmentByTag("publicroutines");

                android.support.v4.app.FragmentTransaction fragTransaction = fragManager.beginTransaction();

                //Detach myroutines_fragment if it exists
                if (myroutines_fragment != null) {
                    fragTransaction.detach(myroutines_fragment);
                }

                //Detach publicroutines_fragment if it exists
                if (publicroutines_fragment != null) {
                    fragTransaction.detach(publicroutines_fragment);
                }

                //If the current tab is on myroutines show only private routines
                if (tabId.equalsIgnoreCase("myroutines")) {
                    setCheckTabPage(4);
                    if (myroutines_fragment == null) {
                        if (savedInstanceState != null) {
                            fragTransaction.add(R.id.realtabcontent, new MyRoutines_Fragment(), "myroutines");
                            return;
                        }
                    } else {
                        fragTransaction.attach(myroutines_fragment);
                    }
                }

                //If the current tab is on publicroutines show only public routines
                if (tabId.equalsIgnoreCase("publicroutines")) {
                    setCheckTabPage(5);
                    if (publicroutines_fragment == null) {
                        if (savedInstanceState != null) {
                            fragTransaction.add(R.id.realtabcontent, new PublicRoutines_Fragment(), "publicroutines");
                            return;
                        }
                    } else {
                        fragTransaction.attach(publicroutines_fragment);
                    }
                }

                fragTransaction.commit();

            }
        };


        //Setting up the tab change listener

        dashboardTabHost.setOnTabChangedListener(tabChangeListener);

        //Setting up builders for each tabs

        //Achievements Tab creation
        dashboardTabHost.addTab(dashboardTabHost.newTabSpec("myroutines").setIndicator("My Routines"), MyRoutines_Fragment.class, null);

        //Records Tab creation
        dashboardTabHost.addTab(dashboardTabHost.newTabSpec("publicroutines").setIndicator("Public Routines"), PublicRoutines_Fragment.class, null);


        //Adjusting the text within the tabs to properly show titles
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
     * Get the tab page number
     * @return a number of tab page
     */
    public int getCheckBWTabPage() {
        return checkTabPage;
    }

    public void setCheckTabPage(int checkTabPage) {
        this.checkTabPage = checkTabPage;
        //Use for debugging - Want to make sure variable is changing with tab clicks.
        System.err.print(checkTabPage);
    }
}
