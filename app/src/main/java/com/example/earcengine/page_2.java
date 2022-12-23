package com.example.earcengine;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class page_2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ImageView next2 = findViewById(R.id.next2);
        ImageView prev1 = findViewById(R.id.prev1);

        next2.setOnClickListener(v -> {
            Intent intent2 = new Intent(page_2.this, page_3.class);
            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent2);
        });

        prev1.setOnClickListener(v -> {
            Intent intent3 = new Intent(page_2.this, getstd_1.class);
            intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent3);
        });
    }
}