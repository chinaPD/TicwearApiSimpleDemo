package com.mobvoi.ticwear.apisimpledemo;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by pd on 16-4-21.
 */
public class ListAdapter extends WearableListView.Adapter{
    private final Context context;
    private final LayoutInflater inflater;
    private String[] dataSet;

    public ListAdapter(Context context, String[] dataSet){
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
        this.dataSet = dataSet;
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        return new ItemViewHolder(inflater.inflate(R.layout.list_item,null));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder,
                                 int position){

        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        TextView view = itemViewHolder.textView;
        view.setText(dataSet[position]);
        ((ItemViewHolder) holder).itemView.setTag(position);
    }


    @Override
    public int getItemCount(){
        return dataSet.length;
    }

    public static class ItemViewHolder extends WearableListView.ViewHolder {
        private TextView textView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.name);
        }
    }
}