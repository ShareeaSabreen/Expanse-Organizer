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

public class expense extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ArrayList<String> listItem;
    ArrayAdapter adapter;
    dbhelper mdb;
    ListView userlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);


        mdb = new dbhelper(this);

        userlist = findViewById(R.id.list1);
        listItem = new ArrayList<>();
        viewData();
        userlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = userlist.getItemAtPosition(i).toString();
                Toast.makeText(expense.this, "" + text, Toast.LENGTH_SHORT).show();
            }
        });
    }


        private void viewData () {
            Cursor cursor = mdb.viewData();
            if (cursor.getCount() == 0) {
                Toast.makeText(this, "none", Toast.LENGTH_SHORT).show();
            } else {
                while (cursor.moveToNext()) {
                    listItem.add(" " + cursor.getString(1) + "\n Cost: " + cursor.getString(3));
                }
                adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItem);
                userlist.setAdapter(adapter);


            }
        }

        @Override
        public void onItemSelected (AdapterView < ? > adapterView, View view,int i, long l){

        }

        @Override
        public void onNothingSelected (AdapterView < ? > adapterView){

        }
    }


