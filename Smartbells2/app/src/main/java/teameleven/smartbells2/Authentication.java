package teameleven.smartbells2;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import teameleven.smartbells2.businesslayer.tableclasses.User;

/**
 * Base Authentication class to authenticate the credentials of the user and return the access_token
 * Created by Brian McMahon on 22/10/2015.
 */
public class Authentication extends User {
    /**
     * AccessToken for API of server(SingularityXL)
     */
    private static String accessToken;
    /**
     * Tag for debugging of errors
     */
    private String TAG = "DEBUGGING!!!!!!!!!!!!!!!!!";

    /**
     * Authentication with user anme and password
     * @param userName user name
     * @param password Passowrd of user's
     */
    public Authentication(String userName, String password) {
        super(userName, password);
    }

    /**
     * searches the response of the post request and returns sets the access token
     * @param json It is a string for query with JSONObject
     */
    public void setAccessToken(String json){
        String accessToken = "";
        try {
            JSONObject obj = new JSONObject(json);
            accessToken = obj.getString("authentication_token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
       // Log.d(TAG, "PRINTING ACCESS_TOKEN");
        //Log.d(TAG, accessToken);
        this.accessToken = accessToken;
    }

    //returns the access token to be used in headers for future requests
    public static String getAccessToken(){
        return accessToken;
    }
    //private boolean authenticated;
}
