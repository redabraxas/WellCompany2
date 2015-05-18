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

import com.chocoroll.ourcompay.R;

import java.io.InputStream;
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
//
//            new DownloadImageTask((ImageView) v.findViewById(R.id.thumbnailDeal))
//                    .execute(p.getThumbnail());

            String str = "["+p.getCompanyName()+"]  "+p.getPurpose();
            ((TextView)  v.findViewById(R.id.reportTitle)).setText(str);
        }
        return v;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
