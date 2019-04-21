package com.imejadevs.diagnosis.ProcessData;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.imejadevs.diagnosis.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Dataprocess extends AppCompatActivity {
    Animation animation;
    CardView cardView;
    ProgressBar progressBar;
    RelativeLayout linearLayout;
//    FirebaseDatabase database;
//    DatabaseReference reference;
    FirebaseFirestore firebaseStorage;
    private int progressStatus = 0;
    private Handler handler;
    private TextView textView, Tap, Large_Text, Im;
    String disease,old_value;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dataprocess);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        old_value = getIntent().getStringExtra("old_value");
        disease= getIntent().getStringExtra("disease");
        progressBar =  findViewById(R.id.progress);
        handler = new Handler();

        mAuth = FirebaseAuth.getInstance();
        cardView =  findViewById(R.id.cv_add);
        textView =  findViewById(R.id.percent);
        Tap =  findViewById(R.id.asdf);
        Large_Text =  findViewById(R.id.large_text);
        Im =  findViewById(R.id.im);
        Im.setText(old_value);
        firebaseStorage = FirebaseFirestore.getInstance();
        addVisit();

        linearLayout = (RelativeLayout) findViewById(R.id.inv);
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1;
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {


                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void run() {
                            progressBar.setProgress(progressStatus);
                            textView.setText(progressStatus + "%");

                            if (progressStatus == 10) {
                                Tap.setText("Reviewing Symptoms");
                            } else if (progressStatus == 20) {
                                Tap.setText("Comparing symptoms");
                            } else if (progressStatus == 30) {
                                Tap.setText("Matching symptoms");
                            } else if (progressStatus == 40) {
                                Tap.setText("Analyzing results");
                            } else if (progressStatus == 50) {
                                Tap.setText("Loading");
                            } else if (progressStatus == 60) {
                                Tap.setText("Updating");
                            } else if (progressStatus == 70) {
                                Tap.setText("Loading");
                            } else if (progressStatus == 80) {
                                Tap.setText("Evaluating results");
                            } else if (progressStatus == 90) {
                                Tap.setText("Finalizing");
                            } else if (progressStatus == 100) {
                                  animateRevealClose();
                            }
                        }
                    });
                }
            }
        }).start();
        if (disease.equalsIgnoreCase("Hair Loss")) {
            universalData("Hair Loss", old_value);
        }
        if (disease.equalsIgnoreCase("Malaria")) {
            universalData("Malaria" ,old_value);
        }
        if (disease.equalsIgnoreCase("Allergy")) {
            universalData("Allergy/",old_value);
        }
        if (disease.equalsIgnoreCase("Anaemia")) {
            universalData("Anaemia", old_value);
        }
        if (disease.equalsIgnoreCase("Asthma")) {
            universalData("Asthma" ,old_value);
        }
        if (disease.equalsIgnoreCase("Cholera")) {
            universalData("Cholera", old_value);
        }
        if (disease.equalsIgnoreCase("CommonCold")) {
            universalData("CommonCold", old_value);
        }
        if (disease.equalsIgnoreCase("Pneumonia")) {
            universalData("Pneumonia", old_value);
        }
        if (disease.equalsIgnoreCase("TB")) {
            universalData("TB" ,old_value);
        }
        if (disease.equalsIgnoreCase("Typhoid")) {
            universalData("Typhoid" , old_value);
        }
    }
    private void addVisit() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            final String email = currentUser.getEmail();
            DocumentReference docRef = firebaseStorage.collection("User Profile").document(email);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null) {
                            String visits = task.getResult().getData().get("visits").toString();
                            updateVisits(visits,email);
                        }
                    }
                }
            });
        }

    }

    private void updateVisits(String visits, final String email) {
        int number_of_visits= Integer.parseInt(visits);
        int final_value=number_of_visits+1;
        Map<String, Object> user = new HashMap<>();
        user.put("visits", final_value);
        firebaseStorage.collection("User Profile").document(email)
                .update(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
saveUserHistory(email);
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

    private void saveUserHistory(String email) {

        if (old_value.equalsIgnoreCase("High Typhoid")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you to call your emergency number before the situation worsens, and when you feel better, try the tips in keep fit module to prevent more complications.");
        }
        if (old_value.equalsIgnoreCase("Moderate Typhoid")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you to see a doctor as early as now, and make good use of the tips in the keep fit module to prevent more complications.");
        }
        if (old_value.equalsIgnoreCase("Low Typhoid")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you seek help from external sources and make good use of the tips in keep feet module.");
        }
        if (old_value.equalsIgnoreCase("No Typhoid")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you to apply the necessary preventive measures and make good use of the tips in Keep fit module.");
        }
        if (old_value.equalsIgnoreCase("High Malaria")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you to call your emergency number before the situation worsens, and when you feel better, try the tips in keep fit module to prevent more complications.");
        }
        if (old_value.equalsIgnoreCase("Moderate Malaria")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you to see a doctor as early as now, and make good use of the tips in the keep fit module to prevent more complications.");
        }
        if (old_value.equalsIgnoreCase("Low Malaria")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you seek help from external sources and make good use of the tips in keep feet module.");
        }
        if (old_value.equalsIgnoreCase("No Malaria")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you to apply the necessary preventive measures and make good use of the tips in Keep fit module.");
        }
        if (old_value.equalsIgnoreCase("High Asthma")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you to call your emergency number before the situation worsens, and when you feel better, try the tips in keep fit module to prevent more complications.");
        }
        if (old_value.equalsIgnoreCase("Moderate Asthma")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you to see a doctor as early as now, and make good use of the tips in the keep fit module to prevent more complications.");
        }
        if (old_value.equalsIgnoreCase("Low Asthma")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you seek help from external sources and make good use of the tips in keep feet module.");
        }
        if (old_value.equalsIgnoreCase("No Asthma")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you to apply the necessary preventive measures and make good use of the tips in Keep fit module.");
        }
        if (old_value.equalsIgnoreCase("High Pneumonia")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you to call your emergency number before the situation worsens, and when you feel better, try the tips in keep fit module to prevent more complications.");
        }
        if (old_value.equalsIgnoreCase("Moderate Pneumonia")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you to see a doctor as early as now, and make good use of the tips in the keep fit module to prevent more complications.");
        }
        if (old_value.equalsIgnoreCase("Low Pneumonia")){
            saveHistory(email,disease,old_value,"You need to see a real doctor for more medication");
        }
        if (old_value.equalsIgnoreCase("No Pneumonia")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you to apply the necessary preventive measures and make good use of the tips in Keep fit module.");
        }
        if (old_value.equalsIgnoreCase("High Cholera")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you to call your emergency number before the situation worsens, and when you feel better, try the tips in keep fit module to prevent more complications.");
        }
        if (old_value.equalsIgnoreCase("Moderate Cholera")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you to see a doctor as early as now, and make good use of the tips in the keep fit module to prevent more complications.");
        }
        if (old_value.equalsIgnoreCase("Low Cholera")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you seek help from external sources and make good use of the tips in keep feet module.");
        }
        if (old_value.equalsIgnoreCase("No Cholera")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you to apply the necessary preventive measures and make good use of the tips in Keep fit module.");
        }
        if (old_value.equalsIgnoreCase("Food Allergy")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you to call your emergency number before the situation worsens, and when you feel better, try the tips in keep fit module to prevent more complications.");
        }
        if (old_value.equalsIgnoreCase("Skin Allergy")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you to see a doctor as early as now, and make good use of the tips in the keep fit module to prevent more complications.");
        }
        if (old_value.equalsIgnoreCase("Respiratory Allergy")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you seek help from external sources and make good use of the tips in keep feet module.");
        }
        if (old_value.equalsIgnoreCase("No Allergy")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you to apply the necessary preventive measures and make good use of the tips in Keep fit module.");
        }
        if (old_value.equalsIgnoreCase("High Anaemia")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you to call your emergency number before the situation worsens, and when you feel better, try the tips in keep fit module to prevent more complications.");
        }
        if (old_value.equalsIgnoreCase("Moderate Anaemia")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you to see a doctor as early as now, and make good use of the tips in the keep fit module to prevent more complications.");
        }
        if (old_value.equalsIgnoreCase("Low Anaemia")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you seek help from external sources and make good use of the tips in keep feet module");
        }
        if (old_value.equalsIgnoreCase("No Anaemia")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you to apply the necessary preventive measures and make good use of the tips in Keep fit module.");
        }
        if (old_value.equalsIgnoreCase("High commonCold")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you to call your emergency number before the situation worsens, and when you feel better, try the tips in keep fit module to prevent more complications.");
        }
        if (old_value.equalsIgnoreCase("Moderate CommonCold")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you to see a doctor as early as now, and make good use of the tips in the keep fit module to prevent more complications.");
        }
        if (old_value.equalsIgnoreCase("Low CommonCold")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you seek help from external sources and make good use of the tips in keep feet module.");
        }
        if (old_value.equalsIgnoreCase("No CommonCold")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you to apply the necessary preventive measures and make good use of the tips in Keep fit module.");
        }
        if (old_value.equalsIgnoreCase("Pulmonary TB")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you to call your emergency number before the situation worsens, and when you feel better, try the tips in keep fit module to prevent more complications.");
        }
        if (old_value.equalsIgnoreCase("Extrapulmonary TB")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you to see a doctor as early as now, and make good use of the tips in the keep fit module to prevent more complications.");
        }
        if (old_value.equalsIgnoreCase("Low TB")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you seek help from external sources and make good use of the tips in keep feet module.");
        }
        if (old_value.equalsIgnoreCase("No TB")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you to apply the necessary preventive measures and make good use of the tips in Keep fit module.");
        }
        if (old_value.equalsIgnoreCase("Type A of Hair Loss")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you to call your emergency number before the situation worsens, and when you feel better, try the tips in keep fit module to prevent more complications.");
        }
        if (old_value.equalsIgnoreCase("Type B of Hair Loss")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you to see a doctor as early as now, and make good use of the tips in the keep fit module to prevent more complications.");
        }
        if (old_value.equalsIgnoreCase("Type C of Hair Loss")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you seek help from external sources and make good use of the tips in keep feet module.");
        }
        if (old_value.equalsIgnoreCase("No Hair Loss ")){
            saveHistory(email,disease,old_value,"Based on the information you got after the diagnosis, We recommend you to apply the necessary preventive measures and make good use of the tips in Keep fit module.");
        }

    }

    private void universalData(String type,String phone) {
        DocumentReference docRef = firebaseStorage.collection(type).document(phone);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        String odonya = task.getResult().getData().get("disease").toString();
                        String history = task.getResult().getData().get("history").toString();
                        Large_Text.setText(""+odonya+"\n\n"+history);

                   }
                } else {
                    Toast.makeText(Dataprocess.this, "Error..!!\n" + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void animateRevealClose () {
            Animator mAnimator = ViewAnimationUtils.createCircularReveal(cardView, cardView.getWidth() / 2, 0, cardView.getHeight(), cardView.getWidth() / 2);
            mAnimator.setDuration(500);
            mAnimator.setInterpolator(new AccelerateInterpolator());
            mAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    cardView.setVisibility(View.INVISIBLE);
                    super.onAnimationEnd(animation);
                    animateRevealShow();
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                }
            });
            mAnimator.start();
        }


        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void animateRevealShow () {
            Animator mAnimator = ViewAnimationUtils.createCircularReveal(cardView, cardView.getWidth() / 2, 0, cardView.getWidth() / 2, cardView.getHeight());
            mAnimator.setDuration(500);
            mAnimator.setInterpolator(new AccelerateInterpolator());
            mAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    cardView.setVisibility(View.VISIBLE);
                    refineData();
                    super.onAnimationStart(animation);
                }
            });
            mAnimator.start();
        }

        private void refineData () {
            progressBar.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            Tap.setVisibility(View.GONE);

        }

        public void repeatTest (View view){
            SharedPreferences preferences = getSharedPreferences("Symptoms", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("user", 0);
            editor.commit();
            Dataprocess.this.finish();

        }

    private void saveHistory(String email,String disease, String level, String recommendation) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat timeFormat = new SimpleDateFormat("HH: mm: ss");
        Date date = new Date();
        Date time = new Date();
        final String a = dateFormat.format(date);
        final String b = timeFormat.format(time);
        Map<String, Object> user = new HashMap<>();
        user.put("disease", disease);
        user.put("level", level);
        user.put("date", a);
        user.put("time", b);
        user.put("email", email);
        user.put("recommendation", recommendation);

        firebaseStorage.collection(email)
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);

                    }
                });
    }
}
