package teameleven.smartbells2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.Toast;

/**
 * This class show the user's profile
 * Created by Jare on 2015-11-21.
 */
public class ViewProfile extends Fragment implements View.OnClickListener {
    /**
     * FloatingActionButton
     */
    private FloatingActionButton fab;
    /**
     * Edit button
     */
    private Button edit;

    /**
     * Create a screen profile_page
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState Bundle
     * @return view for profile_page
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * view for profile_page
         */
        View view = inflater.inflate(R.layout.profile_page, container, false);

        //Save Button
        edit = (Button) view.findViewById(R.id.editProfile);
        edit.setOnClickListener(this);

        //Hide the FAB
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.animate().translationY(fab.getHeight() + 50).setInterpolator(
                new AccelerateInterpolator(2)).start();


        return view;
    }

    /**
     * Shows the edit profile screen when the fragment item is clicked
     * @param view editProfile view
     */
    @Override
    public void onClick(View view) {
        /**
         * Fragment of the veiw
         */
        Fragment fragment;
        FragmentTransaction transaction;
        switch (view.getId()) {
            // for each button
            case R.id.editProfile:
                //Edit Profile
                Toast.makeText(getActivity(), "Edit your profile!", Toast.LENGTH_LONG).show();
        }
    }

}