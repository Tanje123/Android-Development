package com.example.password;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import Recycler.PasswordAdapter;
import Recycler.RecyclerViewClickListener;
import Recycler.RecyclerViewTouchListener;
import Repository.MainActivityViewModel;
//the home fragment
public class HomeFragment extends Fragment {

    private PasswordRoomDatabase db;
    private List<Password> passwordList;
    private List<Password> deletedList;
    private Executor executor = Executors.newSingleThreadExecutor();
    private RecyclerView recyclerView;
    private PasswordAdapter passwordAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        db = PasswordRoomDatabase.getDatabase(getContext());
        passwordList = new ArrayList();
        deletedList = new ArrayList();
        getActivity().setTitle(getString(R.string.title_home));
        return inflater.inflate(R.layout.fragment_home, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.passwordRecyclerView);
        passwordAdapter = new PasswordAdapter(passwordList);
        recyclerView.setAdapter(passwordAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //on click open the detail activity and pass the password to it
        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity().getApplicationContext(), recyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("passedPassword", passwordList.get(position));
                String transitionImage = "simple_activity_transition";
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), view.findViewById(R.id.textView)
                        ,transitionImage);
                getActivity().startActivity(intent, options.toBundle());
            }

            @Override
            public void onLongClick(View view, int position) {
                //LongClick?
            }
        }));;
        getAllPasswords();
        super.onViewCreated(view, savedInstanceState);
    }
    //method to get all passwords
    private void getAllPasswords() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                passwordList.clear();
                List<Password> newPasswordList = db.passwordDao().getAllPassword();
                passwordList.addAll(newPasswordList);
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
    //setup passwords in the recycler view if available
    private void setupPasswords() {
        if (passwordList.size() != 0) {
            passwordAdapter.setPasswordList(passwordList);
        }
        passwordAdapter.notifyDataSetChanged();
    }
    //instert password
    private void instertPassword(final Password password) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.passwordDao().instertPassword(password);
                getAllPasswords(); // Because the Room database has been modified we need to get the new list of reminders.
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //show the bin in the toolbar
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }
    //if the delete button is pressed delete all passwords
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_delete_item) {
            if (passwordList.size() != 0) {
                deleteAllPasswords();
                passwordAdapter.notifyDataSetChanged();
            }
        }
        return super.onOptionsItemSelected(item);
    }
    //update password
    private void updatePassword(final Password password) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.passwordDao().updatePassword(password);
                getAllPasswords(); // Because the Room database has been modified we need to get the new list of reminders.
            }
        });
    }

    //delete a specific password
    private void deletePassword(final Password password) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.passwordDao().deletePassword(password);
                getAllPasswords(); // Because the Room database has been modified we need to get the new list of reminders.
            }
        });
    }
    //delete all passwords
    private void deleteAllPasswords() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                deletedList.clear();
                deletedList.addAll((passwordList));
                db.passwordDao().deleteAllPasswords();
                //make the snackbar apear that can undo the delete action
                Snackbar.make(recyclerView, getString(R.string.Deletedall), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.Undo), new View.OnClickListener() {
                            @Override
                            //when the undo button is clicked add all the items back
                            public void onClick(View v) {
                                for (int i = 0; i < deletedList.size(); i++) {
                                    instertPassword(deletedList.get(i));
                                }
                                //getAllPasswords();
                            }
                            //when action is done show the result
                        }).show();
                getAllPasswords(); // Because the Room database has been modified we need to get the new list of reminders.
            }
        });
    }

    @Override
    //when page is resumed fetch all the data again this is needed when going back from the detail activity
    public void onResume() {
        super.onResume();
        getAllPasswords();
    }
}
