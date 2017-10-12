package com.example.cuongducnguyenkp.dbdemo2;

/**
 * Created by cuongducnguyen.kp on 10/11/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewCustomerActivity extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabaseObj;
    EditText editTextName, editTextPhoneNumber;
    String NameHolder, NumberHolder, SQLiteDataBaseQueryHolder;
    Button EnterData, ButtonDisplayData, buttonTest;
    Boolean EditTextEmptyHold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cust);

        EnterData = (Button)findViewById(R.id.button);
        ButtonDisplayData = (Button)findViewById(R.id.button2);
        editTextName = (EditText)findViewById(R.id.editText);
        editTextPhoneNumber = (EditText)findViewById(R.id.editText2);

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

        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS "+SQLiteHelper.TABLE_NAME+"("+SQLiteHelper.Table_Column_ID+" PRIMARY KEY AUTOINCREMENT NOT NULL, "+SQLiteHelper.Table_Column_1_Name+" VARCHAR, "+SQLiteHelper.Table_Column_2_PhoneNumber+" VARCHAR);");

    }
    //get input text from keyboard
    public void CheckEditTextStatus(){

        NameHolder = editTextName.getText().toString() ;
        NumberHolder = editTextPhoneNumber.getText().toString();

        if(TextUtils.isEmpty(NameHolder) || TextUtils.isEmpty(NumberHolder)){

            EditTextEmptyHold = false ;

        }
        else {

            EditTextEmptyHold = true ;
        }
    }

    public void InsertDataIntoSQLiteDatabase(){

        if(EditTextEmptyHold == true)
        {

            SQLiteDataBaseQueryHolder = "INSERT INTO "+SQLiteHelper.TABLE_NAME+" (name,phone_number) VALUES('"+NameHolder+"', '"+NumberHolder+"');";

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

    }

}