package ph.edu.dlsu.mobapde.tara;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by louis on 11/12/2017.
 */

public class ProfileFragment extends Fragment {
    Button buttonLogout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        buttonLogout = (Button) view.findViewById(R.id.bt_logout);

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to main activity
                Intent i = new Intent(view.getContext(), MainActivity.class);
                startActivity(i);

                SharedPreferences dsp = PreferenceManager.getDefaultSharedPreferences(view.getContext());
                SharedPreferences.Editor dspEditor = dsp.edit();
                dspEditor.remove("name");
                dspEditor.commit();

                getActivity().finish();
            }
        });

        return view;
    }
}

