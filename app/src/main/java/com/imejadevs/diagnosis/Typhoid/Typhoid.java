package com.imejadevs.diagnosis.Typhoid;

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

public class Typhoid extends AppCompatActivity {
  private TextSwitcher textSwitcher;
  private Button Prev, Next, A, B, C, D;
  private int pos = -1;
  private ImageView imageView;

  private static final String[] buttonA = {
          "Male",
          "Yes, mostly at centre of the head and its too much",
          "Two days ago",
          "Yes",
          "Yes i do",
          "Yes",
          "Poor",
          "high fever",
          "A lot pains in the muscles and joints",
          "Yes,somehow",
          "Profuse sweating",
          "Yes, the whole body becomes tired and i am so forgetful",
          "Yes",
          "Yes, whole body is painful more so joints",
          "XXX"};

  private static final String[] buttonB = {
          "Female",
          "Yes, but not too much",
          "Yesterday",
          "Yes, but sometimes i just become alright",
          "Yes, but for a short while",
          "Sometimes i  vomit while some times i don't",
          "Not that good",
          "Moderate fever",
          "Somehow, in the joints",
          "NO",
          "Moderate sweating",
          "Yes, but not that much",
          "Yes",
          "sometimes in some body parts",
          "XXX"};

  private static final String[] buttonC = {
          "Male",
          "Very minimal",
          "Today",
          "I can not tell",
          "Only after eating and not too much",
          "NO",
          "Normal",
          "Changes regularly with the environment",
          "A little pain in the joints",
          "No",
          "Very low sweating",
          "Minimal",
          "Maybe",
          "In rare occasions",
          "XXX" };

  private static final String[] buttonD = {
          "Female",
          "No",
          "I do not have headache",
          "No, i just feel normal",
          "From far",
          "NO",
          "High appetite",
          "Normal",
          "somehow week",
          "No, but i just pass some few stool",
          "When in high temperature areas",
          "No",
          "Am not sure",
          "Only when the body is uncomfortable",
          "XXX"};

  private static final String[] quiz = {
          "Select your gender",
          "Do you feel head ache?",
          "When did you start feeling headache?",
          "Do you feel constipated?",
          "Do you experience Nausea?",
          "Do you experience nausea with vomiting?",
          "How is your appetite towards food?",
          "How is your body temperatures?",
          "How are your muscles?",
          "Are you diarrhearing?",
          "Do you experience sweating?",
          "Do you experience tiredness and confusion?",
          "Have you suffered from typhoid before?",
          "Do you have generalised aches and pains?",
          "The Intelligent System has captured your information, click continue to allow for processing of the results"};
  ArrayList<String> arrayList;
  SharedPreferences preferences;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //To make the window full screen
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_typhoid); ActionBar actionBar = getSupportActionBar();
    assert actionBar != null;
    actionBar.hide();

    preferences = getSharedPreferences("Symptoms", MODE_PRIVATE);
    textSwitcher = (TextSwitcher) findViewById(R.id.textSwitcher);
    arrayList = new ArrayList<>();

    A = (Button) findViewById(R.id.a);
    B = (Button) findViewById(R.id.b);
    C = (Button) findViewById(R.id.c);
    D = (Button) findViewById(R.id.d);
    imageView=findViewById(R.id.hair);
    textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
      @Override
      public View makeView() {
        TextView textView = new TextView(getApplicationContext());
        textView.setTextSize(20);
        textView.setText("What is your age?");
        return textView;
      }
    });
    Animation animation= AnimationUtils.loadAnimation(Typhoid.this,R.anim.move_anim);
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
      redoTest("High Typhoid");
    } else if (old_value > 42 && old_value <= 50) {
      redoTest("Moderate Typhoid");
    } else if (old_value > 26 && old_value <= 42) {
      redoTest("Low Typhoid");
    } else {
      redoTest("No Typhoid");
    }
  }

  @Override
  public void onBackPressed() {

    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Typhoid.this);

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
    Typhoid.this.finish();
  }

  private void redoTest(String old_value) {
    Intent intent = new Intent(Typhoid.this, Dataprocess.class);
    Bundle bundle = new Bundle();
    bundle.putString("old_value", old_value);
    bundle.putString("disease", "Typhoid");
    intent.putExtras(bundle);
    startActivity(intent);
    Typhoid.this.finish();
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

