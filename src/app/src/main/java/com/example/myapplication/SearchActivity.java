package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myapplication.Java.Goods;
import com.example.myapplication.Java.RedBlackTree;
import com.example.myapplication.Java.User;
import com.example.myapplication.Search.FirebaseHandler;
import com.example.myapplication.Search.Search;
import com.example.myapplication.Search.SearchManager;
import com.example.myapplication.TokenizerParser.Exp;
import com.example.myapplication.TokenizerParser.Parser;
import com.example.myapplication.TokenizerParser.Tokenizer;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/** @author u7178864 Jin Zhang */
public class SearchActivity extends Activity {
    private SearchManager searchManager;
    private FirebaseHandler firebaseHandler;
    private RelativeLayout rlRoot;
    private TextView etCondition;
    private TextView tvSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Initialize Firebase database
        FirebaseHandler firebaseHandler = new FirebaseHandler();

        firebaseHandler.fetchGoodsDataAndInsertIntoSearchManager();
        firebaseHandler.fetchUserDataAndInsertIntoSearchManager();


        initView();

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String condition = etCondition.getText().toString().trim();

                //START OF TREE CREATION
                RedBlackTree<User> userTree = new RedBlackTree<>();
                RedBlackTree<Goods> goodsTree = new RedBlackTree<>();
                SearchManager.getInstance().setUserTree(userTree);
                SearchManager.getInstance().getGoodsRedBlackTree().viewTree(goodsTree.getRoot());

                Intent intent = new Intent(SearchActivity.this, MapActivity.class);
                intent.putExtra("condition",condition);
                startActivity(intent);
                //END OF TREE CREATION



            }
        });

        rlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    public void toMap(View view) {
        startActivity(new Intent(this, MapActivity.class));
    }

    private void initView() {
        rlRoot = (RelativeLayout) findViewById(R.id.rl_root);
        etCondition = (TextView) findViewById(R.id.et_condition);
        tvSubmit = (TextView) findViewById(R.id.tv_submit);
    }
}
