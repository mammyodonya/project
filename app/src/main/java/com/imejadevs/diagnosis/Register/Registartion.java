package com.imejadevs.diagnosis.Register;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.imejadevs.diagnosis.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Registartion extends AppCompatActivity {

  FloatingActionButton fab;

  CardView cvAdd;

  private EditText Email, Pass, Conpass;

  String email, password, conpass;

  private FirebaseAuth mAuth;

  private String TAG = "Registartion";

  private SweetAlertDialog sweetAlertDialog;

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  @Override
  public void onStart() {
    super.onStart();
    // Check if user is signed in (non-null) and update UI accordingly.
    FirebaseUser currentUser = mAuth.getCurrentUser();
    updateUI(currentUser);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  private void updateUI(FirebaseUser currentUser) {

    if (currentUser!=null) {

      getWindow().setExitTransition(null);
      getWindow().setEnterTransition(null);

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
        startActivityForResult(new Intent(this, CompleteProfile.class), 100, options.toBundle());
      } else {
        startActivityForResult(new Intent(this, CompleteProfile.class), 100);
      }
    }

  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    setContentView(R.layout.activity_registartion);

    ActionBar actionBar = getSupportActionBar();

    assert actionBar != null;

    actionBar.hide();


    sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);

    sweetAlertDialog.setTitleText("Creating Account");

    sweetAlertDialog.setContentText("Please wait...");

    sweetAlertDialog.setCancelable(false);

    mAuth = FirebaseAuth.getInstance();

    Email = (EditText) findViewById(R.id.et_username);

    Pass = (EditText) findViewById(R.id.et_password);

    Conpass = (EditText) findViewById(R.id.et_repeatpassword);

    fab = (FloatingActionButton) findViewById(R.id.fab);

    cvAdd = (CardView) findViewById(R.id.cv_add);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      ShowEnterAnimation();
    }
    fab.setOnClickListener(new View.OnClickListener() {
      @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
      @Override
      public void onClick(View v) {
        animateRevealClose();

      }
    });
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  private void ShowEnterAnimation() {
    Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
    getWindow().setSharedElementEnterTransition(transition);

    transition.addListener(new Transition.TransitionListener() {
      @Override
      public void onTransitionStart(Transition transition) {
        cvAdd.setVisibility(View.GONE);
      }

      @Override
      public void onTransitionEnd(Transition transition) {
        transition.removeListener(this);
        animateRevealShow();
      }

      @Override
      public void onTransitionCancel(Transition transition) {

      }

      @Override
      public void onTransitionPause(Transition transition) {

      }

      @Override
      public void onTransitionResume(Transition transition) {

      }


    });
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public void animateRevealShow() {
    Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, fab.getWidth() / 2, cvAdd.getHeight());
    mAnimator.setDuration(500);
    mAnimator.setInterpolator(new AccelerateInterpolator());
    mAnimator.addListener(new AnimatorListenerAdapter() {
      @Override
      public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
      }

      @Override
      public void onAnimationStart(Animator animation) {
        cvAdd.setVisibility(View.VISIBLE);
        super.onAnimationStart(animation);
      }
    });
    mAnimator.start();
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public void animateRevealClose() {
    Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, cvAdd.getHeight(), fab.getWidth() / 2);
    mAnimator.setDuration(500);
    mAnimator.setInterpolator(new AccelerateInterpolator());
    mAnimator.addListener(new AnimatorListenerAdapter() {
      @Override
      public void onAnimationEnd(Animator animation) {
        cvAdd.setVisibility(View.INVISIBLE);
        super.onAnimationEnd(animation);
        fab.setImageResource(R.drawable.ic_signup);
        Registartion.super.onBackPressed();
      }

      @Override
      public void onAnimationStart(Animator animation) {
        super.onAnimationStart(animation);
      }
    });
    mAnimator.start();
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  @Override
  public void onBackPressed() {
    animateRevealClose();
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public void clickRegister(View view) {

    email = Email.getText().toString();
    password = Pass.getText().toString();
    conpass = Conpass.getText().toString();
    if (email.isEmpty()) {
      Email.setError("Enter Email Address");

      Email.requestFocus();
    } else if (password.isEmpty()) {
      Pass.setError("Enter Password");
      Pass.requestFocus();
    } else if (conpass.isEmpty()) {
      Conpass.setError("Confirm Password");

      Conpass.requestFocus();
    } else {
      if (!validEmail(email)) {

        Email.setError("Invali Email Address");

        Email.requestFocus();

      } else {
        if (!validPassword(password)) {

          Pass.setError("Password length should be more than 6");
          Pass.requestFocus();

        } else {
          if (password.equalsIgnoreCase(conpass)) {

            proceedToAuthenticate(email, password);

          } else {
            Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
          }
        }
      }
    }
  }

  private void proceedToAuthenticate(String email, String password) {

    mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

              @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                  // Sign in success, update UI with the signed-in user's information
                  Log.d(TAG, "createUserWithEmail:success");

                  FirebaseUser user = mAuth.getCurrentUser();
                  updateUI(user);
                } else {
                  // If sign in fails, display a message to the user.
                  Log.w(TAG, "createUserWithEmail:failure", task.getException());
                  Toast.makeText(Registartion.this, "Authentication failed."+task.getException(),
                          Toast.LENGTH_SHORT).show();
                  updateUI(null);
                }

                // ...
              }
            });

  }


  private boolean validPassword(String password) {

    return password.length() > 6;
  }

  private boolean validEmail(String email) {


    Pattern pattern;

    Matcher matcher;

    final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    pattern = Pattern.compile(EMAIL_PATTERN);

    matcher = pattern.matcher(email);

    return matcher.matches();
  }
}
