package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.concurrent.futures.ResolvableFuture;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.myapplication.Search.QueryService;
import com.example.myapplication.javaclass.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/** @author u7741198 Jessica Lai
 @author u7178864 Jin Zhang * */
public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    Button buttonLogout;


    Button buttonAdd;

    Button buttonstoreUser;

    TextView textView;
    FirebaseUser user;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        buttonLogout = findViewById(R.id.logoutbtn);

        buttonAdd = findViewById(R.id.addbtn);
        buttonstoreUser = findViewById(R.id.storeUser);
        textView = findViewById(R.id.user_details);
        user = auth.getCurrentUser();


        findViewById(R.id.ll_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }
        });

        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        } else {
            textView.setText(user.getEmail());
        }

        buttonLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });



        buttonstoreUser.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,UserView.class));
        });

        buttonAdd.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,UploadInformation.class));
        });


    }

}
