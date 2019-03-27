package com.example.level5_gameback.View;

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

public class CreateGameActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    private EditText title;
    private EditText console;
    private Spinner status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creategame);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupVariable();
        setupFab();
    }

    private void setupVariable() {
        title = findViewById(R.id.editTextTitle);
        console = findViewById(R.id.editTextPlatform);
        status = findViewById(R.id.spinnerStatus);
    }
    private void setupFab() {
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateVariable().equals(false)) {
                    Toast.makeText(CreateGameActivity.this, "Please fill in all fields!",
                            Toast.LENGTH_LONG).show();
                } else {
                    passGame(title.toString(),console.toString(),status.toString());
                    finish();
                }

            }
        });
    }

    private Game passGame(String title, String console, String status) {

        return null;
    }

    private Boolean validateVariable() {
        if (title.length() == 0) {
            return false;
        }

        if (console.length() == 0) {
            return false;
        }

        return true;
    }

}
