package com.fernandocejas.android10.sample.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

abstract class BaseAdapter<T extends RecyclerView.ViewHolder, H> extends RecyclerView.Adapter<T> {

    private final LayoutInflater layoutInflater;
    protected List<H> list;

    BaseAdapter(Context context) {
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = Collections.emptyList();
    }

    @Override
    public int getItemCount() {
        return (this.list != null) ? this.list.size() : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public void setList(List<H> list) {
        if (list == null) {
            return;
        }
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = this.layoutInflater.inflate(getItemLayout(), parent, false);
        return getViewHolder(view);
    }

    protected abstract int getItemLayout();

    protected abstract T getViewHolder(View view);
}
