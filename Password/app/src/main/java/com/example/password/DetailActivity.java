package com.example.password;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import Model.Password;

public class DetailActivity extends AppCompatActivity {
    private Password passedPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        passedPassword = getIntent().getExtras().getParcelable("passedPassword");
        setContentView(R.layout.activity_detail);

    }


}
