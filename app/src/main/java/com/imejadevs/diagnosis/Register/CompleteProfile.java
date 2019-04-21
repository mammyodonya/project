package com.imejadevs.diagnosis.Register;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
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

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.imejadevs.diagnosis.Main.DrawerActivity;
import com.imejadevs.diagnosis.Model.UserModel;
import com.imejadevs.diagnosis.R;

import java.util.HashMap;
import java.util.Map;

public class CompleteProfile extends AppCompatActivity {
    FloatingActionButton fab;
    CardView cvAdd;
    String Firstname, Lastname, Phone, Location,email,Height,Weight;
    EditText A, B, C, D,E,F;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_complete_profile);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;

        firebaseFirestore= FirebaseFirestore.getInstance();

        actionBar.hide();
        mAuth = FirebaseAuth.getInstance();

        A =  findViewById(R.id.aa);
        B =  findViewById(R.id.bb);
        C =  findViewById(R.id.cc);
        D =  findViewById(R.id.dd);
        E =  findViewById(R.id.ee);
        F =  findViewById(R.id.ff);

        fab =  findViewById(R.id.fab);

        cvAdd =  findViewById(R.id.cv_add);

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
                CompleteProfile.super.onBackPressed();
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


    public void clickRegister(View view) {
        Firstname = A.getText().toString();
        Lastname = B.getText().toString();
        Phone = C.getText().toString();
        Location = D.getText().toString();
        Height= E.getText().toString();
        Weight =F.getText().toString();
        if (Firstname.isEmpty()) {
            A.setError("Enter your Firstname");
            A.requestFocus();
        } else if (Lastname.isEmpty()) {
            B.setError("Enter your Lastname");
            B.requestFocus();
        } else if (Phone.isEmpty()) {
            C.setError("Enter your Phone");
            C.requestFocus();
        } else if (Location.isEmpty()) {
            D.setError("Enter your Location");
            D.requestFocus();
        }
        else if (Height.isEmpty()) {
            E.setError("Enter your height");
            E.requestFocus();
        }
        else if (Weight.isEmpty()) {
            F.setError("Enter your Weight");
            F.requestFocus();
        }else {
            if (validPhone(Phone)) {
//                saveUserSqlite(Firstname, Lastname, Phone, Location,Height,Weight);
                savetoFirestore(Firstname, Lastname, Phone, Location,Height,Weight);
            } else {
                Toast.makeText(this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void savetoFirestore(String firstname, String lastname, String phone, String location, String height, String weight) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null) {
            email = currentUser.getEmail();
            Map<String, Object> user = new HashMap<>();
            user.put("firstname", firstname);
            user.put("lastname", lastname);
            user.put("phone", phone);
            user.put("location", location);
            user.put("height", height);
            user.put("email", email);
            user.put("weight", weight);
            user.put("image", "image");
            user.put("visits", "0");
            user.put("diagnosis", "0");
            user.put("messages", "0");

            firebaseFirestore.collection("User Profile").document(email)
                    .set(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(CompleteProfile.this, "Successfully Updated", Toast.LENGTH_SHORT).show();

                            Intent intent=new Intent(CompleteProfile.this,DrawerActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            }).addOnCanceledListener(new OnCanceledListener() {
                @Override
                public void onCanceled() {

                }
            });
        }
    }





    private boolean validPhone(String phone) {

        return phone.length() == 10 ;//&& phone.length() < 11;
    }
}
