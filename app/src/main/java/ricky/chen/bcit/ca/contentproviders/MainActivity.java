package ricky.chen.bcit.ca.contentproviders;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

import static android.R.attr.content;
import static android.R.attr.start;
import static ricky.chen.bcit.ca.contentproviders.R.id.date;
import static ricky.chen.bcit.ca.contentproviders.R.id.subject;

public class MainActivity extends AppCompatActivity {
    private TextView subject,content,pickeddate,startTime,endTime;
    private int year,month,day,startHour,startMinute,endHour,endMinute;
    private long startMillis = 0;
    private long endMillis = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void add(View v){
        subject = (TextView)findViewById(R.id.subject);
        content = (TextView)findViewById(R.id.content);
        pickeddate = (TextView)findViewById(date);
        startTime = (TextView)findViewById(R.id.starttime);
        endTime = (TextView)findViewById(R.id.time);


        year = Integer.valueOf(pickeddate.getText().toString().substring(0,4));
        month = Integer.valueOf(pickeddate.getText().toString().substring(4,6));
        day = Integer.valueOf(pickeddate.getText().toString().substring(6,8));
        startHour = Integer.valueOf(startTime.getText().toString().substring(0,2));
        startMinute = Integer.valueOf(startTime.getText().toString().substring(2,4));
        endHour = Integer.valueOf(endTime.getText().toString().substring(0,2));
        endMinute = Integer.valueOf(endTime.getText().toString().substring(2,4));

        if(startTime != null && endTime != null && subject != null && content != null && pickeddate != null){
            Calendar beginTime = Calendar.getInstance();
            beginTime.set(year,month,day,startHour,startMinute);
            startMillis = beginTime.getTimeInMillis();

            Calendar endTime = Calendar.getInstance();
            endTime.set(year,month,day,endHour,endMinute);
            endMillis = endTime.getTimeInMillis();

            //Insert Event
            Intent intent = new Intent(Intent.ACTION_INSERT)
                    .setType("vnd.android.cursor.item/event")
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                    .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY , false) // just included for completeness
                    .putExtra(CalendarContract.Events.TITLE, subject.getText().toString())
                    .putExtra(CalendarContract.Events.DESCRIPTION, content.getText().toString())
                    .putExtra(CalendarContract.Events.EVENT_LOCATION, "Earth")
                    .putExtra(CalendarContract.Events.RRULE, "FREQ=DAILY;COUNT=10")
                    .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                    .putExtra(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PRIVATE)
                    .putExtra(Intent.EXTRA_EMAIL, "ricky.chen@gmail.com");
            startActivity(intent);

        } else {
            Toast.makeText(this,"Please enter all field",Toast.LENGTH_SHORT).show();
        }

    }

    public void view(View v){
        Intent it = new Intent(this,ViewCalendarActivity.class);
        startActivity(it);
    }
}
