package teameleven.smartbells2.dashboardfragmenttabs;

import android.content.Context;
import android.view.View;
import android.widget.TabHost.TabContentFactory;

/**
 * Created by Jordan Medwid on 10/18/2015.
 * This class will render individual tabs
 */
public class Homepage_TabContent implements TabContentFactory {
    /**
     * Variable Context
     */
    private Context thisContext;

    /**
     * Homepage Tab content
     * @param context context
     */
    public Homepage_TabContent(Context context){
        thisContext = context;
    }

    /**
     * Create a tab content
     * @param tabtag Tab tag
     * @return view for context
     */
    @Override
    public View createTabContent(String tabtag){
        View view = new View(thisContext);
        return view;
    }
}
