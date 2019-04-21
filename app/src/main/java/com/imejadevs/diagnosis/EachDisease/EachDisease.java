package com.imejadevs.diagnosis.EachDisease;

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
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.imejadevs.diagnosis.ProcessData.Dataprocess;
import com.imejadevs.diagnosis.R;

import java.util.ArrayList;

public class EachDisease extends AppCompatActivity {
  private TextSwitcher textSwitcher;
  private Button Prev, Next, A, B, C, D;
  private int pos = -1;
  private ImageView imageView;
  AlertDialog imeldaAD;

  private static final String[] buttonA = {
          "Male",
          "A day ago",
          "1",

          "Yes,exactly",
          "Yes,exactly",
          "Yes,totally thin",
          "Yes,my close relatives",
          "Yes,always",
          "At the back of the head",
          "Abruptly",
          "Yes",
          "Yes, like some blood thinners or antidepressants",
          "Yes, just the same",
          "Yes, just the same",
          "YYY"};
  private static final String[] buttonB = {
          "Female",
          "A week ago",
          "2",
          "Yes,Not exactly",
          "Yes,Not exactly",
          "Yes, but not much thin",
          "Yes, but not that close relatives",
          "Yes,Morning hours",
          "Close to the forehead",
          "Gradually",
          "Yes, but not as in the head",
          "Yes, like some blood thinners or antidepressants",
          "Yes, but slightly different",
          "Yes, but slightly different",
          "YYY"};
  private static final String[] buttonC = {
          "Male",
          "A month ago",
          "3",

          "No, Not as that one exactly",
          "No, Not as that one exactly",
          "No",
          "No",
          "Some days and some no itching",
          "Close to the Right ear",
          "Very slowly",
          "No",
          "Am not sure",
          "No,mine is different",
          "No,mine is different",
          "YYY"};
  private static final String[] buttonD = {
          "Female",
          "Over a period of time",
          "4",

          "Not, its very different",
          "Not, its very different",
          "Am not aware if it does",
          "Not sure if there is any",
          "No",
          "Close to the left ear",
          "Unnoticed",
          "I can tell",
          "Other infections",
          "No, it has not formed a patch",
          "No, it has not formed a patch",
          "YYY"};

  private static final String[] quiz = {
          "Select your gender",
          "When did the hair loss started",
          "Select a similar picture from the ones shown below",
          "Does your hair look similar to this picture?",
          "Does your hair look similar to this picture?",
          "Does it become thinner in the affected areas?",
          "Does any of your relatives affected by the same problem?",
          "Is your scalp itching?",
          "Where did the hair loss start?",
          "How did the hair loss develop over time",
          "Are there other parts of your body affected by hair loss?",
          "Among the medicine, is there any that can lead to hair loss?",
          "Do you have any patch like as shown below?",
          "Do you have any patch like as shown below?",
          "The Intelligent System has captured your information, click continue to allow for processing of the results"};
  ArrayList<String> arrayList;
  SharedPreferences preferences;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_each_disease);
    ActionBar actionBar = getSupportActionBar();
    assert actionBar != null;
    actionBar.hide();

    preferences = getSharedPreferences("Symptoms", MODE_PRIVATE);
    textSwitcher =  findViewById(R.id.textSwitcher);
    arrayList = new ArrayList<>();
    imageView =  findViewById(R.id.hair);
    imageView.setVisibility(View.INVISIBLE);
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
      redoTest("Type A of Hair Loss");
    } else if (old_value > 42 && old_value <= 50) {
      redoTest("Type B of Hair Loss");
    } else if (old_value > 26 && old_value <= 42) {
      redoTest("Type C of Hair Loss");
    } else {
      redoTest("No Hair Loss");
    }
  }

  @Override
  public void onBackPressed() {

    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(EachDisease.this);

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
    EachDisease.this.finish();
  }

  private void redoTest(String old_value) {
    Intent intent = new Intent(EachDisease.this, Dataprocess.class);
    Bundle bundle = new Bundle();
    bundle.putString("old_value", old_value);
    bundle.putString("disease", "Hair Loss");
    intent.putExtras(bundle);
    startActivity(intent);
    EachDisease.this.finish();
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
    checkImages();
  }

  private void checkImages() {

    if (pos == 2) {
      imageView.setVisibility(View.VISIBLE);
      imageView.setImageResource(R.drawable.pone);

    } else if (pos == 3) {
      imageView.setVisibility(View.VISIBLE);
      imageView.setImageResource(R.drawable.ptwo);

    } else if (pos == 4) {
      imageView.setVisibility(View.VISIBLE);
      imageView.setImageResource(R.drawable.pthree);

    } else if (pos == 12) {
      imageView.setVisibility(View.VISIBLE);
      imageView.setImageResource(R.drawable.pfour);

    } else if (pos == 13) {
      imageView.setVisibility(View.VISIBLE);
      imageView.setImageResource(R.drawable.pfive);

    } else {
      imageView.setVisibility(View.INVISIBLE);

    }
  }
}
