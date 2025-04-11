package com.example.myapplication.ui.adapter;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.Java.Goods;
import com.example.myapplication.Java.RedBlackTree;
import com.example.myapplication.Java.User;
import com.example.myapplication.R;
import com.example.myapplication.ui.adapter.UsersBean;
import com.example.myapplication.ui.adapter.StoreAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/** @author u7178864 Jin Zhang */
public class StoreActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    private StoreAdapter storeAdapter;

    private final List<UsersBean> list = new ArrayList<>();
    private static final String TAG = "StoreActivity";

    private ProgressDialog progressDialog;
    private RedBlackTree<Goods> goodsTree;
    private RedBlackTree<User> userTree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        findViewById(R.id.back).setOnClickListener(view -> finish());

        initShowProgressDialog();

        recyclerView = findViewById(R.id.recyclerView);

        storeAdapter = new StoreAdapter(this, list);
        recyclerView.setAdapter(storeAdapter);



        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        reference.child("users").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Gson gson = new Gson();
                String json = gson.toJson(dataSnapshot.getValue());
                Log.d(TAG, "current_json: " + json);
                Type listType = new TypeToken<List<UsersBean>>() {
                }.getType();
                List<UsersBean> usersBeanList = gson.fromJson(json, listType);
                list.clear();
                list.addAll(usersBeanList);
                storeAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        });



    }

    private void initShowProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

}