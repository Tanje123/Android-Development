package com.example.password;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import Model.Password;
import Repository.MainActivityViewModel;

public class SafeFragment extends Fragment {
    private MainActivityViewModel viewModel;
    private Map<String,String> params;
    private TextView textView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_safe, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        textView = view.findViewById(R.id.passwordTextField);
        params = new HashMap<String, String>();
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
                textView.setText(passwords[0].getPassword());
            }
        });
        setupParams();
        viewModel.fetchPassword(params);
        super.onViewCreated(view, savedInstanceState);
    }

    private void setupParams() {
        params.put("upper", "on");
        params.put("lower", "on");
        params.put("numbers", "on");
        params.put("special", "on");
        params.put("length", "20");
        params.put("exclude", "");
        params.put("repeat", "1");
    }


}
