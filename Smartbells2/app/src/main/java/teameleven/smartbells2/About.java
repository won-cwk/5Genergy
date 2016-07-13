package teameleven.smartbells2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

/**
 * About Page Fragment
 * Created by Jarret on 2015-11-10.
 */
public class About extends Fragment {
    /**
     * FloatingActionButton
     */
    private FloatingActionButton fab;

    /**
     * onCreateView
     * inflate the view layout
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide the FAB button
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.animate().translationY(fab.getHeight() + 50).setInterpolator(
                new AccelerateInterpolator(2)).start();

        //Set the view
        View rootView = inflater.inflate(R.layout.about_page, container, false);

        return rootView;
    }

}
