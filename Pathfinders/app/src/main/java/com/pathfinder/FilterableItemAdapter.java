package com.pathfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * General purpose adapter for a List<FilterableItem>
 * Connor Reeder
 */

public class FilterableItemAdapter<T extends FilterableItem> extends BaseAdapter implements Filterable{
    private static final String TAG = "BaseAdapter";
    private Context context;
    private List<T> origList;
    private List<T> filteredList;
    ItemFilter filter;

    public FilterableItemAdapter(Context context, List<T> items) {
        this.context = context;
        this.origList = items;
        this.filteredList = origList;
    }
    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public T getItem(int i) {
        return filteredList.get(i);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String name = filteredList.get(position).valueToFiler();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        }
        TextView bldgName = (TextView) convertView.findViewById(R.id.itemName);
        bldgName.setText(name);
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (filter == null)
            filter = new ItemFilter();
        return filter;
    }
    private class ItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                results.values = origList;
                results.count = origList.size();
            } else {
                filteredList = new ArrayList<T>();
                for (int i = 0; i < origList.size(); i++) {
                    String value = origList.get(i).valueToFiler().toLowerCase();
                    if (value.contains(constraint.toString())) {
                        filteredList.add(origList.get(i));
                    }
                }
                results.values = filteredList;
                results.count = filteredList.size();
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            notifyDataSetChanged();

        }
    }
}
