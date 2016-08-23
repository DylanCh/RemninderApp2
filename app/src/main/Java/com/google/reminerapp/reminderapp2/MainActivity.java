package com.google.reminderapp.reminderapp2;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
//import android.support.v4.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import Model.Database;
import Model.Reminder;
import Model.ReminderApp2;
import Model.SaveReminderToFile;

public class MainActivity extends Activity {
    // references to form elements
    private Reminder reminder;
    private EditText NameAssignment;
    private EditText Course;
    private EditText DueDate;
    private EditText PhoneNum;
    //private EditText Due_time;

    //private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         reminder = new Reminder();
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        finish();
    }

    public void scheduleAlarm(View v) throws ParseException {
        // set model's assignment number
        NameAssignment = (EditText) findViewById(R.id.assignmentNum);
        reminder.setNameAssignment(NameAssignment.getText().toString());

        // set course name
        Course = (EditText) findViewById(R.id.courseNum);
        reminder.setCourseName(Course.getText().toString());

        // set dueDate
        DueDate = (EditText) findViewById(R.id.date);
        String dueDate = DueDate.getText().toString();

        //Set phone num
        PhoneNum =(EditText) findViewById(R.id.phoneNum);
        reminder.setPhoneNum(PhoneNum.getText().toString());
//        Due_time=(EditText)findViewById(R.id.time);
//        String dueTime = Due_time.getText().toString();

        // SET ALARM TIME
        long time=new GregorianCalendar().getTimeInMillis()+24*60*60*1000; // default alarm time: 24 hours from now
        SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date d = f.parse(dueDate);
            time=d.getTime();
            reminder.setDueDate(time);
        }
        catch (ParseException e){
            new AlertDialog.Builder(MainActivity.this).setMessage(e.getMessage()).show();
        }

        //db = new Database(this);
        // insert to database
        Log.d("Insert: ", "Inserting ..");
        ReminderApp2.db.addRecord(reminder);

        Intent intentAlarm = new Intent(this,AlarmReceiver.class);
        intentAlarm.putExtra("PHONENUMBER",reminder.getPhoneNum()); // pass phone number to receiver

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        String message = "Alarm scheduled for Course "+reminder.getCourseName()
                +" Assignment # "+reminder.getNameAssignment()
                + System.lineSeparator()
                +". We will send you a text message at "
                + reminder.getPhoneNum()
                +" 12 hours before "+dueDate
                +".";
        // Dealing with data locally
        String[] data = new String[]{
                ReminderApp2.displayDescriptions[0]+ Integer.toString(reminder.getID()),
                ReminderApp2.displayDescriptions[1]+reminder.getCourseName(),
                ReminderApp2.displayDescriptions[2]+reminder.getNameAssignment(),
                ReminderApp2.displayDescriptions[3]+reminder.getPhoneNum(),
                //Long.toString(reminder.getdueDate())
                ReminderApp2.displayDescriptions[4]+dueDate
        };

        try {
            SaveReminderToFile saveReminderToFile = new SaveReminderToFile(Integer.toString(reminder.getID()));
            saveReminderToFile.writeToFile(
                    data,this
            );
            //saveReminderToFile.writeToFile(message); // This went through
        } catch (FileNotFoundException e) {
            Log.e("File not found",Log.getStackTraceString(e));

        } catch (UnsupportedEncodingException e) {
            Log.e("Unsupported Encoding",Log.getStackTraceString(e));
        }

        // dealing with database
        try{
            alarmManager.set(AlarmManager.RTC_WAKEUP,
                time, PendingIntent.getBroadcast(this,1,intentAlarm,PendingIntent.FLAG_UPDATE_CURRENT));

        // Notify user for successfully set the schedule
//        Toast.makeText(this,
//                message,
//                Toast.LENGTH_LONG)
//                .show();
            Intent popupIntent = new Intent(this,Pop.class);
            popupIntent.putExtra("ReminderDetail",message);
            popupIntent.putExtra("DisplayRecordID",reminder.getID());
            startActivity(popupIntent);
        } catch (Exception e){
            //AlertDialog.Builder builder =
                    new AlertDialog.Builder(this).setCancelable(true).setMessage(e.getMessage())
                    .setPositiveButton("Add another reminder", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent.addFlags(intent.FLAG_ACTIVITY_NO_HISTORY));
                        }
                    });
        }
    }

    public void showList(View view){
        startActivity(new Intent(this,ShowAllActivity.class));
    }
//    @Override
//    protected void onDestroy() {
//        unregisterReceiver(mAppReceiver);
//        super.onDestroy();
//    }
}
