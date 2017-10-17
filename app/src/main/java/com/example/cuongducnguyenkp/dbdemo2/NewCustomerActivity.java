package com.example.cuongducnguyenkp.dbdemo2;

/**
 * Created by cuongducnguyen.kp on 10/11/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class NewCustomerActivity extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabaseObj;
    EditText editTextName, editTextPhoneNumber, editTextAddress
            , editTextServiceType, editTextMonthlyCharge, editTextBox, editTextCost, editTextStartDate
            , editTextMAC, editTextContractDays, editTextExpiredDate, editTextMessage
            ;
    String strNameHolder, strNumberHolder, SQLiteDataBaseQueryHolder
            , strAddressHolder,strServiceTypeHolder, strServiceHolder
            , strMonthlyChargeHolder, strBoxHolder, strCostholder, strStartDateHolder
            , strMACHolder, strExpiredDateHolder, strContractDaysHolder, strMessageHolder
    ;
    FloatingActionButton EnterData, ButtonDisplayData, buttonTest;
    RadioGroup radioGroupService;
    Boolean EditTextEmptyHold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cust);

        EnterData = (FloatingActionButton) findViewById(R.id.button);
        ButtonDisplayData = (FloatingActionButton) findViewById(R.id.button2);
        editTextName = (EditText)findViewById(R.id.editTextNewName1);
        editTextPhoneNumber = (EditText)findViewById(R.id.editTextNewPhone2);
        editTextAddress = (EditText)findViewById(R.id.editTextNewAddress3);

        EnterData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),
                        "Redirecting...",Toast.LENGTH_SHORT).show();

                SQLiteDataBaseBuild();

                SQLiteTableBuild();

                CheckEditTextStatus();

                InsertDataIntoSQLiteDatabase();

                EmptyEditTextAfterDataInsert();


            }
        });
//Button display all input record
        ButtonDisplayData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),
                        "Redirecting...",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NewCustomerActivity.this, DisplaySQLiteDataActivity.class);
                startActivity(intent);
            }
        });
    }

    public void SQLiteDataBaseBuild(){

        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }

    public void SQLiteTableBuild(){

        sqLiteDatabaseObj.execSQL(
//                "DROP TABLE IF EXISTS '"+SQLiteHelper.TABLE_NAME+"'; " +
                "CREATE TABLE IF NOT EXISTS "+SQLiteHelper.TABLE_NAME+"("+SQLiteHelper.Table_Column_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+SQLiteHelper.Table_Column_1_Name+" VARCHAR, "+SQLiteHelper.Table_Column_2_PhoneNumber+" VARCHAR" +
                        ", "+SQLiteHelper.Table_Column_3_Address+" VARCHAR" +
                        ", "+SQLiteHelper.Table_Column_4_ServiceType+" VARCHAR" +
                        ", "+SQLiteHelper.Table_Column_5_Service+" VARCHAR" +
                        ", "+SQLiteHelper.Table_Column_6_MonthlyCharge+" VARCHAR" +
                        ", "+SQLiteHelper.Table_Column_7_Box+" VARCHAR" +
                        ", "+SQLiteHelper.Table_Column_8_Cost+" VARCHAR" +
                        ", "+SQLiteHelper.Table_Column_9_StartDate+" VARCHAR" +
                        ", "+SQLiteHelper.Table_Column_10_MAC+" VARCHAR" +
                        ", "+SQLiteHelper.Table_Column_11_ExpiredDate+" VARCHAR" +
                        ", "+SQLiteHelper.Table_Column_12_ContractDays+" VARCHAR" +
                        ", "+SQLiteHelper.Table_Column_13_Message+" VARCHAR" +
                        ");");

    }
    //get input text from keyboard
    public void CheckEditTextStatus(){

        strNameHolder = editTextName.getText().toString() ;
        strNumberHolder = editTextPhoneNumber.getText().toString();
        strAddressHolder = editTextAddress.getText().toString();

        if(TextUtils.isEmpty(strNameHolder) || TextUtils.isEmpty(strNumberHolder)){

            EditTextEmptyHold = false ;

        }
        else {

            EditTextEmptyHold = true ;
        }
    }

    public void InsertDataIntoSQLiteDatabase(){

        if(EditTextEmptyHold == true)
        {

            SQLiteDataBaseQueryHolder = "INSERT INTO "+SQLiteHelper.TABLE_NAME+" (name,phone_number" +
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
                    ") VALUES('"+ strNameHolder +"', '"+ strNumberHolder +"'" +
                    ",'"+ strAddressHolder +"'"+
                    ",'"+ strServiceTypeHolder +"'"+
                    ",'"+ strServiceHolder +"'"+
                    ",'"+ strMonthlyChargeHolder +"'"+
                    ",'"+ strBoxHolder +"'"+
                    ",'"+ strCostholder +"'"+
                    ",'"+ strStartDateHolder +"'"+
                    ",'"+ strMACHolder +"'"+
                    ",'"+ strExpiredDateHolder +"'"+
                    ",'"+ strContractDaysHolder +"'"+
                    ",'"+ strMessageHolder +"'"+
                    ");";

            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);

            sqLiteDatabaseObj.close();

            Toast.makeText(NewCustomerActivity.this,"Data Inserted Successfully", Toast.LENGTH_LONG).show();

        }
        else {

            Toast.makeText(NewCustomerActivity.this,"Please Fill All The Required Fields.", Toast.LENGTH_LONG).show();

        }

    }

    public void EmptyEditTextAfterDataInsert(){

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
        editTextMessage.getText().clear();


    }

}