package com.example.multipurposeapp.ui.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multipurposeapp.AjoutEditTaskActivity;
import com.example.multipurposeapp.adapter.RecyclerTaskAdapter;
import com.example.multipurposeapp.dbmanager.TaskManager;
import com.example.multipurposeapp.model.Task;
import com.example.multiuseapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TaskFragment extends Fragment {

    RecyclerView recyclerView;

    private TaskManager taskManager;
    private ArrayList<Task> data_tasks;
    private RecyclerTaskAdapter adapter;

    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_task, container, false);
        FloatingActionButton btn_ajout = root.findViewById(R.id.button_add_task);
        recyclerView = root.findViewById(R.id.recycler_task);


        setHasOptionsMenu(true);




        taskManager = new TaskManager(root.getContext());
        data_tasks = taskManager.getAllTasks();
        adapter = new RecyclerTaskAdapter(getActivity(),data_tasks);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);


        btn_ajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AjoutEditTaskActivity.class));
            }
        });



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Task task = adapter.getTaskAt(viewHolder.getLayoutPosition());
                data_tasks.remove(viewHolder.getLayoutPosition());
                taskManager.removeTask(task);
                adapter.notifyItemRemoved(viewHolder.getLayoutPosition());
                Toast.makeText(getActivity(), ""+task.getTask_name()+" Deleted", Toast.LENGTH_SHORT).show();
                return;
            }
        }).attachToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(new RecyclerTaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Task task) {
                Intent intent = new Intent(getActivity(), AjoutEditTaskActivity.class);
                intent.putExtra(AjoutEditTaskActivity.EXTRA_ID_TASK, task.getId());
                intent.putExtra(AjoutEditTaskActivity.EXTRA_NOM_TASK, task.getTask_name());
                intent.putExtra(AjoutEditTaskActivity.EXTRA_DESCRIPTION_TASK, task.getDescription());
                intent.putExtra(AjoutEditTaskActivity.EXTRA_PRIORITY_TASK, task.getPriority());
                startActivity(intent);

            }


        });

        return root;

    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.contact_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all_contacts:
                taskManager.deleteAllTasks();
                data_tasks.clear();
                adapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "All tasks deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }



}