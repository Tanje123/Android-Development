package Recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.password.R;

import java.util.List;

import Model.Password;

public class PasswordAdapter extends RecyclerView.Adapter<PasswordAdapter.PasswordViewHolder>{
    private List<Password> passwordList;

    public PasswordAdapter(List<Password> passwordList) {
        this.passwordList = passwordList;
    }

    @NonNull
    @Override
    public PasswordAdapter.PasswordViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.single_row_cell, viewGroup, false);
        return new PasswordViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull PasswordAdapter.PasswordViewHolder passwordViewHolder, int i) {
        passwordViewHolder.textView.setText(passwordList.get(i).getPassword());
    }

    @Override
    public int getItemCount() {
        return passwordList.size();
    }

    public class PasswordViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public PasswordViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }

    public void setPasswordList(List<Password> passwordList) {
        this.passwordList = passwordList;
    }
}
