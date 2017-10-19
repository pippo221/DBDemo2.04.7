package com.example.cuongducnguyenkp.dbdemo2;

/**
 * Created by cuongducnguyen.kp on 10/11/2017.
 */

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;

public class EditSingleRecordActivity extends AppCompatActivity {

    EditText name, phone_number, textViewAddress, textViewServiceType, textViewMonthlyCharge, textViewBox, textViewCost, textViewStartDate, textViewMAC, textViewExpiredDate, textViewContractDays, textViewMessage;
    FloatingActionButton update;
    SQLiteDatabase sqLiteDatabase;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    String IDholder, strServiceHolder, textViewService, abc;
    String SQLiteDataBaseQueryHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    RadioGroup radioGroup;

    public void ShowSRecordInEditText() {

        sqLiteDatabase = sqLiteHelper.getWritableDatabase();

        IDholder = getIntent().getStringExtra("EditID");

        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + SQLiteHelper.TABLE_NAME + " WHERE id = " + IDholder + "", null);
        // textViewService.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_5_Service)));
//print all information to edittext
        if (cursor.moveToFirst()) {

            do {
                name.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_1_Name)));

                phone_number.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_2_PhoneNumber)));

                textViewAddress.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_3_Address)));

                textViewServiceType.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_4_ServiceType)));

                textViewService = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_5_Service));

                textViewMonthlyCharge.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_6_MonthlyCharge)));

                textViewBox.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_7_Box)));

                textViewCost.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_8_Cost)));

                textViewStartDate.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_9_StartDate)));

                textViewMAC.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_10_MAC)));

                textViewExpiredDate.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_11_ExpiredDate)));

                textViewContractDays.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_12_ContractDays)));

                textViewMessage.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_13_Message)));
            }
            while (cursor.moveToNext());
            abc = textViewService;
            radioGroup = (RadioGroup) findViewById(R.id.radioGroupEditService5);
            if (abc.equals("Buy")) {
                radioGroup.check(R.id.radioButtonEditBuy);
            } else if (abc.equals("Rent")) {
                radioGroup.check(R.id.radioButtonEditRent);
            }
            cursor.close();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_single_record);

        name = (EditText) findViewById(R.id.editText1_EditName);
        phone_number = (EditText) findViewById(R.id.editText2_EditPhone);
        textViewAddress = (EditText) findViewById(R.id.editText3_EditAddress);
        textViewServiceType = (EditText) findViewById(R.id.editText4_EditServiceType);
        // textViewService =  (EditText) findViewById(R.id.editser);
        textViewMonthlyCharge = (EditText) findViewById(R.id.editText6_EditMonthlyCharge);
        textViewBox = (EditText) findViewById(R.id.editText7_EditBox);
        textViewCost = (EditText) findViewById(R.id.editText8_EditCost);
        textViewStartDate = (EditText) findViewById(R.id.editText9_EditStartDate);
        textViewMAC = (EditText) findViewById(R.id.editText10_EditMACAddress);
        textViewExpiredDate = (EditText) findViewById(R.id.editText11_EditExpiredDate);
        textViewContractDays = (EditText) findViewById(R.id.editText12_EditContractDay);
        textViewMessage = (EditText) findViewById(R.id.editText13_EditMessage);
        textViewStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR),
                        mMonth = mcurrentDate.get(Calendar.MONTH),
                        mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(EditSingleRecordActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        selectedmonth = selectedmonth + 1;
                        textViewStartDate.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();

            }
        });
        textViewExpiredDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR),
                        mMonth = mcurrentDate.get(Calendar.MONTH),
                        mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(EditSingleRecordActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        selectedmonth = selectedmonth + 1;
                        textViewExpiredDate.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();

            }
        });

        update = (FloatingActionButton) findViewById(R.id.buttonUpdate);

        sqLiteHelper = new SQLiteHelper(this);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String GetName = name.getText().toString();
                String GetPhoneNumber = phone_number.getText().toString();
                String getTextViewAddress, getTextViewServiceType, getTextViewService, getTextViewMonthlyCharge, getTextViewBox, getTextViewCost, getTextViewStartDate, getTextViewMAC, getTextViewExpiredDate, getTextViewContractDays, getTextViewMessage;
                getTextViewAddress = textViewAddress.getText().toString();
                getTextViewServiceType = textViewServiceType.getText().toString();
                //   getTextViewService= textViewService.getText().toString();
                getTextViewMonthlyCharge = textViewMonthlyCharge.getText().toString();
                getTextViewBox = textViewBox.getText().toString();
                getTextViewCost = textViewCost.getText().toString();
                getTextViewStartDate = textViewStartDate.getText().toString();
                getTextViewMAC = textViewMAC.getText().toString();
                getTextViewExpiredDate = textViewExpiredDate.getText().toString();
                getTextViewContractDays = textViewContractDays.getText().toString();
                getTextViewMessage = textViewMessage.getText().toString();

                int intChecked = radioGroup.getCheckedRadioButtonId();
                switch (intChecked) {
                    case R.id.radioButtonEditBuy:
                        strServiceHolder = "Buy";
                        break;
                    case R.id.radioButtonEditRent:
                        strServiceHolder = "Rent";
                        break;
                }
                getTextViewService = strServiceHolder;
                ;

                OpenSQLiteDataBase();
//sql for update
                SQLiteDataBaseQueryHolder = "UPDATE " + SQLiteHelper.TABLE_NAME + " SET " + SQLiteHelper.Table_Column_1_Name + " = '" + GetName + "' , " + SQLiteHelper.Table_Column_2_PhoneNumber + " = '" + GetPhoneNumber +
                        "', " + SQLiteHelper.Table_Column_3_Address + " = '" + getTextViewAddress +
                        "', " + SQLiteHelper.Table_Column_4_ServiceType + " = '" + getTextViewServiceType +
                        "', " + SQLiteHelper.Table_Column_5_Service + " = '" + getTextViewService +
                        "', " + SQLiteHelper.Table_Column_6_MonthlyCharge + " = '" + getTextViewMonthlyCharge +
                        "', " + SQLiteHelper.Table_Column_7_Box + " = '" + getTextViewBox +
                        "', " + SQLiteHelper.Table_Column_8_Cost + " = '" + getTextViewCost +
                        "', " + SQLiteHelper.Table_Column_9_StartDate + " = '" + getTextViewStartDate +
                        "', " + SQLiteHelper.Table_Column_10_MAC + " = '" + getTextViewMAC +
                        "', " + SQLiteHelper.Table_Column_11_ExpiredDate + " = '" + getTextViewExpiredDate +
                        "', " + SQLiteHelper.Table_Column_12_ContractDays + " = '" + getTextViewContractDays +
                        "', " + SQLiteHelper.Table_Column_13_Message + " = '" + getTextViewMessage +
                        "' WHERE id = " + IDholder + "";

                sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);

                sqLiteDatabase.close();

                Toast.makeText(EditSingleRecordActivity.this, "Data Edit Successfully", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onResume() {

        ShowSRecordInEditText();

        super.onResume();
    }


    public void OpenSQLiteDataBase() {

        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }

}