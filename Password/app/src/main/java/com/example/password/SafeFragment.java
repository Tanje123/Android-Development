package com.example.password;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import Database.PasswordRoomDatabase;
import Model.Password;
import Repository.MainActivityViewModel;

public class SafeFragment extends Fragment {
    private MainActivityViewModel viewModel;
    private Map<String,String> params;
    private TextView passwordTextView, lengthTextView;
    private Switch lowercaseSwitch, uppercaseSwitch, numbersSwitch, specialSwitch;
    private Button generateButton;
    private SeekBar lengthSeekBar;
    private final int minLength = 1, maxLength = 128, defaultLength = 15;
    private int currentProgress;
    private PasswordRoomDatabase db;
    private Executor executor = Executors.newSingleThreadExecutor();
    private Password[] passwordList;
    private Password addedPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        db = PasswordRoomDatabase.getDatabase(getContext());
        passwordList = new Password[0];
        getActivity().setTitle("Safe");
        return inflater.inflate(R.layout.fragment_safe, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        params = new HashMap<>();
        setupAttributes(view);
        setupViewModel();
        super.onViewCreated(view, savedInstanceState);
    }

    private void setupParams(String upper, String lower, String numbers
            , String special, int length, String exclude) {

        params.put("upper", upper);
        params.put("lower", lower);
        params.put("numbers", numbers);
        params.put("special", special);
        params.put("length", Integer.toString(length));
        params.put("exclude", exclude);
        params.put("repeat", "1");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupAttributes(View view) {
        passwordTextView = view.findViewById(R.id.passwordTextField);
        lengthTextView = view.findViewById(R.id.lengthTextView);
        lowercaseSwitch  = view.findViewById(R.id.lowercaseSwitch);
        uppercaseSwitch =  view.findViewById(R.id.uppercaseSwitch);
        numbersSwitch = view.findViewById(R.id.numbersSwitch);
        specialSwitch  = view.findViewById(R.id.specialSwitch);
        generateButton = view.findViewById(R.id.generateButton);
        lengthSeekBar = view.findViewById(R.id.lengthSeekBar);
        //SeekBar setup
        currentProgress = defaultLength;
        lengthTextView.setText("Length: "+currentProgress);
        lengthSeekBar.setMin(minLength);
        lengthSeekBar.setMax(maxLength);
        lengthSeekBar.setProgress(defaultLength);
        lengthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentProgress = seekBar.getProgress();
                lengthTextView.setText("Length: "+currentProgress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


            }
        });

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getAttributes() == false) {
                    Toast.makeText(getContext(), "Please have one of the options " +
                            "activated", Toast.LENGTH_LONG)
                            .show();
                } else if (getAttributes() == true) {
                    viewModel.fetchPassword(params);
                    passwordTextView.setVisibility(View.VISIBLE);
                }
            }
        });

        passwordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(passwordList.length == 0)) {
                    if (!(passwordList[0].equals(addedPassword))) {
                        insertPassword(passwordList[0]);
                        addedPassword = passwordList[0];
                        Toast.makeText(getContext(), "Password saved", Toast.LENGTH_LONG)
                                .show();
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        passwordTextView.setText("");
        passwordTextView.setVisibility(View.INVISIBLE);
    }

    private void setupViewModel() {
        viewModel = ViewModelProviders.of(getActivity()).get(MainActivityViewModel.class);
        viewModel.getError().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_LONG)
                        .show();
            }
        });

        viewModel.getPassword().observe(getViewLifecycleOwner(), new Observer<Password[]>() {
            @Override
            public void onChanged(@Nullable Password[] passwords) {
                passwordList = passwords;
                passwordTextView.setText(passwords[0].getPassword());
            }
        });
    }

    private boolean getAttributes() {
        String upper = "off";
        String lower = "off";
        String numbers = "off";
        String special = "off";
        String exclude = "";
        int length;

        if (lowercaseSwitch.isChecked()) {
            lower = "on";
        }
        if (uppercaseSwitch.isChecked()) {
            upper = "on";
        }
        if (numbersSwitch.isChecked()) {
            numbers = "on";
        }
        if (specialSwitch.isChecked()) {
            special = "on";
        }
        length = lengthSeekBar.getProgress();

        if (upper.equals("off") &&lower.equals("off") && numbers.equals("off") && special.equals("off")) {
            return false;
        }

        setupParams(upper,lower,numbers,special,length,exclude);
        return true;
    }

    private void insertPassword(final Password password) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.passwordDao().instertPassword(password);
            }
        });
    }
}
