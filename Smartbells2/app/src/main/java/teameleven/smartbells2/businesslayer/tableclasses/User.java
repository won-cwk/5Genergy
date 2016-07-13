package teameleven.smartbells2.businesslayer.tableclasses;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import teameleven.smartbells2.businesslayer.RESTCall;

/**
 * This class treats User's information(name, email, passwork)
 * Created by Brian McMahon on 16/10/2015.
 */
public class User {
    /******************************** Attributes***************************************************/
    String TAG = "DEBUGGING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!";
    private String userName;
    private String email;
    private String password;
    private String accessToken;

    //when creating a new user supplying name, email and password
    public User(String userName, String email, String password){
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
    /***********************************Constructors **********************************************/
    /**
     * Default Constructor
     */
    public User(){ }

    /**
     * Constructor with JSONObject parameter to get column values
     * @param user : Type of JSONObject - User values
     */
    public User (JSONObject user){
        try {
            userName = (String) user.get("username");
            email = (String) user.get("email");
            accessToken = (String) user.get("accessToken");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor for when logging in a user supplying username/email and password
     * @param usernameOrEmail
     * @param password
     */
    public User(String usernameOrEmail, String password){
        this.userName = usernameOrEmail;
        this.password = password;
    }

    /****************************************Methods  *********************************************/
    /**
     * return JSON obj as string for username/email and password credentials
     * @return String (JSON Format) of Credentials.
     */
    private String jsonCredentials(){
        String result = "";
        try{
            JSONObject obj = new JSONObject();
            JSONObject cred = new JSONObject();
            cred.put("username", userName);
            cred.put("email", userName);
            cred.put("password", password);
            obj.put("credentials", cred);
            result = obj.toString();
        }catch(JSONException je){
            je.printStackTrace();
        }
        return result;
    }

    /**
     * makes the POST request to the api and returns the user details as well as an access-token
     * @return the authentication Token
     */
    public String restAuthentication(){
        String result = "";
        try{
            String temp = "sessions";
            String temp1 = jsonCredentials();
            AsyncTask test = new RESTCall().execute(temp, "POST", temp1, "");
            result = test.get().toString();
            Log.d(TAG, "PRINTING JSON RESPONSE:");
            accessToken = (String) new JSONObject(result).get("authentication_token");
            Log.d(TAG, accessToken);
            //return result;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * create the JSON to be sent to the server when creating a user
     * @return JSONObject of the User
     */
    public String createUserJSON(){
        String result = "";
        try{
            JSONObject obj = new JSONObject();
            JSONObject cred = new JSONObject();
            cred.put("username", userName);
            cred.put("email", email);
            cred.put("password", password);
            obj.put("user", cred);
            result = obj.toString();
            Log.d(TAG, result);
        }catch(JSONException je){
            je.printStackTrace();
        }
        return result;
    }

    /**
     * Create an user
     * @return A result of "post" USER TABLE
     */
    public String createUser(){
        String result = "";
        try{
            String temp = "users";
            String temp1 = createUserJSON();
            AsyncTask test = new RESTCall().execute(temp, "POST", temp1, "");
            result = test.get().toString();
            Log.d(TAG, "PRINTING JSON RESPONSE:");
            Log.d(TAG, result);
            Log.d(TAG, result);
            return result;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }
}
