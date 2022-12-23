package com.example.earcengine;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class page_3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        LinearLayout linearLayout = findViewById(R.id.importacc);
        LinearLayout linearLayout2 = findViewById(R.id.create);
        ImageView imageView3 = findViewById(R.id.prev2);

        imageView3.setOnClickListener(v -> {
            Intent intent3 = new Intent(page_3.this, page_2.class);
            intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent3);
        });


        linearLayout.setOnClickListener(v -> {
            Intent intent4 = new Intent(page_3.this, page_4.class);
            intent4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent4);
        });

       // linearLayout2.setOnClickListener(v -> {
           // Intent intent5 = new Intent(page_3.this, page_4.class);
         //   intent5.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
         //   startActivity(intent5);
     //   });
    }
}