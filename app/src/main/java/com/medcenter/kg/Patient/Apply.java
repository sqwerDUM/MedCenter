package com.medcenter.kg.Patient;

import android.database.Cursor;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.medcenter.kg.DatabaseHelper;
import com.medcenter.kg.Message;
import com.medcenter.kg.R;



public class Apply extends AppCompatActivity {
    String username, password, d_username, d_password, problem;
    TextView name, s_start, s_end;
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply);

        Bundle bb = getIntent().getExtras();
        username = bb.getString("username");
        password = bb.getString("password");
        d_username = bb.getString("d_username");
        d_password = bb.getString("d_password");

        name = (TextView) findViewById(R.id.tv_d_name);
        s_end = (TextView) findViewById(R.id.tv_slot_start);
        s_start = (TextView) findViewById(R.id.tv_slot_end);
        et = (EditText) findViewById(R.id.et_problem);

        DatabaseHelper dbh = new DatabaseHelper(this);
        Cursor y = dbh.checkduplicates_in_user_credentials(d_username, d_password, getResources().getString(R.string.user_credentials));


        if (y.moveToFirst()) {
            name.setText("Врач.  " + y.getString(1) + " " + y.getString(1));
            DatabaseHelper dbh1 = new DatabaseHelper(this);
            Cursor z = dbh1.checkduplicates_in_user_credentials(d_username, d_password, getResources().getString(R.string.doctor_slot));
            if (z.moveToFirst()) {
                s_start.setText(z.getString(3));
                s_end.setText(z.getString(4));
            }
        }
    }

    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.b_apply ) {
            problem = et.getText().toString();

            if (problem.length() == 0) {
                Message.message(Apply.this, "Пожалуйста, укажите Вашу проблему");
            } else {
                DatabaseHelper dbh2 = new DatabaseHelper(this);
                boolean b = dbh2.insert_doctor_patient(username, password, d_username, d_password, "W", problem, "N", "");

                if (b) {
                    Message.message(Apply.this, "Ваша заявка была отправлена");
                    finish();
                } else {
                    Message.message(Apply.this, "Ошибка...пожалуйста, попробуйте снова");
                }
            }
        }

//        switch (view.getId()) {
//            case  R.id.b_apply:
//
//                problem = et.getText().toString();
//
//                if (problem.length() == 0) {
//                    Message.message(Apply.this, "Please Enter You Problem");
//                } else {
//                    DatabaseHelper dbh2 = new DatabaseHelper(this);
//                    boolean b = dbh2.insert_doctor_patient(username, password, d_username, d_password, "W", problem, "N", "");
//
//                    if (b) {
//                        Message.message(Apply.this, "Your Application has been Sent");
//                        finish();
//                    } else {
//                        Message.message(Apply.this, "Error...Please Try Again");
//                    }
//                }
//                break;
//        }
    }
}
