package teameleven.smartbells2.dashboardfragmenttabs;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import teameleven.smartbells2.SmartBellsMainActivity;
import teameleven.smartbells2.businesslayer.localdatabase.DatabaseAdapter;

/** Created by Jordan Medwid on 10/18/2015.
 * This class will handle an array of record objects to show them to the user
 * This class will be edited to accept JSON objects retrieved from a server- *Sprint 2
 */

public class Records_Fragment extends ListFragment {

    /**
     * List of workout ids
     */
    public ArrayList<String> listOfWorkoutIds;
    /**
     * List of WorkoutSetGroup ids
     */
    public ArrayList<String> listOfWorkoutSetGroupIds;
    /**
     * List of SetGroups ids
     */
    public ArrayList<String> listOfSetGroups;

    /**
     * Display the list of workoutroutine
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState Bundle
     * @return super.onCreateView(inflater, container, savedInstanceState);
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //Tells main activity ADD button what type of item to add (RECORD)
        SmartBellsMainActivity.dashboardTab.setCheckTabPage(1);

        //Change adapter type to handle objects instead of strings later
        //Set the adapter to show in application

        DatabaseAdapter db = new DatabaseAdapter(getActivity());
        try {
            //open the database
            db.openLocalDatabase();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        //List of routines
        /**
         * Specific Workouts list of User
         * => get Set Group Id
         * => get Exercise Id
         * => sum of the values(sets, reps)
         */
        // Select PK_WORKOUTSESSION_ID with UserID from WORKOUTSESSION_TABLE
        listOfWorkoutIds = db.getMyWorkoutIds(db.getUserIDForSession());//get Workout Session Ids
        Iterator iterator = listOfWorkoutIds.iterator();
        Iterator iteratorSetGroupId;//From WORKOUTSETGROUP_TABLE
        Iterator iteratorSetGroup;//From SETGROUP_TABLE


        //Select WorkoutsSetGroupIds with WorkOutIds from WORKOUTSESSION_TABLE
        while (iterator.hasNext()) {// select WourkoutSetGroupIds with WourkoutSessionId
            String workoutSessionId = (String) iterator.next();
            listOfWorkoutSetGroupIds = db.getMyWorkoutSetGroupIds(workoutSessionId);

            //Select WorkoutsSetGroupId with WorkoutSessionIds from WORKOUTSETGROUP_TABLE
            //Set the adapter to show in application
            iteratorSetGroupId = listOfWorkoutSetGroupIds.iterator();
            while (iteratorSetGroupId.hasNext()) {
                String workoutSetGroupId = (String) iteratorSetGroupId.next();
                listOfSetGroups = db.getMySetGroupIdsByWSG(workoutSetGroupId);
                //can we get ExerciseID,sets,reps at once???
            }
            //Select SetGrouops with WorkoutsSetGroupIds from SETGROUP_TABLE
            //Set the adapter to show in application
            //iteratorSetGroup = iteratorSetGroupId.iterator();
            //while()
        }

        /**
         * List of set groups
         */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(),
                android.R.layout.simple_list_item_1, listOfSetGroups);
        setListAdapter(adapter);

        //close the database
        db.closeLocalDatabase();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    //run list code on tab select
    @Override
    public void onStart()
    {
        super.onStart();
        getListView();
    }
}

