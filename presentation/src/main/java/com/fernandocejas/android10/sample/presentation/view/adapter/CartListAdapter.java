package com.fernandocejas.android10.sample.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.model.CartItemModel;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartListAdapter extends BaseAdapter<CartListAdapter.ProductViewHolder, CartItemModel> {

    private final int accentColor;
    private final int primaryColor;
    private final int textColor;
    private CartListAdapter.OnItemClickListener onItemClickListener;

    @Inject
    CartListAdapter(Context context, @Named("accentColor") int accentColor,
                    @Named("primaryColor") int primaryColor, @Named("textColor") int textColor) {
        super(context);
        this.accentColor = accentColor;
        this.primaryColor = primaryColor;
        this.textColor = textColor;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, final int position) {
        final CartItemModel productDescriptionModel = this.list.get(position);

        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
        format.setCurrency(Currency.getInstance("RUB"));
        String result = format.format(productDescriptionModel.getPrice());
        holder.productPrice.setText(result);
        holder.productTitle.setText(productDescriptionModel.getName());
        requestManager.load(productDescriptionModel.getPhotos()).into(holder.photo);
        holder.itemView.setOnClickListener(v -> {
            if (this.onItemClickListener != null) {
                this.onItemClickListener.onItemClicked(productDescriptionModel);
            }
        });

        holder.addButton.setOnClickListener(v -> {
            if (this.onItemClickListener != null) {
                this.onItemClickListener.onItemAddClick(productDescriptionModel);
            }
        });
        holder.removeButton.setOnClickListener(v -> {
            if (this.onItemClickListener != null) {
                this.onItemClickListener.onItemRemoveClick(productDescriptionModel);
            }
        });

        holder.itemView.setBackgroundColor(accentColor);
        holder.productPrice.setTextColor(textColor);
        holder.productTitle.setTextColor(textColor);
        holder.count.setText(String.format("%dialog", productDescriptionModel.getCount()));
        holder.count.setTextColor(textColor);
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
        void onItemClicked(CartItemModel categoryModel);

        void onItemAddClick(CartItemModel productDescriptionModel);

        void onItemRemoveClick(CartItemModel productDescriptionModel);
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.photo_product)
        ImageView photo;
        @BindView(R.id.title)
        TextView productTitle;
        @BindView(R.id.price)
        TextView productPrice;
        @BindView(R.id.count)
        TextView count;
        @BindView(R.id.remove_button)
        ImageView removeButton;
        @BindView(R.id.add_button)
        ImageView addButton;


        ProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
