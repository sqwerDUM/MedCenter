package com.medcenter.kg;


import android.content.Intent;
import android.database.Cursor;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.medcenter.kg.Desktop_Admin.Desktop_Admin;
import com.medcenter.kg.Doctor.Doctor;
import com.medcenter.kg.Patient.Patient;
import com.medcenter.kg.Staff_Member.Staff_Member;



public class Login extends AppCompatActivity {

    EditText username, password;
    String usernames, passwords;
    Button Bregister, Blogin;
    DatabaseHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username = (EditText) findViewById(R.id.etusername);
        password = (EditText) findViewById(R.id.etpassword);
        Bregister = (Button) findViewById(R.id.bregister);
        Blogin = (Button) findViewById(R.id.blogin);
        dbh = new DatabaseHelper(this);

        Bregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
            }
        });

        Blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernames = username.getText().toString();
                passwords = password.getText().toString();

                Cursor y = dbh.checkduplicates_in_user_credentials(usernames, passwords, getResources().getString(R.string.user_credentials));

                if (y.moveToFirst()) {
                    String ut = y.getString(7);
                    Message.message(Login.this, "Добро пожаловать");

                    Bundle b = new Bundle();
                    b.putString("username", usernames);
                    b.putString("password", passwords);
                    b.putString("user_type", ut);

                    Intent i;
                    if (ut.equals("Доктор")) {
                        i = new Intent(Login.this, Doctor.class);
                    } else if (ut.equals("Пациент")) {
                        i = new Intent(Login.this, Patient.class);
                    } else if (ut.equals("Сотрудник")) {
                        i = new Intent(Login.this, Staff_Member.class);
                    } else {
                        i = new Intent(Login.this, Desktop_Admin.class);
                    }

                    i.putExtras(b);
                    startActivity(i);
                } else {
                    Message.message(Login.this, "Такого Пользователя не Существует");
                    Message.message(Login.this, "Пожалуйста, зарегистрируйтесь самостоятельно");
                }

                y.close();
            }
        });
        dbh.close();
    }
}
