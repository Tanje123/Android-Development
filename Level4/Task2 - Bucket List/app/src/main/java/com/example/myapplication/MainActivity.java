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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = ItemRoomDatabase.getDatabase(this);

        rvItemList = findViewById(R.id.recyclerView);
        itemListAdapter = new itemListAdapter(bucketList, this);
        rvItemList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvItemList.setAdapter(itemListAdapter);
        rvItemList.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        rvItemList.addOnItemTouchListener(this);
        rvItemList.addOnItemTouchListener(this);
        getAllProducts();
        initFloatingActionButton();


        // Delete an item from the shopping list on long press.

        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                View child = rvItemList.findChildViewUnder(e.getX(), e.getY());
                if (child != null) {
                    int adapterPosition = rvItemList.getChildAdapterPosition(child);
                    deleteProduct(bucketList.get(adapterPosition));
                }
            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();
        getAllProducts();
        Intent i = getIntent();
        System.out.println("DIT RUNT WELT");
        Item item = (Item) i.getSerializableExtra("sampleObject");
        Item item1 = detailScreen.item;
        if ((item1 == null)) {
            System.out.println(" DSADSAAAAAAAAAAAA");
        } else {
            insertProduct(item1);
        }
        getAllProducts();
    }

    /**
     * Setup the Add Button.
     */
    private void initFloatingActionButton() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, detailScreen.class);
                MainActivity.this.startActivity(myIntent);
            //    String input = etInput.getText().toString();
             //   String input2 = etInput2.getText().toString();
             //   final Item item = new Item(input2,input2);

              //  insertProduct(item);
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


    private void deleteProduct(final Item item) {
        executor.execute(new Runnable() {

            @Override

            public void run() {
                db.productDao().delete(item);
                getAllProducts();
            }

        });
    }

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
        deleteAllProducts();
        return super.onOptionsItemSelected(item);
    }

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

    private void deleteAllProducts() {

        executor.execute(new Runnable() {

            @Override

            public void run() {

                db.productDao().delete(db.productDao().getAllProducts());
              //  db.productDao().getAllProducts();
                getAllProducts();

            }

        });

    }

    private  void updateUI(List<Item> products) {
       bucketList.clear();
        bucketList.addAll(products);
        itemListAdapter.notifyDataSetChanged();

    }

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

    public List<Item> getBucketList() {
        return bucketList;
    }


    @Override
    public void onPlusClick(Item item) {
        System.out.println("DOITASDSADSD");
        if (item.getCompleted().equals(true)) {
           item.setCompleted(false);
        } else if (item.getCompleted().equals(false)) {
            item.setCompleted(true);
        }
        else {
        }
        System.out.println(item.getCompleted()+" COMPLETED");
        updateProduct(item);
    }


}
