package com.fernandocejas.android10.sample.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.model.OrderItemModel;
import com.fernandocejas.android10.sample.presentation.model.OrderModel;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderListAdapter extends BaseAdapter<OrderListAdapter.OrderViewHolder, OrderModel> {

    private final int accentColor;
    private final int primaryColor;
    private final int textColor;
    private final String orderN;
    private final String notPaid;
    private final String paid;
    private final String closed;
    private final boolean isPaymentAvailable;
    private OrderListAdapter.OnItemClickListener onItemClickListener;

    @Inject
    OrderListAdapter(Context context, @Named("accentColor") int accentColor,
                     @Named("primaryColor") int primaryColor, @Named("textColor") int textColor,
                     boolean isPaymentAvailable) {
        super(context);
        orderN = context.getResources().getString(R.string.order_numb);
        notPaid = context.getResources().getString(R.string.not_paid);
        paid = context.getResources().getString(R.string.paid);
        closed = context.getResources().getString(R.string.closed);
        this.accentColor = accentColor;
        this.primaryColor = primaryColor;
        this.textColor = textColor;
        this.isPaymentAvailable = isPaymentAvailable;
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        final OrderModel orderItemModel = this.list.get(position);

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault());
        try {
            Date date = formatDate.parse(orderItemModel.getDate());
            holder.orderDate.setText(DateFormat.getDateInstance().format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.orderNumber.setText(orderN + orderItemModel.getId());

        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
        format.setCurrency(Currency.getInstance("RUB"));
        String result = format.format(orderItemModel.getTotalPrice());
        holder.orderTotal.setText(result);

        int status = orderItemModel.getStatus();
        String statusTxt = "";
        switch (status) {
            case 0:
                statusTxt = notPaid;
                holder.payNowButton.setVisibility(View.VISIBLE);
                break;
            case 1:
                statusTxt = paid;
                holder.payNowButton.setVisibility(View.GONE);
                break;
            case 2:
                statusTxt = closed;
                holder.payNowButton.setVisibility(View.GONE);
                break;
        }
        if (!isPaymentAvailable) {
            holder.payNowButton.setVisibility(View.GONE);
        }
        holder.statusText.setText(statusTxt);
        holder.bottom.setBackgroundColor(accentColor);

        holder.orderNumber.setTextColor(primaryColor);
        holder.statusText.setTextColor(textColor);
        holder.orderTotal.setTextColor(textColor);
        holder.orderDate.setTextColor(textColor);

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClicked(orderItemModel.getOrderItems());
            }
        });
        holder.payNowButton.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onPayClick(orderItemModel.getId(), orderItemModel.getTotalPrice());
            }
        });
    }

    public void setOnItemClickListener(OrderListAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected int getItemLayout() {
        return R.layout.order_list_item;
    }

    @Override
    protected OrderViewHolder getViewHolder(View view) {
        return new OrderViewHolder(view);
    }


    public interface OnItemClickListener {
        void onItemClicked(List<OrderItemModel> models);

        void onPayClick(int categoryId, double totalPrice);
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.order_number)
        TextView orderNumber;
        @BindView(R.id.order_date)
        TextView orderDate;
        @BindView(R.id.status_text)
        TextView statusText;
        @BindView(R.id.pay_now_button)
        Button payNowButton;
        @BindView(R.id.order_total)
        TextView orderTotal;
        @BindView(R.id.bottom)
        View bottom;

        OrderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
