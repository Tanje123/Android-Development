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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import Database.PasswordRoomDatabase;
import Database.UserRoomDatabase;
import Model.Password;
import Model.User;
import Repository.MainActivityViewModel;

public class HomeFragment extends Fragment {

    private PasswordRoomDatabase db;
    List<Password> passwordList;
    private Executor executor = Executors.newSingleThreadExecutor();
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        db = PasswordRoomDatabase.getDatabase(getContext());
        passwordList = new ArrayList();
        return inflater.inflate(R.layout.fragment_home, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        textView = view.findViewById(R.id.textView);
        textView.setText("");
        getAllPasswords();
        super.onViewCreated(view, savedInstanceState);
    }

    private void getAllPasswords() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                passwordList = db.passwordDao().getAllPassword();
                // In a background thread the user interface cannot be updated from this thread.
                // This method will perform statements on the main thread again.
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setupPasswords();
                    }
                });
            }
        });
    }

    private void setupPasswords() {
        if (passwordList.size() != 0) {
            for (int i = 0; i <passwordList.size();i++){
                textView.append(" "+passwordList.get(i).getPassword());
            }
        }
    }

    private void instertPassword(final Password password) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.passwordDao().instertPassword(password);
                getAllPasswords(); // Because the Room database has been modified we need to get the new list of reminders.
            }
        });
    }


    private void updatePassword(final Password password) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.passwordDao().updatePassword(password);
                getAllPasswords(); // Because the Room database has been modified we need to get the new list of reminders.
            }
        });
    }


    private void deletePassword(final Password password) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.passwordDao().deletePassword(password);
                getAllPasswords(); // Because the Room database has been modified we need to get the new list of reminders.
            }
        });
    }
}
