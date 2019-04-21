package com.imejadevs.diagnosis.Frags;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.imejadevs.diagnosis.Model.Keepfit;
import com.imejadevs.diagnosis.Model.VideosDetails;
import com.imejadevs.diagnosis.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fitness extends Fragment {
    RecyclerView recyclerView;
    ViewAdapter mAdapter;
    FirebaseFirestore firestoreDB;
    ProgressBar progressBar;

    public Fitness() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fitness, container, false);
        firestoreDB = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.recycler);
        progressBar =view. findViewById(R.id.pb);
        progressBar.setVisibility(View.GONE);
        readData();
        return view;
    }

    private void readData() {
        progressBar.setVisibility(View.VISIBLE);
        firestoreDB.collection("Body fit")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Keepfit> notesList = new ArrayList<>();
                            progressBar.setVisibility(View.GONE);

                            for (DocumentSnapshot doc : task.getResult()) {
                                Keepfit note = doc.toObject(Keepfit.class);
                                notesList.add(note);
                            }

                            mAdapter = new ViewAdapter(notesList, getContext(), firestoreDB);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
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

    private void addAnimation(RecyclerView recyclerView) {
            final Context context = recyclerView.getContext();
            final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.bottom_layout);
            recyclerView.setLayoutAnimation(controller);
            recyclerView.getAdapter().notifyDataSetChanged();
            recyclerView.scheduleLayoutAnimation();

    }

    public class ViewAdapter extends RecyclerView.Adapter<Holder> {
        Context context;
        FirebaseFirestore firestoreDB;
        private List<Keepfit> details;

        public ViewAdapter(List<Keepfit> videosDetails, Context context, FirebaseFirestore firestoreDB) {
            this.details = videosDetails;
            this.context = context;
            this.firestoreDB = firestoreDB;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.keepfit, viewGroup, false);

            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final Holder viewHolder, int i) {
            final Keepfit videosDetails = details.get(i);

            String name=videosDetails.getTip();
            viewHolder.textView.setText(name);

        }


        @Override
        public int getItemCount() {
            return details.size();
        }
    }


    private class Holder extends RecyclerView.ViewHolder {

        TextView textView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.bbb);
        }
    }

}
