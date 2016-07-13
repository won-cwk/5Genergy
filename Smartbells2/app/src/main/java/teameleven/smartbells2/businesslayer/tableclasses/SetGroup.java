package teameleven.smartbells2.businesslayer.tableclasses;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import teameleven.smartbells2.businesslayer.RESTCall;
import teameleven.smartbells2.businesslayer.localdatabase.DatabaseAdapter;
import teameleven.smartbells2.Authentication;

/**
 * Base SetGroup Class that represents a number of numOfSets with a given number of repsPerSet
 * Created by Brian McMahon on 22/10/2015.
 */
public class SetGroup {

    /*************************************
     * Attributes
     ***********************************************/
    private Exercise exercise = new Exercise();
    private int exerciseId;
    private int routineId;
    private int id;
    private int numOfSets;
    private int repsPerSet;
    private String creationDate;
    private String lastUpdated;
    private String TAG = "DEBUGGING!!!!!!!!!!!!!!!!!";

    /**************************************
     * Constructors
     ********************************************/
    public SetGroup() {
    }

    /**
     * Constructor for Creation of Objects for Insertion into JSON Object
     *
     * @param exerciseId
     * @param num_of_sets
     * @param reps_per_set
     */
    public SetGroup(int exerciseId, int num_of_sets, int reps_per_set) {
        this.exerciseId = exerciseId;
        this.numOfSets = num_of_sets;
        this.repsPerSet = reps_per_set;
    }

    /**
     * @param setGroup
     */
    public SetGroup(JSONObject setGroup) {
        try {
            exerciseId = setGroup.getInt("exercise_id");
            if (setGroup.has("routine_id")) routineId = setGroup.getInt("routine_id");
            if (setGroup.has("id")) id = setGroup.getInt("id");
            numOfSets = setGroup.getInt("number_of_sets");
            repsPerSet = setGroup.getInt("reps_per_set");
            if (setGroup.has("created_at"))creationDate = setGroup.getString("created_at");
            if (setGroup.has("updated_at"))lastUpdated = setGroup.getString("updated_at");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param setGroupId
     * @param exerciseId
     * @param routineId
     * @param reps
     * @param sets
     */
    public SetGroup(int setGroupId, int exerciseId, int routineId, int reps, int sets) {
        this.id = setGroupId;
        this.exerciseId = exerciseId;
        this.routineId = routineId;
        this.repsPerSet = reps;
        this.numOfSets = sets;

    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    /**************************************
     * Base Methods
     ********************************************/

    public int getRoutineId() {
        return routineId;
    }

    public void setRoutineId(int routineId) {
        this.routineId = routineId;
    }

    public void setExerciseId() {
        this.exerciseId = exercise.getId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * returns the exercise
     *
     * @return the exercise
     */
    public Exercise getExercise() {
        return exercise;
    }

    /**
     * numOfSets the Exercise
     *
     * @param exercise : the exercise being set
     */
    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    /**
     * returns the number of numOfSets for the exercise
     *
     * @return the number of numOfSets
     */
    public int getNumberOfSets() {
        return numOfSets;
    }

    /**
     * numOfSets the number of numOfSets for an exercise
     *
     * @param sets : the number of numOfSets
     */
    public void setNumberOfSets(int sets) {
        this.numOfSets = sets;
    }

    /**
     * returns the number of repsPerSet in a set
     *
     * @return the number of repsPerSet
     */
    public int getRepsPerSet() {
        return repsPerSet;
    }

    public void setRepsPerSet(int reps) {
        this.repsPerSet = reps;
    }

    //create JSON parameters to pass to the post method

    public String toString(){
        try {
            return this.createJSON().toString(4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this.getExercise().getName() + " - Set Group " + this.getId();
    }
    /**
     * Create JSON parameters to pass to the post method
     *
     * @return : setGroup
     */
    public JSONObject createJSON() {
        try {
            JSONObject setGroup = new JSONObject();
            setGroup.put("number_of_sets", numOfSets);
            setGroup.put("reps_per_set", repsPerSet);
            setGroup.put("exercise_id", exerciseId);
            if (routineId != 0) setGroup.put("routine_id", routineId);
            if (id != 0) setGroup.put("id", id);
            return setGroup;
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return null;
    }

    /**************************************REST Methods********************************************/
    /**
     * Rest to create Set Group
     *
     * @param adapter : DatabaseAdapter to connect to Database
     * @return
     */
    public String restCreateSetGroup(DatabaseAdapter adapter) {

        String accessToken = Authentication.getAccessToken();
        String result = "";
        try {
            String temp = "set_groups";
            JSONObject temp1 = createJSON();
            AsyncTask test = new RESTCall().execute(temp, "POST", temp1.toString(), accessToken);
            result = test.get().toString();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void restUpdateExercise() {
    }

    public void restDeleteExercise() {
    }

    /*
    * methods to make calls on the API
    * */
    public void restGetAllSetGroups() {

    }

    public void restGetExercise() {
    }
}
