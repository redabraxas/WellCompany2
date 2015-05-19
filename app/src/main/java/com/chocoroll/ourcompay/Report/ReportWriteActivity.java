package com.chocoroll.ourcompay.Report;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.chocoroll.ourcompay.R;

public class ReportWriteActivity extends Activity {

    String purpose, id, date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_write);

        // purpose, 아이디, 날짜 등은 intent로 넘겨받을 것
        // 지금은 있다고 가정한다
        purpose ="취업하기 위해";
        id = "";

    }

}
