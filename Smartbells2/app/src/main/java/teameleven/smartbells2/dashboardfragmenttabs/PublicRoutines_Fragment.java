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

import teameleven.smartbells2.BeginWorkout;
import teameleven.smartbells2.RecordWorkoutRoutine;
import teameleven.smartbells2.SmartBellsMainActivity;
import teameleven.smartbells2.businesslayer.localdatabase.DatabaseAdapter;
import teameleven.smartbells2.businesslayer.tableclasses.Routine;

/**
 * Created by Jare on 2015-11-19.
 * Updated by Brian McMahon
 */


public class PublicRoutines_Fragment extends ListFragment {

    BeginWorkout dashboard;
    public static final String ROUTINE_ITEM_NAME = DatabaseAdapter.ROUTINE_NAME;
    private ArrayList<String> publicroutines;
    private DatabaseAdapter db;
    private ArrayList<Routine> myroutines;
    private ArrayList<Routine> myPrivateRoutines;
    private ArrayList<String> myPublicRoutinesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //Tells main activity ADD button what type of item to add (ACHIEVEMENTS)
        SmartBellsMainActivity.dashboardTab.setCheckTabPage(5);
        //SmartBellsMainActivity.bw2.setCheckTabPage(4);

        //Open Database
        db = new DatabaseAdapter(getActivity());
        try {
            db.openLocalDatabase();
            //insert more routines
            //db.insertTESTRoutines();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Two ArrayLists for each ListView
        myPublicRoutinesList = db.getRoutinesAsStrings();

        //close the database
        db.closeLocalDatabase();

                /*
        * myroutine<Routine> will hold a list of all routines.
        * any routine whose is private will be added to myPrivateRoutines<Routine> <--
         * myPrivateRoutineList<String> will hold the name reference to each private routine.
        * */

//        //Todo Below should sort public from private routines
//        myroutines = db.selectAllRoutines(); //getMyRoutinesAsStrings(db.getUserIDForSession());
//
//        for(Routine routine: myroutines){
//            if(routine.getIsPublic() == true){
//                myPrivateRoutines.add(routine);
//            }
//        }
//
//        for (Routine privateRoutine: myPrivateRoutines){
//        myPublicRoutinesList.add(privateRoutine.getName());
//    }
//    //todo The above should sort the public from the private

        //Change adapter type to handle objects instead of strings later
        //Set the adapter to show in application
        ArrayAdapter<String> publist = new ArrayAdapter<String>(getActivity().getBaseContext(),
                             android.R.layout.simple_list_item_1, myPublicRoutinesList);
        setListAdapter(publist);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    //run list code on tab select
    @Override
    public void onStart()
    {
        super.onStart();
        getListView();
    }

    @Override
    public void onListItemClick(ListView lv, View view, int position, long id) {
        //Start an intent when a list item is clicked

        Intent intent = new Intent(getActivity(), RecordWorkoutRoutine.class);
        //When we start the new intent we want to pass the name of the Routine from the list
        intent.putExtra(ROUTINE_ITEM_NAME, myPublicRoutinesList.get(position));

//        Pull Ispublic, reps, sets from DB, pass to proper view area, below is not how to implement. Just a reference for myself - Jordan
//        intent.putExtra(ROUTINE_ISPUBLIC, list.get(position));
//        intent.putExtra(ROUTINE_REPS, list.get(position));
//        intent.putExtra(ROUTINE_SETS, list.get(position));

        startActivity(intent);
    }
}
