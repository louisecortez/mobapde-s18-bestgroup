package ph.edu.dlsu.mobapde.tara;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class SelectLocActivity extends AppCompatActivity {

    ImageView dropPinView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_loc);

        dropPinView = new ImageView(this);
        dropPinView.setImageResource(R.drawable.marker);
    }
}
