package com.example.password;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import Database.UserRoomDatabase;
import Model.User;
//profile fragment
public class ProfileFragment extends Fragment {
    private UserRoomDatabase db;
    List<User> userList;
    private Executor executor = Executors.newSingleThreadExecutor();
    private TextView editTextFirstName, editTextSurname, editTextEmail;
    private Button buttonSubmit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInsanceState) {
        db = UserRoomDatabase.getDatabase(getContext());
        userList = new ArrayList();
        getActivity().setTitle(getString(R.string.title_profile));
        return inflater.inflate(R.layout.fragment_profile, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getAllUsers();
        setupAttributes(view);
        setupProfile();
        super.onViewCreated(view, savedInstanceState);
    }
    //setup the profile from the data if there is data available
    private void setupProfile() {
        if ((userList.size()) != 0) {
            editTextFirstName.setText(userList.get(0).getFirstName());
            editTextSurname.setText(userList.get(0).getLastName());
            editTextEmail.setText(userList.get(0).getEmail());
        }
    }
    //setup the attributes
    private void setupAttributes(View view) {
        editTextFirstName = view.findViewById(R.id.editTextFirstName);
        editTextSurname = view.findViewById(R.id.editTextSurname);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        buttonSubmit = view.findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(editTextFirstName.getText().toString(),editTextSurname.getText().toString()
                        ,editTextEmail.getText().toString());
                //check if all fields are filled in
                if (validateFields()) {
                    //if there is no data available save the information with instert
                    if (userList.size() == 0) {
                        Toast.makeText(getActivity(),getString(R.string.SavedInformation), Toast.LENGTH_LONG).show();
                        insertUser(user);
                        //otherwise update the new information
                    } else {
                        User newUser = userList.get(0);
                        user.setId(userList.get(0).getId());
                        if (user.getId()== newUser.getId() && user.getEmail().equals(newUser.getEmail())
                        && user.getFirstName().equals(newUser.getFirstName())
                                && user.getLastName().equals(newUser.getLastName())) {
                        } else {
                            System.out.println(user.toString());
                            System.out.println(userList.get(0).toString());
                            Toast.makeText(getActivity(),getString(R.string.UpdatedInformation), Toast.LENGTH_LONG).show();
                            updateUser(user);
                        }
                    }
                }
            }
        });
    }

    //method to validate if all fields are filled in
    private boolean validateFields() {
        if ( editTextFirstName.getText().toString().length() == 0
                ||editTextSurname.getText().toString().length() == 0
                || editTextEmail.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), getString(R.string.Validation), Toast.LENGTH_SHORT).show();
            return false;
        }
        
        return true;
    }

    //database methods
    private void getAllUsers() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                userList = db.userDao().getAllUser();
                // In a background thread the user interface cannot be updated from this thread.
                // This method will perform statements on the main thread again.
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setupProfile();
                    }
                });
            }
        });
    }

    private void insertUser(final User user) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.userDao().insertUser(user);
                getAllUsers(); // Because the Room database has been modified we need to get the new list of reminders.
            }
        });
    }


    private void updateUser(final User user) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.userDao().updateUser(user);
                getAllUsers(); // Because the Room database has been modified we need to get the new list of reminders.
            }
        });
    }


    private void deleteUser(final User user) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.userDao().deleteUser(user);
                getAllUsers(); // Because the Room database has been modified we need to get the new list of reminders.
            }
        });
    }
}
