package teameleven.smartbells2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * This class is a screen for opening splash
 * Created by Jarret on 2015-11-20.
 */
public class OpeningSplashScreen extends Activity {
    /**
     * Create the view for splash_opening_screen
     * @param savedInstanceState Instance of the opening splash
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_opening_screen);

        //Run splash screen on a separate thread to avoid interrupting the main program.
        Thread timedThread = new Thread(){
            public void run(){
                try{
                    //Allow this to run for 2 seconds
                    sleep(2000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{

                    //Start the next Activity
                    Intent intent = new Intent(OpeningSplashScreen.this, SmartBellsMainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timedThread.start();
    }
    /**
     * Destroy the thread when it's finished running
     */
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
