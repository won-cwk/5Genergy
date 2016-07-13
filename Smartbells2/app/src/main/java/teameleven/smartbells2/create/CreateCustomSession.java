package teameleven.smartbells2.create;
//// TODO: 08/11/2015 add database connectivity - also get exerciseId from spinner and pass to restCall

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

import teameleven.smartbells2.BeginWorkout;
import teameleven.smartbells2.R;
import teameleven.smartbells2.businesslayer.localdatabase.DatabaseAdapter;

/**
 * This class is the screen of creating custom session
 * Created by Jare on 2015-10-06.
 * Updated by Brian McMahon
 */
public class CreateCustomSession extends Fragment implements View.OnClickListener {
    //A number of Groups
    private int numberOfGroups = 1;
    // Exercise Spinner
    private Spinner exerciseSpinner;
    //Exercise Name
    private String exerciseName;
    // Edit text field of the custom session name
    private EditText mName;
    // Edit text field of the custom session a number of sets
    private EditText mNumOfSets;
    // Edit text field of the custom session a number of reps per set
    private EditText mRepsPerSet;
    //Database Adapter
    private DatabaseAdapter database;
    // Save Button
    private Button save;
    // Cancel Button
    private Button cancel;
    // Floating Action Button
    private FloatingActionButton fab;

    /**
     * Display the input screen of the create_custom_session
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return View of create_custom_session
     */
    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Main view
        View view = inflater.inflate(R.layout.create_custom_session, container, false);

        //Spinner
        exerciseSpinner = (Spinner) view.findViewById(R.id.exerciseSpinner);
        addListenerOnSpinnerExerciseSelection();

        //CancelButton
        cancel = (Button) view.findViewById(R.id.cancelCreateCustomSession);
        cancel.setOnClickListener(this);

        //SaveButton
        save = (Button) view.findViewById(R.id.saveNewCustomSession);
        save.setOnClickListener(this);

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

        return view;
    }

    //Add Name

    /**
     * Get a name of the custom session
     * @return
     */
    public String addName() {
       // mName = (EditText) findViewById(R.id.editCustomRoutineName);
        //Call setName() in Routine class
        return mName.getText().toString();
    }

    //Set Groups
    /**
     * View of the setGroups
     * @param view
     */
    public void setGroups(View view) {
        //Set the Number of Exercise Groups you want to create in this routine

        numberOfGroups++;

        for (int i = 1; i <= numberOfGroups; i++) {

        }
    }

    //Add Exercise

    /**
     * Add a listener on spinner of exercise selection
     * @return Exercise Name
     */
    public String addListenerOnSpinnerExerciseSelection() {
        exerciseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //Get Set Groups(Exercise List)
                exerciseName = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Do Nothing
            }
        });

        return exerciseName;
    }

    /**
     * Add Reps per set
     * @return A number of reps per set
     * @throws NumberFormatException
     */
    public String addRepsPerSet() throws NumberFormatException {
        //mRepsPerSet = (EditText) findViewById(R.id.editRepsText);
        //call set method in routine class
        return mRepsPerSet.getText().toString();
    }
    /**
     * Add Number of sets
     * @return
     * @throws NumberFormatException
     */
    public String addNumberOfSets() throws NumberFormatException {
        //mNumOfSets = (EditText) findViewById(R.id.editSetsText);
        //call set method in routine class
        return mNumOfSets.getText().toString();
    }

    /**
     * Validate the input data
     * @return boolean after validating
     */
    public boolean validate(){
        boolean valid = true;
        if(addNumberOfSets().isEmpty()){
            mNumOfSets.setError("Please enter the number of sets for your session");
            valid = false;
        }
        if(addName().isEmpty()){
            mName.setError("Please enter the name of your session");
            valid = false;
        }
        if(addRepsPerSet().isEmpty()){
            mRepsPerSet.setError("Please enter the number of reps per set");
            valid = false;
        }
        return valid;
    }

    //SAVE new Routine to database

    /**
     * Save the session of the current view
     * @param view
     */
    public void saveSession(View view) {
/*
        if (!validate()) {

        } else {
            String name = addName();
            int sets = Integer.parseInt(addNumberOfSets());
            int reps = Integer.parseInt(addRepsPerSet());
            int exerciseId = database.getExerciseIdByName(exerciseName);
            String token = database.getTokenAsString();
            //session.restCreateCustomSession(name, exerciseId, sets, reps, token);
            WorkoutSession session = new WorkoutSession(name, sets, reps, exerciseId);
            session.restCreateCustomSession(name, sets, reps, exerciseId,token);
            database.insertWorkoutSession(session);

            //Log.d("TEST", session.restGetAllWorkoutSession());
            //Log.d("TEST", session.restGetSpecificWorkoutSession(39));

            //Back to menu
            Toast.makeText(this, "Session saved!", Toast.LENGTH_LONG).show();
            CreateCustomSession.this.finish();
        }
*/
    }

    /**
     * Display the list of routines
     * @param v
     */
    @Override
    public void onClick(View v) {
        Fragment fragment;
        FragmentTransaction transaction;

        switch (v.getId()) {
            //START NEW INTENT for each button
            case R.id.add_setgroup:
                //Pop set group dialog window
                /*
                fragment = new CreateSetGroupDialog();
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_main, fragment);
                transaction.commit();
                */
                break;
            case R.id.saveNewCustomSession:
                Toast.makeText(getActivity(),"Save a Session!",Toast.LENGTH_LONG).show();
                //Add new workout
                break;
            case R.id.cancelCreateCustomSession:

                fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
                fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
                fragment = new BeginWorkout();
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_main, fragment);
                transaction.commit();
                //Kill the fragment
                //getFragmentManager().beginTransaction().remove(this).commit();
                break;

        }
    }

}
