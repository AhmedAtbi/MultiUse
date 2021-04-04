package com.example.multipurposeapp.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ContactTaskDbSqlite extends SQLiteOpenHelper {

    public static final String DB_CONTACT = "db_contact_multiuse";
    public static final String TABLE_CONTACT = "contacts";
    public static final String COL_ID = "id";
    public static final String COL_NOM = "nom";
    public static final String COL_PRENOM = "prenom";
    public static final String COL_NUM = "num";
    public static final String COL_SEX = "sex";
    public static final String TABLE_TASK = "tasks";
    public static final String COL_ID_TASK = "id";
    public static final String COL_PRIORITY_TASK = "id";
    public static final String COL_NOM_TASK = "nom";
    public static final String COL_DESCRIPTION_TASK = "description";

    public static final int DB_VERSION = 5;
    public String req = null;
    public String reqTask = null;


    public ContactTaskDbSqlite(@Nullable Context context, int version) {
        super(context, DB_CONTACT, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        req = "CREATE TABLE " + TABLE_CONTACT + "(" +
                COL_ID + " INTEGER PRIMARY KEY , " +
                COL_NOM + " VARCHAR(255), " +
                COL_PRENOM + " VARCHAR(255), " +
                COL_NUM + " VARCHAR(255) , " +
                COL_SEX + " VARCHAR(10))";

        reqTask = "CREATE TABLE " + TABLE_TASK + "(" +
                COL_ID_TASK + " INTEGER PRIMARY KEY , " +
                COL_NOM + " VARCHAR(255), " +
                COL_DESCRIPTION_TASK + " VARCHAR(255) , " +
                COL_PRIORITY_TASK + "INTEGER)";


        db.execSQL(req);
        db.execSQL(reqTask);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String deleteDbContact = "DROP TABLE IF EXISTS " + TABLE_CONTACT;
        String deleteDbTask = "DROP TABLE IF EXISTS " + TABLE_TASK;

        db.execSQL(deleteDbContact);
        db.execSQL(deleteDbTask);

        onCreate(db);

    }


}
