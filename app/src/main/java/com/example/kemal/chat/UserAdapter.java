package com.example.kemal.chat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    Context context;
    List<String> list;
    Activity activity;
    String name;


    public UserAdapter(Context context, List<String> list, Activity activity, String name) {
        this.context = context;
        this.list = list;
        this.activity = activity;
        this.name = name;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {    //oluşturuken hangi layout
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {    //tanımlanananlar işlemler dinlemler
        viewHolder.textView.setText(list.get(i).toString());
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ChatActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("othername", list.get(i).toString());
                activity.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {//tanımlamalar

        TextView textView;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.userAnaLayout);
            textView = itemView.findViewById(R.id.tv_name);
        }
    }


}
