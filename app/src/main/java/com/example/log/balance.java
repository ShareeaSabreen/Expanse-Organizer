package com.example.log;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class balance extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    ArrayList<String> listItem2;
    ArrayAdapter adapter2;
    dbhelper mydb;
    ListView userlist2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        mydb =new dbhelper(this);

        userlist2 = findViewById(R.id.list);
        listItem2 = new ArrayList<>();
        viewData();
        userlist2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = userlist2.getItemAtPosition(i).toString();
                Toast.makeText(balance.this, "" + text, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void viewData() {
        Cursor cursor1 = mydb.viewDataB();
        if (cursor1.getCount() == 0) {
            //Toast.makeText(this,"none", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor1.moveToNext()) {
                //listItem.add("Balance Added "+cursor1.getString(1));
                listItem2.add("Balance Added " + cursor1.getString(1));
            }
            //adapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1, listItem);
            //
            // userlist.setAdapter(adapter);
            adapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItem2);
            userlist2.setAdapter(adapter2);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
