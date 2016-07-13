package teameleven.smartbells2.create;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import teameleven.smartbells2.R;

/**
 * This class creates a set group
 * Created by Jarret on 2015-11-13.
 */
public class CreateSetGroupDialog extends DialogFragment {

    //private DatabaseAdapter database;
    /**
     * Exercise name is used as the key of calling JSon
     */
    private String exerciseName;
    /**
     * Exercise id
     */
    private int exerciseId;
    /**
     * a number of sets of exercise
     */
    private int setsNum;
    /**
     * A number of reps of sets
     */
    private int repsNum;
    /**
     * Exercise spinner
     */
    private Spinner exerciseSpinner;
    /**
     * Text view field of sets
     */
    private TextView setsText;
    /**
     * Text view field of reps
     */
    private TextView repsText;
    /**
     * Exercise
     */
    private String exercise;
    //private ArrayList<Exercise> exercises = new ArrayList<>();
    /**
     * List of Exercis name
     */
    private ArrayList<String> exerciseList = new ArrayList<>();

    /**
     * Dispaly ther set_group
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState  Bundle
     * @return view for display set_group
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.set_group, container, false);

        addListenerOnSpinnerExerciseSelection();

        /*
        //Set up the exercise Spinner
        database = new DatabaseAdapter(getActivity());

        try {
            database.openLocalDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Get list of Exercises from the database
        ArrayList<String> exerciseList = database.getExercisesAsStrings();


        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, exerciseList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exerciseSpinner.setAdapter(adapter);
        */

        return view;
    }

    /**
     * Add Exercise
     * @return Exercise name
     */
    public String addListenerOnSpinnerExerciseSelection() {
        exerciseSpinner = (Spinner) exerciseSpinner.findViewById(R.id.exerciseSpinner);
        exerciseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Get Set Groups(Exercise List)
                exerciseName = (String) parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Do Nothing
            }
        });

        return exerciseName;
    }

    /**
     * Add Number of Sets
     * @return a number of sets
     */
    public int addNumberOfSets() {
        setsText = (TextView) setsText.findViewById(R.id.editSetsText);
        //call set method in routine class
        //exercise.setSets(Integer.getInteger(setsText.toString()));
        return Integer.parseInt(setsText.getText().toString());
    }

    /**
     * Add Reps Per Set
     * @return A number of reps per set
     */
    public int addRepsPerSet() {
        repsText = (TextView) repsText.findViewById(R.id.editRepsText);
        //call set method in routine class
        //exercise.setReps(Integer.getInteger(repsText.toString()));
        return Integer.parseInt(repsText.getText().toString());
    }

    /**
     * Show the FragmentTransaction
     * @param transaction FragmentTransaction
     * @param tag message
     * @return super.show(transaction, tag);
     */
    @Override
    public int show(FragmentTransaction transaction, String tag) {
        return super.show(transaction, tag);
    }
}
