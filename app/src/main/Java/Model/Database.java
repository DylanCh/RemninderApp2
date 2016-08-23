package Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Database {

    private static final String DB_NAME = "ReminderApp2";
    private static final int DB_VERSION = 1;

    // DATABASE COLUMNS
    private static final String KEY_ID = "_id";
    private static final String KEY_COURSE_NAME = "COURSE_NAME";
    private static final String KEY_ASSIGNMENT_NUM ="ASSIGNMENT_NUM";
    private static final String KEY_PHONE_NUM = "PHONE_NUM";
    private static final String KEY_DUE_DATE = "DUE_DATE";

    private DbHelper dbHelper;
    private SQLiteDatabase db;

    // database creation statement
    private static final String DATABASE_CREATE
            ="CREATE TABLE "+DB_NAME+" ( "
            +KEY_ID +" INTEGER," // no need to be a primary key since Reminder.ID is unique enough
            +KEY_COURSE_NAME+" TEXT, "
            +KEY_ASSIGNMENT_NUM +" INTEGER, "
            +KEY_PHONE_NUM +" TEXT, "
            +KEY_DUE_DATE+" TEXT"
            +")";

    // constructor for main DB class
    public Database(Context context){
        dbHelper=new DbHelper(context,DB_NAME,null, DB_VERSION);
        db=dbHelper.getWritableDatabase();
    }


    // SWLiteOpenHelper as inner class for preventing accidental instantiation
    private static class DbHelper extends SQLiteOpenHelper {
        public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            // record the version change
            Log.w(DbHelper.class.getName(),
                    "Upgrading database from version " + oldVersion + " to "
                            + newVersion + ", which will destroy all old data");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DB_NAME);
            onCreate(sqLiteDatabase);
        }

        @Override
        public void onDowngrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }

//        public int deleteRecord(Reminder reminder){
//            SQLiteDatabase db= this.getWritableDatabase();
//            int rows = db.delete(DB_NAME,KEY_ID+" = ?", new String[]{String.valueOf(reminder.getID())});
//            db.close();
//            return rows;
//        }
//
//        // Updating single contact
//        public int updateContact(Reminder reminder) {
//            SQLiteDatabase db = this.getWritableDatabase();
//
//            ContentValues values = new ContentValues();
//            values.put(KEY_COURSE_NAME, reminder.getCourseName());
//            values.put(KEY_PHONE_NUM, reminder.getPhoneNum());
//            values.put(KEY_ASSIGNMENT_NUM,reminder.getNameAssignment());
//            values.put(KEY_DUE_DATE,reminder.getdueDate());
//
//            // updating row
//            return db.update(DB_NAME, values, KEY_ID + " = ?",
//                    new String[] { String.valueOf(reminder.getID()) });
//        }
//
//        // insert data into database
//        public void addRecord(Reminder reminder) {
//            SQLiteDatabase db = this.getWritableDatabase();
//            // values to be inserted
//            ContentValues values = new ContentValues();
//            values.put(KEY_COURSE_NAME, reminder.getCourseName());
//            values.put(KEY_PHONE_NUM, reminder.getPhoneNum());
//            values.put(KEY_ASSIGNMENT_NUM,reminder.getNameAssignment());
//            values.put(KEY_DUE_DATE,reminder.getdueDate());
//            // insertion
//            db.insert(DB_NAME,null,values);
//            // close database connection
//            db.close();
//        }
//
//        public List<Reminder> getAllContacts() {
//            List<Reminder> reminderList = new ArrayList<Reminder>();
//            // Select All Query
//            String selectQuery = "SELECT  * FROM " + DB_NAME;
//
//            SQLiteDatabase db = this.getWritableDatabase();
//            Cursor cursor = db.rawQuery(selectQuery, null);
//
//            // looping through all rows and adding to list
//            if (cursor.moveToFirst()) {
//                do {
//                    Reminder reminder = new Reminder();
//                    reminder.setID(Integer.parseInt(cursor.getString(0)));
//                    reminder.setCourseName(cursor.getString(1));
//                    reminder.setNameAssignment(cursor.getString(2));
//                    reminder.setPhoneNum(cursor.getString(3));
//                    reminder.setDueDate(Long.parseLong(cursor.getString(4)));
//                    // Adding contact to list
//                    reminderList.add(reminder);
//                } while (cursor.moveToNext());
//            }
//
//            // return contact list
//            return reminderList;
//        }
//
//        public Reminder getReminder(int id) {
//            SQLiteDatabase db = this.getReadableDatabase();
//            Reminder reminder;
//            Cursor cursor = db.query(DB_NAME, new String[] { KEY_ID,
//                            KEY_COURSE_NAME,KEY_ASSIGNMENT_NUM,KEY_PHONE_NUM,KEY_DUE_DATE }, KEY_ID + "=?",
//                    new String[] { String.valueOf(id) }, null, null, null, null);
//            Log.d("Count",String.valueOf(cursor.getCount()));
//            if (cursor != null) {
//                cursor.moveToFirst();
//                // TODO: double check this step
//                reminder = new Reminder(Integer.parseInt(cursor.getString(0)),
//                        cursor.getString(1), cursor.getString(2), cursor.getString(3), Long.parseLong(cursor.getString(4)));
//                return reminder;
//            }
//            // return contact
//            else return null; //TODO do something other than returning null
//        }
    }

    public int deleteRecord(Reminder reminder){
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        int rows = db.delete(DB_NAME,KEY_ID+" = ?", new String[]{String.valueOf(reminder.getID())});
        db.close();
        return rows;
    }

    // Updating single contact
    public int updateContact(Reminder reminder) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_COURSE_NAME, reminder.getCourseName());
        values.put(KEY_PHONE_NUM, reminder.getPhoneNum());
        values.put(KEY_ASSIGNMENT_NUM,reminder.getNameAssignment());
        values.put(KEY_DUE_DATE,reminder.getdueDate());

        // updating row
        return db.update(DB_NAME, values, KEY_ID + " = ?",
                new String[] { String.valueOf(reminder.getID()) });
    }

    // insert data into database
    public void addRecord(Reminder reminder) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // values to be inserted
        ContentValues values = new ContentValues();
        values.put(KEY_COURSE_NAME, reminder.getCourseName());
        values.put(KEY_PHONE_NUM, reminder.getPhoneNum());
        values.put(KEY_ASSIGNMENT_NUM,reminder.getNameAssignment());
        values.put(KEY_DUE_DATE,reminder.getdueDate());
        // insertion
        db.insert(DB_NAME,null,values);
        // close database connection
        db.close();
    }

    public List<Reminder> getAllContacts() {
        List<Reminder> reminderList = new ArrayList<Reminder>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DB_NAME;

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Reminder reminder = new Reminder();
                reminder.setID(Integer.parseInt(cursor.getString(0)));
                reminder.setCourseName(cursor.getString(1));
                reminder.setNameAssignment(cursor.getString(2));
                reminder.setPhoneNum(cursor.getString(3));
                reminder.setDueDate(Long.parseLong(cursor.getString(4)));
                // Adding contact to list
                reminderList.add(reminder);
            } while (cursor.moveToNext());
        }

        // return contact list
        return reminderList;
    }

    public Reminder getReminder(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Reminder reminder;
        Cursor cursor = db.query(DB_NAME, new String[] { KEY_ID,
                        KEY_COURSE_NAME,KEY_ASSIGNMENT_NUM,KEY_PHONE_NUM,KEY_DUE_DATE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        Log.d("Count",String.valueOf(cursor.getCount()));
        if (cursor != null) {
            cursor.moveToFirst();
            // TODO: double check this step
             reminder = new Reminder(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2), cursor.getString(3), Long.parseLong(cursor.getString(4)));
            return reminder;
        }
        // return contact
        else return null; //TODO do something other than returning null
    }
}
