package com.example.level5_gameback.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.level5_gameback.Model.Game;
import com.example.level5_gameback.R;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder>  {

    private List<Game> mGames;

        @NonNull
        @Override
        public GameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            Context context = viewGroup.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.single_cardview, null);
            // Return a new holder instance
            GameAdapter.ViewHolder viewHolder = new GameAdapter.ViewHolder(view);
            return viewHolder;
        }

        public void setList(List<Game> games){
            mGames = games;
            notifyDataSetChanged();
        }

    @Override
    public void onBindViewHolder(@NonNull GameAdapter.ViewHolder viewHolder, int i) {
        Game game = mGames.get(i);
        viewHolder.titel.setText(game.getTitel());
        viewHolder.console.setText(game.getConsole());
        viewHolder.status.setText(game.getStatus());
        viewHolder.datum.setText(game.getDate());
    }

    @Override
    public int getItemCount() {
        return mGames == null ? 0 : mGames.size();
    }

    public void swapList (List<Game> newList) {
        mGames = newList;
        if (newList != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titel;
        TextView console;
        TextView status;
        TextView datum;

        public ViewHolder(View itemView) {
            super(itemView);
            titel = itemView.findViewById(R.id.titel);
            console = itemView.findViewById(R.id.console);
            status = itemView.findViewById(R.id.status);
            datum = itemView.findViewById(R.id.datum);
        }
    }

}
