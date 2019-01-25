package com.example.kemal.chat;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DeletionSwipeHelper.OnSwipeListener {

    FirebaseDatabase firebaseDatabase;
    FirebaseFirestore db;
    DatabaseReference databaseReference;
    List<String> list;
    Paint p = new Paint();
    String name;
    RecyclerView recyclerView;
    UserAdapter userAdapter;
    CollectionReference collectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tanimla();
        Listele();
    }

    public void tanimla() {
        name = getIntent().getExtras().getString("name");
        firebaseDatabase = firebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        list = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.userList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 1);
        recyclerView.setLayoutManager(layoutManager);
        userAdapter = new UserAdapter(MainActivity.this, list, MainActivity.this, name);//ilk tanımlama
        recyclerView.setAdapter(userAdapter);

        db = FirebaseFirestore.getInstance();



        ItemTouchHelper.Callback callback = new DeletionSwipeHelper(0, ItemTouchHelper.START, this, this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }


    public void onSwiped(RecyclerView.ViewHolder viewHolder, int position) {
     //   ((UserAdapter.ViewHolder) viewHolder).removeItem(position);
    }

    public void Listele() {
        db.collection("Kullanicilar").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if(!document.get("kullaniciadi").equals(name)){
                            list.add(document.getId());
                            userAdapter.notifyDataSetChanged();
                        }

                    }

                }
            }
        });
    }
}

