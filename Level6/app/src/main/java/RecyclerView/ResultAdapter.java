package RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.level6.DetailActivity;
import com.android.level6.MainActivity;
import com.android.level6.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Model.Result;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {
    private List<Result> mResults;
    private String imagePath;
    private Context mContext;

    public ResultAdapter(Context context, List<Result> mResults) {
        this.mContext = context;
        this.mResults = mResults;
    }

    @NonNull
    @Override
    public ResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        imagePath = "http://image.tmdb.org/t/p/w185";
        View view = inflater.inflate(R.layout.row_cell_movie, null);
        ResultAdapter.ViewHolder viewHolder = new ResultAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ResultAdapter.ViewHolder viewHolder, int i) {
        String correctPlace = i+1+"";
        viewHolder.textView.setText(correctPlace);
        Picasso.get().load(imagePath+mResults.get(i).getPosterPath()).into(viewHolder.imageView);
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("passedMovie", mResults.get(i));
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    public List<Result> getmResults() {
        return mResults;
    }

    public void setmResults(List<Result> mResults) {
        this.mResults.clear();
        List res = new ArrayList();
        res.addAll(mResults);
        this.mResults = res;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}


