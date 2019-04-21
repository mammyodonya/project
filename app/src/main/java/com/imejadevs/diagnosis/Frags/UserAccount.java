package com.imejadevs.diagnosis.Frags;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.imejadevs.diagnosis.History.History;
import com.imejadevs.diagnosis.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

//import com.bumptech.glide.annotation.GlideModule;
//import com.bumptech.glide.module.AppGlideModule;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserAccount extends Fragment {

    private static final int PICK_IMAGE = 1994;
    private static DecimalFormat df2 = new DecimalFormat(".##");
    public TextView Name, Last, Visits, Diagnosis, Messages, Email, Phone, Location, Height, Weight;
    TextView diab;
    FirebaseDatabase database;
    DatabaseReference reference;
    CircleImageView imageView;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    Context context;
    FirebaseFirestore firebaseFirestore;
    UploadTask uploadTask;
    private FirebaseAuth mAuth;
    private Uri uri;
    private Uri mImageUri;


    public UserAccount() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_account, container, false);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        context = getContext();
        Name = view.findViewById(R.id.name);
        Last = view.findViewById(R.id.last);
        Visits = view.findViewById(R.id.visits);
        Diagnosis = view.findViewById(R.id.diagnose);
        Messages = view.findViewById(R.id.message);
        Email = view.findViewById(R.id.email);
        Phone = view.findViewById(R.id.phone);
        Location = view.findViewById(R.id.location);
        Height = view.findViewById(R.id.he);
        Weight = view.findViewById(R.id.we);
        diab = view.findViewById(R.id.diabetis);
        imageView = view.findViewById(R.id.profile);
        Visits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), History.class));
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup();
            }
        });
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference().child("Users/");
        loadUserInfor();

        return view;
    }

    private void popup() {


        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Profile")
                .setContentText("Are you sure you want to change the Profile?")
                .setConfirmText("Yes,Update")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_PICK);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                    }
                })
                .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                uri = data.getData();
                uploadSasa(data.getData());
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                    imageView.setImageBitmap(bitmap);
                    uploadSasa(data.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(getContext(), "No Image selected", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }


    private void uploadSasa(Uri uri) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading Profile");
        progressDialog.show();
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Failed to Upload" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                progressDialog.setMessage("Uploaded " + (int) progress + " % ");

            }
        }).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return storageReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri b = task.getResult();
                    updatePic(b.toString());
                }
            }
        });

    }

    private void updatePic(String profile) {
        String user_email = Email.getText().toString();
        String user_name = Name.getText().toString();
        String user_last = Last.getText().toString();
        String user_visits = Visits.getText().toString();
        String user_diagnosis = Diagnosis.getText().toString();
        String user_messages = Messages.getText().toString();
        String user_phone = Phone.getText().toString();
        String user_location = Location.getText().toString();
        String user_height = Height.getText().toString();
        String user_weight = Weight.getText().toString();

        Map<String, Object> user = new HashMap<>();
//        user.put("firstname", user_name);
//        user.put("lastname", user_last);
//        user.put("phone", user_phone);
//        user.put("location", user_location);
//        user.put("height", user_height);
//        user.put("email", user_email);
//        user.put("weight", user_weight);
        user.put("image", profile);
//        user.put("visits", user_visits);
//        user.put("diagnosis", user_diagnosis);
//        user.put("messages", user_messages);
        firebaseFirestore.collection("User Profile").document(user_email)
                .update(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Profile uploaded", Toast.LENGTH_SHORT).show();


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


    private void loadUserInfor() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String email = currentUser.getEmail();
            DocumentReference docRef = firebaseFirestore.collection("User Profile").document(email);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null) {

                            String firstname = task.getResult().getData().get("firstname").toString();
                            String lastname = task.getResult().getData().get("lastname").toString();
                            String phone = task.getResult().getData().get("phone").toString();
                            String location = task.getResult().getData().get("location").toString();
                            String height = task.getResult().getData().get("height").toString();
                            String email = task.getResult().getData().get("email").toString();
                            String weight = task.getResult().getData().get("weight").toString();
                            String image = task.getResult().getData().get("image").toString();
                            String visits = task.getResult().getData().get("visits").toString();
                            String diag = task.getResult().getData().get("diagnosis").toString();
                            String message = task.getResult().getData().get("messages").toString();


                            updateUI(firstname, email, lastname, phone, location, image, height, weight, visits, diag, message);
                        }
                    }
                }
            });
        }
    }

    private void updateUI(String firstname, String email, String lastname, String phone,
                          String location, String profile, String height, String weight,
                          String visits, String diag, String message) {
        Name.setText(firstname);
        Last.setText(lastname);

        Visits.setText("" + visits + " ");
        Diagnosis.setText("" + diag + "");
//        Messages.setText("" + message + "");
        Phone.setText("" + phone + "");
        Location.setText("" + location + "");
        Email.setText("" + email + "");
        Height.setText("" + height);
        Weight.setText("" + weight);
        Picasso.get().load(profile)
                .fit()
                .placeholder(R.drawable.default_avata)
                .centerCrop()
                .into(imageView);

        int A = Integer.parseInt(height);
        int B = Integer.parseInt(weight);
        float BMI = (float) (A / ((B / 100.0) * (B / 100.0)));
        Messages.setText(" " + df2.format(BMI));

        if (BMI < 18) {
            diab.setText("You are considered underweight, Get tips from keep fit module to pull up!!");
        } else if ((BMI < 25) && (BMI > 18)) {
            diab.setText("You are considered normal,Use keep fit module to maintain!!");
        } else if (BMI > 25 && BMI < 30) {
            diab.setText("You are considered Overweight, Get tips from keep fit module to go back to normal!!");
        } else if (BMI >= 30) {
            diab.setText("You are considered obese, Get tips from keep fit module to pull up!!");
        } else {
            Toast.makeText(context, "invalid height or weight", Toast.LENGTH_SHORT).show();
        }

    }

    private void logout() {
        promptUser();
    }

    private void promptUser() {
        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Diagnosis")
                .setContentText("Are you Sure to Logout??")
                .setConfirmText("Yes,Quit")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        mAuth.signOut();
                        getActivity().finish();
                    }
                })
                .setCancelButton("STAY", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

}
