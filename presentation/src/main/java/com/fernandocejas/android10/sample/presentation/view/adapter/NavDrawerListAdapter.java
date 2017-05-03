package com.fernandocejas.android10.sample.presentation.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.model.CategoryModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavDrawerListAdapter extends BaseAdapter<NavDrawerListAdapter.NavDrawerViewHolder, CategoryModel> {

    private final int selectedBackgroundColor;
    private final int unSelectedBackgroundColor;
    private NavDrawerListAdapter.OnItemClickListener onItemClickListener;
    private int selectedCategoryId;

    @Inject
    NavDrawerListAdapter(Context context) {
        super(context);
        selectedBackgroundColor = ContextCompat.getColor(context, R.color.item_background);
        unSelectedBackgroundColor = ContextCompat.getColor(context, R.color.item_background_unselected);
    }

    @Override
    public void onBindViewHolder(final NavDrawerListAdapter.NavDrawerViewHolder holder, final int position) {
        final CategoryModel categoryModel = this.list.get(position);
        if (selectedCategoryId == categoryModel.getId()) {
            holder.itemView.setBackgroundColor(selectedBackgroundColor);
        } else {
            holder.itemView.setBackgroundColor(unSelectedBackgroundColor);
        }
        holder.textViewTitle.setText(categoryModel.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NavDrawerListAdapter.this.onItemClickListener != null) {
                    NavDrawerListAdapter.this.onItemClickListener.onItemClicked(categoryModel);
                    selectedCategoryId = categoryModel.getId();
                    notifyDataSetChanged();
                }
            }
        });
    }

    public void setSelectedCategoryId(int categoryId) {
        selectedCategoryId = categoryId;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(NavDrawerListAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected int getItemLayout() {
        return R.layout.row_drawer_item;
    }

    @Override
    protected NavDrawerViewHolder getViewHolder(View view) {
        return new NavDrawerViewHolder(view);
    }

    public interface OnItemClickListener {
        void onItemClicked(CategoryModel categoryModel);
    }

    static class NavDrawerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView textViewTitle;

        NavDrawerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
