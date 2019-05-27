package com.sayeed.bloodbank;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

//import com.squareup.picasso.Picasso;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Profile> profiles;

    public MyAdapter(Context c , ArrayList<Profile> p)
    {
        context =c;
        profiles =p;
    }

    DatabaseReference reference;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Classes");
        holder.classname.setText(profiles.get(position).getClassname());
        holder.date.setText(profiles.get(position).getDate());
        holder.time.setText(profiles.get(position).getTime());
        holder.location.setText(profiles.get(position).getLocation());
        holder.check.setChecked(profiles.get(position).isStatus());
        if(holder.check.isChecked())
        {
            reference.child(profiles.get(position).getClassname()).child("Applied By").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    holder.apply.setBackground(null);
                    holder.apply.setTypeface(Typeface.DEFAULT);
                    holder.apply.setText(dataSnapshot.getValue().toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        holder.onClick();
        //Picasso.with(context).load(profiles.get(position).getProfile()).into(holder.profile);
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {


        TextView classname,date,time,location;
        Button apply;
        CheckBox check;
     //   ImageView profile;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            classname = itemView.findViewById(R.id.classname);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            location = itemView.findViewById(R.id.location);
            apply = itemView.findViewById(R.id.apply);
            check = itemView.findViewById(R.id.check1);
            //     profile = (ImageView) itemView.findViewById(R.id.profile);
        }

        public void onClick()
        {
            apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    check.setChecked(true);

                    reference.child(classname.getText().toString()).child("status").setValue(check.isChecked());
                    reference.child(classname.getText().toString()).child("Applied By").setValue(user.getEmail());
                    apply.setBackground(null);
                    apply.setTypeface(Typeface.DEFAULT);
                    apply.setText(user.getEmail().toString());

                }
            });
        }
    }
}
