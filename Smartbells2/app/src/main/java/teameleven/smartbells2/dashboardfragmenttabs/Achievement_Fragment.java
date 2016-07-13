package teameleven.smartbells2.dashboardfragmenttabs;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import teameleven.smartbells2.AchievementDashboard;
import teameleven.smartbells2.businesslayer.localdatabase.DatabaseAdapter;

/**
 * Created by Jordan Medwid on 10/18/2015.
 * This class will handle an array of achievement objects to show them to the user
 * This class will be edited to accept JSON objects retrieved from a server- *Sprint 2
 */
public class Achievement_Fragment extends ListFragment {
    /**
     * AchievementDashboard
     */
    AchievementDashboard dashboard;
    /**
     * Database Adapter
     */
    private DatabaseAdapter db;

    /**
     * Variable for saving a list of the workouts name
     */
    private ArrayList<String> myWorkoutList;
    /**
     * Variable for count a list of the workouts name
     */
    private ArrayList<String> myCountedWorkoutList;
    /**
     * ArrayAdapter for saving the list of WorkoutSessions
     */
    private ArrayAdapter<String> adapter;
    /**
     * Previous WorkoutSession name
     */
    private String previousWorkout = "";
    /**
     * Counter for Achievement
     */
    private int counter = 0;


    //Temporary string array to populate list
    String[] listOfRecords = new String[] {"Nothing to show for yourself."};
    /**
     * Display the list of Achievements
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState Bundle
     * @return onCreateView of the listOfAchievements
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        //Change adapter type to handle objects instead of strings later
        //Set the adapter to show in application
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                 getActivity().getBaseContext(),android.R.layout.simple_list_item_1, listOfRecords);
        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    //run list code on tab select
    // @Override
    public void onStart()
    {
        super.onStart();
        getListView();
    }

}
