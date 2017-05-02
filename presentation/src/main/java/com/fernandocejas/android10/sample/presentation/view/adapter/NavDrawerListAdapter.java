package com.fernandocejas.android10.sample.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.model.CategoryModel;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavDrawerListAdapter extends RecyclerView.Adapter<NavDrawerListAdapter.NavDrawerViewHolder> {

    private final LayoutInflater layoutInflater;
    private NavDrawerListAdapter.OnItemClickListener onItemClickListener;
    private List<CategoryModel> list;

    @Inject
    NavDrawerListAdapter(Context context) {
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = Collections.emptyList();
    }

    @Override
    public int getItemCount() {
        return (this.list != null) ? this.list.size() : 0;
    }

    @Override
    public NavDrawerListAdapter.NavDrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = this.layoutInflater.inflate(R.layout.row_drawer_item, parent, false);
        return new NavDrawerListAdapter.NavDrawerViewHolder(view);
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

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setOnItemClickListener(NavDrawerListAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setCategoriesList(List<CategoryModel> categoryModels) {
        if (categoryModels == null) {
            return;
        }
        this.list = categoryModels;
        notifyDataSetChanged();
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
