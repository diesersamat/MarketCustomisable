package com.fernandocejas.android10.sample.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.model.ContactDetailModel;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressListAdapter extends BaseAdapter<AddressListAdapter.AddressViewHolder, ContactDetailModel> {
    private final int accentColor;
    private final int primaryColor;
    private final int textColor;
    private final int backgroundColor;
    private OnItemClickListener onItemClickListener;

    @Inject
    AddressListAdapter(Context context, @Named("accentColor") int accentColor,
                       @Named("primaryColor") int primaryColor,
                       @Named("backgroundColor") int backgroundColor,
                       @Named("textColor") int textColor) {
        super(context);
        this.accentColor = accentColor;
        this.primaryColor = primaryColor;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

    @Override
    public void onBindViewHolder(AddressListAdapter.AddressViewHolder holder, int position) {
        final ContactDetailModel contactDetailModel = this.list.get(position);
        holder.address.setText(contactDetailModel.getAddress());
        holder.name.setText(contactDetailModel.getAddress());
        holder.phone.setText(contactDetailModel.getPhone());

        holder.address.setTextColor(textColor);
        holder.name.setTextColor(textColor);
        holder.phone.setTextColor(textColor);

        if (contactDetailModel.isDefault()) {
            holder.itemView.setBackgroundColor(primaryColor);
        } else {
            holder.itemView.setBackgroundColor(backgroundColor);
        }

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClicked(contactDetailModel.getId());
            }
        });
    }

    public void setOnItemClickListener(AddressListAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected int getItemLayout() {
        return R.layout.address_list_item;
    }

    @Override
    protected AddressListAdapter.AddressViewHolder getViewHolder(View view) {
        return new AddressViewHolder(view);
    }


    public interface OnItemClickListener {
        void onItemClicked(int id);
    }

    class AddressViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.address)
        TextView address;
        @BindView(R.id.phone)
        TextView phone;

        AddressViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
