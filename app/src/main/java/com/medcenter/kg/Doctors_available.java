package com.medcenter.kg;

    import android.database.Cursor;
    import android.os.Bundle;

    import android.widget.ArrayAdapter;
    import android.widget.ListView;

    import androidx.appcompat.app.AppCompatActivity;

    import java.util.ArrayList;

    public class Doctors_available extends AppCompatActivity {

    ListView lv_avail;
    ArrayList<String> dname = new ArrayList<>();
    DatabaseHelper db = new DatabaseHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctors_available);

        lv_avail = (ListView) findViewById(R.id.lv_doc_available);

        Cursor y = db.checkduplicates_in_user_credentials("", "", "get_all_doctors");

        if (y.moveToFirst()) {
            while (true) {
                if ((y.getString(7)).equals("Doctor")) {
                    dname.add("Dr. " + y.getString(1) + "  " + y.getString(2));
                }

                if (y.isLast())
                    break;
                y.moveToNext();
            }

            ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dname);
            lv_avail.setAdapter(adapter);
        } else {
            Message.message(Doctors_available.this, "Нет доступных Врачей");
            finish();
        }
    }

}
