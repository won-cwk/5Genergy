package teameleven.smartbells2.businesslayer.tableclasses;
//// TODO: 08/11/2015 Find way to retrieve Exercise ID. Add error handling for inputs to avoid stateExceptions

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
 * Created by Andrew Rabb on 2015-10-23.
 */
public class WorkoutSession {

    /****************************************Attributes********************************************/
    static final private String RESTID = "workout_sessions";
    /**
     *
     */
    private ArrayList<WorkoutSetGroup> setGroups = new ArrayList<>();
    /**
     *
     */
    private String name;
    /**
     *
     */
    private String created_At;
    /**
     *
     */
    private String updated_At;
    /**
     *
     */
    private int id;
    /**
     *
     */
    private int user_Id;

    /******************************Constructors****************************************************/

    /**
     * Default Constructor
     */
    public WorkoutSession() {}
    /**
     *
     * @param workoutSession
     */
    public WorkoutSession(JSONObject workoutSession) {
        try {
            if (workoutSession.has("workout_session")) {
                workoutSession = workoutSession.getJSONObject("workout_session");
            }
            id = workoutSession.getInt("id");
            user_Id = workoutSession.getInt("user_id");
            name = workoutSession.getString("name");
            created_At = workoutSession.getString("created_at");
            updated_At = workoutSession.getString("updated_at");

            if (workoutSession.has("workout_set_groups")) {
                JSONArray workoutSetGroups =
                        workoutSession.getJSONArray("workout_set_groups");
                WorkoutSetGroup sessionSetGroup;
                for (int index = 0; index < workoutSetGroups.length(); index++) {
                    sessionSetGroup = new WorkoutSetGroup(workoutSetGroups.getJSONObject(index));
                    setGroups.add(sessionSetGroup);
                }
            }
            }catch(JSONException e){
                e.printStackTrace();
            }
    }
    /**
     *
     * @param name
     * @param sets
     * @param reps
     * @param exercise_id
     */
    public WorkoutSession(String name, int sets, int reps, int exercise_id) {
        this.name = name;
        WorkoutSetGroup workoutSetGroup = new WorkoutSetGroup();
        workoutSetGroup.getSet_group().setExerciseId(exercise_id);
        workoutSetGroup.getSet_group().setNumberOfSets(sets);
        workoutSetGroup.getSet_group().setRepsPerSet(reps);
        this.getSetGroups().add(workoutSetGroup);
    }

    public WorkoutSession(String name, int id) {
        this.name = name;
        this.id = id;
    }

    /**************************					Base Methods               ************************/
    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return
     */
    public String getCreated_At() {
        return created_At;
    }

    /**
     * @param created_At
     */
    public void setCreated_At(String created_At) {
        this.created_At = created_At;
    }

    /**
     * @return
     */
    public String getUpdated_At() {
        return updated_At;
    }

    /**
     * @param updated_At
     */
    public void setUpdated_At(String updated_At) {
        this.updated_At = updated_At;
    }

    /**
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return
     */
    public int getUser_Id() {
        return user_Id;
    }

    /**
     * @param user_Id
     */
    public void setUser_Id(int user_Id) {
        this.user_Id = user_Id;
    }

    /**
     *
     * @return
     */
    public ArrayList<WorkoutSetGroup> getSetGroups() {
        return setGroups;
    }

    /**
     *
     * @param setGroups
     */
    public void setSetGroups(ArrayList<WorkoutSetGroup> setGroups) {
        this.setGroups = setGroups;
    }

    public String toString(){
        try {
            return this.createJSON().toString(4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this.name;
    }
    /****************************************** JSON Methods **************************************/

    /**
     * create the workout_set_groups attribute and add to JSONArray object
     * currently saves one workout_set_group to the workoutsession
     * @param exerciseId
     * @param numberOfSets
     * @param repsPerSet
     * @return
     */
    private JSONArray jsonWorkoutSetGroupAttr(int exerciseId, int numberOfSets, int repsPerSet) {
        JSONObject setGroupsAttr = new JSONObject();
        JSONArray sessionArray = new JSONArray();
        //todo need to find way to make this an arraylist to add multiple JSON objects for multiple set groups
        //todo will need to remove the passed in parameters and use getters instead. populate with loop.
        //ArrayList<String> values = new ArrayList<>();
        try {
            setGroupsAttr.put("exercise_id", exerciseId);
            setGroupsAttr.put("number_of_sets", numberOfSets);
            setGroupsAttr.put("reps_per_set", repsPerSet);

            /*for (int index = 0; index < this.setGroups.size(); index++) {
                sessionArray.put(this.setGroups.get(index).createJSON());
            }*/

            return sessionArray.put(setGroupsAttr);
            //values.add(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * create JSON parameters to pass to the post method
     * @param name
     * @param exerciseId
     * @param numOfSets
     * @param repsPerSet
     * @return
     */
    private String jsonWorkoutSession(String name, int exerciseId, int numOfSets, int repsPerSet) {
        String result = "";

        try {
            JSONObject workoutSession = new JSONObject();
            JSONObject params = new JSONObject();
            params.put("name", name);
            params.put("workout_set_groups_attributes", jsonWorkoutSetGroupAttr(exerciseId, numOfSets, repsPerSet));
            workoutSession.put("workout_session", params);
            result = workoutSession.toString();
            Log.d("--- Debugging ---", result);
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return result;
    }

    /**
     * Creates a JSON Object from the current Workout session Object
     * capable JSON'ing all available data (skips null values if present)
     */
    public JSONObject createJSON() {
        JSONArray workoutSetGroup = new JSONArray();
        JSONObject workoutSession = new JSONObject();
        JSONObject json = new JSONObject();
        try {
            if (getId() != 0) workoutSession.put("id", id);
            if (user_Id != 0) workoutSession.put("user_id", user_Id);
            workoutSession.put("name", this.name);
            if (created_At != null) workoutSession.put("created_at", created_At);
            if (updated_At != null) workoutSession.put("updated_at", updated_At);

            for (WorkoutSetGroup setGroup : setGroups){
                workoutSetGroup.put(setGroup.createJSON());
            }

            workoutSession.put("workout_set_groups_attributes", workoutSetGroup);
            json.put("workout_session", workoutSession);
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
   }

    /**
     *
     * @param database
     */
    public void restPost(DatabaseAdapter database){

        AsyncTask test = new RESTCall()
                .execute(RESTID, "POST", createJSON().toString(), database.getTokenAsString());
        WorkoutSession session = null;
        try {
            session = new WorkoutSession((JSONObject)test.get());
            database.insertWorkoutSession(session, false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return
     */
    public String restGetAllWorkoutSession() {

        try {
            String temp = RESTID;
            //System.out.println(temp);
            AsyncTask test = new RESTCall().execute(temp, "GET");
            return (String) test.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "Rest Call Failed";

    }

    /**
     *todo this should probably actually return a workoutsession
     * @param id
     * @return
     */
    public static String restGetSpecificWorkoutSession(int id) {
        try {
            String temp = RESTID + "/" + String.valueOf(id);
            System.out.println(temp);
            AsyncTask test = new RESTCall().execute(temp, "GET");
            return ((JSONObject) test.get()).toString(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "Rest Call Failed";
    }

//    public void restUpdateWorkoutSession() {}
//    public void restDeleteWorkoutSession() {}

    /**
     * Get all records of the WorkoutSession by User id
     * @param userIDForSession : User id
     * @return : List of Workoutsession of a user
     */
    public static ArrayList<WorkoutSession> restGetAll(int userIDForSession) {
        try {;
            //Log.d("Exercise.restGetAll - ", RESTID);
            AsyncTask result = new RESTCall().execute(RESTID + "?user_id=" + userIDForSession, "GET");
            //AsyncTask result = new RESTCall().execute(RESTID, "GET");
            JSONObject json = (JSONObject) result.get();

            return restGetWorkoutSessions(json, userIDForSession);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
   }

    /**
     * Get WorkoutSession by user id
     * @param json : JSONObject
     * @param userIDForSession : User id
     * @return List of WorkoutSession
     */
    private static ArrayList<WorkoutSession> restGetWorkoutSessions(JSONObject json, int userIDForSession) {
        ArrayList<WorkoutSession> workoutSessions = new ArrayList<>();

        try{
            JSONArray jsonArray = json.getJSONArray("workout_sessions");
            for (int index = 0 ; index < jsonArray.length(); index ++){
                JSONObject json2 = (jsonArray.getJSONObject(index));

                if (json2.getInt("user_id") == userIDForSession){
                    workoutSessions.add(new WorkoutSession(json2));
                }
            }
            return workoutSessions;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


































}
