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
    static Game game = new Game("","","");
    private static Game currGame = game;
    private GameAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private MainViewModel mMainViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupRecyclervView();
        setupMainViewModel();
        setupFAB();
    }
    //setup Recyclerview
    private void setupRecyclervView() {
        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new GameAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        setUpItemTouchHelper();
    }

    private void setUpItemTouchHelper() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int swipedPosition = viewHolder.getAdapterPosition();
                mMainViewModel.delete(swipedPosition);

            }

        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    //Setup FAB
    private void setupFAB() {
        FloatingActionButton fab = findViewById(R.id.fab);
        //Setup onclick listneren on FAB
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateGameActivity.staticTitle = "";
                CreateGameActivity.staticConsole = "";
                CreateGameActivity.staticStatus = "";
                Intent intent = new Intent(MainActivity.this, CreateGameActivity.class);
                startActivity(intent);
            }
        });
    }

    //Setup MainViewModel
    private void setupMainViewModel() {
        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mMainViewModel.getGames().observe(this, new Observer<List<Game>>() {
            @Override
            public void onChanged(@Nullable List<Game> reminders) {
                mAdapter.setList(reminders);
                updateUI(reminders);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Game game = CreateGameActivity.gameStat;
        if (!(game == null)) {
                System.out.println("NIET NULL");
                if (!(currGame.equals(game))) {
                    mMainViewModel.insert(game);
                    System.out.println("UPDATED");
                    mAdapter.notifyDataSetChanged();
                    currGame = game;
                }
            }
        }


    private void updateUI(List<Game> reminders) {
        if (mAdapter == null) {
            mAdapter = new GameAdapter(this);
            mAdapter.setList(reminders);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.swapList(reminders);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete_item) {
            List myList = new ArrayList();
            for (int i = 0; i < mMainViewModel.getGames().getValue().size(); i++) {
                myList.add(mMainViewModel.getGames().getValue().get(i));
            }


            mMainViewModel.deleteAll();
            Snackbar.make(mRecyclerView, "Deleted all games", Snackbar.LENGTH_LONG)
                    .setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for (int i = 0; i < myList.size(); i++) {
                                mMainViewModel.insert((Game) myList.get(i));
                            }
                        }
                    }).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
