package teameleven.smartbells2.dashboardfragmenttabs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

import teameleven.smartbells2.BeginWorkout;
import teameleven.smartbells2.RecordWorkoutRoutine;
import teameleven.smartbells2.SmartBellsMainActivity;
import teameleven.smartbells2.businesslayer.localdatabase.DatabaseAdapter;
import teameleven.smartbells2.businesslayer.tableclasses.Routine;

/**
 * This class shows the list of user's routines
 * Created by Jare on 2015-11-19.
 * Updated by Brian McMahon
 */
public class MyRoutines_Fragment extends ListFragment {

    BeginWorkout dashboard;
    public static final String ROUTINE_ITEM_NAME = DatabaseAdapter.ROUTINE_NAME;
    //Temporary string array to populate list
    private ArrayList<Routine> myroutines;
    private ArrayList<Routine> myPrivateRoutines;
    private ArrayList<String> myPrivateRoutinesList;
    private DatabaseAdapter db;
    private ArrayAdapter<String> adapter;

    /**
     * Display the page of user's routines
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState Bundle
     * @return super.onCreateView(inflater, container, savedInstanceState);
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //Tells main activity ADD button what type of item to add (ACHIEVEMENTS)
        SmartBellsMainActivity.dashboardTab.setCheckTabPage(4);
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
        /*
        * myroutine<Routine> will hold a list of all routines.
        * any routine whose is private will be added to myPrivateRoutines<Routine> <--
         * myPrivateRoutineList<String> will hold the name reference to each private routine.
        * */


        myPrivateRoutinesList = db.getMyRoutinesAsStrings(db.getUserIDForSession());


        //close the database
        db.closeLocalDatabase();

        //Change adapter type to handle objects instead of strings later
        //Set the adapter to show in application
        adapter = new ArrayAdapter<String>(getActivity().getBaseContext(),
                                      android.R.layout.simple_list_item_1, myPrivateRoutinesList);
        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    //run list code on tab select
    @Override
    public void onStart()
    {
        super.onStart();
        getListView();
    }

    /***
     *
     * @param lv
     * @param view
     * @param position
     * @param id
     * This method will allow a routine to be deleted
     */
    @Override
    public void onListItemClick(ListView lv, View view, int position, long id) {
        final int pos = position;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("Record", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //Start an intent when a list item is clicked
                Intent intent = new Intent(getActivity(), RecordWorkoutRoutine.class);
                //When we start the new intent we want to pass the name of the Routine from the list
                intent.putExtra(ROUTINE_ITEM_NAME, myPrivateRoutinesList.get(pos));
                startActivity(intent);
            }

        });
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

                DatabaseAdapter db = new DatabaseAdapter(getActivity());
                try {
                    db.openLocalDatabase();
                    //insert more routines
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                long routineId = db.getRoutineIDByName(myPrivateRoutinesList.get(pos));
                db.deleteRoutine(routineId, true);
                adapter.notifyDataSetChanged();
                db.closeLocalDatabase();

                Toast.makeText(getActivity(), "Routine Deleted", Toast.LENGTH_LONG).show();
            }
        });

        Dialog mydialog = builder.setView(new View(getActivity())).create();

        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.copyFrom(mydialog.getWindow().getAttributes());
        params.width = 630;
        params.height = 250;
        mydialog.show();
        mydialog.getWindow().setAttributes(params);

    }
}
