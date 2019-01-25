package com.example.kemal.chat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    String name, othername;
    TextView txt_name, et_mesaj;
    ImageView img_back, img_send;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView chatRecyView;
    MesajAdapter mesajAdapter;
    List<MesajModel> mesajModelList;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        tanimla();


    }

    public void tanimla() {
        mesajModelList = new ArrayList<>();
        name = getIntent().getExtras().getString("name");
        othername = getIntent().getExtras().getString("othername");
        txt_name = (TextView) findViewById(R.id.txt_name);
        et_mesaj = (TextView) findViewById(R.id.et_mesaj);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_send = (ImageView) findViewById(R.id.img_send);
        txt_name.setText(othername);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatActivity.this, MainActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        db = FirebaseFirestore.getInstance();

        img_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mesaj = et_mesaj.getText().toString();
                if (mesaj.length() != 0)
                    mesajGonder(mesaj);
                et_mesaj.setText("");
            }
        });
        chatRecyView = (RecyclerView) findViewById(R.id.chatRecyView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(ChatActivity.this, 1);
        chatRecyView.setLayoutManager((layoutManager));
        mesajAdapter = new MesajAdapter(ChatActivity.this, mesajModelList, ChatActivity.this, name);
        chatRecyView.setAdapter(mesajAdapter);
        loadMesaj();
    }
    public void mesajGonder(String text) {
        final String key = db.collection("chat").document().getId();
        final Map messageMap = new HashMap();
        messageMap.put("text", text);
        messageMap.put("from", name);
        db.collection("chat").document(name).collection(othername).document(key).set(messageMap);
        db.collection("chat").document(othername).collection(name).document(key).set(messageMap);
        loadMesaj();
    }
    public void loadMesaj() {
        Log.i("gelenler", name + " >>>" + othername);
        db.collection("chat").document(name).collection(othername).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                MesajModel mesajModel = document.toObject(MesajModel.class);
                                Log.i("gelenler", mesajModel.toString());
                                mesajModelList.add(mesajModel);
                                mesajAdapter.notifyDataSetChanged();
                                chatRecyView.scrollToPosition(mesajModelList.size() - 1);
                            }
                        } else {
                        }
                    }
                });
      /*  databaseReference.child("Mesajlar").child(name).child(othername).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                MesajModel mesajModel = dataSnapshot.getValue(MesajModel.class);//cast
                mesajModelList.add(mesajModel);
                mesajAdapter.notifyDataSetChanged();
                chatRecyView.scrollToPosition(mesajModelList.size() - 1);
                Log.i("Mesajlar", mesajModel.toString());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        */
    }
}
