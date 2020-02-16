package com.example.hyvu.alarmclockpro;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.hyvu.alarmclockpro.adapter.AdapterAlarm;
import com.example.hyvu.alarmclockpro.model.Alarm;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    DatabaseAlarm databaseAlarm;
    ListView listView_Alarm;
    AdapterAlarm adapterAlarm;
    ArrayList<Alarm> arrayListAlarm;
    Dialog dialog;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mapping();
        GetDataListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        listView_Alarm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete this alarm ?");
                DialogInterface.OnClickListener diaglog_ClickListener=new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int k) {
                        switch (k) {
                            case DialogInterface.BUTTON_POSITIVE:
                                databaseAlarm.deleteRow(arrayListAlarm.get(i).getId());
                                GetDataListView();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;

                        }
                    }
                };
                builder.setPositiveButton("YES",diaglog_ClickListener).setNegativeButton("NO",diaglog_ClickListener);
                builder.show();
            }
        });
    }



    private void GetDataListView() {
        arrayListAlarm.clear();
        arrayListAlarm.addAll(databaseAlarm.getAllAlarm());
        adapterAlarm.notifyDataSetChanged();
    }

    private void Mapping() {
        databaseAlarm = new DatabaseAlarm(getApplicationContext());
        listView_Alarm = findViewById(R.id.id_main_listView);
        arrayListAlarm=new ArrayList<>();
        arrayListAlarm.addAll(databaseAlarm.getAllAlarm());
        adapterAlarm = new AdapterAlarm(MainActivity.this,R.layout.line_alarm,arrayListAlarm);
        listView_Alarm.setAdapter(adapterAlarm);
    }

    public void setAlarmEnable(int id,boolean b){
        Alarm alarm = databaseAlarm.getAlarm(id);
        if(b==true){
            alarm.setChecked(1);
            databaseAlarm.setChecked(id,alarm);
            setCalendar(alarm);
        }
        else if(b==false){
            alarm.setChecked(0);
            databaseAlarm.setChecked(id,alarm);
        }
        Toast.makeText(getApplicationContext(),alarm.getChecked()+"",Toast.LENGTH_LONG).show();
}

    private void setCalendar(Alarm alarm){
        calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,alarm.getHour());
        calendar.set(Calendar.MINUTE,alarm.getMinute());
        alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this,AlarmReceiver.class);
        long startUpTime = calendar.getTimeInMillis();
        if (System.currentTimeMillis() > startUpTime) {
            startUpTime = startUpTime + 24 * 60 * 60 * 1000;
        }
        pendingIntent=PendingIntent.getBroadcast(MainActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startUpTime, 24 * 60 * 60 * 1000, pendingIntent);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.alarm_list,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.id_alarmlist_newAlarm:
                dialog=new Dialog(this);
                dialog.setContentView(R.layout.dialog_new_alarm);
                final TimePicker timePicker=dialog.findViewById(R.id.id_dialogNewAlarm_timePicker);
                Button btn_SetAlarm=dialog.findViewById(R.id.id_diaglogNewAlarm_btn_setAlarm);
                final EditText edt_Message = dialog.findViewById(R.id.id_dialogNewAlarm_edt_Message);
                dialog.show();
                btn_SetAlarm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Alarm alarm = new Alarm();
                        alarm.setHour(timePicker.getCurrentHour());
                        alarm.setMinute(timePicker.getCurrentMinute());
                        alarm.setMessage(edt_Message.getText().toString());
                        int t_check=0;
                        for(int i=0;i<arrayListAlarm.size();i++){
                            if (alarm.getHour() == arrayListAlarm.get(i).getHour() && alarm.getMinute() == arrayListAlarm.get(i).getMinute()){
                                t_check=1;
                                Toast.makeText(getApplicationContext(),"Time already set before",Toast.LENGTH_LONG).show();
                            }
                        }
                        if(t_check==0) {
                            databaseAlarm.addAlarm(alarm);
                            GetDataListView();
                            dialog.cancel();
                        }
                    }
                });
                break;
            case R.id.id_alarmlist_deleteAllAlarm:
                databaseAlarm.deleteAllRow();
                GetDataListView();

        }
        return super.onOptionsItemSelected(item);
    }
}
