package teameleven.smartbells2.businesslayer.synchronization;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * This class Overrides the Sync Adaptor Binder
 * Created by Andrew Rabb on 2015-11-12.
 */
public class SyncService extends Service {

    private static SyncAdaptor syncAdaptor = null;
    private static final Object syncAdaptorLock = new Object();

    public void onCreate(){
        synchronized (syncAdaptorLock){
            if (syncAdaptor == null){
                syncAdaptor = new SyncAdaptor(getApplicationContext(), true);
            }
        }
    }

    /**
     * Override the syncAdaptor with syncAdapter Binder.
     * @param intent : Intent for binding
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return syncAdaptor.getSyncAdapterBinder();
    }
}
