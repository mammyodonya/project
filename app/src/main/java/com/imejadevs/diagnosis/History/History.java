package com.imejadevs.diagnosis.History;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.imejadevs.diagnosis.Model.Hist;
import com.imejadevs.diagnosis.Model.Keepfit;
import com.imejadevs.diagnosis.R;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class History extends AppCompatActivity {
    ActionBar actionBar;
    private FirebaseAuth mAuth;
    RecyclerView recyclerView;
    ViewAdapter mAdapter;
    FirebaseFirestore firestoreDB;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        /*Back arrow*/
        actionBar = getSupportActionBar();
        actionBar.setTitle("History");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        firestoreDB = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recycler);
        progressBar = findViewById(R.id.pb);
        progressBar.setVisibility(View.GONE);

        readData();
    }

    private void readData() {

        progressBar.setVisibility(View.VISIBLE);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String email = currentUser.getEmail();
            firestoreDB.collection(email)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                List<Hist> notesList = new ArrayList<>();
                                progressBar.setVisibility(View.GONE);

                                for (DocumentSnapshot doc : task.getResult()) {
                                    Hist note = doc.toObject(Hist.class);
                                    notesList.add(note);
                                }

                                mAdapter = new ViewAdapter(notesList, getApplicationContext(), firestoreDB);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(mAdapter);
addAnimation(recyclerView);
                            } else {
                                Log.d("TAG", "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }

    }
    public class ViewAdapter extends RecyclerView.Adapter<Holder> {
        Context context;
        FirebaseFirestore firestoreDB;
        private List<Hist> details;

        public ViewAdapter(List<Hist> videosDetails, Context context, FirebaseFirestore firestoreDB) {
            this.details = videosDetails;
            this.context = context;
            this.firestoreDB = firestoreDB;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history, viewGroup, false);

            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final Holder viewHolder, int i) {
            final Hist videosDetails = details.get(i);
            final String disease,level,date,time,email,recommendation;
            disease=videosDetails.getDisease();
            level=videosDetails.getLevel();
            date=videosDetails.getDate();
            time=videosDetails.getTime();
            recommendation=videosDetails.getRecommendation();
            email=videosDetails.getEmail();
            viewHolder.textView.setText(date);
            viewHolder.test.setText(disease);
            viewHolder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDisplay(disease,level,date,time,email,recommendation);
                }
            });

        }


        @Override
        public int getItemCount() {
            return details.size();
        }
    }

    private void openDisplay(String disease, String level, String date,
                             String time, String email, String recommendation) {
        Intent intent=new Intent(History.this,DisplayHistory.class);
        Bundle bundle=new Bundle();
        bundle.putString("disease",disease);
        bundle.putString("level",level);
        bundle.putString("date",date);
        bundle.putString("time",time);
        bundle.putString("email",email);
        bundle.putString("recommendation",recommendation);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    private class Holder extends RecyclerView.ViewHolder {

        TextView textView,test;
        public Holder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.bbb);
            test=itemView.findViewById(R.id.ccc);
        }
    }
    private void addAnimation(RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.right_layout);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();

    }

        @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
