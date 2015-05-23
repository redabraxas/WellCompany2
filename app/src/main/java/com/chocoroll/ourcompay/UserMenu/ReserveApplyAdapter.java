package com.chocoroll.ourcompay.UserMenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.chocoroll.ourcompay.Model.Reserve;

import java.util.ArrayList;

/**
 * Created by RA on 2015-05-23.
 */
public class ReserveApplyAdapter extends ArrayAdapter<Reserve> {
    private ArrayList<Reserve> items;
    private Context context;
    private int textViewResourceId;

    public ReserveApplyAdapter(Context context, int textViewResourceId, ArrayList<Reserve> items) {
        super(context, textViewResourceId, items);
        this.items = items;
        this.context = context;
        this.textViewResourceId = textViewResourceId;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(textViewResourceId, null);
        }
        Reserve p = items.get(position);
        if (p != null) {
            //((TextView)  v.findViewById(R.id.companyName)).setText(str);
        }
        return v;
    }
}
