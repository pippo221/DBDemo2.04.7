package com.example.cuongducnguyenkp.dbdemo2;

/**
 * Created by cuongducnguyen.kp on 10/11/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowSingleRecordActivity extends AppCompatActivity {

    String IDHolder;
    TextView id, name, phone_number
            , textViewAddress, textViewServiceType, textViewService, textViewMonthlyCharge
            , textViewBox, textViewCost, textViewStartDate, textViewMAC, textViewExpiredDate, textViewContractDays
            , textViewMessage;
    SQLiteDatabase sqLiteDatabase;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    Button Delete, Edit;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_record);

        id = (TextView) findViewById(R.id.textView1_ID);
        name = (TextView) findViewById(R.id.textView2_Name);
        phone_number = (TextView) findViewById(R.id.textView3_PhoneNumber);
        textViewAddress=(TextView) findViewById(R.id.textView3_Address);
        textViewServiceType=(TextView) findViewById(R.id.textView5_ServiceType);
        textViewService=(TextView) findViewById(R.id.textView6_Service);
        textViewMonthlyCharge=(TextView) findViewById(R.id.textView7_MonthlyCharge);
        textViewBox=(TextView) findViewById(R.id.textView8_Box);
        textViewCost=(TextView) findViewById(R.id.textView9_Cost);
        textViewStartDate=(TextView) findViewById(R.id.textView10_StartedDate);
        textViewMAC=(TextView) findViewById(R.id.textView11_MAC);
        textViewExpiredDate=(TextView) findViewById(R.id.textView12_ExpiredDate);
        textViewContractDays=(TextView) findViewById(R.id.textView13_ContractDays);
        textViewMessage=(TextView) findViewById(R.id.textView14_Message);

        Delete = (Button)findViewById(R.id.buttonDelete);
        Edit = (Button)findViewById(R.id.buttonEdit);

        sqLiteHelper = new SQLiteHelper(this);

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OpenSQLiteDataBase();

                SQLiteDataBaseQueryHolder = "DELETE FROM "+SQLiteHelper.TABLE_NAME+" WHERE id = "+ IDHolder +"";

                sqLiteDatabase.execSQL(SQLiteDataBaseQueryHolder);

                sqLiteDatabase.close();

                finish();

            }
        });

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),EditSingleRecordActivity.class);

                intent.putExtra("EditID", IDHolder);

                startActivity(intent);

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
                textViewContractDays.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_12_ContractDays)));
                textViewMessage.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_13_Message)));;
            }
            while (cursor.moveToNext());

            cursor.close();
        }
    }

    public void OpenSQLiteDataBase(){

        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }
}