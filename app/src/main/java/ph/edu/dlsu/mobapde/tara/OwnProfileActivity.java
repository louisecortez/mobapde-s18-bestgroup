package ph.edu.dlsu.mobapde.tara;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class OwnProfileActivity extends AppCompatActivity {

    RecyclerView rvOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_profile);
        // Get ListView object from xml

        rvOptions = (RecyclerView) findViewById(R.id.rv_options);


        ArrayList<String> data = new ArrayList<>();
        data.add("Change Password");
        data.add("Change Username");
        data.add("Log out");

        OptionsAdapter oa = new OptionsAdapter(data);
        rvOptions.setAdapter(oa);

        oa.setOnItemClickListener(new OptionsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String s) {

                Toast.makeText(getBaseContext(), s + " was clicked!", Toast.LENGTH_SHORT).show();

            }
        });

        rvOptions.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));

    }
}
