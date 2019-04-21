package com.imejadevs.diagnosis;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.imejadevs.diagnosis.Main.DrawerActivity;
import com.imejadevs.diagnosis.Register.Registartion;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;


    private EditText Email, Pass, Conpass;

    String email, password, conpass;

    private TextView reg;

    private FirebaseAuth mAuth;

    private String TAG = "Registartion";

    private SweetAlertDialog sweetAlertDialog;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {

            MainActivity.this.finish();
            startActivity(new Intent(MainActivity.this, DrawerActivity.class));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;

        actionBar.hide();

        mAuth = FirebaseAuth.getInstance();

        Email = (EditText) findViewById(R.id.et_username);

        Pass = (EditText) findViewById(R.id.et_password);

        reg= (TextView)findViewById(R.id.Reg);

        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    public void clickResetPassword(View view) {
        String email = Email.getText().toString();
        if (validEmail(email)) {
            sendResetLink(email);
        } else {
            Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendResetLink(final String email) {
        sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitleText("Reset Password");
        sweetAlertDialog.setContentText("Please wait...");
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.show();
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                sweetAlertDialog.changeAlertType(SweetAlertDialog.BUTTON_CONFIRM);
                sweetAlertDialog.setTitleText("Password Recovery");
                sweetAlertDialog.setContentText("Sent email link to "+ email);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                sweetAlertDialog.setTitleText("Failed");
                sweetAlertDialog.setContentText("Failed to send email to "+ email);

            }
        });


    }

    public void clickLogin(View view) {
        email = Email.getText().toString();
        password = Pass.getText().toString();
        if (email.isEmpty()) {
            Email.setError("Enter Email Address");
            Email.requestFocus();
        } else if (password.isEmpty()) {
            Pass.setError("Enter Password");
            Pass.requestFocus();
        } else {
            if (!validEmail(email)) {

                Email.setError("Invali Email Address");

                Email.requestFocus();

            } else {
                if (!validPassword(password)) {

                    Pass.setError("Password length should be more than 6 characters");
                    Pass.requestFocus();

                } else {

                    proceedToAuthenticate(email, password);

                }
            }
        }
    }

    private void proceedToAuthenticate(String email, String password) {
        sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitleText("Authenticating");
        sweetAlertDialog.setContentText("Please wait...");
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            sweetAlertDialog.dismissWithAnimation();
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            sweetAlertDialog.dismissWithAnimation();
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void clickRegisterLayout(View view) {
        getWindow().setExitTransition(null);
        getWindow().setEnterTransition(null);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
            startActivityForResult(new Intent(this, Registartion.class), 100, options.toBundle());
        } else {
            startActivityForResult(new Intent(this, Registartion.class), 100);
        }
    }

    public void clickReg(View view) {
        Intent intent= new Intent(MainActivity.this,Registartion.class);
        startActivity(intent);
    }
}
