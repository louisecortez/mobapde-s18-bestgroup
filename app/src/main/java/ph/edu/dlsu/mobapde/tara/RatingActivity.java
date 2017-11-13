package ph.edu.dlsu.mobapde.tara;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

public class RatingActivity extends AppCompatActivity {

    public RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        // Initialize RatingBar
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

    }

    /**
     * Display rating by calling getRating() method.
     * @param view
     */
    public void rateMe(View view){

        Toast.makeText(getApplicationContext(),
                String.valueOf(ratingBar.getRating()), Toast.LENGTH_LONG).show();
    }
}
