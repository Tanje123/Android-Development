package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

//With this app is a bucket list app where the user can add things to his bucket list.
//and keep track of them by crossing them deleting them and adding new things to his bucket list
//Made by Tanveer Singh

//MainActivity class
public class MainActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener,itemClickListner {
    private ItemRoomDatabase db;
    private Executor executor = Executors.newSingleThreadExecutor();
    private List<Item> bucketList  = new ArrayList<>();;
    private RecyclerView rvItemList;
    private itemListAdapter itemListAdapter;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setup the database
        db = ItemRoomDatabase.getDatabase(this);
        //setup all of the components of the mainactivity
        setupToolbar();
        setupRecycler();
        setupGestureDetector();
        getAllProducts();
        initFloatingActionButton();
    }

    //onResume method that runs when the mainactivity is opened or resumed
    @Override
    public void onResume(){
        super.onResume();
        //get all of the products
        getAllProducts();
        //get the item that needs to be added from the other activity
        Intent i = getIntent();
        //get the item from the detailscreen
        Item item1 = detailScreen.item;
        if ((item1 == null)) {
            //when the item1 is null dont do anything
        } else {
            //if the item exist insert the product into the db
            insertProduct(item1);
        }
        //getAll of the products refresh
        getAllProducts();
    }

    /**
     * Setup the Add Button.
     */
    private void initFloatingActionButton() {
        //Find the fab from the activity
        FloatingActionButton fab = findViewById(R.id.fab);
        //set a onclicklistnerer
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //when the fab is clicked open the new activity
                Intent myIntent = new Intent(MainActivity.this, detailScreen.class);
                MainActivity.this.startActivity(myIntent);
            }
        });
    }
    /**
     * Insert a product into the database and update the UI.
     * @param item Product to be inserted
     */
    public void insertProduct(final Item item) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.productDao().insert(item);
                getAllProducts();
                runOnUiThread(new Runnable() { // Optionally clear the text from the input field
                    @Override
                    public void run() {
                    }
                });
            }
        });
    }

    //delete product from db
    private void deleteProduct(final Item item) {
        executor.execute(new Runnable() {

            @Override

            public void run() {
                db.productDao().delete(item);
                getAllProducts();
            }

        });
    }
    //update a existingproduct
    private void updateProduct(final Item item) {
        executor.execute(new Runnable() {

            @Override

            public void run() {
                db.productDao().update(item);
               getAllProducts();
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //when the bin is selected delete all the products
        deleteAllProducts();
        return super.onOptionsItemSelected(item);
    }

    //get all of the products
    private void getAllProducts() {
        executor.execute(new Runnable() {

            @Override

            public void run() {
                final List<Item> products = db.productDao().getAllProducts();
                runOnUiThread(new Runnable() {

                    @Override

                    public void run() {
                        updateUI(products);

                    }

                });

            }
        });
    }
    //setup the recyclerview
    private void setupRecycler() {
        //find the recyclerview
        rvItemList = findViewById(R.id.recyclerView);
        //add a adapter
        itemListAdapter = new itemListAdapter(bucketList, this);
        //add the layoutmanger
        rvItemList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //set the adapter of to the rv
        rvItemList.setAdapter(itemListAdapter);
        //add the divider under each item
        rvItemList.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        rvItemList.addOnItemTouchListener(this);
    }

    private void setupGestureDetector() {
        // Delete an item from the shopping list on long press.
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                //get the position
                View child = rvItemList.findChildViewUnder(e.getX(), e.getY());
                //check if there is a item on the place where is pressed
                if (child != null) {
                    //delete the recyclerlist using the position
                    int adapterPosition = rvItemList.getChildAdapterPosition(child);
                    deleteProduct(bucketList.get(adapterPosition));
                }
            }
        });
    }

    //setup the toolbar
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    //delete all of the products
    private void deleteAllProducts() {

        executor.execute(new Runnable() {

            @Override

            public void run() {
                //delete all of the products using getAllofthe products
                db.productDao().delete(db.productDao().getAllProducts());
              //  db.productDao().getAllProducts();
                getAllProducts();

            }

        });

    }
    //updat the ui
    private  void updateUI(List<Item> products) {
        //clear the current list
       bucketList.clear();
       //add the items from the db
       bucketList.addAll(products);
       //tell the adapter that the data changed
        itemListAdapter.notifyDataSetChanged();

    }

    //Gesture detector on only the checkbox
    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {


    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }

    //when the checkbox is clicked
    @Override
    public void onPlusClick(Item item) {
        //if the item status is done sett is back to false (not done)
       if (item.getCompleted().equals(true)) {
           item.setCompleted(false);
           //if the item status is not done set it to true (done)
       } else if (item.getCompleted().equals(false)) {
            item.setCompleted(true);
        }
        //update the product with the new details
        updateProduct(item);
    }


}
