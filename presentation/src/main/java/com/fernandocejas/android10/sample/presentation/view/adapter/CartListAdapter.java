package com.fernandocejas.android10.sample.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.model.ProductDescriptionModel;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartListAdapter extends BaseAdapter<CartListAdapter.ProductViewHolder, ProductDescriptionModel> {

    private final int accentColor;
    private final int primaryColor;
    private CartListAdapter.OnItemClickListener onItemClickListener;

    @Inject
    CartListAdapter(Context context, @Named("accentColor") int accentColor,
                    @Named("primaryColor") int primaryColor) {
        super(context);
        this.accentColor = accentColor;
        this.primaryColor = primaryColor;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, final int position) {
        final ProductDescriptionModel productDescriptionModel = this.list.get(position);
        holder.productPrice.setText(String.format("%s%s",
                productDescriptionModel.getPrice(), productDescriptionModel.getCurrency()));
        holder.productTitle.setText(productDescriptionModel.getName());
        requestManager.load(productDescriptionModel.getLinkToImage()).into(holder.photo);
        holder.itemView.setOnClickListener(v -> {
            if (CartListAdapter.this.onItemClickListener != null) {
                CartListAdapter.this.onItemClickListener.onItemClicked(productDescriptionModel);
            }
        });
        holder.itemView.setBackgroundColor(primaryColor);
    }

    public void setOnItemClickListener(CartListAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected int getItemLayout() {
        return R.layout.cart_list_item;
    }

    @Override
    protected ProductViewHolder getViewHolder(View view) {
        return new ProductViewHolder(view);
    }

    public interface OnItemClickListener {
        void onItemClicked(ProductDescriptionModel categoryModel);
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.photo_product)
        ImageView photo;
        @BindView(R.id.title)
        TextView productTitle;
        @BindView(R.id.price)
        TextView productPrice;

        ProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
