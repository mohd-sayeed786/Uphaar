package com.sayeed.bloodbank;

import android.support.annotation.NonNull;
import android.view.View;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.w3c.dom.Text;

import Interface.IRecyclerItemClickListener;

public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txt_user_email;
    IRecyclerItemClickListener iRecyclerItemClickListener;

    public void setiRecyclerItemClickListener(IRecyclerItemClickListener iRecyclerItemClickListener) {
        this.iRecyclerItemClickListener = iRecyclerItemClickListener;
    }

    public UserViewHolder (@NonNull View itemView){
        super (itemView);

        txt_user_email = itemView.findViewById(R.id.txt_email);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        iRecyclerItemClickListener.onItemClickListener(v,getAdapterPosition());
    }
}
