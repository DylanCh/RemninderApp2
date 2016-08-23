package com.google.reminderapp.reminderapp2;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import Model.Reminder;

public class EditActivity extends AppCompatActivity {
    private String[] editDetailsArray;
    private EditText NameAssignment;
    private EditText Course;
    private EditText DueDate;
    private EditText PhoneNum;
    private Reminder reminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        String editDetails;
        Course =(EditText)findViewById(R.id.courseNum2);
        NameAssignment = (EditText)findViewById(R.id.assignmentNum2);
        PhoneNum =(EditText)findViewById(R.id.phoneNum2);
        DueDate = (EditText)findViewById(R.id.date2);

        if(getIntent().getExtras()!=null) // if extra string successfully sent to this activity
        {
            editDetails= getIntent().getStringExtra("EditDetails");
            editDetailsArray=editDetails.split("\\r?\\n");
            long time=new GregorianCalendar().getTimeInMillis()+24*60*60*1000; // default alarm time: 24 hours from now
            SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date d = f.parse(editDetailsArray[4]);
                time=d.getTime();
            }
            catch (ParseException e){
                new AlertDialog.Builder(EditActivity.this).setMessage(e.getMessage()).show();
            }
            reminder=new Reminder(Integer.parseInt(editDetailsArray[0]),
                    editDetailsArray[1],
                    editDetailsArray[2],
                    editDetailsArray[3],
                    time);

            Course.setText(editDetailsArray[1]);
            NameAssignment.setText(editDetailsArray[2]);
            PhoneNum.setText(editDetailsArray[3]);
            DueDate.setText(editDetailsArray[4]);
        }
        else{

        }
    }

}
