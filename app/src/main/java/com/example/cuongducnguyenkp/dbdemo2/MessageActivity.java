package com.example.cuongducnguyenkp.dbdemo2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabaseObj;
    SQLiteDatabase sqLiteDatabase;
    SQLiteHelper sqLiteHelper;
    String id, name, start, end;
    GridView gridView;
    List<String> li;
    ArrayAdapter<String> dataAdapter;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


        gridView = (GridView) findViewById(R.id.gridViewMessage);
        li = new ArrayList<String>();

//        dataAdapter.setDropDownViewResource(R.layout.activity_message);

        try {
            OpenSQLiteDataBase();
            sqLiteDatabase = sqLiteHelper.getWritableDatabase();
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + SQLiteHelper.TABLE2_NAME + "", null);

            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table2_Column_ID));
                    name = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table2_Column_Name));
                    start = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table2_Column_DurationStart));
                    end = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table2_Column_DurationEnd));

                    li.add(id);
                    li.add(name);
                    li.add(start);
                    li.add(end);


                    Toast.makeText(getApplicationContext(), "abc" + id + " " + name + " " + start + " " + end, Toast.LENGTH_SHORT).show();
                } while (cursor.moveToNext());
            }
            gridView.setAdapter(dataAdapter);
            cursor.close();
            sqLiteDatabase.close();
            dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, li);
        } catch (Exception e) {

        }
    }

    public void OpenSQLiteDataBase() {

        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }
}
