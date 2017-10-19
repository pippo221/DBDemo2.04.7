package com.example.cuongducnguyenkp.dbdemo2;

/**
 * Created by cuongducnguyen.kp on 10/11/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ShowSingleRecordActivity extends AppCompatActivity {

    String IDHolder, tempName, tempStartDate;
    TextView id, name, phone_number, textViewAddress, textViewServiceType, textViewService, textViewMonthlyCharge, textViewBox, textViewCost, textViewStartDate, textViewMAC, textViewExpiredDate, textViewContractDays, textViewMessage;
    SQLiteDatabase sqLiteDatabase;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    Button Delete, Edit;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder, SQLiteDataBaseQueryHolder2;
    List<String> DurationListStart = new ArrayList<>();
    List<String> DurationListEnd = new ArrayList<>();

    private GestureDetectorCompat gestureDetectorCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_record);


        id = (TextView) findViewById(R.id.textView1_ID);
        name = (TextView) findViewById(R.id.textView2_Name);
        phone_number = (TextView) findViewById(R.id.textView3_PhoneNumber);
        textViewAddress = (TextView) findViewById(R.id.textView3_Address);
        textViewServiceType = (TextView) findViewById(R.id.textView5_ServiceType);
        textViewService = (TextView) findViewById(R.id.textView6_Service);
        textViewMonthlyCharge = (TextView) findViewById(R.id.textView7_MonthlyCharge);
        textViewBox = (TextView) findViewById(R.id.textView8_Box);
        textViewCost = (TextView) findViewById(R.id.textView9_Cost);
        textViewStartDate = (TextView) findViewById(R.id.textView10_StartedDate);
        textViewMAC = (TextView) findViewById(R.id.textView11_MAC);
        textViewExpiredDate = (TextView) findViewById(R.id.textView12_ExpiredDate);
        textViewContractDays = (TextView) findViewById(R.id.textView13_ContractDays);
        textViewMessage = (TextView) findViewById(R.id.textView14_Message);


        Delete = (Button) findViewById(R.id.buttonDelete);
        Edit = (Button) findViewById(R.id.buttonEdit);

        sqLiteHelper = new SQLiteHelper(this);

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OpenSQLiteDataBase();

                SQLiteDataBaseQueryHolder = "DELETE FROM " + SQLiteHelper.TABLE_NAME + " WHERE id = " + IDHolder + "";

                sqLiteDatabase.execSQL(SQLiteDataBaseQueryHolder);

                sqLiteDatabase.close();

                finish();

            }
        });

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), EditSingleRecordActivity.class);

                intent.putExtra("EditID", IDHolder);

                startActivity(intent);

                OpenSQLiteDataBase();
                sqLiteDatabase = sqLiteHelper.getWritableDatabase();

                IDHolder = getIntent().getStringExtra("ListViewClickedItemValue");

                cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + SQLiteHelper.TABLE_NAME + " WHERE id = " + IDHolder + "", null);

                if (cursor.moveToFirst()) {

                    do {

                    } while (cursor.moveToNext());

                    cursor.close();
                }
            }


        });
        gestureDetectorCompat = new GestureDetectorCompat(this, new MyGestureListener());
        textViewMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OpenSQLiteDataBase();

                SQLiteDataBaseQueryHolder2 = "DELETE FROM " + SQLiteHelper.TABLE2_NAME + " WHERE name = '" + tempName + "'";
                sqLiteDatabase.execSQL(SQLiteDataBaseQueryHolder2);

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Calendar c = new GregorianCalendar();
                String temp = "";
                Calendar today = Calendar.getInstance();
                today.set(Calendar.HOUR_OF_DAY, 0);
                Date startDate = new Date();
                try {
                    startDate = formatter.parse(tempStartDate);
                    temp = formatter.format(startDate);
                    DurationListStart.add(temp);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                c.setTime(startDate);
                int i = 0;
                while (c.before(today)) {
                    c.add(Calendar.DATE, 10);
                    startDate = c.getTime();
                    temp = formatter.format(startDate);
                    DurationListEnd.add(temp);
//                    Toast.makeText(getApplicationContext(),DurationListStart.get(i), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getApplicationContext(),DurationListEnd.get(i), Toast.LENGTH_SHORT).show();
                    c.add(Calendar.DATE, 1);
                    startDate = c.getTime();
                    temp = formatter.format(startDate);
                    DurationListStart.add(temp);

                    i++;
                }
                int m = DurationListEnd.size();
                int i2 = 0;
                do {
                    SQLiteDataBaseQueryHolder2 = "INSERT INTO " + SQLiteHelper.TABLE2_NAME + "(" +
                            "name, duration_start, duration_end" +
                            ")VALUES('" +
                            tempName + "'" +
                            ",'" + DurationListStart.get(i2) + "'" +
                            ",'" + DurationListEnd.get(i2) + "" +
                            "')"
                    ;
                    i2++;
                } while (i2 < m);
                sqLiteDatabase.execSQL(SQLiteDataBaseQueryHolder2);


                Toast.makeText(ShowSingleRecordActivity.this, temp, Toast.LENGTH_LONG).show();
                sqLiteDatabase.close();
                Intent intent = new Intent(
                        ShowSingleRecordActivity.this, MessageActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    @Override
    protected void onResume() {

        ShowSingleRecordInTextView();

        super.onResume();
    }

    public void ShowSingleRecordInTextView() {


        sqLiteDatabase = sqLiteHelper.getWritableDatabase();

        IDHolder = getIntent().getStringExtra("ListViewClickedItemValue");

        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + SQLiteHelper.TABLE_NAME + " WHERE id = " + IDHolder + "", null);

        if (cursor.moveToFirst()) {

            do {
                id.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_ID)));
                name.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_1_Name)));
                phone_number.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_2_PhoneNumber)));
                textViewAddress.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_3_Address)));
                textViewServiceType.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_4_ServiceType)));
                textViewService.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_5_Service)));
                textViewMonthlyCharge.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_6_MonthlyCharge)));
                textViewBox.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_7_Box)));
                textViewCost.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_8_Cost)));
                textViewStartDate.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_9_StartDate)));
                textViewMAC.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_10_MAC)));
                textViewExpiredDate.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_11_ExpiredDate)));

//                textViewContractDays.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_12_ContractDays)));
                textViewMessage.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_13_Message)));
                tempName = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_1_Name));
                tempStartDate = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_9_StartDate));
            }
            while (cursor.moveToNext());

            cursor.close();
        }
    }

    public void OpenSQLiteDataBase() {

        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        //handle 'swipe left' action only

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

         /*
         Toast.makeText(getBaseContext(),
          event1.toString() + "\n\n" +event2.toString(),
          Toast.LENGTH_SHORT).show();
         */

            if (event2.getX() < event1.getX()) {
                Toast.makeText(getBaseContext(),
                        "Swipe left - startActivity()",
                        Toast.LENGTH_SHORT).show();

                //switch another activity
                Intent intent = new Intent(
                        ShowSingleRecordActivity.this, SearchActivity1.class);
                startActivity(intent);
            }

            return true;
        }
    }
}