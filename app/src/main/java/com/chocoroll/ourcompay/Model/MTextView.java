package com.chocoroll.ourcompay.Model;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by HyeJi on 2015. 6. 8..
 */
public class MTextView extends TextView{

        public MTextView(Context context, AttributeSet attrs){
            super(context, attrs);
            this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/SeoulNamsanM.ttf"));
        }

}
