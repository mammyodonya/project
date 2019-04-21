package com.imejadevs.diagnosis.cholera;

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

public class D_cholera extends AppCompatActivity {

  private TextSwitcher textSwitcher;
  private Button Prev, Next, A, B, C, D;
  private int pos = -1;
  private ImageView imageView;

  private static final String[] buttonA = {
          "Male",
          "Yes, mostly after having meals",
          "Yes i do",
          "Yes",
          "YES",
          "Painless",
          "Yes, most of the times i feel thirsty",
          "Yes,i do feel Muscle cramps",
          "5 days ago",
          "Yes, Severally",
          "Yes, there is a lot of cold where i stay",
          "Yes, i have used pain killers to relieve pains and some multi vitamins to improve my appetite",
          "Three days",
          "Sometimes",
          "XXX"};

  private static final String[] buttonB = {
          "Female",
          "Yes, but not too much",
          "Yes, but for a short while",
          "Sometimes i  vomit while some times i don't",
          "Yes, but not frequently",
          "I cant tell",
          "Somehow, sometimes i just feel like i should take fluids",
          "Yes, I experience Shock",
          "Three days ago",
          "Yes, once",
          "Yes, the place is somehow cold",
          "Yes, have used pain killers to relieve chest pain",
          "One day ",
          "No",
          "XXX"};

  private static final String[] buttonC = {
          "Male",
          "Not onset",
          "Only after eating and not too much",
          "NO",
          "Not sure",
          "Little pains",
          "Unnoticed",
          "I don't think",
          "Yesterday",
          "Am not sure",
          "Yes, the place is somehow cold",
          "No,i have just been trying to keep my body warm",
          "I have been diarrhearing with no blood",
          "Sometimes",
          "XXX" };

  private static final String[] buttonD = {
          "Female",
          "No",
          "From far",
          "NO",
          "NO, just Normal",
          "Painful",
          "No",
          "NO",
          "Am just Fine",
          "No",
          "No, only if caught unprepared",
          "I did not use since i was fine",
          "I have not been diarrhearing",
          "NO",
          "XXX"};

  private static final String[] quiz = {
          "Select your gender",
          "Do you have sudden onset diarrhea?",
          "Do you experience Nausea?",
          "Do you experience nausea with vomiting?",
          "Do you have watery diarrhoea ?",
          "How do you feel when you diarrhoea?",
          "Does your body undergo dehydration?",
          "Do you experience Electrolyte imbalance, if yes, how do you feel?",
          "When did you started feeling sick",
          "Have you ever been diagnosed with Cholera?",
          "You do expose your body to cold?",
          "Have you uses any medicine since you started feeling unwell",
          "For how long have you been diarrhearing?",
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
    setContentView(R.layout.activity_d_cholera);
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
    Animation animation= AnimationUtils.loadAnimation(D_cholera.this,R.anim.move_anim);
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
      redoTest("High Cholera");
    } else if (old_value > 42 && old_value <= 50) {
      redoTest("Moderate Cholera");
    } else if (old_value > 26 && old_value <= 42) {
      redoTest("Low Cholera");
    } else {
      redoTest("No Cholera");
    }
  }

  @Override
  public void onBackPressed() {

    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(D_cholera.this);

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
    D_cholera.this.finish();
  }

  private void redoTest(String old_value) {
    Intent intent = new Intent(D_cholera.this, Dataprocess.class);
    Bundle bundle = new Bundle();
    bundle.putString("old_value", old_value);
    bundle.putString("disease", "Cholera");
    intent.putExtras(bundle);
    startActivity(intent);
    D_cholera.this.finish();
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

