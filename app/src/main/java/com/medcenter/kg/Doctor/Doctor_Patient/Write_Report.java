package com.medcenter.kg.Doctor.Doctor_Patient;


import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.medcenter.kg.DatabaseHelper;
import com.medcenter.kg.Message;
import com.medcenter.kg.R;



public class Write_Report extends AppCompatActivity {

    String username, password, user_type, p_username, p_password, problem, rp, fee;
    DatabaseHelper dbh;
    EditText etr;
    TextView pro;
    Button b_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_report);

        Bundle bb = getIntent().getExtras();
        username = bb.getString("username");
        password = bb.getString("password");
        user_type = bb.getString("user_type");
        p_username = bb.getString("p_username");
        p_password = bb.getString("p_password");
        problem = bb.getString("problem");
        fee = bb.getString("fees");

        pro = (TextView) findViewById(R.id.tv_problem);
        etr = (EditText) findViewById(R.id.et_report);
        b_submit = (Button) findViewById(R.id.b_submit_report);
        dbh = new DatabaseHelper(this);
        pro.setText(problem);

        b_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rp = etr.getText().toString();

                boolean b = dbh.update_doctor_patient(p_username, p_password, username, password, "F", problem, fee, rp);

                if (b) {
                    Message.message(Write_Report.this, "Отчет успешно загружен");
                    finish();
                } else {
                    Message.message(Write_Report.this, "Сообщение не загружено, повторите попытку");
                }
            }
        });


    }
}
