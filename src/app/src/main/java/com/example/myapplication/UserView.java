package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.Java.RedBlackTree;
import com.example.myapplication.Java.User;
import com.example.myapplication.ui.adapter.StoreUserAdapter;
import com.example.myapplication.ui.adapter.UsersBean;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

/** @author u7741198 Jessica Lai* */
public class UserView extends AppCompatActivity {

    RecyclerView  recyclerView;
    StoreUserAdapter storeUserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);

        findViewById(R.id.backhome).setOnClickListener(view -> finish());

        recyclerView = findViewById(R.id.UserRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager( this));

        FirebaseRecyclerOptions<UsersBean> options =
                new FirebaseRecyclerOptions.Builder<UsersBean>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users"), UsersBean.class)
                        .build();

        storeUserAdapter = new StoreUserAdapter(options);
        recyclerView.setAdapter(storeUserAdapter);


        //For Red Black Tree
        RedBlackTree<UsersBean> tree = new RedBlackTree<>();
        storeUserAdapter.setRedBlackTree(tree);

    }





    @Override
    protected void onStart() {
        super.onStart();
        storeUserAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        storeUserAdapter.stopListening();
    }
}