package com.example.vaccinestatuscheck;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class alarmViewAdaptor extends RecyclerView.Adapter<alarmViewAdaptor.ViewHolder> {

    Switch aSwitch;

    ArrayList<alarmView> items = new ArrayList<alarmView>();

    public alarmViewAdaptor(ArrayList<alarmView> items) {
        this.items = items;
    }

    public void addItem(alarmView item){
        items.add(item);
    }

    public void setItems(ArrayList<alarmView> items){
        this.items = items;
    }

    public alarmView getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, alarmView item){
        items.set(position, item);
    }

    @NonNull
    @Override
    public alarmViewAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.alarm_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull alarmViewAdaptor.ViewHolder holder, int position) {
        alarmView item = items.get(position);

        holder.setItem(item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String Time = holder.textView1.getText().toString();
//                Toast.makeText(v.getContext(), Time, Toast.LENGTH_SHORT).show();
                //?????? activity??? ??????
                int position = holder.getAdapterPosition();
                mListner.onItemClick(v, position);
//                remove(holder.getAdapterPosition());
            }
        });
//        Switch alarmCheck = holder.itemView.findViewById(R.id.alarmRemove);
        //????????? ???????????? ???????????? ?????? ???????????? ??????
        //????????? alarmID??? ?????? pendingintent??? ???????????? service ???????????????
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent receiver = new Intent(holder.itemView.getContext(),alarmReceiver.class);
                Context context = holder.itemView.getContext();
                AlarmManager alarmManager;
                alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                Log.d("msg", String.valueOf(item.alarmId));
                PendingIntent alarmCancle = PendingIntent.getBroadcast(context, item.alarmId, receiver, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.cancel(alarmCancle);
                remove(holder.getAdapterPosition());
            }
        }
//        {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    Log.d("msg", "switch on");
//                    Log.d("msg", String.valueOf(holder.alarmTime.getText()));
//                    Log.d("ID", String.valueOf(item.alarmId));
////                    Intent service = new Intent(get, alarmService.class);
////                    service.putExtra("state", "switch_on");
////                    service.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                }else {
//                    Log.d("msg", "switch off");
//
//                    //inent??? state???????????? service ?????? ?????????
////                    AlarmManager alarmManager;
////                    alarmManager = (AlarmManager) context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
////                    Intent intent = new Intent(context.getApplicationContext(), alarmReceiver.class);
////                    Intent intent1 = new Intent(context.getApplicationContext(), alarmService.class);
////                    PendingIntent alarmCancle = PendingIntent.getBroadcast(context.getApplicationContext(), item.alarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
////                    alarmManager.cancel(alarmCancle);
////                    context.stopService(intent1);
//
//
////                    Intent service = new Intent(context, alarmService.class);
////                    service.putExtra("state", "switch_off");
////                    service.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                }
//            }
//        }
        );

//        alarmCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    Log.d("msg", "switch on");
//                    Log.d("msg", String.valueOf(holder.textView1.getText()));
////                    Intent service = new Intent(get, alarmService.class);
////                    service.putExtra("state", "switch_on");
////                    service.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                }else {
//                    Log.d("msg", "switch off");
////                    Intent service = new Intent(context, alarmService.class);
////                    service.putExtra("state", "switch_off");
////                    service.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                }
//            }
//        });
    }

    public interface OnItemClickListenr{
        void onItemClick(View v, int position);
    }

    public OnItemClickListenr mListner = null;

    public void setOnItemClickListener(OnItemClickListenr listener) {
        this.mListner = listener ;
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public void remove(int position){
        try{
            items.remove(position);
            notifyItemRemoved(position);
            notifyDataSetChanged();

        }catch(IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView alarmTime;
        TextView helperName;
        Button remove;
        ImageView helperImage;
        //?????????
        public ViewHolder(View itemView){
            super(itemView);

            alarmTime = itemView.findViewById(R.id.alarmTime);
            helperName = itemView.findViewById(R.id.helperName);
            remove = itemView.findViewById(R.id.alarmRemove);
            helperImage =itemView.findViewById(R.id.helperImage);
        }

        public void setItem(alarmView item){
            alarmTime.setText(item.getAlarmTime());
            helperName.setText(item.getHelperName());
            Glide.with(itemView).load(item.getHelperImageId()).into(helperImage);
        }
    }
}
