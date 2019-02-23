package com.example.logica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        editText4 = (EditText) findViewById(R.id.editText4);
        button = (Button) findViewById(R.id.buttonSubmit);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //method
                if (editText1.getText().length() == 0) {
                    errorText();

                } else if (editText2.getText().length() == 0) {
                    errorText();

                } else if (editText3.getText().length() == 0) {
                    errorText();

                } else if (editText4.getText().length() == 0) {
                    errorText();

                } else {
                    checkAnswers(editText1.getText().toString(),editText2.getText().toString(),editText3.getText().toString(),editText4.getText().toString());
                }
               // editText1.getText();
            }
        });

    }

    private void checkAnswers(String ans1,String ans2,String ans3,String ans4) {
            String an1 = "Fout";
        String an2 = "Fout";
        String an3 = "Fout";
        String an4 = "Fout";
            if (ans1.equals("f")) {
                an1 = "Goed";
            }
            if (ans2.equals("f")) {
                //good
                an2 = "Goed";

            }
            if (ans3.equals("f")) {
                //good
                an3 = "Goed";

            }
            if (ans4.equals("t")) {
                //good
                an4 = "Goed";

            }
            sumbitText(an1,an2,an3,an4);
    }

    private void errorText() {
        String answer = "Goed";
        Toast.makeText(this, "Vul alle vragen in ",Toast.LENGTH_SHORT).show();
    }

    private void sumbitText(String an1,String an2,String an3, String an4) {
        String result = "Question1: "+an1+"\nQuestion 2: "+an2+"\nQuestion 3: "+an3+"\nQuestion 4: "+an4;
        Toast.makeText(this, result,Toast.LENGTH_SHORT).show();

    }
}
