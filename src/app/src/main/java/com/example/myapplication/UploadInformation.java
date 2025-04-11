package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.myapplication.ui.adapter.UsersBean;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.DriverPropertyInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** @author u7741198 Jessica Lai */
public class UploadInformation extends AppCompatActivity {

    EditText userIDEditText, usernameEditText, passwordEditText, surlEditText;
    EditText goodsIDEditText1, goodsIDEditText2,goodsIDEditText3;
    EditText likeIDEditText1,likeIDEditText2,likeIDEditText3;
    Button uploadButton;

    Button backhomeButton;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_information);

        userIDEditText = findViewById(R.id.userID);
        usernameEditText = findViewById(R.id.useruName);
        passwordEditText = findViewById(R.id.userPassword);
        surlEditText= findViewById(R.id.userSurl);

        goodsIDEditText1 = findViewById(R.id.goodsID1);
        goodsIDEditText2 = findViewById(R.id.goodsID2);
        goodsIDEditText3 = findViewById(R.id.goodsID3);

        likeIDEditText1 = findViewById(R.id.likeID1);
        likeIDEditText2 = findViewById(R.id.likeID2);
        likeIDEditText3 = findViewById(R.id.likeID3);

        uploadButton = findViewById(R.id.uploadbtn);
        backhomeButton = findViewById(R.id.backhomebtn);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");

                // Retrieve user input from EditText fields
                //UsersBean(String upassword, String likeIDs, int uId, String name, String goodIDs)
                int uId = Integer.parseInt(userIDEditText.getText().toString());
                String name = usernameEditText.getText().toString();
                String upassword = passwordEditText.getText().toString();
                String surl =surlEditText.getText().toString();

                String goodID1 = goodsIDEditText1.getText().toString();
                String goodID2 = goodsIDEditText2.getText().toString();
                String goodID3 = goodsIDEditText3.getText().toString();


                String likeID1 = likeIDEditText1.getText().toString();
                String likeID2 = likeIDEditText2.getText().toString();
                String likeID3 = likeIDEditText3.getText().toString();


                List<String> likeIDs = new ArrayList<>();
                likeIDs.add(likeID1);
                likeIDs.add(likeID2);
                likeIDs.add(likeID3);

                List<String> goodIDs = new ArrayList<>();
                goodIDs.add(goodID1);
                goodIDs.add(goodID2);
                goodIDs.add(goodID3);


                //UsersBean(String upassword, List<String> likeIDs, int uId, String name, List<String> goodIDs)
                // Create User object
                //Users users =  new Users(uPassword, likeIDs, uId, uName, goodIDs);
                Map<String, Object> users = new HashMap<>();
                users.put("upassword", upassword);
                users.put("likeIDs", likeIDs);
                users.put("uId", uId);
                users.put("name", name);
                users.put("goodIDs", goodIDs);
                users.put("surl", surl);
                //UsersBean usersBean =  new UsersBean(uPassword, likeIDs, uId, uName, goodIDs);
                // Upload user data to Firebase
                //reference.child(String.valueOf(uId)).setValue(users);

                //Toast.makeText(UploadInformation.this, "User data uploaded successfully!", Toast.LENGTH_SHORT).show();
                //FirebaseDatabase.getInstance().getReference().child("users").child(String.valueOf(uId)).push()
                        //.setValue(users)
                reference.child(String.valueOf(uId)).setValue(users)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(UploadInformation.this, "Data Inserted Successfully.", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                Toast.makeText(UploadInformation.this, "Error while Insertion.", Toast.LENGTH_SHORT).show();


                            }

                        });
            }
        });





        backhomeButton.setOnClickListener(view -> finish());


            }
        }
