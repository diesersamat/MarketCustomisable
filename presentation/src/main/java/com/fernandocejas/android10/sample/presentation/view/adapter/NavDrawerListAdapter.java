package com.fernandocejas.android10.sample.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.model.CategoryModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavDrawerListAdapter extends BaseAdapter<NavDrawerListAdapter.NavDrawerViewHolder, CategoryModel> {

    private NavDrawerListAdapter.OnItemClickListener onItemClickListener;

    @Inject
    NavDrawerListAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(NavDrawerListAdapter.NavDrawerViewHolder holder, final int position) {
        final CategoryModel categoryModel = this.list.get(position);
        holder.textViewTitle.setText(categoryModel.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NavDrawerListAdapter.this.onItemClickListener != null) {
                    NavDrawerListAdapter.this.onItemClickListener.onItemClicked(categoryModel);
                }
            }
        });
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
