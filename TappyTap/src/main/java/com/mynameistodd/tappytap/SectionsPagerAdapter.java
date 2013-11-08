package com.mynameistodd.tappytap;

/**
 * Created by todd on 10/17/13.
 */

import android.content.Context;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Locale;

/**
 * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private Intent intent;
    NdefMessage[] msgs;

    public SectionsPagerAdapter(FragmentManager fm, Context context, Intent intent) {
        super(fm);
        this.context = context;
        this.intent = intent;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a DummySectionFragment (defined as a static inner class
        // below) with the page number as its lone argument.
        Fragment fragment = null;
        switch (position)
        {
            case 0:
                fragment = new MessagesFragment();
                break;
            case 1:
                fragment = new SubscriptionsFragment();
                if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
                    Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
                    if (rawMsgs != null) {
                        msgs = new NdefMessage[rawMsgs.length];
                        for (int i = 0; i < rawMsgs.length; i++) {
                            msgs[i] = (NdefMessage) rawMsgs[i];
                        }
                    }

                    if (msgs != null && msgs.length > 0)
                    {
                        NdefRecord[] records = msgs[0].getRecords();
                        String payloadText = "";
                        for (int i=0;i<records.length;i++)
                        {
                            if (records[i].getTnf() == NdefRecord.TNF_WELL_KNOWN && records[i].toMimeType() == "text/plain")
                            {
                                byte[] payload = records[i].getPayload();
                                String text = new String(payload);
                                payloadText += text.substring(3);
                            }
                        }

                        Bundle args = new Bundle();
                        args.putString("followName", payloadText);
                        fragment.setArguments(args);
                    }
                }
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return context.getString(R.string.title_section1).toUpperCase(l);
            case 1:
                return context.getString(R.string.title_section2).toUpperCase(l);
        }
        return null;
    }
}
