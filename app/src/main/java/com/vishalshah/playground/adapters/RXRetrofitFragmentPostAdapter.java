package com.vishalshah.playground.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vishalshah.playground.R;
import com.vishalshah.playground.models.PostModelRXRetrofit;
import com.vishalshah.playground.models.PostModelRXRetrofit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vishalshah on 08/07/16.
 */
public class RXRetrofitFragmentPostAdapter extends RecyclerView.Adapter<RXRetrofitFragmentPostAdapter.ViewHolder>{

    private List<PostModelRXRetrofit> postList;
    private Context context;

    public RXRetrofitFragmentPostAdapter(List<PostModelRXRetrofit> postList, Context context) {
        if (postList == null) {
            this.postList = new ArrayList<>();
        } else {
            this.postList = postList;
        }
        this.context = context;
    }

    public void addPost(PostModelRXRetrofit product) {
        postList.add(product);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rx_retrofit, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PostModelRXRetrofit post = postList.get(position);
        holder.itemTitleTextView.setText(post.getTitle());
        holder.itemBodyTextView.setText(String.valueOf(post.getBody()));
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public void clearContents() {
        postList.clear();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView itemTitleTextView;
        public TextView itemBodyTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            itemTitleTextView = (TextView) itemView.findViewById(R.id.text_view_item_name_rx_retorofit);
            itemBodyTextView = (TextView) itemView.findViewById(R.id.text_view_item_price_rx_retorofit);
        }
    }
}
