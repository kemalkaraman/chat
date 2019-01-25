package com.example.kemal.chat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MesajAdapter extends RecyclerView.Adapter<MesajAdapter.ViewHolder> {
    Context context;
    List<MesajModel> list;
    Activity activity;
    String name;
    Boolean state;
    int view_send = 1, view_received = 2;


    public MesajAdapter(Context context, List<MesajModel> list, Activity activity, String name) {
        this.context = context;
        this.list = list;
        this.activity = activity;
        this.name = name;
        state = false;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view;
        if (i == view_send) {
            view = LayoutInflater.from(context).inflate(R.layout.send_layout, viewGroup, false);
            return new ViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.received_layout, viewGroup, false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.textView.setText(list.get(i).getText().toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            if(state == true){
                textView = itemView.findViewById(R.id.txt_send);
            }
            else{
                textView = itemView.findViewById(R.id.txt_received);
            }

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getFrom().equals(name)) {
            state = true;
            return view_send;
        } else {
            state = false;
            return view_received;
        }
    }
}
