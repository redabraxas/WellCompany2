package com.chocoroll.ourcompay.Company;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.chocoroll.ourcompay.R;

/**
 * Created by RA on 2015-05-17.
 */
public class ReserveFragment extends Fragment {

    String companyNum;

    public ReserveFragment() {
    }

    @SuppressLint("ValidFragment")
    public ReserveFragment(String companyNum) {
        this.companyNum = companyNum;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragemnt_reserve, container, false);




        return v;
    }

}
