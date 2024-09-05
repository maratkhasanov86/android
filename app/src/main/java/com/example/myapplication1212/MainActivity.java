package com.example.myapplication1212;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    Button enter;
    Button Sign_in;
    EditText login, password;
    private String USER_KEY = "User";
    private DatabaseReference mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        login = findViewById(R.id.idlogin);
        password = findViewById(R.id.idsign_in);
        mDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY);

    View.OnClickListener btn1 = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            String id = mDataBase.getKey();
            String login_text = login.getText().toString();
            String pass_text = password.getText().toString();

            User newUser = new User (id, login_text);
            mDataBase.push().setValue(newUser);
          //  Intent Intent = new Intent(MainActivity.this, ReadActivity.class);
          //  startActivity(Intent);

              }
    };

        View.OnClickListener btn2 = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent Intent = new Intent(MainActivity.this, ReadActivity.class);
                startActivity(Intent);

            }
        };
    enter = findViewById(R.id.button);

    enter.setOnClickListener(btn1);

    Sign_in = findViewById(R.id.button2);
    Sign_in.setOnClickListener(btn2);
        }
}