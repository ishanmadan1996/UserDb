package com.example.ishant.realdb;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import static com.example.ishant.realdb.DBOpenHelper.COLUMN_ID;
import static com.example.ishant.realdb.DBOpenHelper.TABLE_NAME;


public class MainActivity extends AppCompatActivity {

    SQLiteOpenHelper dbHelper;
    SQLiteDatabase database;
    DBOpenHelper ddb;
    EditText nm,em,gr,team,phno,id;
    Button bt;
    Button btnviewAll;
    Button btnDelete,sms;

    Button btnviewUpdate;
    Button edit;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dbHelper = new DBOpenHelper(this);
        database = dbHelper.getWritableDatabase();
        ddb = new DBOpenHelper(this);
        nm = (EditText)findViewById(R.id.editText);
        em = (EditText)findViewById(R.id.editText3);
        gr = (EditText)findViewById(R.id.editText4);
        team = (EditText)findViewById(R.id.editText5);
        phno = (EditText)findViewById(R.id.editText6);
        id = (EditText)findViewById(R.id.editText7);
        bt = (Button)findViewById(R.id.button) ;
        btnviewUpdate = (Button)findViewById(R.id.button2);
        btnDelete = (Button)findViewById(R.id.button4);
        btnviewAll = (Button)findViewById(R.id.button5);
        edit = (Button)findViewById(R.id.button6);
        sms = (Button)findViewById(R.id.button3);
        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              sendSMSMessage();
            }
        });


        Add();
        viewAll();
        UpdateData();
        DeleteData();

    }
    protected void sendSMSMessage() {


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        }else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
            SendMsg();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SendMsg();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS failed, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }

    public void SendMsg() {
        List<ContactsConstructor> contacts = ddb.getAllContacts();
        for (ContactsConstructor cn : contacts) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(cn.getPhoneNumber(), null,"I'm in danger", null, null);


        }
        Toast.makeText(getApplicationContext(), "SMS sent.",
                Toast.LENGTH_LONG).show();
    }


    public void Add () {
        {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isInserted = ddb.insertData(
                            nm.getText().toString(),
                            em.getText().toString(),
                            phno.getText().toString(),
                            gr.getText().toString(),
                            team.getText().toString());
                    if(isInserted)
                        Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();



                }
            });
        }}

        public void DeleteData() {
            btnDelete.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Integer deletedRows = ddb.deleteData(id.getText().toString());
                            if(deletedRows > 0)
                                Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(MainActivity.this,"Data not Deleted",Toast.LENGTH_LONG).show();
                        }
                    }
            );
        }
        public void UpdateData() {
            final StringBuffer buffer1 = new StringBuffer();


            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {

                        final String  []str = nm.getText().toString().split(" ");
//                        Intent intent  = new Intent(MainActivity.this.)

                        Cursor res = ddb.showAll(str);
                        if(res != null && res.moveToFirst()){
                            id.setText(res.getString(0));
                            nm.setText(res.getString(1));
                            em.setText(res.getString(2));
                            phno.setText(res.getString(3));
                            gr.setText(res.getString(4));
                            team.setText(res.getString(5));
                            res.close();
                        }

                    }
                    catch (NumberFormatException e) {}
                }
            });




            btnviewUpdate.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            boolean isUpdate = ddb.updateData(id.getText().toString(),
                                    nm.getText().toString(),
                                    em.getText().toString(),
                                    phno.getText().toString(),
                                    gr.getText().toString(),
                                    team.getText().toString());


                            if(isUpdate == true)
                                Toast.makeText(MainActivity.this,"Data Update",Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(MainActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();
                        }
                    }
            );
        }


        public void viewAll() {
            btnviewAll.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Cursor res = ddb.getAllData();
                            if(res.getCount() == 0) {
                                // show message
                                showMessage("Error","Nothing found");
                                return;
                            }

                            StringBuffer buffer = new StringBuffer();
                            while (res.moveToNext()) {
                                buffer.append("Id :"+ res.getString(0)+"\n");
                                buffer.append("Name :"+ res.getString(1)+"\n");
                                buffer.append("Email :"+ res.getString(2)+"\n");
                                buffer.append("PhoneNo :"+ res.getString(3)+"\n");
                                buffer.append("Group :"+ res.getString(4)+"\n");
                                buffer.append("Team :"+ res.getString(5)+"\n\n");
                            }

                            // Show all data
                            showMessage("Data",buffer.toString());
                        }
                    }
            );
        }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    }

