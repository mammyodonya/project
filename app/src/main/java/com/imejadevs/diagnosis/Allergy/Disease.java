package com.imejadevs.diagnosis.Allergy;


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



public class Disease extends AppCompatActivity {
  private TextSwitcher textSwitcher;
  private Button Prev, Next, A, B, C, D;
  private int pos = -1;
 private ImageView imageView;

  private static final String[] buttonA = {
          "Male",
          "Once in a while",
          "Just fine",
          "Yes",
          "Mouth and trout",
          "Yes",
          "A little pains in the muscles and joints",
          "No",
          "Yes,sometimes",
          "No",
          "Yes",
          "5 days ago",
          "Yes, Severally",
          "Yes, i have used pain killers to relieve pains and some multi vitamins to improve my appetite",
          "XXX"};

  private static final String[] buttonB = {
          "Female",
          "I rarely sneeze",
          "Its sometime fine and sometime not",
          "Sometimes i  vomit while some times i don't",
          "Skin",
          "NO",
          "A lot of pain in the joints",
          "NO",
          "No",
          "Somehow",
          "Yes",
          "Three days ago",
          "Yes, once",
          "Yes, have used pain killers to relieve chest pain",
          "XXX"};

  private static final String[] buttonC = {
          "Male",
          "I do sneeze most of the times",
          "I have runny nose",
          "NO",
          "In the nose",
          "Sometimes i feel dizzy",
          "A little pain in the joints",
          "NO",
          "YES",
          "Yes, they are much watery",
          "Yes, in the nose",
          "Yesterday",
          "Am not sure",
          "No,i have just been trying to keep my body warm",
          "XXX" };

  private static final String[] buttonD = {
          "Female",
          "In rare occasions",
          "Just fine",
          "NO",
          "It is not itchy",
          "i sometimes feel lightheadedness",
          "NO",
          "NO, just a small rush with no sore",
          "NO",
          "I cant tell",
          "No",
          "Am just Fine",
          "No",
          "I did not use since i was fine",
          "XXX"};

  private static final String[] quiz = {
          "Select your gender",
          "How often do you sneeze?",
          "How is your mucus flow?",
          "Do you experience Nausea and vomiting",
          "Which body part is itchy?",
          "Do you experience Dizziness, lightheadedness or fainting",
          " Are your joints painful?",
          "Do you have body rush that forms sore?",
          "Do you experience difficulty in breathing?",
          "Are your eyes watery?",
          "Swelling of the lips, face, tongue and throat or other parts of the body?",
          "When did you started feeling sick",
          "Have you ever been diagnosed with Allergy?",
          "Have you uses any medicine since you started feeling unwell",
          "The Intelligent System has captured your information, click continue to allow for processing of the results"};
  ArrayList<String> arrayList;
  SharedPreferences preferences;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    //To make the window full screen
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    setContentView(R.layout.activity_disease);
    ActionBar actionBar = getSupportActionBar();
    assert actionBar != null;
    actionBar.hide();

    preferences = getSharedPreferences("Symptoms", MODE_PRIVATE);
    textSwitcher =  findViewById(R.id.textSwitcher);
    arrayList = new ArrayList<>();

    A =  findViewById(R.id.a);
    B =  findViewById(R.id.b);
    C =  findViewById(R.id.c);
    imageView=findViewById(R.id.hair);
    D =  findViewById(R.id.d);
    textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
      @Override
      public View makeView() {
        TextView textView = new TextView(getApplicationContext());
        textView.setTextSize(20);
        textView.setText("What is your age?");
        return textView;
      }
    });
    Animation animation= AnimationUtils.loadAnimation(Disease.this,R.anim.move_anim);
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
      redoTest("Food Allergy");
    } else if (old_value > 42 && old_value <= 50) {
      redoTest("Skin Allergy");
    } else if (old_value > 26 && old_value <= 42) {
      redoTest("Respiratory Allergy");
    } else {
      redoTest("No Allergy");
    }
  }

  @Override
  public void onBackPressed() {

    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Disease.this);

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
    Disease.this.finish();
  }

  private void redoTest(String old_value) {
    Intent intent = new Intent(Disease.this, Dataprocess.class);
    Bundle bundle = new Bundle();
    bundle.putString("old_value", old_value);
    bundle.putString("disease", "Allergy");
    intent.putExtras(bundle);
    startActivity(intent);
    Disease.this.finish();
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

