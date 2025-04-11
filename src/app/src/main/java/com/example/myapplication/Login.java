package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Java.Goods;
import com.example.myapplication.Java.RedBlackTree;
import com.example.myapplication.Java.User;
import com.example.myapplication.Search.SearchManager;
import com.example.myapplication.javaclass.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/** @author u7741198 Jessica Lai */
public class Login extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    Button buttonLogin;

    Button buttonRegister;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    //TextView textView;

    //https://firebase.google.com/docs/auth/android/password-auth#java_2
    //When initializing your Activity, check to see if the user is currently signed in
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//START OF TREE CREATION
//        RedBlackTree<User> userTree = new RedBlackTree<>();
//        RedBlackTree<Goods> goodsTree = new RedBlackTree<>();
//        // add data into the tree
//        //todo replace sample code with actual data
//        String sampleUserString = "u1234|Tom|p12345|g1234|l2345";   //sample user raw string
//        ArrayList<User> sampleUsers = new ArrayList<>();
//        sampleUsers.add(new User(sampleUserString));    //sample load data from raw string
//        for (User sampleUser : sampleUsers) {
//            userTree.insert(sampleUser);    // insert user to the user tree
//        }
//        String sampleGoodString = "g0001|gPanadol|dPanadol is a common brand for Paracetamol.  Paracetamol (acetaminophen) is a non-opioid analgesic and antipyretic agent used to treat fever and mild to moderate pain.  It is a widely used over the counter medication.|pAUD18.49|c(10, 20)";
//        ArrayList<Goods> sampleGoods = new ArrayList<>();
////        sampleGoods.add(new Goods(sampleGoodString));    //sample load data from raw string
//        for (Goods sampleGood : sampleGoods) {
//            goodsTree.insert(sampleGood);    // insert user to the user tree
//        }
        //END OF TREE CREATION


        // Email = (TextView) findViewById(R.id.Email);
        //TextView password = (TextView) findViewById(R.id.password);
        //Button loginbtn = (Button) findViewById(R.id.loginbtn);

        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.Email);
        editTextPassword = findViewById(R.id.password);
        buttonLogin = findViewById (R.id.loginbtn);
        progressBar = findViewById(R.id.progressBar);
        //textView = findViewById(R.id.registerNow);
        buttonRegister= findViewById(R.id.registerbtn);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
                finish();

            }
        });



// Original code only click!
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());

                /*
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Login.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Login.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                 */

                /*if (username.getText().toString().equals("test") && password.getText().toString().equals("test")) {
                    //correct
                    Toast.makeText(MainActivity.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                } else
                    //incorrect
                    Toast.makeText(MainActivity.this, "LOGIN FAILED !!!", Toast.LENGTH_SHORT).show();
                */
                //performLogin(editTextEmail.toString(), editTextPassword.toString());



                // From https://firebase.google.com/docs/auth/android/password-auth?hl=zh-tw#java_5
                //When a user signs in to your app, pass the user's email address and password to signInWithEmailAndPassword
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {


                                // Assume UserSession or similar class handles login
                                UserSession userCheck = new UserSession();
                                boolean isLoggedIn = userCheck.login(email, password);

                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                //if (isLoggedIn) {

                                    Toast.makeText(Login.this, "LOGIN SUCCESSFUL",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else {

                                    Toast.makeText(Login.this, "LOGIN FAILED!!!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


    }
    /*
    private void performLogin(String username, String password) {
        // Assume UserSession or similar class handles login
        UserSession userCheck = new UserSession();
        boolean isLoggedIn = userCheck.login(username, password);
        if (isLoggedIn) {
            Toast.makeText(Login.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
            // Should navigate to another activity or update UI
        } else {
            Toast.makeText(Login.this, "LOGIN FAILED!!!", Toast.LENGTH_SHORT).show();
        }
    }
     */
}