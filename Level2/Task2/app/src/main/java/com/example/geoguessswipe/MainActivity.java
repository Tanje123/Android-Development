package com.example.geoguessswipe;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener  {
    List<GeoObject> mGeoObjects;
    private GestureDetector mGestureDetector;
    private GestureDetector leftGestureDetector;
    private GestureDetector rightGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGeoObjects = new ArrayList<>();

        for (int i = 0; i < GeoObject.PRE_DEFINED_GEO_OBJECT_NAMES.length; i++) {
            mGeoObjects.add(new GeoObject(GeoObject.PRE_DEFINED_GEO_OBJECT_NAMES[i]
                    ,GeoObject.PRE_DEFINED_GEO_OBJECT_IMAGE_IDS[i],
                    GeoObject.PRE_DEFINED_GEO_OBJECT_BOOLEAN[i]));
        }
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnItemTouchListener(this);
        GeoObjectAdapter mAdapter = new GeoObjectAdapter(this, mGeoObjects);
        recyclerView.setAdapter(mAdapter);

        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        leftGestureDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {

            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
               final int SWIPE_THRESHOLD = 100;
                final int SWIPE_VELOCITY_THRESHOLD = 100;

                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    System.out.println("FIRST POINT: LEFT"+diffY+" SECOND POINT "+diffX);
                    System.out.println(" LEFT"+Math.abs(diffX)+" SECOND POINT "+diffY);
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            }
                            result = true;
                        }
                    }
                    else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom();
                        } else {
                            onSwipeTop();
                        }
                        result = true;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
            public void onSwipeRight() {
            }

            public void onSwipeLeft() {
            }

            public void onSwipeTop() {
            }

            public void onSwipeBottom() {
            }

        });

        rightGestureDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {

            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                final int SWIPE_THRESHOLD = 100;
                final int SWIPE_VELOCITY_THRESHOLD = 100;

                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    System.out.println("FIRST POINT: RIGHT"+diffY+" SECOND POINT "+diffX);
                    System.out.println(" RIGHT"+Math.abs(diffX)+" SECOND POINT "+diffY);
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                                result = true;
                            }
                        }
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
            public void onSwipeRight() {
            }

            public void onSwipeLeft() {
            }

            public void onSwipeTop() {
            }

            public void onSwipeBottom() {
            }

        });

    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        int mAdapterPosition = rv.getChildAdapterPosition(child);
        if (child != null && mGestureDetector.onTouchEvent(e)) {
            Toast.makeText(this, mGeoObjects.get(mAdapterPosition).getmGeoName(), Toast.LENGTH_SHORT).show();
        }

        if (child != null && leftGestureDetector.onTouchEvent(e)) {
            if (mGeoObjects.get(mAdapterPosition).getInEurope().equals(true)) {
                Toast.makeText(this, "WRONG, THIS IS IN EUROPE ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "CORRECT, THIS IS NOT IN EUROPE ", Toast.LENGTH_SHORT).show();
            }
        }

        if (child != null && rightGestureDetector.onTouchEvent(e)) {
            if (mGeoObjects.get(mAdapterPosition).getInEurope().equals(true)) {
                Toast.makeText(this, "CORRECT, THIS IS IN EUROPE ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "WRONG, THIS IS NOT IN EUROPE ", Toast.LENGTH_SHORT).show();
            }        }

        return false;
    }



    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }
}
