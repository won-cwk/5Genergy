package teameleven.smartbells2;

import android.app.Application;
import android.content.Context;
import android.provider.ContactsContract;
import android.test.ApplicationTestCase;
import android.test.ServiceTestCase;

import junit.framework.Test;
import junit.framework.TestSuite;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import teameleven.smartbells2.businesslayer.localdatabase.DatabaseAdapter;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    static TestSuite suite;
    public ApplicationTest() {
        super(Application.class);
    }

    public static Test testExercises(){

        suite.addTest(new ExerciseTest());
        return suite;
    }


    private Context getTestContext(){
        try{
            Method getTestContext = ServiceTestCase.class.getMethod("getTestContext");
            return (Context) getTestContext.invoke(this);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}