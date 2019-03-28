package com.example.level5_gameback.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.level5_gameback.Model.Game;
import com.example.level5_gameback.R;

//CreateGameActivity which is used for adding a new game
//or updating a already existing game
public class CreateGameActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    private EditText title;
    private EditText console;
    private Spinner status;
    //Static variable for adding a game these variable are used in the onResume method of the
    //MainActivity class
    public static Game gameStat;
    public static String staticTitle = "";
    public static String staticConsole = "";
    public static String staticStatus = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creategame);
        //Setup of the toolbar, variable and the floating action button
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupVariable();
        setupFab();

        //This part is for updating a game
        //This part of the code will not run when the user clicks on the
        //fab in the mainActivity only when a user clicks on a game to update
        //information
        if (!(staticTitle.length() == 0)) {
            //change the title to title of the game
            title.setText(staticTitle);
            //change the console to the console of the game
            console.setText(staticConsole);
            //detirmine which status the game is of
            int pos = 3;
            if (staticStatus.equals("Stalled")) {
                pos = 2;
            } else if (staticStatus.equals("Playing")) {
                pos = 1;
            } else if (staticStatus.equals("Want to play")) {
                pos = 0;
            }
            //set the status to the status of the game
            status.setSelection(pos);
        }
    }
    //find all the variable
    private void setupVariable() {
        title = findViewById(R.id.editTextTitle);
        console = findViewById(R.id.editTextPlatform);
        status = findViewById(R.id.spinnerStatus);
    }
    //setup the fab
    private void setupFab() {
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if all the variable are filled in
                if (validateVariable().equals(false)) {
                    //tell the user to fill in everything
                    Toast.makeText(CreateGameActivity.this, "Please fill in all fields!",
                            Toast.LENGTH_LONG).show();
                } else {
                    //pass the game details to the passGame method which will make a static variable of the game
                    passGame(title.getText().toString(),console.getText().toString(),status.getSelectedItem().toString());
                    //close this activity
                    finish();
                }

            }
        });
    }
    //passGame makes all of the details into a game object
    //and sets the static variable to the new created game
    //this static variable is used on the onResume in the mainactivity to add the game
    private void passGame(String title, String console, String status) {
        gameStat = new Game(title,console,status);
    }
    //method to validate if all of the fields are filled in
    //no need to check the rules because this is always set to want to play as default
    private Boolean validateVariable() {
        //check title
        if (title.length() == 0) {
            return false;
        }
        //check console
        if (console.length() == 0) {
            return false;
        }

        return true;
    }

}
