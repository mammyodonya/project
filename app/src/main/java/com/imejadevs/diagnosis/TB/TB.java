package com.imejadevs.diagnosis.TB;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.imejadevs.diagnosis.ProcessData.Dataprocess;
import com.imejadevs.diagnosis.R;

import java.util.ArrayList;

public class TB extends AppCompatActivity {
    private TextSwitcher textSwitcher;
    private Button Prev, Next, A, B, C, D;
    private int pos = -1;
    private ImageView imageView;

    private static final String[] buttonA = {
            "Male",
            "Yes",
            "Yes, A cough that worsens over time and does not go away",
            "Yellow/green phlegm first thing in the morning ",
            "A lot of pain in the chest",
            "Trouble breathing",
            "Loss of weight and appetite",
            "Profuse sweating at night",
            "Yes,Generalized body aches",
            "Three days ago",
            "No, am thinking of it",
            "Yes",
            "Yes, paracitamol to relieve pain",
            "A lot of Fatigue",
            "XXX"};

    private static final String[] buttonB = {
            "Female",
            "Feels unwell in the whole body",
            "Yes",
            "Blood and phlegm",
            "Very less",
            "Sometimes breathing becomes a problem",
            "Yes",
            "Moderate sweating",
            "Yes, body aches sometimes",
            "A day ago",
            "Yes, often, last tested last month",
            "NO",
            "Yes, Ibruffen to reduce pains",
            "sometimes the body feels tired",
            "XXX"};

    private static final String[] buttonC = {
            "Female",
            "Feels ill some times",
            "Yes",
            "NO",
            "No chest pains",
            "some difficulties when nose blocks",
            "Reduced appetite",
            "Sweating when weather is hot",
            "No aches",
            "A few hours ago",
            "Yes, Severally. I even tested last week",
            "No",
            "Yes, multi-vitamins to improve appetite",
            "Only after engaging the body in hard tasks",
            "XXX"};
    private static final String[] buttonD = {
            "Male",
            "Feels ill some times",
            "Yes, only when chocked",
            "NO",
            "No chest pains",
            "some difficulties when nose blocks",
            "Good appetite towards food",
            "Sweating when weather is hot",
            "No aches",
            "Am not unwell",
            "Yes, i usually test after every 3 months",
            "NO",
            "Yes, but not for the purpose of treatment",
            "Only after engaging the body in hard tasks",
            "XXX"};

    private static final String[] quiz = {
            "Select your gender",
            "Do you have a general sense of being unwell?",
            "Do you experience coughing?",
            "Do you cough up blood or phlegm?",
            "Does your chest pain?",
            "How is your breathing?",
            "Since you started feeling sick, how is your weight and appetite?",
            "Do you experience sweating?",
            "Does your body ache?",
            "When did you started feeling unwell?",
            "Have ever tested for HIV/AIDS?",
            "Have been diagnosed with TB before?",
            "Have you used some drugs?",
            "Is your body weak?",
            "The Intelligent System has captured your information, click continue to allow for processing of the results"};
    ArrayList<String> arrayList;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //To make the window full screen
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_tb);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        preferences = getSharedPreferences("Symptoms", MODE_PRIVATE);
        textSwitcher = (TextSwitcher) findViewById(R.id.textSwitcher);
        arrayList = new ArrayList<>();

        A = (Button) findViewById(R.id.a);
        B = (Button) findViewById(R.id.b);
        C = (Button) findViewById(R.id.c);
        D = (Button) findViewById(R.id.d);
        imageView= findViewById(R.id.hair);
        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(getApplicationContext());
                textView.setTextSize(20);
                textView.setText("What is your age?");
                return textView;
            }
        });
        Animation animation= AnimationUtils.loadAnimation(TB.this,R.anim.move_anim);
        imageView.setAnimation(animation);
    }
    public void buttonA(View view) {
        if (pos < quiz.length - 1) {
            pos = pos + 1;
            textSwitcher.setText(quiz[pos]);
            changebuttonTexts("4");
        }
    }

    public void buttonB(View view) {
        if (pos < quiz.length - 1) {
            pos = pos + 1;
            textSwitcher.setText(quiz[pos]);
            changebuttonTexts("3");
        }
    }

    public void buttonC(View view) {
        if (pos < quiz.length - 1) {
            pos = pos + 1;
            textSwitcher.setText(quiz[pos]);
            changebuttonTexts("2");
        }
    }

    public void buttonD(View view) {
        if (pos < quiz.length - 1) {
            pos = pos + 1;
            textSwitcher.setText(quiz[pos]);
            changebuttonTexts("1");
        } else if (D.getText().equals("Continue")) {

            carryTest();
        }
    }

    private void carryTest() {
        int value = 0;
        int old_value = preferences.getInt("user", value);

        if (old_value > 50) {
            redoTest("Pulmonary TB");
        } else if (old_value > 42 && old_value <= 50) {
            redoTest("Extrapulmonary TB");
        } else if (old_value > 26 && old_value <= 42) {
            redoTest("Low TB");
        } else {
            redoTest("No TB");
        }
    }

    @Override
    public void onBackPressed() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(TB.this);

        alertDialog.setTitle("Alert");

        alertDialog.setMessage("Sure to cancel diagnosis?");

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancelData();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.show();
    }

    private void cancelData() {

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("user", 0);
        editor.commit();
        TB.this.finish();
    }

    private void redoTest(String old_value) {
        Intent intent = new Intent(TB.this, Dataprocess.class);
        Bundle bundle = new Bundle();
        bundle.putString("old_value", old_value);
        bundle.putString("disease", "TB");
        intent.putExtras(bundle);
        startActivity(intent);
        TB.this.finish();
    }

    private void changebuttonTexts(String user_answer) {
        int b = Integer.parseInt(user_answer);
        int value = 0;
        int old_value = preferences.getInt("user", value);

        int new_value = old_value + b;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("user", new_value);
        editor.commit();

        A.setText(buttonA[pos]);
        B.setText(buttonB[pos]);
        C.setText(buttonC[pos]);
        D.setText(buttonD[pos]);

        if (pos == 0) {
            A.setVisibility(View.INVISIBLE);
            B.setVisibility(View.INVISIBLE);
        } else {

            A.setVisibility(View.VISIBLE);
            B.setVisibility(View.VISIBLE);
            if (pos == quiz.length - 1) {
                D.setText("Continue");
                B.setVisibility(View.INVISIBLE);
                C.setVisibility(View.INVISIBLE);
                A.setVisibility(View.INVISIBLE);
            }
        }

    }


}

