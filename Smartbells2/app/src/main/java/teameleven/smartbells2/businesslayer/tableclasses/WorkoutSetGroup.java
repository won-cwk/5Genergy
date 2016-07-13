
package teameleven.smartbells2.businesslayer.tableclasses;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class treats the WorkoutSetGroup Table.
 * created : October 4th, 2015
 * @author Andrew Rabb
 */
public class WorkoutSetGroup {
    /**************************
     * Attributes
     ********************************************/
    /**
     * Declaration of set_group as the SetGroup object
     */
    private SetGroup set_group = new SetGroup();
    /**
     * WorkoutSession Id
     */
    private int workoutSessionId;
    /**
     * Resistance of the WorkoutSession
     */
    private int resistance;

    /**
     * Constructor with JSONObject parameter : Set the values of set_group
     * @param json JSONObject
     */
    public WorkoutSetGroup(JSONObject json) {
        try {
            //Log.d("json result of constructor - ", json.toString(4));
            this.setSetGroupID(json.getInt("id"));
            this.setWorkoutSessionId(json.getInt("workout_session_id"));

            if (json.has("set_group")){
                json = json.getJSONObject("set_group");


                this.setExerciseID(json.getInt("exercise_id"));
                set_group.setNumberOfSets(json.getInt("number_of_sets"));
                set_group.setRepsPerSet(json.getInt("reps_per_set"));
                set_group.setCreationDate(json.getString("created_at"));
                set_group.setLastUpdated(json.getString("updated_at"));
                this.setResistance(json.getInt("resistance"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public WorkoutSetGroup(SetGroup setGroup){
        this.set_group = setGroup;
    }
    /**
     * Default Constructor
     */
    public WorkoutSetGroup() {}

    public WorkoutSetGroup(int id, int exerciseId, int sessionId) {
        this.workoutSessionId = sessionId;
        this.set_group.setId(id);
        this.set_group.getExercise().setId(exerciseId);
    }

    /**
     * Get the Resistance of WorkoutSetGroup
     * @return resistance
     */
    public int getResistance() {
        return resistance;
    }

    /**
     * Set the Resistance with parameter resistance
     * @param resistance : Resistance of execercise set groups
     */
    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    /**
     * Get the Set_group
     * @return : the SetGroup
     */
    public SetGroup getSet_group() {
        return set_group;
    }

    /**
     * Set the Set_group
     * @param set_group : SetGroup
     */
    public void setSet_group(SetGroup set_group) {        this.set_group = set_group;    }

    public int getSetGroupID(){
        return this.getSet_group().getId();
    }

    public int getExerciseID(){
        return this.getSet_group().getExercise().getId();
    }
    public void setSetGroupID(int id){
        this.getSet_group().setId(id);
    }
    public void setExerciseID(int id){
        this.getSet_group().getExercise().setId(id);
    }
    /**
     * Get the WorkoutSessionID
     * @return WorkoutSessionID
     */
    public int getWorkoutSessionId() {
        return workoutSessionId;
    }

    /**
     * Set the WorkoutSession id
     * @param workoutSessionId
     */
    public void setWorkoutSessionId(int workoutSessionId){this.workoutSessionId = workoutSessionId;}

   /* public WorkoutSetGroup (JSONObject workoutSetGroup){
        try {
            set_group = (SetGroup) workoutSetGroup.get("user_id");
            exerciseId = (int) workoutSetGroup.get("exerciseId");
            workoutSessionId = (int) workoutSetGroup.get("workout_session_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/

    /**
     * Creates a new workoutSetGroup JSON Object
     //todo SPRINT 3:  pass this value to the ArrayList of WorkOutSetGroups in WorkoutSession
     //todo then loop through the ArrayList and pass each value into the JSONArray jsonWorkoutSetGroupAttr() of WorkoutSession
     //todo then adjust jsonWorkoutSession to take the array
     //todo pass in the exerciseId, sets and reps in the Constructor of the WorkoutSetGroup, Instantiate when new SetGroup is created on button click
     * @return the JSONObject for the WorkoutSetGroup
     */
    public JSONObject createJSON() {
        try {
            JSONObject workoutSetGroup = new JSONObject();
            workoutSetGroup.put("workout_session_id", getWorkoutSessionId());
            workoutSetGroup.put("id", this.set_group.getId());
            workoutSetGroup.put("resistance", resistance);
            workoutSetGroup.put("number_of_sets", this.set_group.getNumberOfSets());
            workoutSetGroup.put("reps_per_set", this.set_group.getRepsPerSet());
            workoutSetGroup.put("created_at", this.set_group.getCreationDate());
            workoutSetGroup.put("updated_at", this.set_group.getLastUpdated());
            workoutSetGroup.put("exercise_id", this.set_group.getExerciseId());



            return workoutSetGroup;
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return null;
    }


    @Override
    public String toString() {
        try {
            return this.createJSON().toString(4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "setgroupId = " + this.getSetGroupID();
    }

}