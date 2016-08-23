package com.google.reminderapp.reminderapp2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import Model.Database;
import Model.Reminder;
import Model.ReminderApp2;
import Model.SaveReminderToFile;

public class Pop extends Activity{
    private TextView DisplayDetails;
    //private Database db;
    private  Reminder reminder;
    private String displayText;
    @Override
    protected void onCreate(Bundle savedInstancestate){
        super.onCreate(savedInstancestate);
        setContentView(R.layout.popwindow);

        String message = getIntent().getStringExtra("ReminderDetail"); // get message from the last activity
        int DisplayRecordID=getIntent().getIntExtra("DisplayRecordID",0);


        DisplayDetails = (TextView) findViewById(R.id.DisplayDetails);
        //db = new Database(this);

        try {
//            reminder = ReminderApp2.db.getReminder(DisplayRecordID);
//            DisplayDetails.setText("Added Reminder:"
//                    + System.lineSeparator()
//                    + "Course Name: " + reminder.getCourseName()
//                    + System.lineSeparator()
//                    + "Assignment #: " + reminder.getNameAssignment()
//                    + System.lineSeparator()
//                    + "Due Date: " + reminder.getdueDate()
//                    + System.lineSeparator()
//                    + "Notification sends to: " + reminder.getPhoneNum()
//            );
                SaveReminderToFile saveReminderToFile = new SaveReminderToFile(Integer.toString(DisplayRecordID));
                 displayText=saveReminderToFile.readFromFile(this);
                DisplayDetails.setText(displayText);
        }
        catch (Exception e){
            /*Cursor is not having any value and still you are trying to read from your Cursor*/
           //DisplayDetails.setText(e.toString()+"!!!");
            Log.e("Exception: ",e.toString());
//            try {
//                SaveReminderToFile saveReminderToFile = new SaveReminderToFile(Integer.toString(DisplayRecordID));
//                DisplayDetails.setText(saveReminderToFile.readFromFile(this));
//            } catch (FileNotFoundException e1) {
//                //e.printStackTrace();
//                Log.e("login activity", "File not found: " + e1.toString());
//                DisplayDetails.setText(e1.toString());
//            } catch (UnsupportedEncodingException e1) {
//                Log.e("Unsupported Encoding","Unsupported encoding"+ e1.toString());
//                DisplayDetails.setText(e1.toString());
//            }
            new AlertDialog.Builder(this).setCancelable(true).setMessage(e.getMessage())
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Pop.this, Pop.class);
                            startActivity(intent);
                        }
                    });
        }


        Toast.makeText(this,
                message,
                Toast.LENGTH_LONG)
                .show();
    }

    // Events for pressing the edit button
    public void editRecord(View v){
        Intent editIntent = new Intent(this,EditActivity.class);
        if (displayText!="" && displayText!=null) {
            editIntent.putExtra("EditDetails", displayText); // carry data over to edit mode
        }
        else{
            Toast.makeText(this,
                    "You don't have a reminder to edit",
                    Toast.LENGTH_LONG)
                    .show();
        }
        startActivity(editIntent);
    }

    public void goBackToMain(View V){
        startActivity(new Intent(this,MainActivity.class));
    }
}
