package com.imejadevs.diagnosis.Frags;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import com.imejadevs.diagnosis.Model.VideosDetails;
import com.imejadevs.diagnosis.R;
import com.imejadevs.diagnosis.VideoPlay;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Videos extends Fragment {
    RecyclerView recyclerView;
    ViewAdapter mAdapter;
    FirebaseFirestore firestoreDB;
    ProgressBar progressBar;


    public Videos() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_videos, container, false);
        firestoreDB = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.recycler);
        progressBar =view. findViewById(R.id.pb);
        progressBar.setVisibility(View.GONE);
        readData();
        return view;
    }

    private void readData() {
        progressBar.setVisibility(View.VISIBLE);
        firestoreDB.collection("Videos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<VideosDetails> notesList = new ArrayList<>();
                            progressBar.setVisibility(View.GONE);

                            for (DocumentSnapshot doc : task.getResult()) {
                                VideosDetails note = doc.toObject(VideosDetails.class);
                                note.setId(doc.getId());
                                notesList.add(note);
                            }

                            mAdapter = new ViewAdapter(notesList, getContext(), firestoreDB);
                            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(),2);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(mAdapter);
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    public class ViewAdapter extends RecyclerView.Adapter<Holder> {
        Context context;
        FirebaseFirestore firestoreDB;
        private List<VideosDetails> details;

        public ViewAdapter(List<VideosDetails> videosDetails, Context context, FirebaseFirestore firestoreDB) {
            this.details = videosDetails;
            this.context = context;
            this.firestoreDB = firestoreDB;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.videos, viewGroup, false);

            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final Holder viewHolder, int i) {
            final VideosDetails videosDetails = details.get(i);
            final String url=videosDetails.getMedia();
            String name=videosDetails.getDisease();
            viewHolder.textView.setText(name);
            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    videoData(url);
                }
            });




        }


        @Override
        public int getItemCount() {
            return details.size();
        }
    }

    private void videoData(String url) {

        Intent intent=new Intent(getContext(), VideoPlay.class);
        Bundle bundle=new Bundle();
        bundle.putString("video",url);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private class Holder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public Holder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.aa);
            textView=itemView.findViewById(R.id.bb);
        }
    }
}

