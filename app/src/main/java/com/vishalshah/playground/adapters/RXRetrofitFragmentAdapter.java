package com.vishalshah.playground.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vishalshah.playground.R;
import com.vishalshah.playground.models.ProductModelRXRetrofit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vishalshah on 08/07/16.
 */
public class RXRetrofitFragmentAdapter extends RecyclerView.Adapter<RXRetrofitFragmentAdapter.ViewHolder>{

    private List<ProductModelRXRetrofit> productList;
    private Context context;

    public RXRetrofitFragmentAdapter(List<ProductModelRXRetrofit> productList, Context context) {
        if (productList == null) {
            this.productList = new ArrayList<>();
        } else {
            this.productList = productList;
        }
        this.context = context;
    }

    public void addProduct(ProductModelRXRetrofit product) {
        productList.add(product);
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
        ProductModelRXRetrofit product = productList.get(position);
        holder.itemNameTextView.setText(product.getName());
        holder.itemPriceTextView.setText(String.valueOf(product.getPrice()));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView itemNameTextView;
        public TextView itemPriceTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            itemNameTextView = (TextView) itemView.findViewById(R.id.text_view_item_name_rx_retorofit);
            itemPriceTextView = (TextView) itemView.findViewById(R.id.text_view_item_price_rx_retorofit);
        }
    }
}
