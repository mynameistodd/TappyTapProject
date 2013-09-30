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
public class MyArrayAdapter extends ArrayAdapter<Message> {

    private Context context;
    private List<Message> objects;

    public MyArrayAdapter(Context context, int resource, List<Message> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_message_item, null);
        TextView message = (TextView)rowView.findViewById(R.id.itemMessageText);
        TextView sender = (TextView)rowView.findViewById(R.id.itemSender);
        TextView dateReceived = (TextView)rowView.findViewById(R.id.itemDateReceived);

        Message msg = objects.get(position);

        message.setText(msg.getMessageText());
        sender.setText(msg.getSender());
        dateReceived.setText(msg.getReceived());

        return rowView;
        //return super.getView(position, convertView, parent);
    }
}
