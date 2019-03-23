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

public class detailScreen extends AppCompatActivity {
    private Button button;
    private EditText editText1;
    private EditText editText2;
    private CheckBox checkBox;
    public static Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_screen);
        button = findViewById(R.id.button1);
        editText1 = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText1.length() == 0) {
                    Toast.makeText(detailScreen.this, "Please fill in all fields!",
                            Toast.LENGTH_LONG).show();
                }

                else if (editText2.length() == 0) {
                    Toast.makeText(detailScreen.this, "Please fill in all fields!",
                            Toast.LENGTH_LONG).show();
                } else {
                     item = new Item(editText1.getText().toString(),editText2.getText().toString(),false);
                    Intent i = new Intent(detailScreen.this, MainActivity.class);
                    i.putExtra("sampleObject", item);
                    finish();
                   // startActivity(i);
                }
            }
        });
    }
}
