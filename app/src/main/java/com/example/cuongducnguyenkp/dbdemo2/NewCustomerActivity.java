package com.example.cuongducnguyenkp.dbdemo2;

/**
 * Created by cuongducnguyen.kp on 10/11/2017.
 */

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class NewCustomerActivity extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabaseObj;
    EditText editTextName, editTextPhoneNumber, editTextAddress, editTextServiceType, editTextMonthlyCharge, editTextBox, editTextCost, editTextStartDate, editTextMAC, editTextContractDays, editTextExpiredDate, editTextMessageHolder;
    String strNameHolder, strNumberHolder, SQLiteDataBaseQueryHolder, SQLiteDataBaseQueryHolder2, strAddressHolder, strServiceTypeHolder, strServiceHolder, strMonthlyChargeHolder, strBoxHolder, strCostholder, strStartDateHolder, strMACHolder, strExpiredDateHolder, strContractDaysHolder, strMessageHolder;
    FloatingActionButton EnterData, ButtonDisplayData, buttonTest;
    RadioGroup radioGroupService;
    Boolean EditTextEmptyHold;
    List<String> DurationListStart = new ArrayList<>();
    List<String> DurationListEnd = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cust);

        EnterData = (FloatingActionButton) findViewById(R.id.button);
        ButtonDisplayData = (FloatingActionButton) findViewById(R.id.button2);

        editTextName = (EditText) findViewById(R.id.editText1_EditName);
        editTextPhoneNumber = (EditText) findViewById(R.id.editText2_NewPhone);
        editTextAddress = (EditText) findViewById(R.id.editText3_NewAddress);
        editTextServiceType = (EditText) findViewById(R.id.editText4_NewServiceType);
        editTextBox = (EditText) findViewById(R.id.editText7_NewBox);
        editTextCost = (EditText) findViewById(R.id.editText8_NewCost);
        editTextStartDate = (EditText) findViewById(R.id.editText9_NewStartDate);
        editTextMAC = (EditText) findViewById(R.id.editText10_NewMACAddress);
        editTextContractDays = (EditText) findViewById(R.id.editText12_NewContractDay);
        editTextExpiredDate = (EditText) findViewById(R.id.editText11_NewExpiredDate);
        editTextMessageHolder = (EditText) findViewById(R.id.editText13_NewtMessage);
        editTextMonthlyCharge = (EditText) findViewById(R.id.editTextNew6_MonthlyCharge);
        editTextStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR),
                        mMonth = mcurrentDate.get(Calendar.MONTH),
                        mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(NewCustomerActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        selectedmonth = selectedmonth + 1;
                        editTextStartDate.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();

            }
        });

        editTextExpiredDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR),
                        mMonth = mcurrentDate.get(Calendar.MONTH),
                        mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(NewCustomerActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        selectedmonth = selectedmonth + 1;
                        editTextExpiredDate.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();

            }
        });


        EnterData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                SQLiteDataBaseBuild();

                SQLiteTableBuild();

                CheckEditTextStatus();

                Toast.makeText(getApplicationContext(),
                        "Button Insert Click...", Toast.LENGTH_SHORT).show();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Calendar c = new GregorianCalendar();
                String temp = "";
                Calendar today = Calendar.getInstance();
                today.set(Calendar.HOUR_OF_DAY, 0);
                Date startDate = new Date();
                try {
                    startDate = formatter.parse(strStartDateHolder);
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
                    Toast.makeText(getApplicationContext(),
                            DurationListStart.get(i), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),
                            DurationListEnd.get(i), Toast.LENGTH_SHORT).show();
                    c.add(Calendar.DATE, 1);
                    startDate = c.getTime();
                    temp = formatter.format(startDate);
                    DurationListStart.add(temp);

                    i++;
                }


                try {
                    InsertDataIntoSQLiteDatabase();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                EmptyEditTextAfterDataInsert();


            }
        });
//Button display all input record
        ButtonDisplayData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),
                        "Redirecting...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NewCustomerActivity.this, DisplaySQLiteDataActivity.class);
                startActivity(intent);
            }
        });
    }

    public void SQLiteDataBaseBuild() {

        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }

    public void SQLiteTableBuild() {

        sqLiteDatabaseObj.execSQL(
//                "DROP TABLE IF EXISTS '"+SQLiteHelper.TABLE_NAME+"'; " +
                "CREATE TABLE IF NOT EXISTS " + SQLiteHelper.TABLE_NAME + "(" + SQLiteHelper.Table_Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + SQLiteHelper.Table_Column_1_Name + " VARCHAR, " + SQLiteHelper.Table_Column_2_PhoneNumber + " VARCHAR" +
                        ", " + SQLiteHelper.Table_Column_3_Address + " VARCHAR" +
                        ", " + SQLiteHelper.Table_Column_4_ServiceType + " VARCHAR" +
                        ", " + SQLiteHelper.Table_Column_5_Service + " VARCHAR" +
                        ", " + SQLiteHelper.Table_Column_6_MonthlyCharge + " VARCHAR" +
                        ", " + SQLiteHelper.Table_Column_7_Box + " VARCHAR" +
                        ", " + SQLiteHelper.Table_Column_8_Cost + " VARCHAR" +
                        ", " + SQLiteHelper.Table_Column_9_StartDate + " VARCHAR" +
                        ", " + SQLiteHelper.Table_Column_10_MAC + " VARCHAR" +
                        ", " + SQLiteHelper.Table_Column_11_ExpiredDate + " VARCHAR" +
                        ", " + SQLiteHelper.Table_Column_12_ContractDays + " VARCHAR" +
                        ", " + SQLiteHelper.Table_Column_13_Message + " VARCHAR" +
                        ");");
        sqLiteDatabaseObj.execSQL(
                "CREATE TABLE IF NOT EXISTS " + SQLiteHelper.TABLE2_NAME + "(" + SQLiteHelper.Table2_Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL " +
                        ", " + SQLiteHelper.Table2_Column_Name + " VARCHAR" +
                        ", " + SQLiteHelper.Table2_Column_DurationStart + " VARCHAR" +
                        ", " + SQLiteHelper.Table2_Column_DurationEnd + " VARCHAR" +
                        ", " + SQLiteHelper.Table2_Column_Message + " VARCHAR" +
                        ");");

    }

    //get input text from keyboard
    public void CheckEditTextStatus() {

        strNameHolder = editTextName.getText().toString();
        strNumberHolder = editTextPhoneNumber.getText().toString();
        strAddressHolder = editTextAddress.getText().toString();
        strServiceTypeHolder = editTextServiceType.getText().toString();
        //  strServiceHolder=editTextService.getText().toString();
        strMonthlyChargeHolder = editTextMonthlyCharge.getText().toString();
        strBoxHolder = editTextBox.getText().toString();
        strCostholder = editTextCost.getText().toString();
        strStartDateHolder = editTextStartDate.getText().toString();
        strMACHolder = editTextMAC.getText().toString();
        strExpiredDateHolder = editTextExpiredDate.getText().toString();
        strContractDaysHolder = editTextContractDays.getText().toString();
        strMessageHolder = editTextMessageHolder.getText().toString();
        radioGroupService = (RadioGroup) findViewById(R.id.radioGroupNewService5);
        int intChecked = radioGroupService.getCheckedRadioButtonId();
        switch (intChecked) {
            case R.id.radioButtonBuy:
                strServiceHolder = "Buy";
                break;
            case R.id.radioButtonRent:
                strServiceHolder = "Rent";
                break;
        }
        ;

        if (TextUtils.isEmpty(strNameHolder) || TextUtils.isEmpty(strNumberHolder)
                || TextUtils.isEmpty(strContractDaysHolder)
                || TextUtils.isEmpty(strBoxHolder)
                || TextUtils.isEmpty(strCostholder)
                || TextUtils.isEmpty(strMACHolder)
                || TextUtils.isEmpty(strMonthlyChargeHolder)
                || TextUtils.isEmpty(strServiceTypeHolder)
                || TextUtils.isEmpty(strAddressHolder)
                || TextUtils.isEmpty(strExpiredDateHolder)
                || TextUtils.isEmpty(strStartDateHolder)
                ) {

            EditTextEmptyHold = false;

        } else {

            EditTextEmptyHold = true;
        }
    }

    public void InsertDataIntoSQLiteDatabase() throws ParseException {


//        Toast.makeText(getApplicationContext(),
//                strStartDateHolder, Toast.LENGTH_SHORT).show();

        if (EditTextEmptyHold == true) {

            SQLiteDataBaseQueryHolder = "INSERT INTO " + SQLiteHelper.TABLE_NAME + " (name,phone_number" +
                    ",address" +
                    ",service_type" +
                    ",service" +
                    ",monthly_charge" +
                    ",box" +
                    ",cost" +
                    ",start_date" +
                    ",MAC_address" +
                    ",ExpiredDate" +
                    ",Contract_Days" +
                    ",message" +
                    ") VALUES('" + strNameHolder + "', '" + strNumberHolder + "'" +
                    ",'" + strAddressHolder + "'" +
                    ",'" + strServiceTypeHolder + "'" +
                    ",'" + strServiceHolder + "'" +
                    ",'" + strMonthlyChargeHolder + "'" +
                    ",'" + strBoxHolder + "'" +
                    ",'" + strCostholder + "'" +
                    ",'" + strStartDateHolder + "'" +
                    ",'" + strMACHolder + "'" +
                    ",'" + strExpiredDateHolder + "'" +
                    ",'" + strContractDaysHolder + "'" +
                    ",'" + strMessageHolder + "'" +
                    ");"
//                    +"INSERT INTO " + SQLiteHelper.TABLE2_NAME+"(" +
//                    "name"+
//                    ")VALUES('" +
//
//                    "')" +
//                    ""
            ;
            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);
            int m = DurationListEnd.size();
            int i2 = 0;
            do {
                SQLiteDataBaseQueryHolder2 = "INSERT INTO " + SQLiteHelper.TABLE2_NAME + "(" +
                        "name, duration_start, duration_end" +
                        ")VALUES('" +
                        strNameHolder + "'" +
                        ",'" + DurationListStart.get(i2) + "'" +
                        ",'" + DurationListEnd.get(i2) + "" +
                        "')"

                ;
                i2++;
            } while (i2 < m);

            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder2);


            sqLiteDatabaseObj.close();

            Toast.makeText(NewCustomerActivity.this, "Data Inserted Successfully", Toast.LENGTH_LONG).show();

        } else {

            Toast.makeText(NewCustomerActivity.this, "Please Fill All The Required Fields.", Toast.LENGTH_LONG).show();

        }
    }

    public void EmptyEditTextAfterDataInsert() {

        editTextName.getText().clear();
        editTextPhoneNumber.getText().clear();
        editTextServiceType.getText().clear();
        editTextMonthlyCharge.getText().clear();
        editTextBox.getText().clear();
        editTextCost.getText().clear();
        editTextStartDate.getText().clear();
        editTextMAC.getText().clear();
        editTextContractDays.getText().clear();
        editTextExpiredDate.getText().clear();
        editTextAddress.getText().clear();
        editTextMessageHolder.getText().clear();
        DurationListStart.clear();
    }


}