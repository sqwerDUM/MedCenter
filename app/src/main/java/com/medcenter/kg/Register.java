package com.medcenter.kg;


import android.content.Intent;
import android.database.Cursor;

import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import androidx.appcompat.app.AppCompatActivity;

import com.medcenter.kg.Desktop_Admin.Desktop_Admin;
import com.medcenter.kg.Doctor.Doctor;
import com.medcenter.kg.Patient.Patient;
import com.medcenter.kg.Staff_Member.Staff_Member;

import java.util.ArrayList;
import java.util.List;


public class Register extends AppCompatActivity {

    EditText fname, lname, age, dd, yy, city, pincode, mobno, uname, password;
    String fnames, lnames, ages, sexs, bgroups, dobs, dds, yys, mms, citys, pincodes, mobnos, unames, passwords, utypes;
    Button register;
    Spinner usertype, mm, sex, bgroup;

    DatabaseHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        //DEFINING ALL VIEWS
        fname = (EditText) findViewById(R.id.etfname);
        lname = (EditText) findViewById(R.id.etlname);
        age = (EditText) findViewById(R.id.etage);
        dd = (EditText) findViewById(R.id.etdd);
        yy = (EditText) findViewById(R.id.etyy);
        city = (EditText) findViewById(R.id.etcity);
        pincode = (EditText) findViewById(R.id.etpin);
        mobno = (EditText) findViewById(R.id.etmobile);
        uname = (EditText) findViewById(R.id.etusername);
        password = (EditText) findViewById(R.id.etpassword);
        register = (Button) findViewById(R.id.bregister);
        usertype = (Spinner) findViewById(R.id.spinnerusertype);
        mm = (Spinner) findViewById(R.id.spinnermonth);
        sex = (Spinner) findViewById(R.id.spinnersex);
        bgroup = (Spinner) findViewById(R.id.spinnerbgroup);
        dbh = new DatabaseHelper(this);


        List<String> category = new ArrayList<>();
        category.add("Пациент");
        category.add("Доктор");
        category.add("Сотрудник");
        category.add("Администратор");

        List<String> sexc = new ArrayList<>();
        sexc.add("Мужской");
        sexc.add("Женский");

        List<String> bgroupc = new ArrayList<>();
        bgroupc.add("A+");
        bgroupc.add("A-");
        bgroupc.add("B+");
        bgroupc.add("B-");
        bgroupc.add("O+");
        bgroupc.add("O-");
        bgroupc.add("AB+");
        bgroupc.add("AB-");

        List<String> months = new ArrayList<>();
        months.add("Янв");
        months.add("Фев");
        months.add("Мар");
        months.add("Апр");
        months.add("Май");
        months.add("Июн");
        months.add("Июл");
        months.add("Авг");
        months.add("Сен");
        months.add("Окт");
        months.add("Ноя");
        months.add("Дек");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, category);
        ArrayAdapter<String> adapterm = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, months);
        ArrayAdapter<String> adapters = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bgroupc);
        ArrayAdapter<String> adapterb = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sexc);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        usertype.setAdapter(adapter);
        mm.setAdapter(adapterm);
        sex.setAdapter(adapters);
        bgroup.setAdapter(adapterb);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lnames = lname.getText().toString();
                fnames = fname.getText().toString();
                ages = age.getText().toString();
                dds = dd.getText().toString();
                yys = yy.getText().toString();
                citys = city.getText().toString();
                pincodes = pincode.getText().toString();
                unames = uname.getText().toString();
                passwords = password.getText().toString();
                mobnos = mobno.getText().toString();
                utypes = usertype.getSelectedItem().toString();
                mms = mm.getSelectedItem().toString();
                sexs = sex.getSelectedItem().toString();
                bgroups = bgroup.getSelectedItem().toString();

                if (fnames.equals("") || lnames.equals("") || ages.equals("") || dds.equals("") ||
                        yys.equals("") || citys.equals("") || pincodes.equals("") || unames.equals("") ||
                        passwords.equals("") || mobnos.equals("")) {
                    Message.message(Register.this, "Пожалуйста, заполните все ваши данные");
                } else {


                    Cursor y = dbh.checkduplicates_in_user_credentials(unames, passwords, getResources().getString(R.string.user_credentials));
                    if (y.moveToFirst()) {
                        Message.message(Register.this, "Пользователь Уже существует");
                        Message.message(Register.this, "Войдите под своим именем пользователя и паролем");
                        finish();
                    } else {

                        if (dds.length() == 1)
                            dds = "0" + dds;
                        dobs = dds + " " + mms + " " + yys;

                        boolean b = dbh.insert_user_credentials(fnames, lnames, ages, dobs, citys, pincodes, unames, passwords, mobnos, utypes, sexs, bgroups);
                        if (b) {
                            Intent i;
                            Bundle bb = new Bundle();
                            bb.putString("username", unames);
                            bb.putString("password", passwords);
                            bb.putString("user_type", utypes);

                            if (utypes.equals("Пациент")) {
                                i = new Intent(Register.this, Patient.class);
                            } else if (utypes.equals("Доктор")) {
                                i = new Intent(Register.this, Doctor.class);
                            } else if (utypes.equals("Сотрудник")) {
                                i = new Intent(Register.this, Staff_Member.class);
                            } else {
                                i = new Intent(Register.this, Desktop_Admin.class);
                            }

                            i.putExtras(bb);
                            startActivity(i);
                            finish();
                        }
                    }
                }
            }
        });
    }
}
