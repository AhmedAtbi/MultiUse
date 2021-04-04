package com.example.multipurposeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.multipurposeapp.dbmanager.TaskManager;
import com.example.multipurposeapp.model.Task;
import com.example.multiuseapp.R;
import com.google.android.material.textfield.TextInputLayout;

public class AjoutEditTaskActivity extends AppCompatActivity {


    private TextInputLayout ed_nom_task, ed_description_task;
    private NumberPicker np_priority_task;
    private Task task;
    int priority;
    private TaskManager taskManager;
    public static final String EXTRA_ID_TASK =
            "com.example.multiuseapp.EXTRA_ID_TASK";
    public static final String EXTRA_NOM_TASK =
            "com.example.multiuseapp.EXTRA_NOM_TASK";
    public static final String EXTRA_DESCRIPTION_TASK =
            "com.example.multiuseapp.EXTRA_DESCRIPTION_TASK";
    public static final String EXTRA_PRIORITY_TASK =
            "com.example.multiuseapp.PRIORITY_TASK";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_ajout_edit_task);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        ed_nom_task = findViewById(R.id.ed_nom_task);
        ed_description_task = findViewById(R.id.ed_description_task);
        np_priority_task = new NumberPicker(this);
        np_priority_task = (NumberPicker) findViewById(R.id.number_picker_priority);
        np_priority_task.setMinValue(1);
        np_priority_task.setMaxValue(10);


        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID_TASK)) {
            setTitle("Edit Task");
            ed_nom_task.getEditText().setText(intent.getStringExtra(EXTRA_NOM_TASK));
            ed_description_task.getEditText().setText(intent.getStringExtra(EXTRA_DESCRIPTION_TASK));
            np_priority_task.setValue(intent.getIntExtra(EXTRA_PRIORITY_TASK, 0));


        } else {
            setTitle("Add Task");
        }
    }

    private void saveTask() {
        String nom = ed_nom_task.getEditText().getText().toString();
        String description = ed_description_task.getEditText().getText().toString();
        priority = np_priority_task.getValue();


        if (nom.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert task's infos", Toast.LENGTH_SHORT).show();
            return;
        } else {

            task = new Task(nom, description, priority);

            int id = getIntent().getIntExtra(EXTRA_ID_TASK, -1);
            if (id != -1) {
                task.setId(id);
                taskManager = new TaskManager(AjoutEditTaskActivity.this);
                taskManager.updateTask(task);
                Toast.makeText(this, "Task " + task.getTask_name() + " updated", Toast.LENGTH_SHORT).show();

            } else {
                taskManager = new TaskManager(AjoutEditTaskActivity.this);
                task.setPriority(priority);
                taskManager.ajouterTask(task);
                Toast.makeText(this, "Task " + task.getTask_name() + " added", Toast.LENGTH_SHORT).show();
            }
            startActivity(new Intent(AjoutEditTaskActivity.this, MainActivity.class));
            finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.ajout_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_contact_menu:
                saveTask();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
