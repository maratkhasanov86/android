package com.example.myapplication1212;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    private ListView listView;

    private DatabaseReference mDataBase;
    private List<String> DiscrTasks;
    private ListView ListUserTasks;
    private String USER_KEY = "User";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ListUserTasks = (ListView) findViewById(R.id.discr_for_tasks);
        mDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY);
        ///updateUI();
        mDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>(){};
                DiscrTasks = snapshot.getValue(t);



            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        }
    private void updateUI(){
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1,DiscrTasks);
        ListUserTasks.setAdapter(adapter);
    }

}