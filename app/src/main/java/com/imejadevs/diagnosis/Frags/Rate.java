package com.imejadevs.diagnosis.Frags;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.imejadevs.diagnosis.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Rate extends Fragment {
    TextView text1, mRatingScale;
    Button mSendFeedback;
    EditText mFeedback ;
    RatingBar ratingBar;

    public Rate() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rate, container, false);
        text1 = view.findViewById(R.id.tvRatingScale);
        ratingBar = view.findViewById(R.id.ratingBar);
        mSendFeedback = view.findViewById(R.id.send);
        mFeedback= view.findViewById(R.id.feedback);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                switch ((int) ratingBar.getRating()) {
                    case 1:
                        text1.setText("Very bad");
                        break;
                    case 2:
                        text1.setText("Need some improvement");
                        break;
                    case 3:
                        text1.setText("Good");
                        break;
                    case 4:
                        text1.setText("Great");
                        break;
                    case 5:
                        text1.setText("Awesome. I love it");
                        break;
                    default:
                        text1.setText("");
                }
            }
        });

        mSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFeedback.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please Give feedback", Toast.LENGTH_SHORT).show();
                } else {
                    mFeedback.setText("");
                    ratingBar.setRating(0);
                    Toast.makeText(getContext(), "Thank you for sharing your feedback", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

}