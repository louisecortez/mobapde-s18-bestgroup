package ph.edu.dlsu.mobapde.tara;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by louis on 11/13/2017.
 */

public class RequestFragment extends Fragment {

    RecyclerView rvRequests;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.current_fragment, container, false);

        rvRequests = (RecyclerView) view.findViewById(R.id.rv_requests);

        return view;
    }
}
