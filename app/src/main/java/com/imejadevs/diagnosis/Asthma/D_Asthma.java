package com.imejadevs.diagnosis.Asthma;

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

public class D_Asthma extends AppCompatActivity {
  private TextSwitcher textSwitcher;
  private Button Prev, Next, A, B, C, D;
  private int pos = -1;
  private ImageView imageView;


  private static final String[] buttonA = {
          "Male",
          "Severe wheezing",
          "Yes, Uncontrollable coughing",
          "Chest tiredness ",
          "Extreme difficulty breathing",
          "Anxiety or panic",
          "Yes, i do sweat all through",
          "Rapid ",
          "5 days ago",
          "Yes, Severally",
          "Yes, there is a lot of cold where i stay",
          "Yes, i have used pain killers to relieve pains and some multi vitamins to improve my appetite",
          "Three days",
          "Yes a very close relative",
          "XXX"};

  private static final String[] buttonB = {
          "Female",
          "Lower wheezing",
          "Yes, moderate coughing",
          "Sometimes i feel tired in the chest",
          "Sometimes breathing becomes a problem",
          "Panic",
          "Moderate sweating",
          "Moderate",
          "Three days ago",
          "Yes, once",
          "Yes, the place is somehow cold",
          "Yes, have used pain killers to relieve chest pain",
          "One day ",
          "Yes, but not that close relative",
          "XXX"};

  private static final String[] buttonC = {
          "Male",
          "Just snowing",
          "Yes",
          "No chest pains",
          "some difficulties when nose blocks",
          "Anxiety",
          "Sweating when weather is hot",
          "very low",
          "Yesterday",
          "Am not sure",
          "Yes, the place is somehow cold",
          "No,i have just been trying to keep my body warm",
          "I have been coughing with no phlegm",
          "Not sure",
          "XXX"};
  private static final String[] buttonD = {
          "Female",
          "No",
          "Yes, only when chocked",
          "No chest pains",
          "some difficulties when nose blocks",
          "Panic",
          "Sweating when weather is hot",
          "No ",
          "Am just Fine",
          "No",
          "No, only if caught unprepared",
          "I did not use since i was fine",
          "I have not been coughing out phlegm",
          "NO",
          "XXX"};

  private static final String[] quiz = {
          "Select your gender",
          "Do you  undergo wheezing sound while breathing?",
          "Do you experience coughing?",
          "Does your chest pain and have tiredness in the chest?",
          "How is your breathing?",
          "How do you feel?",
          "Do you experience sweating?",
          "How is your nostril movement?",
          "When did you started feeling sick",
          "Have you ever been diagnosed with Asthma?",
          "You do expose your body to cold?",
          "Have you uses any medicine since you started feeling unwell",
          "For how long have you been coughing?",
          "Does any of your Relatives suffer from Asthma?",
          "The Intelligent System has captured your information, click continue to allow for processing of the results"};
  ArrayList<String> arrayList;
  SharedPreferences preferences;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    //To make the window full screen
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    setContentView(R.layout.activity_d__asthma);
    ActionBar actionBar = getSupportActionBar();
    assert actionBar != null;
    actionBar.hide();

    preferences = getSharedPreferences("Symptoms", MODE_PRIVATE);
    textSwitcher =  findViewById(R.id.textSwitcher);
    arrayList = new ArrayList<>();

    A =  findViewById(R.id.a);
    B =  findViewById(R.id.b);
    C =  findViewById(R.id.c);
    D =  findViewById(R.id.d);
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
    Animation animation= AnimationUtils.loadAnimation(D_Asthma.this,R.anim.move_anim);
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
      redoTest("High Asthma");
    } else if (old_value > 42 && old_value <= 50) {
      redoTest("Moderate Asthma");
    } else if (old_value > 26 && old_value <= 42) {
      redoTest("Low Asthma");
    } else {
      redoTest("No Asthma");
    }
  }

  @Override
  public void onBackPressed() {

    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(D_Asthma.this);

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
    D_Asthma.this.finish();
  }

  private void redoTest(String old_value) {
    Intent intent = new Intent(D_Asthma.this, Dataprocess.class);
    Bundle bundle = new Bundle();
    bundle.putString("old_value", old_value);
    bundle.putString("disease", "Asthma");
    intent.putExtras(bundle);
    startActivity(intent);
    D_Asthma.this.finish();
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


