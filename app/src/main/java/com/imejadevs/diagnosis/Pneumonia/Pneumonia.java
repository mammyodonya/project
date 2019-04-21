package com.imejadevs.diagnosis.Pneumonia;

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

public class Pneumonia extends AppCompatActivity {
    private TextSwitcher textSwitcher;
    private Button Prev, Next, A, B, C, D;
    private int pos = -1;
    private ImageView imageView;

    private static final String[] buttonA = {
            "Male",
            "Yes",
            "Yes, A cough that worsens over time and does not go away",
            "Yellow/green phlegm",
            "Chest pain when coughing or breathing deeply",
            "Shortness of breath ,Difficulty breathing ",
            "Loss of appetite ",
            "A lot of Fatigue",
            "5 days ago",
            "Yes, Severally",
            "Yes, there is a lot of cold where i stay",
            "Yes, i have used pain killers to relieve pains and some multi vitamins to improve my appetite",
            "Three days",
            "Sometimes",
            "XXX"};

    private static final String[] buttonB = {
            "Female",
            "Yes, i do feel Nausea and sometimes i vomit",
            "Yes",
            "Few phlegm",
            "Chest pains while coughing",
            "Sometimes breathing becomes a problem",
            "Appetite is somehow low",
            "sometimes the body feels tired",
            "Three days ago",
            "Yes, once",
            "Yes, the place is somehow cold",
            "Yes, have used pain killers to relieve chest pain",
            "One day ",
            "No",
            "XXX"};

    private static final String[] buttonC = {
            "Male",
            "I only feel nausea with no vomiting and diarrhea",
            "Yes",
            "NO",
            "chest pains",
            "some difficulties when nose blocks",
            "Reduced appetite",
            "Only after engaging the body in hard tasks",
            "Yesterday",
            "Am not sure",
            "Yes, the place is somehow cold",
            "No,i have just been trying to keep my body warm",
            "I have been coughing with no phlegm",
            "Sometimes",
            "XXX"};
    private static final String[] buttonD = {
            "Female",
            "I does not feel nausea, vomiting or diarrhea",
            "Yes, only when chocked",
            "NO",
            "No chest pains",
            "some difficulties when nose blocks",
            "Good appetite towards food",
            "Only after engaging the body in hard tasks",
            "Am just Fine",
            "No",
            "No, only if caught unprepared",
            "I did not use since i was fine",
            "I have not been coughing out phlegm",
            "NO",
            "XXX"};

    private static final String[] quiz = {
            "Select your gender",
            "Do you feel nausea, diarrhea or vomiting?",
            "Do you experience coughing?",
            "Do you cough up phlegm?",
            "Does your chest pain?",
            "How is your breathing?",
            "Since you started feeling sick, how is your weight and appetite?",
            "Is your body weak and you get tired easily?",
            "When did you started feeling sick",
            "Have you ever been diagnosed with pneumonia?",
            "You do expose your body to cold?",
            "Have you uses any medicine since you started feeling unwell",
            "For how long have you been coughing up phlegm?",
            "Do You walk bare footed in swampy areas?",
            "The Intelligent System has captured your information, click continue to allow for processing of the results"};
    ArrayList<String> arrayList;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //To make the window full screen
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pneumonia);
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
        Animation animation= AnimationUtils.loadAnimation(Pneumonia.this,R.anim.move_anim);
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
            redoTest("High Pneumonia");
        } else if (old_value > 42 && old_value <= 50) {
            redoTest("Moderate Pneumonia");
        } else if (old_value > 26 && old_value <= 42) {
            redoTest("Low Pneumonia");
        } else {
            redoTest("No Pneumonia");
        }
    }

    @Override
    public void onBackPressed() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Pneumonia.this);

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
        Pneumonia.this.finish();
    }

    private void redoTest(String old_value) {
        Intent intent = new Intent(Pneumonia.this, Dataprocess.class);
        Bundle bundle = new Bundle();
        bundle.putString("old_value", old_value);
        bundle.putString("disease", "Pneumonia");
        intent.putExtras(bundle);
        startActivity(intent);
        Pneumonia.this.finish();
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


