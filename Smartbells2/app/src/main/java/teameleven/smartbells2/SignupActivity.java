package teameleven.smartbells2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import teameleven.smartbells2.businesslayer.tableclasses.User;

/**
 * Sign up Activites
 * Created by Brian McMahon on 14/10/2015.
 */
public class SignupActivity extends Activity{
    /**
     * Edit Text field of User name
     */
    private EditText mUserName;
    /**
     * Edit Text field of User email
     */
    private EditText mEmail;
    /**
     * Edit Text field of User password
     */
    private EditText mPassword;
    /**
     * Edit Text field of configuration password
     */
    private EditText mConfPassword;
    /**
     * Create Account button
     */
    private Button mCreateAccount;

    /**
     * Create the page of sign up
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mUserName = (EditText)findViewById(R.id.editText1);
        mEmail = (EditText)findViewById(R.id.editText2);
        mPassword = (EditText)findViewById(R.id.editText3);
        mConfPassword = (EditText)findViewById(R.id.editText4);
        mCreateAccount = (Button)findViewById(R.id.signup_button);

        //sign up a new user on click of button
        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

    }

    /**
     * Get the user name
     * @return User name
     */
    public String getUsername(){
        return mUserName.getText().toString();
    }

    /**
     * Get the email
     * @return email of user
     */
    public String getEmail(){
        return mEmail.getText().toString();
    }

    /**
     * Get the password of user
     * @return Passowrd
     */
    public String getPassword(){
        return mPassword.getText().toString();
    }

    /**
     * Get the configuration password
     * @return configuration passowrd
     */
    public String getConfPassword(){
        return mConfPassword.getText().toString();
    }

    /**
     * Create a user account
     */
    public void signup(){
        if(!validate()){
            Toast.makeText(this, "Signup Failed", Toast.LENGTH_LONG).show();
        }else{
            //TODO save to database - check if user exists
            //create the new user
            User user = new User(getUsername(), getEmail(), getPassword());

            user.createUserJSON();
            user.createUser();
            //temporary
            Toast.makeText(this, "Signup Successful", Toast.LENGTH_SHORT).show();
            //move to main activity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            //close activity
            finish();
        }

    }

    /**
     * Method to handle returning to the login screen on pressing the back button
     */
    @Override
    public void onBackPressed(){
        Log.d("CDA", "onBackPressed Called");

        Intent intent = new Intent(this, LoginActivity.class);

        startActivity(intent);
        finish();
    }

    /**
     * Validate the user input datas
     * @return boolean after validating
     */
    public boolean validate(){
        boolean valid = true;
        if(getUsername().isEmpty() || getUsername().length() < 6 || getUsername().length() > 10){
            mUserName.setError("Please enter a name containing between 6 and 10 characters");
            valid = false;
        }
        if(getEmail().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches()){
            mEmail.setError("Please enter a valid email address");
            valid = false;
        }

        if(getPassword().isEmpty() || getPassword().length() < 6 || getPassword().length() > 12){
            mPassword.setError("Please enter a password containing between 6 and 12 characters");
            valid = false;
        }

        if(getConfPassword().isEmpty() || !getConfPassword().equals(getPassword())){
            mConfPassword.setError("Passwords don't match, please try again");
            valid = false;
        }
        return valid;
    }

}
