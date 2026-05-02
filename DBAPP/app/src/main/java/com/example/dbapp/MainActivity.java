package com.example.dbapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    Button b1, b2;
    TextView t1;
    String str = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);
        t1 = findViewById(R.id.textView3);

        // Create Database
        db = openOrCreateDatabase("StudentDB", MODE_PRIVATE, null);

        // Create Table
        db.execSQL("CREATE TABLE IF NOT EXISTS Temp(id INTEGER, name TEXT)");

        // Insert Button
        b1.setOnClickListener(v -> {
            EditText eid = findViewById(R.id.editText1);
            EditText ename = findViewById(R.id.editText2);

            ContentValues values = new ContentValues();
            values.put("id", eid.getText().toString());
            values.put("name", ename.getText().toString());

            if (db.insert("Temp", null, values) != -1) {
                Toast.makeText(this, "Inserted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        // Display Button
        b2.setOnClickListener(v -> {
            Cursor c = db.rawQuery("SELECT * FROM Temp", null);
            str = "";

            if (c.moveToFirst()) {
                do {
                    str += c.getString(0) + " " + c.getString(1) + "\n";
                } while (c.moveToNext());
            }

            t1.setText(str);
            c.close();
        });
    }
}