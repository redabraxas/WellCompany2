package com.chocoroll.ourcompay.Mine;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chocoroll.ourcompay.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class MyInfoFragment extends Fragment {


    public MyInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_info, container, false);

        // 비밀번호 변경!
        return  v;
    }


}
