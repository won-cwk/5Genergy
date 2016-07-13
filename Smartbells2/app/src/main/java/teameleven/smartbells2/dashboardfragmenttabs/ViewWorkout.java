package teameleven.smartbells2.dashboardfragmenttabs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;

import teameleven.smartbells2.R;
import teameleven.smartbells2.businesslayer.localdatabase.DatabaseAdapter;

/**
 * View Workout Class
 * Created by Jarret on 2015-11-30.
 */
public class ViewWorkout extends AppCompatActivity {

    private DatabaseAdapter database;
    private ArrayList<String> workoutroutinelist;

    private String nameValue;
    private String routineNameValue;
    private String exerciseNameValue;
    private String resistanceValue;
    private String setsValue;
    private String repsValue;

    private TextView nameView;
    private TextView routineName;
    private TextView exerciseName;
    private TextView resistance;
    private TextView sets;
    private TextView reps;

    private ListView routinelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_view_detail);

        //Set values passed into this activity
        nameValue = getIntent().getStringExtra(Workouts_Fragment.SESSION_ITEM_NAME);

        //Associate text Views with Id in the layout xml
        nameView = (TextView) findViewById(R.id.workoutViewNameValue);

        //routinelist = (ListView) findViewById(R.id.)

        //Set values
        nameView.setText(nameValue.toString());

        //Get the list of routines in this workout
        database = new DatabaseAdapter(this);
        try {
            database.openLocalDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /**
         * Specific routines of workout
         */
        //workoutroutinelist = database.getWorkoutRoutines(/* workout ID */);

        /**
         * Set the adapter to show in application
         */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this.getBaseContext(), android.R.layout.simple_list_item_1, workoutroutinelist);
        //setListAdapter(adapter);

        database.closeLocalDatabase();

    }


    public void recordWorkout(View view) {
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //                              RECORD THIS WORKOUT AGAIN
    //
    // /////////////////////////////////////////////////////////////////////////////////////////////
    }

    /**
     * Back to Dashboard button
     * @param view
     */
    public void goBack(View view) {
        ViewWorkout.this.finish();
    }

}
