package ph.edu.dlsu.mobapde.tara;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateRaceActivity extends AppCompatActivity {

    TextView tvBack;

    EditText etTitle;

    LinearLayout llDate;
    LinearLayout llTime;

    Button buttonDate;
    Button buttonTime;
    Button buttonCreateMeeting;

    static int chosenMonth, chosenDay, chosenYear;
    static int chosenHour, chosenMinute;
    static String sdate, stime;

    SimpleDateFormat sdfDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_race);

        tvBack = (TextView) findViewById(R.id.tv_back);
        etTitle = (EditText) findViewById(R.id.et_title);
        llDate = (LinearLayout) findViewById(R.id.ll_pickdate);
        llTime = (LinearLayout) findViewById(R.id.ll_picktime);
        buttonDate = (Button) findViewById(R.id.button_date);
        buttonTime =(Button) findViewById(R.id.button_time);
        buttonCreateMeeting = (Button) findViewById(R.id.button_createmeeting);

        buttonCreateMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    onCreateRaceButtonClick();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog dpd = new DatePickerDialog(getActivity(), this, year, month, day);
            dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 10000);

            return dpd;
        }public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            chosenDay = day;
            chosenMonth = month;
            chosenYear = year;
        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            chosenHour = hourOfDay;
            chosenMinute = minute;
        }
    }

    public void onCreateRaceButtonClick() throws ParseException {
        String currMonth, currDay;

        if(chosenDay < 10) {
            currDay = "0" + chosenDay;
        } else {
            currDay = "" + chosenDay;
        }

        if(chosenMonth < 10) {
            currMonth = "0" + chosenMonth;
        } else {
            currMonth = "" + chosenMonth;
        }

        sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdfDate.parse(chosenYear + "-" + (chosenMonth + 1) + "-" + chosenDay
                    + " " + chosenHour + ":" + chosenMinute + ":59");

        Race newRace = new Race(etTitle.getText().toString(), "LOCATION", date);
        Toast t = Toast.makeText(getBaseContext(), "Created: " + newRace.toString(), Toast.LENGTH_LONG);
        t.show();
    }
}
