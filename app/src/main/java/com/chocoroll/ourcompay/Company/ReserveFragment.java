package com.chocoroll.ourcompay.Company;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
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

        CalendarView calendarView = (CalendarView) v.findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, final int year, final int month, final int dayOfMonth) {

                String str = String.valueOf(year)+"년 "+String.valueOf(month)+"월 "+String.valueOf(dayOfMonth)+"일";

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("이 날짜가 맞습니까?")        // 제목 설정
                        .setMessage(str)        // 메세지 설정
                        .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            // 확인 버튼 클릭시 설정
                            public void onClick(DialogInterface dialog, int whichButton) {

                                Intent intent = new Intent(getActivity(), ReserveActivity.class);
                                intent.putExtra("year", year);
                                intent.putExtra("month", month);
                                intent.putExtra("day", dayOfMonth);
                                startActivity(intent);
                            }
                        });

                AlertDialog dialog = builder.create();    // 알림창 객체 생성
                dialog.show();    // 알림창 띄우기


            }
        });


        //calendarView.getChildAt(1).setBackgroundColor(Color.parseColor("#000000"));
        Log.e("count", String.valueOf(calendarView.getChildCount()));
        calendarView.get()



        return v;
    }

}
