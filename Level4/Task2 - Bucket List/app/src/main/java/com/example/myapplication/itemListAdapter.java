package com.example.myapplication;

import android.content.Intent;
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

public class itemListAdapter extends RecyclerView.Adapter<itemListAdapter.ViewHolder> {
    private List<Item> itemList;
    private itemClickListner itemClickListner;

    private Executor executor = Executors.newSingleThreadExecutor();


    public itemListAdapter(List<Item> itemList, itemClickListner itemClickListner) {
        this.itemList = itemList;
        this.itemClickListner = itemClickListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.simple_row, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        System.out.println("Size is = " + itemList.size() + " titel= " + itemList.get(viewHolder.getAdapterPosition()).getTitel() + " functie= " + itemList.get(viewHolder.getAdapterPosition()).getDescription()
                + " COMPLETED: " + itemList.get(viewHolder.getAdapterPosition()).getCompleted());
        viewHolder.tvTitle.setText(itemList.get(viewHolder.getAdapterPosition()).getTitel());
        viewHolder.tvDescription.setText(itemList.get(viewHolder.getAdapterPosition()).getDescription());
        viewHolder.checkBox.setChecked(itemList.get(viewHolder.getAdapterPosition()).getCompleted());

        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (itemList.get(viewHolder.getAdapterPosition()).getCompleted()) {
                    itemList.get(viewHolder.getAdapterPosition()).setCompleted(false);
                } else {
                    itemList.get(viewHolder.getAdapterPosition()).setCompleted(true);
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvDescription;
        private CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitel);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            checkBox = itemView.findViewById(R.id.checkBox);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 if (buttonView.isPressed()) {
                            System.out.println("CLICKEDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
                            itemClickListner.onPlusClick(itemList.get(getAdapterPosition()));
                 }
             }
             }
            );

        }
    }
}


