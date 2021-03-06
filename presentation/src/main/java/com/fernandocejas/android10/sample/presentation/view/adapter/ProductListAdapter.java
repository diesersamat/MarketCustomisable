package com.fernandocejas.android10.sample.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.model.ProductWrapperModel;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListAdapter extends BaseAdapter<ProductListAdapter.ProductViewHolder, ProductWrapperModel> {

    private final int accentColor;
    private final int primaryColor;
    private final int textColor;
    private final Context context;
    private ProductListAdapter.OnItemClickListener onItemClickListener;

    @Inject
    ProductListAdapter(Context context, @Named("accentColor") int accentColor,
                       @Named("primaryColor") int primaryColor, @Named("textColor") int textColor) {
        super(context);
        this.accentColor = accentColor;
        this.primaryColor = primaryColor;
        this.textColor = textColor;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, final int position) {
        final ProductWrapperModel productDescriptionModel = this.list.get(position);

        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
        format.setCurrency(Currency.getInstance(productDescriptionModel.getProduct().getCurrency()));
        String result = format.format(productDescriptionModel.getProduct().getPrice());
        holder.productPrice.setText(result);
        holder.productTitle.setText(productDescriptionModel.getProduct().getName());

        if (productDescriptionModel.getImages() != null) {
            if (!productDescriptionModel.getImages().isEmpty()) {
                if (productDescriptionModel.getImages().get(0) != null) {
                    requestManager.load(productDescriptionModel.getImages().get(0).getUrl()).into(holder.photo);
                }
            }
        }

        holder.itemView.setOnClickListener(v -> {
            if (this.onItemClickListener != null) {
                this.onItemClickListener.onItemClicked(productDescriptionModel);
            }
        });

        holder.addToCart.setOnClickListener(v -> {
            if (this.onItemClickListener != null) {
                this.onItemClickListener.onCartClicked(holder.addToCart, productDescriptionModel);
            }
        });

        holder.itemView.setBackgroundColor(accentColor);
        holder.productTitle.setTextColor(textColor);
        holder.productPrice.setTextColor(textColor);
    }

    public void setOnItemClickListener(ProductListAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected int getItemLayout() {
        return R.layout.product_list_item;
    }

    @Override
    protected ProductViewHolder getViewHolder(View view) {
        return new ProductViewHolder(view);
    }

    public interface OnItemClickListener {
        void onItemClicked(ProductWrapperModel categoryModel);

        void onCartClicked(View addToCart, ProductWrapperModel productDescriptionModel);

    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.photo_product)
        ImageView photo;
        @BindView(R.id.title)
        TextView productTitle;
        @BindView(R.id.price)
        TextView productPrice;
        @BindView(R.id.add_to_cart)
        View addToCart;

        ProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
