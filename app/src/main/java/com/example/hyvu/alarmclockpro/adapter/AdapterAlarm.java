package com.example.hyvu.alarmclockpro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hyvu.alarmclockpro.DatabaseAlarm;
import com.example.hyvu.alarmclockpro.MainActivity;
import com.example.hyvu.alarmclockpro.R;
import com.example.hyvu.alarmclockpro.model.Alarm;

import java.util.ArrayList;

public class AdapterAlarm extends BaseAdapter {

    Context context;
    int layout;
    ArrayList<Alarm> alarmArrayList;

    public AdapterAlarm(Context context, int layout, ArrayList<Alarm> alarmArrayList) {
        this.context = context;
        this.layout = layout;
        this.alarmArrayList = alarmArrayList;
    }

    @Override
    public int getCount() {
        return alarmArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return alarmArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, final ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            viewHolder = new ViewHolder();
            viewHolder.tv_time = view.findViewById(R.id.id_lineAlarm_tv_time);
            viewHolder.tv_message = view.findViewById(R.id.id_lineAlarm_tv_message);
            viewHolder.checkBox_setAlarm = view.findViewById(R.id.id_lineAlarm_checkBox_chooseAlarm);
            view.setTag(viewHolder);
        }
        else{
            viewHolder= (ViewHolder) view.getTag();
        }
        final Alarm alarm = alarmArrayList.get(i);
        viewHolder.tv_time.setText(alarm.getHour() + ":" + alarm.getMinute());
        viewHolder.tv_message.setText(alarm.getMessage());

        for(int k=0;k<alarmArrayList.size();k++){
            if(alarmArrayList.get(i).getChecked()==1){
                viewHolder.checkBox_setAlarm.setChecked(true);
            }
        }


        viewHolder.checkBox_setAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==false){
                    ((MainActivity)context).setAlarmEnable(alarm.getId(),false);
                }
                else if(b == true){
                    ((MainActivity)context).setAlarmEnable(alarm.getId(),true);
                }
            }
        });
        return view;
    }
    private class ViewHolder{
        TextView tv_time,tv_message;
        CheckBox checkBox_setAlarm;
    }
}
