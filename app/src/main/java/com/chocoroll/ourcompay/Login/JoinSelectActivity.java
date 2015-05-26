package com.chocoroll.ourcompay.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.chocoroll.ourcompay.R;

public class JoinSelectActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_select);
    }


    public void btnJoin(View view){

        Intent intent = null;

        switch (view.getId()){
            case R.id.btnJoinUser:
                 intent = new Intent(JoinSelectActivity.this, JoinActivity.class);

                break;
            case R.id.btnJoinCompany:
                intent = new Intent(JoinSelectActivity.this, JoinCompanyActivity.class);
                startActivity(intent);
                break;
        }

        startActivity(intent);
        finish();
    }
}
