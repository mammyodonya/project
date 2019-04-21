package com.imejadevs.diagnosis.History;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.imejadevs.diagnosis.R;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DisplayHistory extends AppCompatActivity {
    ActionBar actionBar;
    String disease, level, date, time, email, recommendation;
    TextView A, B, C, D, E;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_history);
        /*Back arrow*/
        disease = getIntent().getStringExtra("disease");
        level = getIntent().getStringExtra("level");
        date = getIntent().getStringExtra("date");
        time = getIntent().getStringExtra("time");
        email = getIntent().getStringExtra("email");
        recommendation = getIntent().getStringExtra("recommendation");

        actionBar = getSupportActionBar();
        actionBar.setTitle("" + disease);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        A = findViewById(R.id.aaa);
        B = findViewById(R.id.bbb);
        C = findViewById(R.id.ccc);
        D = findViewById(R.id.ddd);
        E = findViewById(R.id.eee);
        A.setText(date);
        B.setText(time);
        C.setText(disease);
        D.setText(level);
        E.setText(recommendation);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    }

