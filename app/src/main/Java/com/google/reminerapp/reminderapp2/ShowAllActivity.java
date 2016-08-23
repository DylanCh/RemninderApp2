package com.google.reminderapp.reminderapp2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import Model.SaveReminderToFile;

public class ShowAllActivity extends Activity {

    private ListView showAllList;
    private String[] recordArray;
    //private List<File> files;
    private File[] files;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);

        // get all files from ReminderApp2 directory where all the txt files are stored
        //files = getListFiles(SaveReminderToFile.getDataDirectory());
        //files=SaveReminderToFile.getDataDirectory().listFiles();
        try {
            files = getListFiles(SaveReminderToFile.getDataDirectory());
        }
        catch (Exception e){
            new AlertDialog.Builder(this).setMessage(e.getMessage()).create().show();
        }

        //Toast.makeText(this,"Opening "+SaveReminderToFile.getDataDirectory().getPath(),Toast.LENGTH_LONG);
        final AlertDialog opening =
            new AlertDialog.Builder(this)
                    .setMessage("Opening "+SaveReminderToFile.getDataDirectory().getPath())
                    .create();
        opening.show();

        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {}

            @Override
            public void onFinish() {
                opening.dismiss();
            }
        }.start();

        /* TODO: correctly implement this:
            1) get all Files into an array, read each one into a string,
            2) put all strings into an array
            4) put array into ArrayAdapter
         */

        // read each file, load text into list of strings
        //for (File file :files) {

        for (int i=0; i<files.length;i++){
//            try {
//                FileInputStream inputStream = new FileInputStream(files[i]);
//                if (inputStream != null) {
//                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//                    String receiveString = "";
//                    StringBuilder stringBuilder = new StringBuilder(); // TODO: either dispose or reuse this StringBuilder
//
//                    while ((receiveString = bufferedReader.readLine()) != null) {
//                        stringBuilder.append(receiveString);
//                        stringBuilder.append("     "); //space for next record
//                    }
//                    inputStream.close();
//                    //showAllStringList.add(stringBuilder.toString());
//                    recordArray[i]=stringBuilder.toString();
//                } // end if
//            } // end try
//            catch (IOException e) {
//                Log.e("IO Exception thrown",e.toString());
//                final AlertDialog alert = new AlertDialog.Builder(this).setCancelable(true).setMessage(e.getMessage())
//                        .create();
//                alert.show();
//            } // end catch
////            catch (FileNotFoundException e1) {
////                Log.e("Can't open file", e1.toString());
////
////            } // end catch
            recordArray[i]=files[i].getName();
        } // end foreach
        //recordArray = showAllStringList.toArray(new String[0]);

            showAllList = (ListView)findViewById(R.id.showAllList);
        ArrayAdapter adapter= new ArrayAdapter<String>(this,R.layout.activity_show_all,
                recordArray);
        showAllList.setAdapter(adapter);
    }

    private File[] getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
//            if (file.isDirectory()) {
//                inFiles.addAll(getListFiles(file));
//            } else {
                if(!file.isDirectory() && file.getName().endsWith(".txt")){
                    inFiles.add(file);
                }
            //}
        }
        return inFiles.toArray(new File[0]);
    }



    // return button
    public void goBackToMainFromShowAll(View v){
        startActivity(new Intent(this,MainActivity.class));
    }
}
