package com.example.cuongducnguyenkp.dbdemo2;

/**
 * Created by cuongducnguyen.kp on 10/11/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DisplaySQLiteDataActivity extends AppCompatActivity {
    SQLiteOpenHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    ListAdapter listAdapter;
    ListView LISTVIEW;
    SQLiteDatabase sqLiteDatabaseObj;

    ArrayList<String> ID_Array;
    ArrayList<String> NAME_Array;
    ArrayList<String> PHONE_NUMBER_Array;
    ArrayList<Integer> CONTRACT_DAYS_Array;
    ArrayList<String> START_DATE;
    ArrayList<String> ListViewClickItemArray = new ArrayList<String>();
    String TempHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_sqlite_data);

        LISTVIEW = (ListView) findViewById(R.id.listView1);

        ID_Array = new ArrayList<String>();

        NAME_Array = new ArrayList<String>();

        PHONE_NUMBER_Array = new ArrayList<String>();

        CONTRACT_DAYS_Array = new ArrayList<Integer>();

        START_DATE = new ArrayList<String>();

        sqLiteHelper = new SQLiteHelper(this);

        LISTVIEW.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // TODO Auto-generated method stub

                Intent intent = new Intent(getApplicationContext(), ShowSingleRecordActivity.class);

                intent.putExtra("ListViewClickedItemValue", ListViewClickItemArray.get(position).toString());

                startActivity(intent);

                sqLiteDatabase = sqLiteHelper.getWritableDatabase();
                String IDholder;
                IDholder = getIntent().getStringExtra("ID");
                cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + SQLiteHelper.TABLE_NAME + " WHERE id = " + IDholder + "", null);if (cursor.moveToFirst()) {
                    do {
                        START_DATE.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_9_StartDate)));
                    } while (cursor.moveToNext());
                }
                for (int y = 0; y < START_DATE.size(); y++) {
                    String z;
                    Date today = new Date();
                    DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                    Date date = null;
                    try {
                        date = format.parse(START_DATE.get(y));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    today.getTime();
                    z = Long.toString(getDateDiff(date, today, TimeUnit.DAYS));
                    OpenSQLiteDataBase();
                    String SQLiteDataBaseQueryHolder;
                    SQLiteDataBaseQueryHolder = "UPDATE "+ SQLiteHelper.TABLE_NAME + " SET " +SQLiteHelper.Table_Column_12_ContractDays+ " = '" + z + "' WHERE id = " + IDholder + "";
                    sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);

                    sqLiteDatabase.close();
                    Toast.makeText(DisplaySQLiteDataActivity.this, z, Toast.LENGTH_LONG).show();
                }
                cursor.close();
            }
        });


    }

    private void OpenSQLiteDataBase() {
        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);
    }

    @Override
    protected void onResume() {

        try {
            ShowSQLiteDBdata();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        super.onResume();
    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    private void ShowSQLiteDBdata() throws ParseException {

        sqLiteDatabase = sqLiteHelper.getWritableDatabase();

        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + SQLiteHelper.TABLE_NAME + "", null);

        ID_Array.clear();
        NAME_Array.clear();
        PHONE_NUMBER_Array.clear();

        if (cursor.moveToFirst()) {
            do {

                ID_Array.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_ID)));

                //Inserting Column ID into Array to Use at ListView Click Listener Method.
                ListViewClickItemArray.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_ID)));

                NAME_Array.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_1_Name)));

                PHONE_NUMBER_Array.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_2_PhoneNumber)));

                // CONTRACT_DAYS_Array.add(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.Table_Column_12_ContractDays))+1);


            } while (cursor.moveToNext());
        }


//
        listAdapter = new ListAdapter(DisplaySQLiteDataActivity.this,

                ID_Array,
                NAME_Array,
                PHONE_NUMBER_Array
        );

        LISTVIEW.setAdapter(listAdapter);

        cursor.close();
    }

}