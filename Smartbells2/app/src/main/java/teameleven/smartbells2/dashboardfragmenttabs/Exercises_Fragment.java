package teameleven.smartbells2.dashboardfragmenttabs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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

import teameleven.smartbells2.Dashboard;
import teameleven.smartbells2.SmartBellsMainActivity;
import teameleven.smartbells2.businesslayer.localdatabase.DatabaseAdapter;

/**
 * This class shows the list of Exercise
 * Created by Jare on 2015-11-17.
 * Updated by Brian McMahon
 * Updated by Jordan Medwid
 */
public class Exercises_Fragment extends ListFragment {
    /**
     * Dashboard of Smartbells(Workout,Routine,Exercise)
     */
    Dashboard dashboard;
    ArrayList<String> listOfExercises;
    ArrayAdapter<String> adapter;
    //Temporary string array to populate list
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        /**
         * Refer to ONCLICK- SmartBellsMainActivity
         */
        SmartBellsMainActivity.dashboardTab.setCheckTabPage(2);
        /**
         * DB adaptoer
         */
        DatabaseAdapter db = new DatabaseAdapter(getActivity());
        try {
            db.openLocalDatabase();
            //insert more routines
        } catch (SQLException e) {
            e.printStackTrace();
        }
        /**
         * List of Exercise from Database
         */
        listOfExercises = db.getExercisesAsStrings();

        /**
         * Change adapter type to handle objects instead of strings later
         * Set the adapter to show in application
         */

        adapter = new ArrayAdapter<String>(
                getActivity().getBaseContext(), android.R.layout.simple_list_item_1, listOfExercises);
        setListAdapter(adapter);

        //close the database
        db.closeLocalDatabase();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    //Load view on exercise Tab selected.
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
     * Method displays buttons to [edit/delete] items from the exercise list.
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
                int exerciseId = db.getExerciseIdByName(listOfExercises.get(pos));
                db.deleteExercise(exerciseId, true);
                adapter.notifyDataSetChanged();
                db.closeLocalDatabase();
                Toast.makeText(getActivity(), "Exercise Deleted", Toast.LENGTH_LONG).show();
            }
        });

        Dialog mydialog = builder.setView(new View(getActivity())).create();

        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.copyFrom(mydialog.getWindow().getAttributes());
        params.width = 400;
        params.height = 250;
        mydialog.show();
        mydialog.getWindow().setAttributes(params);

    }
}
