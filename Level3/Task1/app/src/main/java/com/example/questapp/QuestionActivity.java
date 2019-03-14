package com.example.questapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.support.design.widget.Snackbar;


public class QuestionActivity extends AppCompatActivity {
    private TextView textView;
    private Button buttonSubmit;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private int questionNumber = 1;
    private RadioGroup radioGroup;
    private String[] items;
    private String correctAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        textView = findViewById(R.id.textView);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        radioGroup = findViewById(R.id.radioGroup);
        populateQuestion(questionNumber);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Snackbar.make(v, getApplicationContext().getString(R.string.choose_answer),
                            Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else {
                    RadioButton answer = (RadioButton) findViewById(selectedId);
                    if (answer.getText() == correctAnswer) {
                        //good
                    } else {

                    }
                }

            }
        });
    }


    public void populateQuestion(int questionNumber){
        //   populate question
        String question = "question" + questionNumber;
        int holderint1 = getResources().getIdentifier(question, "string",
                this.getPackageName()); // You had used "name"
        String questionScreen = getResources().getString(holderint1);
        textView.setText(questionScreen);
        // populate answer
        String answer = "answer" + questionNumber;
        int holderint = getResources().getIdentifier(answer, "array",
                this.getPackageName()); // You had used "name"
        items = getResources().getStringArray(holderint);
        radioButton1.setText(items[1]);
        radioButton2.setText(items[2]);
        radioButton3.setText(items[3]);
        correctAnswer = items[0];
    }


}
