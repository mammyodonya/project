package com.imejadevs.diagnosis.Frags;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.imejadevs.diagnosis.Allergy.Disease;
import com.imejadevs.diagnosis.Anaemia.Anaemia;
import com.imejadevs.diagnosis.Asthma.D_Asthma;
import com.imejadevs.diagnosis.Common_cold.CommonCold;
import com.imejadevs.diagnosis.EachDisease.EachDisease;
import com.imejadevs.diagnosis.Malaria.Malaria;
import com.imejadevs.diagnosis.Pneumonia.Pneumonia;
import com.imejadevs.diagnosis.R;
import com.imejadevs.diagnosis.TB.TB;
import com.imejadevs.diagnosis.Typhoid.Typhoid;
import com.imejadevs.diagnosis.cholera.D_cholera;

/**
 * A simple {@link Fragment} subclass.
 */
public class PHR extends Fragment {
    private ListView listView;
    String[] values = new String[]{"Malaria Test", "Tuberculosis Test", "Typhoid Test",
            "Hair Loss", "Asthma", "pneumonia Disease", "Cholera","Allergy","Anaemia","Common colds"};
    int[] images = {
            R.drawable.malaria,
            R.drawable.tuber,
            R.drawable.typhoid,
            R.drawable.hair,
            R.drawable.asma,
            R.drawable.pneu,
            R.drawable.typhy,
            R.drawable.cholera,
            R.drawable.naemia,
            R.drawable.commoncold};


    public PHR() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_phr, container, false);

        listView =  view.findViewById(R.id.list);
        MyAdapter adapter = new MyAdapter(getContext(), values, images);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(getContext(),Malaria.class));
                        break;
                    case 1:
                        startActivity(new Intent(getContext(), TB.class));
                        break;
                    case 2:
                        startActivity(new Intent(getContext(), Typhoid.class));
                        break;
                    case 3:

                        startActivity(new Intent(getContext(),EachDisease.class));
                        break;
                    case 4:
                        startActivity(new Intent(getContext(),D_Asthma.class));
                        break;
                    case 5:
                        startActivity(new Intent(getContext(),Pneumonia.class));
                        break;
                    case 6:
                        startActivity(new Intent(getContext(), D_cholera.class));
                        break;
                    case 7:
                        startActivity(new Intent(getContext(),Disease.class));
                        break;
                    case 8:
                        startActivity(new Intent(getContext(),Anaemia.class));
                        break;
                    case 9:
                        startActivity(new Intent(getContext(),CommonCold.class));
                        break;
                    default:
                        break;
                }
            }
        });
        return view;
    }

    private class MyAdapter extends ArrayAdapter {
        Context context;
        String[] values;
        int[] imageArray;

        public MyAdapter(Context context, String[] values, int[] imageArray) {
            super(context, R.layout.phr, R.id.bb, values);
            this.context = context;
            this.values = values;
            this.imageArray = imageArray;
        }

        @NonNull
        @Override
        public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.phr, parent, false);
            ImageView imageView =  row.findViewById(R.id.aa);
            TextView textView = row.findViewById(R.id.bb);
            imageView.setImageResource(imageArray[position]);
            textView.setText(values[position]);
            return row;
        }
    }

}
