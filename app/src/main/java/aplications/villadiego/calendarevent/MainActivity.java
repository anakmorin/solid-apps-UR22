package aplications.villadiego.calendarevent;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int day, month, yeart, hours, mins, houre, mine;
    EditText title, desc, local, email, dnumdays;
    TextView dstart, dend, times, timed;
    Button dates, datee, bthours, btnhoure, btnopen;
    RadioGroup selectR;
    RadioButton rbday, rbmont, rbyear;
    String SelecRecurencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.txttitle);
        desc = findViewById(R.id.txtdescription);
        local = findViewById(R.id.txtlocal);
        email = findViewById(R.id.txtemail);
        dstart = findViewById(R.id.txtdates);
        dend = findViewById(R.id.txtdatee);
        times = findViewById(R.id.tthour);
        timed = findViewById(R.id.txthoure);
        dnumdays = findViewById(R.id.txtnumdays);
        dates = findViewById(R.id.btndates);
        datee = findViewById(R.id.btndatee);
        bthours = findViewById(R.id.btnhour);
        btnhoure = findViewById(R.id.btnhoure);
        btnopen = findViewById(R.id.btnopen);
        selectR=findViewById(R.id.radioGroup);
        rbday = findViewById(R.id.rgday);
        rbmont = findViewById(R.id.rgmonth);
        rbyear = findViewById(R.id.rgyear);
        dnumdays.setVisibility(View.INVISIBLE);

        selectR.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rgnever){
                    dnumdays.setEnabled(false);
                }
                if(checkedId == R.id.rgday){
                    dnumdays.setVisibility(View.VISIBLE);
                    dnumdays.setEnabled(true);
                    dnumdays.setHint("Veces al dia");
                    SelecRecurencia="DAILY";
                }
                if(checkedId == R.id.rgweek){
                    dnumdays.setVisibility(View.VISIBLE);
                    dnumdays.setEnabled(true);
                    dnumdays.setHint("Veces la semana");
                    SelecRecurencia="WEEKLY";
                }
                if(checkedId == R.id.rgmonth){
                    dnumdays.setVisibility(View.VISIBLE);
                    dnumdays.setEnabled(true);
                    dnumdays.setHint("Veces al mes");
                    SelecRecurencia="MONTHLY";
                }
                if (checkedId == R.id.rgyear){
                    dnumdays.setVisibility(View.VISIBLE);
                    dnumdays.setEnabled(true);
                    dnumdays.setHint("Veces al año");
                    SelecRecurencia="YEARLY";
                }

            }
        });

        dates.setOnClickListener(this);
        datee.setOnClickListener(this);
        bthours.setOnClickListener(this);
        btnhoure.setOnClickListener(this);
        btnopen.setOnClickListener(this);

    }

    public void dateStart(){
            Calendar calendar = Calendar.getInstance();
            day = calendar.get(Calendar.DAY_OF_MONTH);
            month = calendar.get(Calendar.MONTH);
            yeart = calendar.get(Calendar.YEAR);

            DatePickerDialog picker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    yeart=year;
                    day=dayOfMonth;
                    month=monthOfYear;
                    dstart.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                }
            },yeart, month, day);
            picker.show();
    }

    public void dateEnd(){
        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        yeart = calendar.get(Calendar.YEAR);

        DatePickerDialog picker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                yeart=year;
                day=dayOfMonth;
                month=monthOfYear;
                dend.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
            }
        },yeart, month, day);
        picker.show();
    }

    public void hourStart(){
        final Calendar c= Calendar.getInstance();
        hours=c.get(Calendar.HOUR_OF_DAY);
        mins=c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mins=minute;
                hours=hourOfDay;
                times.setText(hourOfDay+":"+minute);


            }
        },hours,mins,false);
        timePickerDialog.show();
    }

    public void hourEnd(){
        final Calendar c= Calendar.getInstance();
        houre=c.get(Calendar.HOUR_OF_DAY);
        mine=c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mine=minute;
                houre=hourOfDay;
                timed.setText(hourOfDay+":"+minute);


            }
        },houre,mine,false);
        timePickerDialog.show();
    }

    public void addEvent(){
        Calendar calendar = Calendar.getInstance();

        Intent i;
        boolean b = false;

        while (b==false){
            try {
                calendar.set(Calendar.YEAR, yeart);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                calendar.set(Calendar.HOUR_OF_DAY, hours);
                calendar.set(Calendar.MINUTE, mins);

                i = new Intent(Intent.ACTION_EDIT);
                i.setType("vnd.android.cursor.item/event");
                i.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calendar.getTimeInMillis());
                i.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, calendar.getTimeInMillis()+ 60 * 60 * 1000);
                i.putExtra(CalendarContract.Events.TITLE, title.getText().toString());
                i.putExtra(CalendarContract.Events.DESCRIPTION, desc.getText().toString());
                i.putExtra(CalendarContract.Events.EVENT_LOCATION, local.getText().toString());
                i.putExtra(CalendarContract.Attendees.ATTENDEE_NAME, email.getText().toString());
                i.putExtra(CalendarContract.Events.RRULE,"FREQ="+SelecRecurencia+";COUNT="+dnumdays.getText().toString());

                startActivity(i);
                b = true;
            } catch (Exception e){
                Toast.makeText(getApplicationContext(), "Digite una fecha correcta", Toast.LENGTH_LONG).show();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btndates:
                dateStart();
                break;
            case R.id.btndatee:
                dateEnd();
                break;
            case R.id.btnhour:
                hourStart();
                break;
            case R.id.btnhoure:
                hourEnd();
                break;
            case R.id.btnopen:
                addEvent();
                break;
        }
    }

}
