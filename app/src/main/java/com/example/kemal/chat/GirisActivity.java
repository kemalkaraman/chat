package com.example.kemal.chat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class GirisActivity extends AppCompatActivity {


    AppCompatEditText et_name;
    AppCompatButton btn_login;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        tanimla();
    }

    public void tanimla() {
        et_name = (AppCompatEditText) findViewById(R.id.et_name);
        btn_login = (AppCompatButton) findViewById(R.id.btn_login);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        db = FirebaseFirestore.getInstance();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString();
                if (name != "") {
                    et_name.setText("");
                    ekle(name);
                } else {
                    Toast.makeText(getApplicationContext(), "boş", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void ekle(final String name) {

        final Map messageMap = new HashMap();
        Map<String, Object> note = new HashMap<>();
        note.put("kullaniciadi", name);

        db.collection("Kullanicilar").document(name)
                .set(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent = new Intent(GirisActivity.this, MainActivity.class);
                        intent.putExtra("name", name);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

}
