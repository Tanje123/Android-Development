package com.example.level5_gameback.View;

//This application is a application in which you can track the games that you are playing, want to play or have played.
//Made by:
//Tanveer Singh
//50078

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.level5_gameback.Model.Game;
import com.example.level5_gameback.R;
import com.example.level5_gameback.ViewModel.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Game> mGames;
    //private GameAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private MainViewModel mMainViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // mMainViewModel.insert();

            }
        });


        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mMainViewModel.getReminders().observe(this, new Observer<List<Game>>() {
            @Override
            public void onChanged(@Nullable List<Game> reminders) {
                mGames = reminders;
                updateUI();
            }
        });

    }

    private void updateUI() {
    //    if (mAdapter == null) {
      //      mAdapter = new ReminderAdapter(mReminders);
        //    mRecyclerView.setAdapter(mAdapter);
        //} else {
          //  mAdapter.swapList(mReminders);
        //}
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
