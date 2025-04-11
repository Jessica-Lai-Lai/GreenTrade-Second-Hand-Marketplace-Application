package com.example.myapplication.ui.adapter;

import com.bumptech.glide.Glide;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Java.RedBlackTree;
import com.example.myapplication.Java.User;
import com.example.myapplication.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/** @author u7741198 Jessica Lai* */

public class StoreUserAdapter extends FirebaseRecyclerAdapter<UsersBean,StoreUserAdapter.myViewHolder>{

    private RedBlackTree<UsersBean> tree;
    public void setRedBlackTree(RedBlackTree<UsersBean> tree) {
        this.tree = tree;
    }

    public StoreUserAdapter(@NonNull FirebaseRecyclerOptions<UsersBean> options) {

        super(options);
        //2. Call this method in StoreUserAdapter to load data and create a tree:
        loadDataAndCreateTree();

    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull UsersBean model) {
        holder.name.setText(model.getName());

        // Convert the list of like IDs to a string
        if (model.getLikeIDs() != null && !model.getLikeIDs().isEmpty()) {
            String likeIdsString = TextUtils.join(",\n", model.getLikeIDs());
            holder.likeIdsString.setText(likeIdsString);
        } else {
            holder.likeIdsString.setText("null");
        }

        // Convert the list of good IDs to a string
        if (model.getGoodIDs() != null && !model.getGoodIDs().isEmpty()) {
            String goodIdsString = TextUtils.join(",\n", model.getGoodIDs());
            holder.goodIdsString.setText(goodIdsString);
        } else {
            holder.goodIdsString.setText("null");
        }

        // You can set the CircleImageView here using a library like Picasso or Glide
        // For example:
        // Picasso.get().load(model.getImageUrl()).into(holder.img);

        Glide.with(holder.img.getContext())
                .load(model.getSurl())
                .placeholder(R.drawable.baseline_android_24_greenlakeblue)
                .circleCrop()
                .error (R.drawable.baseline_android_24_gray)
                .into (holder.img);


        //Delete
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Are you Sure?");
                builder.setMessage("Deleted data can't be Undo.");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*
                        FirebaseDatabase.getInstance().getReference().child("users")
                                .child(getRef(position).getKey()).removeValue();

                         */

                        if (tree != null) {
                            // Get the location of the user to delete from the adapter
                            int adapterPosition = holder.getAdapterPosition();
                            // Get the user object to be deleted
                            UsersBean user = getItem(adapterPosition);
                            // Delete user data from RedBlackTree
                            tree.delete(user);

                            //Update the Firebase database after deleting the node
                            FirebaseDatabase.getInstance().getReference().child("users")
                                    .child(String.valueOf(user.getuId())) // Assume the user object has a method to get its unique identifier
                                    .removeValue()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            //Operation after successful deletion
                                            // For example, display Toast or perform other operations
                                            notifyDataSetChanged(); // Notify the adapter that the data has changed
                                            Toast.makeText(holder.name.getContext(),"Delete Successfully!",Toast.LENGTH_SHORT).show();

                                        }
                                    });
                        }

                    }

                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.name.getContext(),"Cancelled.",Toast.LENGTH_SHORT).show();

                    }
                });
                builder.show();
            }
        });
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.storeuser_item, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView name, likeIdsString, goodIdsString;

        Button btnDelete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (CircleImageView)itemView.findViewById(R.id.imgl);
            name = (TextView) itemView.findViewById(R.id.nametext);
            goodIdsString = (TextView)itemView.findViewById(R.id.goodIDs);
            likeIdsString = (TextView)itemView.findViewById(R.id.likeIDs);
            btnDelete = (Button) itemView.findViewById(R.id.delete);
        }
    }


    //1. Create a method to load data and create a tree:
    public void loadDataAndCreateTree() {
        // Create a list to store data obtained from Firebase
        List<UsersBean> userList = new ArrayList<>();

        //Query the Firebase database to get user data
        FirebaseDatabase.getInstance().getReference().child("users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            //Convert each data item to a UsersBean object and add it to the list
                            UsersBean user = snapshot.getValue(UsersBean.class);
                            userList.add(user);
                        }

                        // Create a RedBlackTree instance and insert the user list into it
                        RedBlackTree<UsersBean> tree = new RedBlackTree<>();
                        for (UsersBean user : userList) {
                            tree.insert(user);
                        }

                        //Set the adapter's RedBlackTree
                        setRedBlackTree(tree);
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle errors when loading data
                    }
                });
    }


}
