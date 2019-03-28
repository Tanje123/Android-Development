package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
//detailScreen screen where the user can enter a new item into the list
public class detailScreen extends AppCompatActivity {
    //attributes
    private Button button;
    private EditText editText1;
    private EditText editText2;
    public static Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_screen);

        //setup the variable and the on click listner on the button
        setupVar();
        setupOnClickListnerButton();
    }
    //onClickListner
    private void setupOnClickListnerButton() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if th tekst is empty make a toast that tells the user to fill in all fields
                if (editText1.length() == 0) {
                    Toast.makeText(detailScreen.this, "Please fill in all fields!",
                            Toast.LENGTH_LONG).show();
                }
                //if th tekst is empty make a toast that tells the user to fill in all fields
                else if (editText2.length() == 0) {
                    Toast.makeText(detailScreen.this, "Please fill in all fields!",
                            Toast.LENGTH_LONG).show();
                }
                //otherwise make a new item and set it to the static variable
                //this static variable is used in the onREsume method of the mainactivity to add
                //the item into the bucketlist
                else {
                    //create item
                    item = new Item(editText1.getText().toString(),editText2.getText().toString(),false);
                    //finish this activity
                    finish();
                }
            }
        });
    }
    //setup the variable
    private void setupVar() {
        //find the variable
        button = findViewById(R.id.button1);
        editText1 = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
    }
}
