package Recycler;

import android.view.View;
//interface that is used for onclick in the recyclerview
public interface RecyclerViewClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
