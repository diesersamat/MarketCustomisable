package com.fernandocejas.android10.sample.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.model.OrderItemModel;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemsDialogAdapter extends BaseAdapter<ItemsDialogAdapter.ProductViewHolder, OrderItemModel> {

    private final int accentColor;
    private final int primaryColor;
    private final int textColor;
    private OnItemClickListener onItemClickListener;

    @Inject
    ItemsDialogAdapter(Context context,
                       List<OrderItemModel> orderItemModels,
                       @Named("accentColor") int accentColor,
                       @Named("primaryColor") int primaryColor,
                       @Named("textColor") int textColor) {
        super(context);
        setList(orderItemModels);
        this.accentColor = accentColor;
        this.primaryColor = primaryColor;
        this.textColor = textColor;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        final OrderItemModel orderItemModel = this.list.get(position);
        holder.title.setText(orderItemModel.getProductName());

        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
        format.setCurrency(Currency.getInstance("RUB"));
        String result = format.format(orderItemModel.getProductPrice());
        holder.price.setText(result);

        holder.count.setText(String.valueOf(orderItemModel.getQuantity()));

        holder.price.setTextColor(textColor);
        holder.title.setTextColor(textColor);
        holder.count.setTextColor(textColor);

        holder.itemView.setBackgroundColor(accentColor);
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClicked(orderItemModel.getProductId());
            }
        });
    }

    public void setOnItemClickListener(ItemsDialogAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected int getItemLayout() {
        return R.layout.dialog_list_item;
    }

    @Override
    protected ProductViewHolder getViewHolder(View view) {
        return new ProductViewHolder(view);
    }

    public interface OnItemClickListener {
        void onItemClicked(int productId);
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.count)
        TextView count;


        public ProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
