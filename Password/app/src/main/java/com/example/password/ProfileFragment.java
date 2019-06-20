package com.example.password;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private ImageView imageViewProfile;
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
        imageViewProfile = view.findViewById(R.id.imageViewProfile);
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
                        saveInformationFireBase();
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
                            saveInformationFireBase();
                        }
                    }
                }
            }
        });

        imageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent,4344);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 4344) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            imageViewProfile.setImageBitmap(image);
        }
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

    private void saveInformationFireBase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("members");
        Map<String, String> userData = new HashMap<String, String>();
        String name = editTextFirstName.getText().toString();
        String surName = editTextSurname.getText().toString();
        String email = editTextEmail.getText().toString();
        userData.put("Naam", name);
        userData.put("surName", surName);
        userData.put("Email", email);
        myRef.setValue(userList.get(0).getId() + "");
        myRef = myRef.child(userList.get(0).getId() + "");
        myRef.setValue(userData);
    }
}
