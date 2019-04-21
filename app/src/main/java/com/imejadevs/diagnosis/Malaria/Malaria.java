package com.imejadevs.diagnosis.Malaria;

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

import com.imejadevs.diagnosis.EachDisease.EachDisease;
import com.imejadevs.diagnosis.ProcessData.Dataprocess;
import com.imejadevs.diagnosis.R;

import java.util.ArrayList;

public class Malaria extends AppCompatActivity {
    private TextSwitcher textSwitcher;
    private Button Prev, Next, A, B, C, D;
    private int pos = -1;
    private ImageView imageView;

    private static final String[] buttonA = {
            "Male",
            "Yes, mostly at centre of the head and its too much",
            "Three days ago",
            "Yes i do",
            "Yes",
            "So far two days now",
            "high fever",
            "A lot pains in the muscles and joints",
            "Yes,somehow",
            "Profuse sweating",
            "Yes, severe shaking chills",
            "All the time specifically in the stomach",
            "Yes, have used panadols to relieve pain",
            "It has been long, i can't remember",
            "XXX"};

    private static final String[] buttonB = {
            "Female",
            "Yes, but not too much",
            "A day ago",
            "Yes, but for a short while",
            "Sometimes i  vomit while some times i don't",
            "It started just a few hours ago",
            "Moderate fever",
            "Somehow, in the joints",
            "NO",
            "Moderate sweating",
            "Yes, but not that much",
            "sometimes in the stomach",
            "Yes, have used Paracitamol to relieve pain",
            "A month ago",
            "XXX"};

    private static final String[] buttonC = {
            "Male",
            "Very minimal",
            "Some few hours ago",
            "Only after eating and not too much",
            "NO",
            "Have not been vomiting at all",
            "Changes regularly with the environment",
            "A little pain in the joints",
            "No",
            "Very low sweating",
            "Minimal",
            "In rare occasions",
            "Yes, have used ibruffen to relieve pain",
            "Two weeks ago",
            "XXX" };

    private static final String[] buttonD = {
            "Female",
            "No",
            "I don't feel headache",
            "From far",
            "NO",
            "I have not been vomiting",
            "Normal",
            "somehow week",
            "No, but i just pass some few stool",
            "When in high temperature areas",
            "No",
            "Only when the body is uncomfortable",
            "NO",
            "A week ago",
            "XXX"};

    private static final String[] quiz = {
            "Select your gender",
            "Do you feel head ache?",
            "When did it start?",
            "Do you experience Nausea?",
            "Do you experience nausea with vomiting?",
            "For how long have you been vomiting?",
            "How is your body temperatures?",
            "How are your muscles?",
            "Are you diarrhearing?",
            "Do you experience sweating?",
            "Do you have shaking chills?",
            "Do you feel some abdominal pains?",
            "Have you used any Medicine since you started feeling unwell?",
            "When did you last take antibiotics for malaria?",
            "The Intelligent System has captured your information, click continue to allow for processing of the results"};
    ArrayList<String> arrayList;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //To make the window full screen
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_malaria);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        preferences = getSharedPreferences("Symptoms", MODE_PRIVATE);
        textSwitcher = (TextSwitcher) findViewById(R.id.textSwitcher);
        arrayList = new ArrayList<>();

        A = (Button) findViewById(R.id.a);
        B = (Button) findViewById(R.id.b);
        C = (Button) findViewById(R.id.c);
        imageView=findViewById(R.id.hair);
        D = (Button) findViewById(R.id.d);
        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(getApplicationContext());
                textView.setTextSize(20);
                textView.setText("What is your age?");
                return textView;
            }
        });
        Animation animation= AnimationUtils.loadAnimation(Malaria.this,R.anim.move_anim);
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
            redoTest("High Malaria");
        } else if (old_value > 42 && old_value <= 50) {
            redoTest("Moderate Malaria");
        } else if (old_value > 26 && old_value <= 42) {
            redoTest("Low Malaria");
        } else {
            redoTest("No Malaria");
        }
    }

    @Override
    public void onBackPressed() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Malaria.this);

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
        Malaria.this.finish();
    }

    private void redoTest(String old_value) {
        Intent intent = new Intent(Malaria.this, Dataprocess.class);
        Bundle bundle = new Bundle();
        bundle.putString("old_value", old_value);
        bundle.putString("disease", "Malaria");
        intent.putExtras(bundle);
        startActivity(intent);
        Malaria.this.finish();
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

