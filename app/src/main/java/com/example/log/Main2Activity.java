package com.example.log;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String ae = "";
    String tvText = "";
    int balan = 0;
    EditText e1, e3, e4;
    Button btn1, btn2;

    dbhelper mydb;
    //GraphView graphView;
    //SQLiteDatabase sqLiteDatabase; //graph
    //LineGraphSeries<DataPoint> series; //graph

    ArrayList<String> listItem, listItem2;
    ArrayAdapter adapter, adapter2;
    ListView userlist, userlist2;
    String qq = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView tv = (TextView) findViewById(R.id.tv1);

        mydb = new dbhelper(this);
        //ae=mydb.viewData2();
        //sqLiteDatabase = mydb.getWritableDatabase();//graph
        //exqButton(); //graph
        //graphView.addSeries(series); //graph


        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("General");
        categories.add("Food");
        //categories.add("Transport");
        categories.add("Others");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        e1 = (EditText) findViewById(R.id.et1);
        //e2= (EditText) findViewById(R.id.et2);
        e3 = (EditText) findViewById(R.id.et3);//expense FOR graph
        e4 = (EditText) findViewById(R.id.et4);//balance for graph
       // graphView = findViewById(R.id.graph);


        btn1 = (Button) findViewById(R.id.submit);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveData();
               // exqButton(); //graph
            }
        });

        Button b1 = (Button) findViewById(R.id.button3);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveDataB();

            }
        });


        Button b0 = (Button) findViewById(R.id.gotobalance);
        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, balance.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        Button b00 = (Button) findViewById(R.id.gotoexpense);
        b00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, expense.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        Button b000 = (Button) findViewById(R.id.outbttn);
        b000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        Button g = (Button) findViewById(R.id.btngraph);
        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, graph1.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        Button v = (Button) findViewById(R.id.v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewData();

            }
        });


        userlist = findViewById(R.id.tv);
        userlist2 = findViewById(R.id.tvB);
        listItem = new ArrayList<>();
        listItem2 = new ArrayList<>();
        viewData();
        userlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = userlist.getItemAtPosition(i).toString();
                Toast.makeText(Main2Activity.this, "" + text, Toast.LENGTH_SHORT).show();
            }
        });

        Cursor cursor = mydb.viewDataB();
        if (cursor.getCount() == 0) {
            tvText = "0";
        } else {
            while (cursor.moveToNext()) {
                tvText += cursor.getString(1);
                balan += Integer.parseInt(tvText);
                tvText = "";

            }
            tvText = "";
        }
        Cursor cursor2 = mydb.viewData();
        if (cursor2.getCount() == 0) {
            //ae="0";
        } else {
            while (cursor2.moveToNext()) {
                tvText += cursor2.getString(3);
                balan -= Integer.parseInt(tvText);
                tvText = "";

            }
            tvText = "";
        }


        tvText += "Current Balance: " + balan;
        tv.setText(tvText);



    }

    /*private void exqButton() {  //graph

                int balanceVal = Integer.parseInt(String.valueOf(e4.getText()));
                int expenseVal = Integer.parseInt(String.valueOf(e3.getText()));
                mydb.insertDataGraph(balanceVal, expenseVal);
                series.resetData(getDataPoint());

            }



    private DataPoint[] getDataPoint() { //graph
        String []columns= {"balanceValues","expenseValues"};
        Cursor cursor=sqLiteDatabase.query("MyTable",columns,null,null,null,null,null);
        DataPoint[] dp=new DataPoint[cursor.getCount()];

        for(int i=0;i<cursor.getCount();i++ ){
            cursor.moveToNext();
            dp[i]= new DataPoint(cursor.getInt(0),cursor.getInt(1));
        }
        return dp;
    }*/


    public void SaveDataB(){

        String amount=e4.getText().toString();
        //int balance=0;
        int amnt=0;
        try {
            amnt = Integer.parseInt(amount);
        }
        catch (Exception e){

        }
        if(amnt>0){

            Boolean result= mydb.insertDataB(amnt);
            if (result==true){
                Toast.makeText(Main2Activity.this, "Balance inserted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(Main2Activity.this, "Balance not inserted", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(Main2Activity.this, "ADDED Balance can not be zero", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);

    }

    public void SaveData(){
        String type=qq;
        //String desc=e2.getText().toString();
        String amount=e3.getText().toString();
        //int balance=0;
        int amnt=0;
        try {
            amnt = Integer.parseInt(amount);
        }
        catch (Exception e){

        }
        if(amnt>0){
            if (type.equals("Others")){//type
                type=e1.getText().toString();
            }
            else{
                type=qq;
            }

            Boolean result= mydb.insertData(type,amnt);
            if (result==true){
                Toast.makeText(Main2Activity.this, "Expense deducted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(Main2Activity.this, "DATA not inserted", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(Main2Activity.this, "Amount can not be zero", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);

    }


    @SuppressLint("ResourceType")
    private void viewData() {
        Cursor cursor=mydb.viewData();
        if(cursor.getCount()==0){
            Toast.makeText(this,"none", Toast.LENGTH_SHORT).show();
        }
        else{
            while (cursor.moveToNext()){
                listItem.add(" "+cursor.getString(1)+"\n Cost: " +cursor.getString(3));
            }
            adapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1, listItem);
            userlist.setAdapter(adapter);

            Cursor cursor1=mydb.viewDataB();
            if(cursor1.getCount()==0){
                Toast.makeText(this,"none", Toast.LENGTH_SHORT).show();
            }
            else{
                while (cursor1.moveToNext()){
                    //listItem.add("Balance Added "+cursor1.getString(1));
                    listItem2.add("Balance Added "+cursor1.getString(1));
                }
                //adapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1, listItem);
                //
                // userlist.setAdapter(adapter);
                adapter2= new ArrayAdapter(this,android.R.layout.simple_list_item_1, listItem2);
                userlist2.setAdapter(adapter2);
            }

        }
        //Toast.makeText(this,"eita" + cursor.getString(3),Toast.LENGTH_LONG).show();
        Toast.makeText(this,"ei jjjjjjj", Toast.LENGTH_SHORT).show();
    }




    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();
        qq=item;


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
