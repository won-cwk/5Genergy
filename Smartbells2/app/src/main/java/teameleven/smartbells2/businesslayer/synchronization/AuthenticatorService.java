package teameleven.smartbells2.businesslayer.synchronization;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * This class offers the Service of Authenticator
 * Created by Andrew Rabb on 2015-11-13.
 */
public class AuthenticatorService extends Service {
    /**
     * Authenticator
     */
    private Authenticator authenticator;


    /**
     * Set the value of the authenticator
     */
    public void onCreate(){
        authenticator = new Authenticator(this);
    }

    /**
     * Intent on Bind
     * @param intent : intent method
     * @return : authericator.getIBinder()
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return authenticator.getIBinder();
    }
}
