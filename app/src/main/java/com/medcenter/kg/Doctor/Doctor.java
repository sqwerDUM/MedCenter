package com.medcenter.kg.Doctor;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.medcenter.kg.DatabaseHelper;
import com.medcenter.kg.Doctor.Doctor_Patient.Report_Upload;
import com.medcenter.kg.Doctor.Leaves.Leaves;
import com.medcenter.kg.Feedback;
import com.medcenter.kg.Personal_Info;
import com.medcenter.kg.R;



public class Doctor extends AppCompatActivity {

    String username,password,user_type;
    DatabaseHelper dbh;
    TextView dname;


     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor);

        dbh = new DatabaseHelper(this);
        dname = (TextView) findViewById(R.id.tv_d_name);


        Bundle bb = getIntent().getExtras();
        username = bb.getString("username");
        password = bb.getString("password");
        user_type = bb.getString("user_type");

        Cursor y = dbh.checkduplicates_in_user_credentials(username, password,getResources().getString(R.string.user_credentials));

        if (y.moveToFirst()) {
            String name = y.getString(1);
            dname.setText("Добро пожаловать "+name);
        }
    }

    public void onClick(View view){

        Intent i;
        Bundle b = new Bundle();
        b.putString("username",username);
        b.putString("password",password);
        b.putString("user_type",user_type);

        int id = view.getId();
        if (id == R.id.b_add_specialization ) {
            i = new Intent(Doctor.this, Specialization.class);
        }
        else if (id ==  R.id.b_d_info) {
            i = new Intent(Doctor.this, Personal_Info.class);
        }
        else if (id== R.id.b_d_leaves) {
            i = new Intent(Doctor.this, Leaves.class);
        }
        else if (id== R.id.b_d_upload_report) {
            i = new Intent(Doctor.this, Report_Upload.class);
        }
        else if (id== R.id.b_d_staff_assigned) {
            i = new Intent(Doctor.this, View_Assigned_Staff.class);
        }
        else if (id== R.id.b_d_slot) {
            i = new Intent(Doctor.this, D_Slot.class);
        }
        else {
            i = new Intent(Doctor.this, Feedback.class);
        }
//        switch (view.getId())
//        {
//
//            case R.id.b_add_specialization:
//                i = new Intent(Doctor.this, Specialization.class);
//                break;
//            case R.id.b_d_info:
//                i = new Intent(Doctor.this, Personal_Info.class);
//                break;
//            case  R.id.b_d_leaves :
//                i = new Intent(Doctor.this, Leaves.class);
//                break;
//            case R.id.b_d_upload_report:
//                i = new Intent(Doctor.this, Report_Upload.class);
//                break;
//            case R.id.b_d_staff_assigned:
//                i = new Intent(Doctor.this, View_Assigned_Staff.class);
//                break;
//            case R.id.b_d_slot:
//                i = new Intent(Doctor.this, D_Slot.class);
//                break;
//            default:
//                i = new Intent(Doctor.this, Feedback.class);
//                break;
//        }
        i.putExtras(b);
        startActivity(i);
    }
}