package com.chocoroll.ourcompay.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chocoroll.ourcompay.Extra.DownloadImageTask;
import com.chocoroll.ourcompay.R;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by RA on 2015-05-17.
 */
public class CompanyAdapter extends ArrayAdapter<Company> {
    private ArrayList<Company> items;
    private Context context;

    public CompanyAdapter(Context context, int textViewResourceId, ArrayList<Company> items) {
        super(context, textViewResourceId, items);
        this.items = items;
        this.context = context;
    }
    public View getView(int position, View convertView, ViewGroup parent) { //리스트뷰 한칸씩 출력될때마다 호출 됨
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.model_company, null);
        }
        Company p = items.get(position);
        if (p != null) {

            new DownloadImageTask((ImageView) v.findViewById(R.id.companyLogo))
                    .execute(p.getLogo());

            String str = p.getName();
            ((TextView)  v.findViewById(R.id.companyName)).setText(str);
        }
        return v;
    }

}
