package com.example.password;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import Database.PasswordRoomDatabase;
import Model.Password;

//detail activity this opens when user clicks on one of the entries
public class DetailActivity extends AppCompatActivity {
    private Password passedPassword;
    private PasswordRoomDatabase db;
    private Button share, delete;
    private TextView detailPassword;
    private Executor executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        passedPassword = getIntent().getExtras().getParcelable("passedPassword");
        setContentView(R.layout.activity_detail);
        db = PasswordRoomDatabase.getDatabase(this);
        setupAttributes();

    }
    //setup the attributes
    private void setupAttributes() {
        share = findViewById(R.id.shareButton);
        delete = findViewById(R.id.deleteButton);
        detailPassword = findViewById(R.id.detailPasswordTextField);
        detailPassword.setText(passedPassword.getPassword());
        //share button
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Share functionality
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody = passedPassword.getPassword();
                String shareSubject = getString(R.string.ShareSubject);
                myIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody);
                myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(myIntent, getString(R.string.Share)));
            }
        });
        //delete password then close the activity
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePassword(passedPassword);
                finish();
            }
        });
    }
    //method that deletes the password
    private void deletePassword(final Password password) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.passwordDao().deletePassword(password);
            }
        });
    }


}
