package com.mynameistodd.tappytap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.List;

public class SubscriptionsFragment extends ListFragment {

    public SubscriptionsFragment() {

    }

    Context context;
    private ArrayAdapter<Subscription> adapter;
    private List<Subscription> subscriptions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();

        subscriptions = MySQLiteOpenHelper.getAllSubscriptions(context);

        adapter = new SubscriptionsAdapter(context, R.layout.list_subscriptions, subscriptions);
        setListAdapter(adapter);
        setEmptyText(context.getString(R.string.no_subscriptions));

        Bundle args = getArguments();
        if (args != null && !args.isEmpty())
        {
            DialogFragment followDialog = new FollowDialogFragment();
            followDialog.setArguments(args);
            followDialog.show(getFragmentManager(),"followDialog");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.list_subscriptions, menu);
    }

    public class FollowDialogFragment extends DialogFragment
    {
        String followName = "empty";

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            followName = getArguments().getString("followName");

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Follow \"" + followName + "\"?")
                    .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MySQLiteOpenHelper.insertSubscription(context, followName);
                            Toast.makeText(getActivity(), getString(R.string.follow_yes), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getActivity(), getString(R.string.follow_no), Toast.LENGTH_SHORT).show();
                        }
                    });
            return builder.create();
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            super.onDismiss(dialog);
            subscriptions = MySQLiteOpenHelper.getAllSubscriptions(context);

            adapter.clear();
            adapter.addAll(subscriptions);
        }
    }
}
