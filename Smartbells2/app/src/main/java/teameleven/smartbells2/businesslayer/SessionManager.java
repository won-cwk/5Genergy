package teameleven.smartbells2.businesslayer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import teameleven.smartbells2.LoginActivity;

/**
 * Created by Brian McMahon on 28/10/2015.
 * Code adapted from http://www.androidhive.info/2012/08/android-session-management-using-shared-preferences/
 */
public class SessionManager {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    public static final String PREF_NAME = "Login";

    // All Shared Preferences Keys
    public static final String IS_LOGIN = "IsLoggedIn";

    // UserName/Email
    public static final String KEY_NAME = "username";

    public static final String KEY_PASSWORD = "password";

    // Email
    public static final String KEY_EMAIL = "email";

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(String userName, String password) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, userName);

        editor.putString(KEY_PASSWORD, userName);

        // Storing email in pref
        //editor.putString(KEY_EMAIL, email);

        // commit changes
        editor.commit();
    }

    /**
     * check user login status
     * If false, redirect user to login page
     */
    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent intent = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

            // Add new Flag to start new Activity
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Start Login Activity
            _context.startActivity(intent);
        }

    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent intent = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(intent);
    }

    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

}
