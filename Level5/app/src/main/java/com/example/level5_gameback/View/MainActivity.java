package com.example.level5_gameback.View;

//This application is a application in which you can track the games that you are playing, want to play or have played.
//Made by:
//Tanveer Singh
//50078

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.level5_gameback.Model.Game;
import com.example.level5_gameback.R;
import com.example.level5_gameback.ViewModel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Static variable that is used to get the new added game from the createGameActivity
    //This is a variable that is used for the first time that the MainActivity is opened
    static Game game = new Game("","","");
    //Static variable that keeps track of the previous game that was added
    //So that the same game doesnt get added multiple times
    private static Game currGame = game;
    private GameAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private MainViewModel mMainViewModel;

    //Oncreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Setup all of the components of the mainActivity
        //The recyclerview
        setupRecyclervView();
        //The mainViewModel
        setupMainViewModel();
        //The floating action button
        setupFAB();
    }
    //setup Recyclerview
    private void setupRecyclervView() {
        //Find the recyclerView from the activity
        mRecyclerView = findViewById(R.id.recycler);
        //Add the layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Setup the game adapter
        mAdapter = new GameAdapter(this);
        //Set the adapter to the recyclerview
        mRecyclerView.setAdapter(mAdapter);
        //Method to setup the left and right swiping on the recyclerview
        setUpItemTouchHelper();
    }

    //Method for left and right swiping
    private void setUpItemTouchHelper() {
        //dragDirs is zero because we only want horizontal sliding
        //the swipteDirs are set to left and right because we want the user to be able
        //to swipe to left and right
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            //The onmove
            //We do nothing with this
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            //When the item is completelty swiped
            //delete item
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int swipedPosition = viewHolder.getAdapterPosition();
                mMainViewModel.delete(swipedPosition);

            }

        };
        //Make a new object of the ItemTouchHelper class
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        //Make the itemTouchHelper active on the recyclerview
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    //Setup FAB
    private void setupFAB() {
        FloatingActionButton fab = findViewById(R.id.fab);
        //Setup onclick listneren on FAB
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //When we want to create a new game
                //All of the static variable of the CreateGameActivity get set to "" empty
                //These variable are used in the createGameActivity when you click on one of the items
                //in the recyclerview to show the information of the item since we are using the same screen
                //for updating and adding items
                CreateGameActivity.staticTitle = "";
                CreateGameActivity.staticConsole = "";
                CreateGameActivity.staticStatus = "";
                //Start a new activity
                Intent intent = new Intent(MainActivity.this, CreateGameActivity.class);
                startActivity(intent);
            }
        });
    }

    //Setup MainViewModel
    private void setupMainViewModel() {
        //Setup the mainViewModel
        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mMainViewModel.getGames().observe(this, new Observer<List<Game>>() {
            @Override
            public void onChanged(@Nullable List<Game> reminders) {
                mAdapter.setList(reminders);
                updateUI(reminders);
            }
        });
    }


    //onResume method that will run when the mainActivity is opened or resumed
    @Override
    protected void onResume() {
        super.onResume();
        //Create a new game with the static variable from the createGameActivity
        //This if for when we want to add a new Game to the database after a user has
        //hit the save button on the createAgame apge
        Game game = CreateGameActivity.gameStat;
        //if the game is not null do the following
        if (!(game == null)) {
                //Check if the game is not the same as the previous game that was added
                //We check this to make sure that the same information doesnt get added twice to
                //The database for example when someone goes to the createGame page and backs out of it
                if (!(currGame.equals(game))) {
                    //insert the game
                    mMainViewModel.insert(game);
                    //tell the adapter that our data changed
                    mAdapter.notifyDataSetChanged();
                    //set the current game to the newly added game
                    currGame = game;
                }
            }
        }

    //updateTheUI
    private void updateUI(List<Game> games) {
        //for the first time check
        if (mAdapter == null) {
            mAdapter = new GameAdapter(this);
            mAdapter.setList(games);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            //Otherwise update the ui
            mAdapter.swapList(games);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //Handles the deletion of the all the items when the user presses on the
        //bin in the header
        if (id == R.id.action_delete_item) {
            //This part is for backup so that the user can undo the delete action
            List myList = new ArrayList();
            //get all the save games from the database and add them to our list
            for (int i = 0; i < mMainViewModel.getGames().getValue().size(); i++) {
                myList.add(mMainViewModel.getGames().getValue().get(i));
            }

            //delete all of the games
            mMainViewModel.deleteAll();
            //make the snackbar apear that can undo the delete action
            Snackbar.make(mRecyclerView, getString(R.string.app_deleted), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.app_undo), new View.OnClickListener() {
                        @Override
                        //when the undo button is clicked add all the items back
                        public void onClick(View v) {
                            for (int i = 0; i < myList.size(); i++) {
                                mMainViewModel.insert((Game) myList.get(i));
                            }
                        }
                        //when action is done show the result
                    }).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
