package com.example.footlongsubs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Custom Adapter provides the adapter to add the Subscriptions in the AllSubscriptions object
 * to the ListView display
 *
 * Based on https://guides.codepath.com/android/Using-a-BaseAdapter-with-ListView
 * Retrieved 2018-02-05
 */
public class CustomAdapter extends BaseAdapter {
    private Context context;
    private AllSubscriptions subList;

    //public constructor
    public CustomAdapter(Context context, AllSubscriptions subList) {
        this.context = context;
        this.subList = subList;
    }

    @Override
    public int getCount() {
        return subList.getSize(); //returns number of subscriptions in subList
    }

    @Override
    public Subscription getItem(int position) {
        return subList.getSub(position); //returns subscription at specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get the view for the listView object.
     *
     * @param position integer representing position in ListView
     * @param convertView current View
     * @param parent parent ViewGroup
     * @return View
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.list_item, parent, false);
        }

        // get current item to be displayed
        Subscription currentItem = getItem(position);

        // get TextView objects
        TextView nameTextView = (TextView) convertView.findViewById(R.id.name);
        TextView dateTextView = (TextView) convertView.findViewById(R.id.date);
        TextView chargeTextView = (TextView) convertView.findViewById(R.id.charge);

        // get Subscription information and display in textViews
        nameTextView.setText(currentItem.getName());
        dateTextView.setText(currentItem.getDate_s());
        chargeTextView.setText(currentItem.getCharge_s());

        // returns the view for the current row
        return convertView;
    }
}


