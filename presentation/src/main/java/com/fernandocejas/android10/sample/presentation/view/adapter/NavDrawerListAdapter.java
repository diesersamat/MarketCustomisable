package com.fernandocejas.android10.sample.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.model.CategoryModel;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavDrawerListAdapter extends BaseAdapter<NavDrawerListAdapter.NavDrawerViewHolder, CategoryModel> {

    private int accentColor;
    private int backgroundColor;
    private NavDrawerListAdapter.OnItemClickListener onItemClickListener;
    private int selectedCategoryId;
    private int textColor;

    @Inject
    NavDrawerListAdapter(Context context, @Named("accentColor") int accentColor,
                         @Named("backgroundColor") int backgroundColor, @Named("textColor") int textColor) {
        super(context);
        this.accentColor = accentColor;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

    @Override
    public void onBindViewHolder(final NavDrawerListAdapter.NavDrawerViewHolder holder, final int position) {
        final CategoryModel categoryModel = this.list.get(position);
        if (selectedCategoryId == categoryModel.getId()) {
            holder.itemView.setBackgroundColor(accentColor);
        } else {
            holder.itemView.setBackgroundColor(backgroundColor);
        }
        holder.textViewTitle.setTextColor(textColor);
        holder.textViewTitle.setText(categoryModel.getName());
        holder.itemView.setOnClickListener(v -> {
            if (NavDrawerListAdapter.this.onItemClickListener != null) {
                NavDrawerListAdapter.this.onItemClickListener.onItemClicked(categoryModel);
                selectedCategoryId = categoryModel.getId();
                notifyDataSetChanged();
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
