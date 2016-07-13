package teameleven.smartbells2.dashboardfragmenttabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;

import teameleven.smartbells2.Dashboard;
import teameleven.smartbells2.RecordWorkoutRoutine;
import teameleven.smartbells2.SmartBellsMainActivity;
import teameleven.smartbells2.businesslayer.localdatabase.DatabaseAdapter;

/** Created by Jordan Medwid on 10/18/2015.
 * This class will handle an array of workout objects to show them to the user
 */

public class Workouts_Fragment extends ListFragment {

    Dashboard dashboard;
    public static final String SESSION_ITEM_NAME = DatabaseAdapter.SESSION_NAME;
    private ArrayList<String> listOfWorkouts;
    //Temporary string array to populate list
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //Tells main FAB button what type of item to add (WORKOUT)
        //Refer to ONCLICK- SmartBellsMainActivity
        SmartBellsMainActivity.dashboardTab.setCheckTabPage(0);

        DatabaseAdapter db = new DatabaseAdapter(getActivity());
        try {
            db.openLocalDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Change adapter type to handle objects instead of strings later
        //Set the adapter to show in application
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, listOfWorkouts);
        //setListAdapter(adapter);

        /**
         * Select Workouts of userId
         */
        listOfWorkouts = db.getMyWorkoutsAsStrings(db.getUserIDForSession());
        if (listOfWorkouts != null ){
            //Change adapter type to handle objects instead of strings later
            //Set the adapter to show in application
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(),
                    android.R.layout.simple_list_item_1, listOfWorkouts);
            setListAdapter(adapter);
        }else{
            System.out.println("listOfWorkouts null ");
        }
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

    /**
     * Override the view when the list item clicked
     * @param lv ListView of Routine name
     * @param view View
     * @param position postion of the item
     * @param id Id of the clicked item
     */
    @Override
    public void onListItemClick(ListView lv, View view, int position, long id) {

        Intent intent = new Intent(getActivity(), RecordWorkoutRoutine.class);
        //When we start the new intent we want to pass the name of the Routine from the list
        intent.putExtra(SESSION_ITEM_NAME, listOfWorkouts.get(position));

        startActivity(intent);




    }

}
