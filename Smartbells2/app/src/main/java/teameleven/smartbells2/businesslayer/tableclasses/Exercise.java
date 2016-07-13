package teameleven.smartbells2.businesslayer.tableclasses;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import teameleven.smartbells2.businesslayer.RESTCall;
import teameleven.smartbells2.businesslayer.localdatabase.DatabaseAdapter;

/**
 * Base Exercise Class. Describes a Single Exercise, including the number of reps and sets in a basic performance of the exercise.
 * portion of the SmartBells Project, created by Team Eleven.
 * Edits
 * Updated to match API fields AR Oct 21, 2015
 * <p/>
 * created : October 4th, 2015
 *
 * @author Andrew Rabb
 */
public class Exercise {
    /************************************** Attributes*********************************************/
    static final private String RESTID = "exercises";
    /**
     * specific ID of the Exercise.
     */
    private int id;
    /**
     * specific Name of the Exercise
     */
    private String name;
    /**
     * The increase in resistance per session.
     */
    private int increase_Per_Session;
    /**
     * Date-stamp of the Creation Time of the Object
     */
    private String created_At;
    /**
     * Date-stamp of the last time that the object was edited.
     */
    private String updated_At;
    private boolean is_Public = true;
    /**
     * User ID of the creator of the exercise.
     */
    private int user_Id;

    /**************************************Constructors********************************************/
    /**
     * Default constructor
     */
    public Exercise() {
        id = -1;
    }

    /**
     * Constructor with exercise value
     * @param exercise : Exercise values from JSONObject
     */
    public Exercise(JSONObject exercise) {
        try {
            //Log.d("Constructor exercise - ",exercise.toString());
            if (exercise.has("exercise")) exercise = exercise.getJSONObject("exercise");

            //Log.d("Exercise.JSONConstructor - ", exercise.toString(4));
            id = exercise.getInt("id");
            name = (String) exercise.getString("name");
            increase_Per_Session = exercise.getInt("increase_per_session");
            created_At = exercise.getString("created_at");
            updated_At = exercise.getString("updated_at");
            if (!exercise.isNull("is_public")) {
                is_Public = exercise.getBoolean("is_public");
            }else{
                is_Public = false;
            }
            if (!exercise.isNull("user_id")) {
                user_Id = exercise.getInt("user_id");
            }else{
                user_Id = 0;
            }
            //Log.d("Constructor, Exercise - ", toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set the Exercise name and Exercise id
     * @param name : Exercise name
     * @param id   : Exercise id
     */
    public Exercise(String name, int id) {
        this.name = name;
        this.id = id;
    }

    /******************************** Base Methods     ********************************************/
    /**
     * Get an arraylist of all exercises from the Exercise table
     * @param json : JSONObject for query
     * @return : Arraylist of all execises
     */
    private static ArrayList<Exercise> getAllExercise(JSONObject json) {
        ArrayList<Exercise> exercises = new ArrayList<>();
        try {
            JSONArray jsonArray = json.getJSONArray("exercises");
            //Log.d("Exercise.getAllExercise - ", jsonArray.toString(4));
            for (int index = 0; index < jsonArray.length(); index++) {
                exercises.add(new Exercise(jsonArray.getJSONObject(index)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return exercises;
    }

    /**
     * Get an exercise record by exercise id
     * @param id : Excercise id
     * @return : a result of rest call to get an record of exercise
     */
    public static JSONObject restGetExercise(int id) {
        try {
            String temp = RESTID + "/" + String.valueOf(id);
            System.out.println(temp);
            AsyncTask test = new RESTCall().execute(temp, "GET");
            return (JSONObject) test.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get all Exercise records
     * @return : ArrayList of Exercises
     */
    public static ArrayList<Exercise> restGetAll() {
        try {
            //Log.d("Exercise.restGetAll - ", RESTID);
            AsyncTask result = new RESTCall().execute(RESTID, "GET");
            JSONObject json = (JSONObject) result.get();

            //Log.d("Exercise.RestGetAll - ", String.valueOf(json.optInt("id")));
            return getAllExercise(json);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * retrieves the name of the exercise
     *
     * @return Exercise Name
     */
    public String getName() {
        return name;
    }

    /**
     * changes or creates the Name of the Exercise
     * User Defined
     *
     * @param name : name to be changed to
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * returns the ID of the current Exercise
     *
     * @return id of this exercise
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the current Exercise
     * System Defined
     *
     * @param id ID of the object to be set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * returns the Increase per session
     *
     * @return integer, increase per session
     */
    public int getIncrease_Per_Session() {
        return increase_Per_Session;
    }

    /**
     * sets the Increase Per Session
     * User Defined
     *
     * @param increase_Per_Session sets the increase per session of this exercise
     */
    public void setIncrease_Per_Session(int increase_Per_Session) {
        this.increase_Per_Session = increase_Per_Session;
    }

    /**
     * User who initially created the object
     *
     * @return id of the user that created this object
     */
    public int getUser_Id() {
        return user_Id;
    }

    /**
     * Sets the user who created the Object
     * System Defined
     *
     * @param user_Id user id of this exercises creator.
     */
    public void setUser_Id(int user_Id) {
        this.user_Id = user_Id;
    }

    /**
     * returns the most recent time that the exercise in question was updated
     *
     * @return date of last update
     */
    public String getUpdated_At() {
        return updated_At;
    }

    /**
     *
     */
    public void setUpdated_At(String updated_at) {
        updated_At = updated_at;
    }

    /**
     * returns the creation date of the Exercise
     *
     * @return exercise creation date
     */
    public String getCreated_At() {
        return created_At;
    }

    /**
     * Set the created date of a record
     */
    public void setCreated_At(String created_at) {
        this.created_At = created_at;
    }

    /**
     * Get a Boolean where it is public or not
     * @return
     */
    public boolean getIsPublic() {
        return is_Public;
    }

    /**
     * Set a Boolean where it is public or not
     * @param is_Public
     */
    public void setIs_Public(boolean is_Public) {
        this.is_Public = is_Public;
    }
    /* (non-Javadoc)
     * returns the Name of the Exercise
     * @see java.lang.Object#toString()
     */
    public String toString() {
        try {
            return (this.createJSON().toString(4));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this.name;
    }

    /************************************** REST Methods*******************************************/


    /**
     * Creates a JSON object from the parameters required to create a new object in API
     * missing some fields
     *
     * @return - JSON object
     */
    public JSONObject createJSON() {
        JSONObject json = new JSONObject();
        JSONObject exercise = new JSONObject();
        try {
            if (getId() != 0) json.put("id", getId());
            json.put("name", name);
            json.put("increase_per_session", increase_Per_Session);
            if (created_At != null) json.put("created_at", created_At);
            if (updated_At != null) json.put("updated_at", updated_At);
            json.put("is_public", is_Public);
            if (user_Id != 0) json.put("user_id", user_Id);
            exercise.put("exercise", json);
            return exercise;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
//
//
//    /**
//     * Accesses the Database and the RESTCall class create a new object from the Server,
//     * and save it into the database. This creates a new Object on the server
//     *
//     * @param database
//     */
//public void restPOST(DatabaseAdapter database) {
//    Log.d("Exercise.restPOST", this.createJSON().toString());
//    AsyncTask test = new RESTCall()
//            .execute(RESTID, "POST", createJSON().toString(), database.getTokenAsString());
//    //send to database here also
//    Exercise exercise = null;
//
//    try {
//        exercise = new Exercise(new JSONObject(test.get().toString()));
//        database.insertExercise(exercise, false);
//    } catch (InterruptedException e) {
//        e.printStackTrace();
//    } catch (ExecutionException e) {
//        e.printStackTrace();
//    } catch (JSONException e) {
//        e.printStackTrace();
//    }
//    Log.d("database load - ", exercise.toString());
//}
//
//    /**
//     * Insert an exercise record
//     * @param database : Database Adapter
//     */
//    public void restPUT(DatabaseAdapter database) {
//        Log.d("Exercise.restPOST", this.createJSON().toString());
//        AsyncTask test = new RESTCall()
//                .execute(RESTID, "GET", createJSON().toString(), database.getTokenAsString());
//        //send to database here also
//        Exercise exercise = null;
//        try {
//            exercise = new Exercise(new JSONObject(test.get().toString()));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Log.d("database load - ", this.toString());
//        database.insertExercise(exercise, false);
//    }
// */