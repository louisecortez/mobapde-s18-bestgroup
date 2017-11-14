package ph.edu.dlsu.mobapde.tara;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by louis on 11/12/2017.
 */

public class CurrentFragment extends Fragment {

    TextView tvYourLoc;
    TextView tvRaceLoc;
    TextView tvRaceDate;
    TextView tvRaceTime;
    TextView tvAddU;
    Button buttonTara;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.current_fragment, container, false);

        tvYourLoc = (TextView) view.findViewById(R.id.tv_yourLoc);
        tvRaceLoc = (TextView) view.findViewById(R.id.tv_yourLoc);
        tvRaceDate = (TextView) view.findViewById(R.id.tv_yourLoc);
        tvRaceTime = (TextView) view.findViewById(R.id.tv_yourLoc);
        tvAddU = (TextView) view.findViewById(R.id.tv_curradd);
        buttonTara = (Button) view.findViewById(R.id.button_go);

        tvYourLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), MapsActivity.class);
                startActivity(i);
            }
        });

        return view;
    }
}
