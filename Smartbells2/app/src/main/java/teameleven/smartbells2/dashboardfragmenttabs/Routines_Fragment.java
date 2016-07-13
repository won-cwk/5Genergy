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

import teameleven.smartbells2.RecordWorkoutRoutine;
import teameleven.smartbells2.SmartBellsMainActivity;
import teameleven.smartbells2.businesslayer.localdatabase.DatabaseAdapter;

/**
 * Created by Jordan Medwid Medwid on 10/18/2015.
 * Updated by Brian McMahon
 * This class will handle an array of routine objects to show them to the user
 * This class will be edited to accept JSON objects retrieved from a server- *Sprint 2
 */
public class Routines_Fragment extends ListFragment {
    /**
     * Routine name
     */
    public static final String ROUTINE_ITEM_NAME = DatabaseAdapter.ROUTINE_NAME;
    /**
     * Boolean of the routins is public or not
     */
    public static final String ROUTINE_ISPUBLIC = DatabaseAdapter.ROUTINE_IS_PUBLIC;

    //Need to pull reps and sets from Database, added variables for Sprint 3 to Database
//    public static final String ROUTINE_REPS = DatabaseAdapter.ROUTINE_REPS;
//    public static final String ROUTINE_SETS = DatabaseAdapter.ROUTINE_SETS;
    /**
     * List for display
     */
    public ArrayList<String> list;
    private ArrayAdapter<String> adapter;


    /**
     *
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState Bundle
     * @return return super.onCreateView(inflater, container, savedInstanceState);
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         /**
         * Set The tab page(1) of the main activity page for Workout
         */
        SmartBellsMainActivity.dashboardTab.setCheckTabPage(1);

        /**
         * List of routines
         */
        DatabaseAdapter db = new DatabaseAdapter(getActivity());
        try {
            db.openLocalDatabase();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        /**
         * Specific routines of User
         */
        list = db.getRoutinesAsStrings();

        /**
         * Set the adapter to show in application
         */
        adapter = new ArrayAdapter<String>(
                getActivity().getBaseContext(), android.R.layout.simple_list_item_1, list);
        setListAdapter(adapter);


        //imageView = (ImageView) getActivity().findViewById(R.id.routineListIcon);
        //imageView.setOnClickListener(this);

        //close the database
        db.closeLocalDatabase();

        return super.onCreateView(inflater, container, savedInstanceState);
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
        final int pos = position;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface arg0, int arg1) {
                    DatabaseAdapter db = new DatabaseAdapter(getActivity());
                    try {
                        db.openLocalDatabase();
                        //insert more routines
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    long routineId = db.getRoutineIDByName(list.get(pos));
                    db.deleteRoutine(routineId, true);
                    adapter.notifyDataSetChanged();
                    db.closeLocalDatabase();
                    Toast.makeText(getActivity(), "Routine Deleted", Toast.LENGTH_LONG).show();
                }
                });
            builder.setPositiveButton("Record", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface arg0, int arg1) {
                    //Start an intent when a list item is clicked
                    Intent intent = new Intent(getActivity(), RecordWorkoutRoutine.class);
                    //When we start the new intent we want to pass the name of the Routine from the list
                    intent.putExtra(ROUTINE_ITEM_NAME, list.get(pos));
                    startActivity(intent);
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

    //run list code on tab select
    @Override
    public void onStart() {
        super.onStart();
        getListView();
    }

}

