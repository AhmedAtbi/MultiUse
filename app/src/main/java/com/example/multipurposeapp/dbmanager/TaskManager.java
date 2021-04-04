package com.example.multipurposeapp.dbmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.multipurposeapp.model.ContactTaskDbSqlite;
import com.example.multipurposeapp.model.Task;

import java.util.ArrayList;

public class TaskManager {



    SQLiteDatabase db=null;


    public TaskManager(Context context)
    {
        ContactTaskDbSqlite helper = new ContactTaskDbSqlite(context,
                ContactTaskDbSqlite.DB_VERSION);

        db = helper.getWritableDatabase();

    }


    public void ajouterTask (Task task)
    {
        int id=0;
        Cursor myCursor = db.rawQuery("SELECT ID FROM "+ ContactTaskDbSqlite.TABLE_CONTACT+" ORDER BY "+ ContactTaskDbSqlite.COL_ID+" DESC",null);
        if (myCursor.moveToFirst()){
            id = (myCursor.getInt(0))+1;
        }
        ContentValues cv = new ContentValues();
        cv.put(ContactTaskDbSqlite.COL_ID_TASK,id);
        cv.put(ContactTaskDbSqlite.COL_NOM_TASK,task.getTask_name());
        cv.put(ContactTaskDbSqlite.COL_DESCRIPTION_TASK,task.getDescription());
        cv.put(ContactTaskDbSqlite.COL_PRIORITY_TASK,task.getPriority());

        db.insert(ContactTaskDbSqlite.TABLE_TASK,
                null,
                cv);
    }

    public ArrayList<Task> getAllTasks()
    {
        ArrayList<Task> data_tasks = new ArrayList<Task>();

        Cursor myCursor = db.query(ContactTaskDbSqlite.TABLE_TASK,
                new String[]{ContactTaskDbSqlite.COL_ID_TASK,
                        ContactTaskDbSqlite.COL_NOM_TASK,
                        ContactTaskDbSqlite.COL_DESCRIPTION_TASK,
                        ContactTaskDbSqlite.COL_PRIORITY_TASK},
                null,
                null,
                null,
                null,
                ContactTaskDbSqlite.COL_PRIORITY_TASK);

        myCursor.moveToFirst();

        while (! myCursor.isAfterLast()) {
            int i = myCursor.getInt(0);
            String nom = myCursor.getString(1);
            String description = myCursor.getString(2);
            int priority = myCursor.getInt(3);
            data_tasks.add(new Task(i,nom, description,priority));
            myCursor.moveToNext();
        }
        return data_tasks;
    }

    public void updateTask (Task task)
    {
        ContentValues data = new ContentValues();
        data.put(ContactTaskDbSqlite.COL_NOM_TASK,task.getTask_name());
        data.put(ContactTaskDbSqlite.COL_DESCRIPTION_TASK,task.getDescription());
        data.put(ContactTaskDbSqlite.COL_PRIORITY_TASK,task.getPriority());


        db.update(ContactTaskDbSqlite.TABLE_TASK,data,"id=?",new String[]{String.valueOf(task.getId())});
    }

    public void deleteAllTasks (){
        db.execSQL("delete from "+ ContactTaskDbSqlite.TABLE_TASK);

    }

    public void removeTask (Task task)
    {
        int id = task.getId();
        db.execSQL("delete from "+ ContactTaskDbSqlite.TABLE_TASK +" WHERE ID="+id);

    }


}
