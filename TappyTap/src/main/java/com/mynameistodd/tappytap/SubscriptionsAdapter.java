package com.mynameistodd.tappytap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by todd on 9/30/13.
 */
public class SubscriptionsAdapter extends ArrayAdapter<Subscription> {

    private Context context;
    private List<Subscription> objects;

    public SubscriptionsAdapter(Context context, int resource, List<Subscription> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_subscription_item, null);

        TextView subscribedTo = (TextView)rowView.findViewById(R.id.itemSubscriptionName);

        Subscription sub = objects.get(position);

        subscribedTo.setText(sub.getName() + sub.getId());

        return rowView;
    }
}
