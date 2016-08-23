package Model;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.reminderapp.reminderapp2.R;


public class ReminderApp2 extends Application {
    public static SharedPreferences sp;
    public static Database db;
    public static final String RINGTONE_PREF = "ringtone_pref";
    public static String[] displayDescriptions = new String[]{
            "Reminder ID: ",
            "Course Name: ",
            "Assignment #: ",
            "Phone Number: ",
            "Due Date: "
    };

    @Override
    public void onCreate() {
        super.onCreate();

        PreferenceManager.setDefaultValues(this, R.xml.settings, false);
        sp = PreferenceManager.getDefaultSharedPreferences(this);

        db = new Database(this);
    }

    public static String getRingtone() {
        return sp.getString(RINGTONE_PREF, android.provider.Settings.System.DEFAULT_NOTIFICATION_URI.toString());
    }
}
