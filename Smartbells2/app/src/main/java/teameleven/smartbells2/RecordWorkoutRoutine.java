package teameleven.smartbells2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

import teameleven.smartbells2.businesslayer.localdatabase.DatabaseAdapter;
import teameleven.smartbells2.dashboardfragmenttabs.MyRoutines_Fragment;

/**
 * Created by Jare on 2015-11-03.
 * Updated by Brian McMahon
 */
public class RecordWorkoutRoutine extends AppCompatActivity {

    private String nameValue;
    private TextView nameView;
    private TextView mExercise;
    private EditText mResistance;
    private EditText mSets;
    private EditText mReps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_workout);

        //Get the name value passed and set it to the textview value
        //nameValue = getIntent().getStringExtra(BeginWorkout.ITEM_NAME);
        nameValue = getIntent().getStringExtra(MyRoutines_Fragment.ROUTINE_ITEM_NAME);
        nameView = (TextView) findViewById(R.id.nameTextValue);
        mExercise = (TextView) findViewById(R.id.exerciseTextViewCustom);
        //todo need to get the exercise name here and set the mExercise text with it.
        nameView.setText(nameValue.toString());
        mResistance = (EditText) findViewById(R.id.editResistanceText);
        mSets = (EditText) findViewById(R.id.editSetsTextCustom);
        mReps = (EditText) findViewById(R.id.editRepsTextCustom);
    }

    public boolean validate(){
        boolean valid = true;
        if(mSets.getText().toString().isEmpty()){
            mSets.setError("Please enter the number of sets for your session");
            valid = false;
        }
        if(mResistance.getText().toString().isEmpty()){
            mResistance.setError("Please enter the resistance for your session");
            valid = false;
        }
        if(mReps.getText().toString().isEmpty()){
            mReps.setError("Please enter the number of reps per set");
            valid = false;
        }
        return valid;
    }

    //Save new workout
    public void saveSession(View view) {
        if(validate()){

            DatabaseAdapter db = new DatabaseAdapter(this);
            try {
                db.openLocalDatabase();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //add new workout to the workoutsession in database
            //the -1 is to signify a placeholder workout id number. it is replaced by an incrementer
            //in the database
            db.insertWorkoutSession(-1 ,db.getUserIDForSession(), nameValue, null, null, true);
            Toast.makeText(this, "Session Saved!", Toast.LENGTH_LONG).show();
            //close the database
            db.closeLocalDatabase();
            //close activity
            finish();
        }
    }

    //Cancel - back to menu
    public void cancelCreateCustomSession(View view) {
        RecordWorkoutRoutine.this.finish();
    }
}
