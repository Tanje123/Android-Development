package com.example.level5_gameback.View;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.level5_gameback.Model.Game;
import com.example.level5_gameback.R;

import java.util.List;
//Game adapter class this class puts all of the information in the recuycler
public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder>  {
    private List<Game> mGames;
    private Context context;
    //constructor of the addapter added context
    //so that we can start a activity from the adapterclass
    public GameAdapter(Context context) {
        this.context = context;
    }

        @NonNull
        @Override
        public GameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            Context context = viewGroup.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            //set the view to the singe_cardView that i made
            View view = inflater.inflate(R.layout.single_cardview, null);
            // Return a new holder instance
            GameAdapter.ViewHolder viewHolder = new GameAdapter.ViewHolder(view);
            return viewHolder;
        }
        //setList method
        public void setList(List<Game> games){
            mGames = games;
            notifyDataSetChanged();
        }
    //set all of the game details into the attributes of the cardview
    @Override
    public void onBindViewHolder(@NonNull GameAdapter.ViewHolder viewHolder, int i) {
        Game game = mGames.get(i);
        viewHolder.titel.setText(game.getTitel());
        viewHolder.console.setText(game.getConsole());
        viewHolder.status.setText(game.getStatus());
        viewHolder.datum.setText(game.getDate());

    }
    //get all of the items
    @Override
    public int getItemCount() {
        return mGames == null ? 0 : mGames.size();
    }
    //swap old list with new list
    public void swapList (List<Game> newList) {
        mGames = newList;
        if (newList != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    //viewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titel;
        TextView console;
        TextView status;
        TextView datum;
        //constructor
        public ViewHolder(View itemView) {
            super(itemView);
            titel = itemView.findViewById(R.id.titel);
            console = itemView.findViewById(R.id.console);
            status = itemView.findViewById(R.id.status);
            datum = itemView.findViewById(R.id.datum);
            //setonCLicklistner on the items of the recyclerview
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //create a game
                    Game game = mGames.get(getAdapterPosition());
                    //make the new activity
                    Intent intent= new Intent(context, CreateGameActivity.class);
                    //set all of the game details into the static variable
                    CreateGameActivity.staticTitle = titel.getText().toString();
                    CreateGameActivity.staticConsole = console.getText().toString();
                    CreateGameActivity.staticStatus = status.getText().toString();
                    //start the new activity
                    context.startActivity(intent);
                }
            });
        }
    }

}
