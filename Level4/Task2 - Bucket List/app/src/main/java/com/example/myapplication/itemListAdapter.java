package com.example.myapplication;

import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

//Adapter for the items in the recyclerview
public class itemListAdapter extends RecyclerView.Adapter<itemListAdapter.ViewHolder> {
    private List<Item> itemList;
    private itemClickListner itemClickListner;

    //constructor
    public itemListAdapter(List<Item> itemList, itemClickListner itemClickListner) {
        this.itemList = itemList;
        this.itemClickListner = itemClickListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        //set up the layout of the items in the recyclerview
        View view = inflater.inflate(R.layout.simple_row, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        //set the atributes from the item to the details of the item
        viewHolder.tvTitle.setText(itemList.get(viewHolder.getAdapterPosition()).getTitel());
        viewHolder.tvDescription.setText(itemList.get(viewHolder.getAdapterPosition()).getDescription());
        viewHolder.checkBox.setChecked(itemList.get(viewHolder.getAdapterPosition()).getCompleted());

        //This part checks if the text should be strike trough. if the item status is completed
        //it will strike trough both the title and description
        //if not the strike trough will be removed nothing will happen if the item has no striketrough
        //and it gets removed
        if (itemList.get(viewHolder.getAdapterPosition()).getCompleted()) {
            viewHolder.tvTitle.setPaintFlags(viewHolder.tvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.tvDescription.setPaintFlags(viewHolder.tvDescription.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else if (!(itemList.get(viewHolder.getAdapterPosition()).getCompleted())) {
            viewHolder.tvTitle.setPaintFlags(viewHolder.tvTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            viewHolder.tvDescription.setPaintFlags(viewHolder.tvDescription.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));

        }

    }

    //get amount of items
    @Override
    public int getItemCount() {
        return itemList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvDescription;
        private CheckBox checkBox;
        //find the attributes in the view
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitel);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            checkBox = itemView.findViewById(R.id.checkBox);
            //set onclick listneren
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //if the checkbox is activated set the status to false (not finked in)
                    if (checkBox.isActivated()) {
                        checkBox.setEnabled(false);
                        //otherwise set it to true (finked in)
                    } else {
                        checkBox.setEnabled(true);
                    }
                    //tell the itemclick lisnteren that a click happened
                    itemClickListner.onPlusClick(itemList.get(getAdapterPosition()));
                }
            });


        }
    }
}


