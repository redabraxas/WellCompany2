package com.chocoroll.ourcompay.Mine;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chocoroll.ourcompay.MainActivity;
import com.chocoroll.ourcompay.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyListFragment extends Fragment {

    String user_id;

    public MyListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_mylist, container, false);

        // 내 아이디
        user_id = ((MainActivity)MainActivity.mContext).getUserId();
        return v;
    }


}
