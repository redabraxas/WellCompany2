package com.chocoroll.ourcompay.Login;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chocoroll.ourcompay.R;

public class LoginActivity extends Activity {
    TextView textview=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    final EditText editID=(EditText)findViewById(R.id.editID);
    final EditText editPwd=(EditText)findViewById(R.id.editPwd);

    Button login_bt=(Button)findViewById(R.id.login_bt);
    Button login_findid=(Button)findViewById(R.id.login_findid);
    Button login_findpasswd=(Button)findViewById(R.id.login_findpasswd);

    login_bt.setOnClickListener(new View.OnClickListener(){

        public void onClick(View v){

            String id=editID.getText().toString();
            String pwd=editPwd.getText().toString();

        }
    });

    login_findid.setOnClickListener(new View.OnClickListener(){

        public void onClick(View v){

        }
    });

    login_findpasswd.setOnClickListener(new View.OnClickListener(){

        public void onClick(View v){

        }
    });

}



}
