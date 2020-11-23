package com.example.log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

public class dbhelper extends SQLiteOpenHelper {

    private Context con;//graph

    public  static  final  String DATABASE_NAME = "myDB.db";
    public  static  final  String TABLE_NAME  = "expense";
    public  static  final  String TABLE_NAME1  = "balance";

    public  static  final  String COL_1  = "ID";
    public  static  final  String COL_2  = "TYPE";
   // public  static  final  String COL_3  = "DESCR";
    public  static  final  String COL_4  = "COST";
    public  static  final  String COL_5  = "BALANCE";


    public dbhelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);

        con=context;//graph

        SQLiteDatabase database = this.getWritableDatabase();

    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT ,TYPE TEXT , COST INTEGER)");
        sqLiteDatabase.execSQL("CREATE TABLE balance (ID INTEGER PRIMARY KEY AUTOINCREMENT, BALANCE INTEGER)" );

        //String createTable="create table MyTable (balanceValues INTEGER,expenseValues INTEGER);"; //graph

        //sqLiteDatabase.execSQL(createTable); //graph

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME1);


        onCreate(sqLiteDatabase);

    }


    /*public void insertDataGraph(int x,int y){ //graph
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("balanceValues",x);
        contentValues.put("expenseValues",y);
        sqLiteDatabase.insert("MyTable",null,contentValues);
    }*/



    public boolean insertData(String type,  int cost){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COL_2,type);
        //cv.put(COL_3,desc);
        cv.put(COL_4,cost);
        //cv.put(COL_5,balance);
        long result= db.insert(TABLE_NAME,null,cv);
        db.close();
        if(result==-1){
            return false;
        }
        else {
            return true;
        }

    }

    public boolean insertDataB(int balance){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();


        cv.put(COL_5,balance);
        long result= db.insert(TABLE_NAME1,null,cv);
        db.close();
        if(result==-1){
            return false;
        }
        else {
            return true;
        }

    }

    public Cursor viewData(){
        SQLiteDatabase db=this.getReadableDatabase();
        String query= "Select * from expense";
        Cursor cursor=db.rawQuery(query, null);

        return cursor;
    }

    public Cursor viewDataB(){
        SQLiteDatabase db=this.getReadableDatabase();
        String query= "Select * from balance";
        Cursor cursor=db.rawQuery(query, null);

        return cursor;
    }



}
