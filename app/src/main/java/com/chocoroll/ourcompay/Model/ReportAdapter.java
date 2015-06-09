package com.chocoroll.ourcompay.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.chocoroll.ourcompay.R;

import java.util.ArrayList;

/**
 * Created by RA on 2015-05-17.
 */
public class ReportAdapter  extends ArrayAdapter<Report> {
    private ArrayList<Report> items;
    private Context context;

    public ReportAdapter(Context context, int textViewResourceId, ArrayList<Report> items) {
        super(context, textViewResourceId, items);
        this.items = items;
        this.context = context;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.model_report, null);
        }
        Report p = items.get(position);
        if (p != null) {

          String str = "["+p.getCompanyName()+"]  "+p.getPurpose();
            ((TextView) v.findViewById(R.id.comName)).setText(str);
            ((TextView)v.findViewById(R.id.date)).setText(p.getDate());
        }
        return v;
    }

}
