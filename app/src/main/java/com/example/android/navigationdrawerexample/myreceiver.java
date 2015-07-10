package com.example.android.navigationdrawerexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Vinay Potluri on 7/5/2015.
 */

public class myreceiver extends BroadcastReceiver {

    public ListView listView;
    public ArrayAdapter adapter;
    public MainActivity activity;
    public f3 frag;

    @Override
    public void onReceive(Context context, Intent intent) {

        CharSequence intentData = intent.getCharSequenceExtra("message");

        f3.getInstace().updateTheTextView(intentData.toString());

    }
}
