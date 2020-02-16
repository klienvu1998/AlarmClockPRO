package com.example.hyvu.alarmclockpro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hyvu.alarmclockpro.model.Alarm;

import java.util.ArrayList;

public class DatabaseAlarm extends SQLiteOpenHelper {

    Context context;
    private final static String DATABASE_NAME = "clock";
    private final static String TABLE_NAME = "alarm";
    private final static int VERSION = 1;
    private static final String ID = "id";
    private static final String HOUR = "hour";
    private static final String MINUTE = "minute";
    private static final String MESSAGE = "message";
    private static final String CHECKED = "checked";

    public DatabaseAlarm(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME +"( " +
                ID + " integer primary key, "+
                HOUR + " integer, "+
                MINUTE + " integer, "+
                MESSAGE + " TEXT, "+
                CHECKED + " integer)";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addAlarm(Alarm alarm){
        SQLiteDatabase sb = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HOUR,alarm.getHour());
        values.put(MINUTE,alarm.getMinute());
        values.put(MESSAGE,alarm.getMessage());
        values.put(CHECKED,alarm.getChecked());
        sb.insert(TABLE_NAME,null,values);
    }

    public ArrayList<Alarm> getAllAlarm(){
        ArrayList<Alarm> arrayList = new ArrayList<>();
        final String QUERY_ALL = "SELECT * FROM "+TABLE_NAME;
        Cursor cursor = getWritableDatabase().rawQuery(QUERY_ALL,null);
        if (cursor.moveToFirst()){
            do{
                Alarm alarm = new Alarm();
                alarm.setId(cursor.getInt(0));
                alarm.setHour(cursor.getInt(1));
                alarm.setMinute(cursor.getInt(2));
                alarm.setMessage(cursor.getString(3));
                alarm.setChecked(cursor.getInt(4));
                arrayList.add(alarm);
            }while(cursor.moveToNext());
        }
        return arrayList;
    }

    public void setChecked(int id,Alarm alarm){
        ContentValues values = new ContentValues();
        values.put(ID,alarm.getId());
        values.put(HOUR,alarm.getHour());
        values.put(MINUTE,alarm.getMinute());
        values.put(MESSAGE,alarm.getMessage());
        values.put(CHECKED,alarm.getChecked());
        getWritableDatabase().update(TABLE_NAME,values,ID + " = ?",new String[] {String.valueOf(id)});
    }

    public Alarm getAlarm(int id){
        Alarm alarm=new Alarm();
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = " + id;
        Cursor cursor = db.rawQuery(select, null);
        if (cursor.moveToNext()) {
            alarm.setId(cursor.getInt(0));
            alarm.setHour(cursor.getInt(1));
            alarm.setMinute(cursor.getInt(2));
            alarm.setMessage(cursor.getString(3));
            return alarm;
        }
        return null;
    }

    public void deleteRow(int id){
        getWritableDatabase().delete(TABLE_NAME,ID +" = ?",new String[] {String.valueOf(id)});
    }
    public void deleteAllRow(){
        getWritableDatabase().delete(TABLE_NAME,null,null);
    }

}
