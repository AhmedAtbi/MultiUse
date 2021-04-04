package com.example.multipurposeapp.dbmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.multipurposeapp.model.Contact;
import com.example.multipurposeapp.model.ContactTaskDbSqlite;

import java.util.ArrayList;

public class ContactManager {

    SQLiteDatabase db=null;


    public ContactManager(Context context)
    {
        ContactTaskDbSqlite helper = new ContactTaskDbSqlite(context,
                ContactTaskDbSqlite.DB_VERSION);

        db = helper.getWritableDatabase();

    }


    public ArrayList<Contact> searchContact(String name){
        Cursor myCursor = db.rawQuery("SELECT * FROM "+ ContactTaskDbSqlite.TABLE_CONTACT+" WHERE "+ ContactTaskDbSqlite.COL_NOM+" LIKE '"+name+"%'",null);
        ArrayList<Contact> data_contact = new ArrayList<>();
        myCursor.moveToFirst();

        while (! myCursor.isAfterLast()) {
            int i = myCursor.getInt(0);
            String nom = myCursor.getString(1);
            String prenom = myCursor.getString(2);
            String numero = myCursor.getString(3);
            String sex = myCursor.getString(4);
            data_contact.add(new Contact(i,nom, prenom, numero, sex));
            myCursor.moveToNext();
        }
        return data_contact;
    }

    public void ajouterContact (Contact contact)
    {
        int id=0;
        Cursor myCursor = db.rawQuery("SELECT ID FROM "+ ContactTaskDbSqlite.TABLE_CONTACT+" ORDER BY "+ ContactTaskDbSqlite.COL_ID+" DESC",null);
        if (myCursor.moveToFirst()){
            id = (myCursor.getInt(0))+1;
        }
        ContentValues cv = new ContentValues();
        cv.put(ContactTaskDbSqlite.COL_ID,id);
        cv.put(ContactTaskDbSqlite.COL_NOM,contact.getNom());
        cv.put(ContactTaskDbSqlite.COL_PRENOM,contact.getPrenom());
        cv.put(ContactTaskDbSqlite.COL_NUM,contact.getNumero());
        cv.put(ContactTaskDbSqlite.COL_SEX,contact.getSex());
        db.insert(ContactTaskDbSqlite.TABLE_CONTACT,
                null,
                cv);
    }

    public ArrayList<Contact> getAllContact()
    {
        ArrayList<Contact> data_contact = new ArrayList<Contact>();

        Cursor myCursor = db.query(ContactTaskDbSqlite.TABLE_CONTACT,
                new String[]{ContactTaskDbSqlite.COL_ID,
                        ContactTaskDbSqlite.COL_NOM,
                        ContactTaskDbSqlite.COL_PRENOM,
                        ContactTaskDbSqlite.COL_NUM,
                        ContactTaskDbSqlite.COL_SEX},
                null,
                null,
                null,
                null,
                ContactTaskDbSqlite.COL_NOM);

        myCursor.moveToFirst();

        while (! myCursor.isAfterLast()) {
            int i = myCursor.getInt(0);
            String nom = myCursor.getString(1);
            String prenom = myCursor.getString(2);
            String numero = myCursor.getString(3);
            String sex = myCursor.getString(4);
            data_contact.add(new Contact(i,nom, prenom, numero, sex));
            myCursor.moveToNext();
        }
        return data_contact;
    }

    public void updateContact (Contact c)
    {
        ContentValues data = new ContentValues();
        data.put(ContactTaskDbSqlite.COL_NOM,c.getNom());
        data.put(ContactTaskDbSqlite.COL_PRENOM,c.getPrenom());
        data.put(ContactTaskDbSqlite.COL_NUM,c.getNumero());
        data.put(ContactTaskDbSqlite.COL_SEX,c.getSex());

        db.update(ContactTaskDbSqlite.TABLE_CONTACT,data,"id=?",new String[]{String.valueOf(c.getId())});
    }

    public void deleteAllContacts (){
        db.execSQL("delete from "+ ContactTaskDbSqlite.TABLE_CONTACT);

    }

    public void removeContact (Contact contact)
    {
        int id = contact.getId();
        db.execSQL("delete from "+ ContactTaskDbSqlite.TABLE_CONTACT +" WHERE ID="+id);

    }



}
