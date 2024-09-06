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

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
        r();

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
    private void r()
    {
        EditText edit_kurs;
        edit_kurs = findViewById(R.id.idsign_in);


        // создаем singleton объект клиента
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://www.cbr-xml-daily.ru/daily_eng_utf8.xml").newBuilder();

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .cacheControl(new CacheControl.Builder().maxStale(30, TimeUnit.DAYS).build())
                .build();
        // выполняем запрос
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    // читаем данные в отдельном потоке
                    final String responseData = response.body().string();

                    // выполняем операции по обновлению UI
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int index_1 = responseData.indexOf("<Value>");
                            int index_2 = responseData.indexOf("</Value>");
                            String s = "Курс австр.доллара " + responseData.substring(index_1+7,index_2);

                            edit_kurs.setText(s);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }

}



