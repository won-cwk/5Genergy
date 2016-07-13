package teameleven.smartbells2.create;

import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;

import teameleven.smartbells2.Dashboard;
import teameleven.smartbells2.R;
import teameleven.smartbells2.businesslayer.localdatabase.DatabaseAdapter;
import teameleven.smartbells2.businesslayer.tableclasses.Exercise;
import teameleven.smartbells2.businesslayer.tableclasses.Routine;
import teameleven.smartbells2.RoutineDialogFragment;
import teameleven.smartbells2.businesslayer.tableclasses.SetGroup;

/**
 * CreateRoutine class makes a Routine of Exercises(Set Group)
 * Created  by Jarret on 2015-10-05.
 * Update   by WonKyoung on 2015-10-10
 * Update   by WonKyoung on 2015-10-25
 * Update   by WonKyoung On 2015-11-05
 * Updated by Brian McMahon
 * updated: Oct 8th, 2015 : added methods to interact with Exercise and Routine for Routine saving and creation.
 */
public class CreateRoutine extends Fragment implements View.OnClickListener {
    /**
     * DatabaseAdapter for query
     */
    private DatabaseAdapter database;
    /**
     * Routine
     */
    private Routine routine;
    /**
     * Exercise Spinner
     */
    private Spinner exerciseSpinner;
    /**
     * Boolean where it is public or not
     */
    private Boolean isPublic = false;
    /**
     * Exercise id
     */
    private int exerciseId;
    /**
     * A number of sets of exercise
     */
    private int setsNum;
    /**
     * A number of reps per a set
     */
    private int repsNum;
    /**
     * exercise
     */
    private String exercise;
    /**
     * List of excercises(Exercise attribute)
     */
    ArrayList<String> exerciseName = new ArrayList<>();
    private ArrayList<SetGroup> setGroups = new ArrayList<>();
    /**
     * List of string exercise
     */
    private ArrayList<String> exerciseList = new ArrayList<>();
    /**
     * Radio Group for choosing a radio button of Public or Private
     */
    private RadioGroup radioGroup;
    /**
     * Public radio button
     */
    private RadioButton publicButton;

    private RadioButton publicRoutine;
    private RadioButton privateRoutine;
    /**
     * A number of groups
     */
    int numberOfGroups = 1;
    /**
     * Save Button
     */
    private Button save;
    /**
     * Cancel Button
     */
    private Button cancel;
    /***
     * Add Set Group Button
     */
    private Button addSetGroup;
    /**
     * FloatingActionButton
     */
    private FloatingActionButton fab;
    /**
     * onCreate - Create view of input screen for creating  set group
     *
     * @param savedInstanceState
     */
    private EditText mRoutineName;
    private EditText mNumOfSets;
    private EditText mRepsPerSet;
    private ListView routineSetGroups;
    private BroadcastReceiver localBroadcastReceiver;
    private ArrayAdapter adapter;

    protected void addSetGroup(SetGroup setGroup) {
        this.setGroups.add(setGroup);
    }

    //@Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {


        localBroadcastReceiver = new LocalBroadcastReceiver();
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext())
                .registerReceiver(localBroadcastReceiver, new IntentFilter("DialogResult"));
        //Main view
        View view = inflater.inflate(R.layout.create_routine, container, false);
        //Save Button
        save = (Button) view.findViewById(R.id.routineExerciseSaveButton);
        save.setOnClickListener(this);
        //CancelButton
        cancel = (Button) view.findViewById(R.id.routineExerciseCancelButton);
        cancel.setOnClickListener(this);

        routineSetGroups = (ListView) view.findViewById(R.id.routineSetGroups);

        addSetGroup = (Button) view.findViewById(R.id.addExerciseListButton);
        addSetGroup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getActivity().getFragmentManager();
//                CustomDialogFragment dialog = new CustomDialogFragment();
                RoutineDialogFragment dialog = new RoutineDialogFragment();

                dialog.show(fm, "SetGroupCreator");
            }
        });
        //Open Database
        database = new DatabaseAdapter(getActivity());
        try {
            database.openLocalDatabase();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        //ArrayList<String> exerciseList = database.getExercisesAsStrings();

        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, exerciseName);
        routineSetGroups.setAdapter(adapter);

        return view;
    }

    /**
     * Add Routine Name
     *
     * @return Routine name
     */
    public String addRoutineName() {
        mRoutineName = (EditText) getActivity().findViewById(R.id.editNameText);
        //Call setName() in Routine class
        //routine.setName(name.getText().toString());
        return mRoutineName.getText().toString();
    }

    /**
     * Return the boolean when a radio button clicked(Public or not(Private))
     *
     * @return isPublic
     */
    public Boolean addListenerOnRadioButton() {

        publicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroup
                //int selectedId = radioGroup.getCheckedRadioButtonId();
                // declaring "public" radio button value
                publicRoutine = (RadioButton) getActivity().findViewById(R.id.publicTextView);
                privateRoutine = (RadioButton) getActivity().findViewById(R.id.privateTextView);
                // Comparison selectedId and buttonId
                //if (selectedId == publicRoutine.getId()) {
                if (publicRoutine.isChecked()) {
                    isPublic = true;
                } else if (privateRoutine.isChecked()) {
                    isPublic = false;
                }
                //Toast.makeText(CreateRoutine.this,
                //publicButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        return isPublic;
    }

    /**
     * Create a routine with set group and cancel
     *
     * @param v View
     */
    @Override
    public void onClick(View v) {
        Fragment fragment;
        FragmentTransaction transaction;

        switch (v.getId()) {
            //Move to a new FRAGMENT for each button
            case R.id.add_setgroup:
                //Pop set group dialog window
                /*
                fragment = new CreateSetGroupDialog();
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_main, fragment);
                transaction.commit();
                */
                break;
            case R.id.routineExerciseSaveButton:
                //SAVE new Routine to databasepiyep
                //if (validate()) {
                //SetGroup setGroup = new SetGroup(
                //database.getExerciseIdByName(exerciseName),
                //Integer.parseInt(addNumberOfSets()),
                //Integer.parseInt(addRepsPerSet()
                // );
                Routine routine = new Routine();
                //routine.getSetGroups().add(setGroup);
                routine.setName(addRoutineName());
                routine.setIs_Public(isPublic);
                routine.setSetGroups(setGroups);
                Log.d("CreateRoutine.saveRoutine - ", routine.toString());
                //database is called in restputroutine. both calls not necessary
                database.insertRoutine(routine, true);
                //routine.RestPutRoutine(database);//should not call rest here - data will be
                //input into update table for later synchronizing
                //Close the database
                database.closeLocalDatabase();

                //Back to menu
                Toast.makeText(getActivity(), "routine " + routine.getName() + " created!!.", Toast.LENGTH_LONG).show();

                fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
                fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
                fragment = new Dashboard();
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_main, fragment);
                transaction.commit();
                break;


            case R.id.routineExerciseCancelButton:

                fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
                fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
                fragment = new Dashboard();
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_main, fragment);
                transaction.commit();
                //Kill the fragment
                //getFragmentManager().beginTransaction().remove(this).commit();
                break;
        }
    }

    private void refreshAdapter(int id) {
        try {
            exerciseName.add(new Exercise(database.getExercise(id)).getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
    }

    private class LocalBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null || intent.getAction() == null) {
                return;
            }
            if (intent.getAction().equals("DialogResult")) {
                try {
                    SetGroup setGroup = new SetGroup(new JSONObject(intent.getStringExtra("setGroup")));
                    setGroups.add(setGroup);
                    refreshAdapter(setGroup.getExerciseId());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

/**
 * Text view fields of sets and reps
 *
 * @param view of set groups
 * <p/>
 * Return ther exercise name from the input page
 * @return Exercise Name
 * <p/>
 * Return a number of sets of exercise
 * @return a number of sets
 * <p/>
 * Get a number of reps per set
 * @return A number of reps
 */
//    public void setGroups(View view) {
//        //Set the Number of Exercise Groups you want to create in this routine
//        numberOfGroups++;//When clicked the plus button, it should be added 1.
//        for (int i = 1; i <= numberOfGroups; i++) {
//
//            TextView setsNum = (TextView) getActivity().findViewById(R.id.editSetsText);
//            TextView repsNum = (TextView) getActivity().findViewById(R.id.editRepsText);
//            //Add ArrayList exercises
//            //exercises.add("1");//test : need to restCall get exercise ID.
//            //exercises.add(setsNum.getText().toString());
//            //exercises.add(repsNum.getText().toString());
//        }
//    }

/**
 * Return ther exercise name from the input page
 * @return Exercise Name
 */
//    public String addListenerOnSpinnerExerciseSelection() {
//        exerciseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                //Get Set Groups(Exercise List)
//                exerciseName = parent.getItemAtPosition(position).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                //Do Nothing
//            }
//        });
//
//        return exerciseName;
//    }

/**
 * Return a number of sets of exercise
 * @return a number of sets
 */
//    public String addNumberOfSets() {
//        mNumOfSets = (EditText) getActivity().findViewById(R.id.editSetsText);
//        //call set method in routine class
//        //exercise.setSets(Integer.getInteger(setsText.toString()));
//        return mNumOfSets.getText().toString();
//    }


//Add Reps Per Set

/**
 * Get a number of reps per set
 * @return A number of reps
 */
//    public String addRepsPerSet() {
//        mRepsPerSet = (EditText) getActivity().findViewById(R.id.editRepsText);
//        //call set method in routine class
//        //exercise.setReps(Integer.getInteger(repsText.toString()));
//        return mRepsPerSet.getText().toString();
//    }

//    private boolean validate() {
//
//        boolean valid = true;
//        if(addNumberOfSets().isEmpty()){
//            mNumOfSets.setError("Please enter the number of sets for your session");
//            valid = false;
//        }
//        if(addRoutineName().isEmpty()){
//            mRoutineName.setError("Please enter the name of your session");
//            valid = false;
//        }
//        if(addRepsPerSet().isEmpty()){
//            mRepsPerSet.setError("Please enter the number of reps per set");
//            valid = false;
//        }
//        return valid;
//        //return true;
//    }
