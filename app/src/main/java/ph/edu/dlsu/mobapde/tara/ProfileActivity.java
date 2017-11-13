package ph.edu.dlsu.mobapde.tara;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProfileActivity extends AppCompatActivity {

    Button buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        buttonLogout = (Button) findViewById(R.id.bt_logout);

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

                finish();
            }
        });
    }
}
