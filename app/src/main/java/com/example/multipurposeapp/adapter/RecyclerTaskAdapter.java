package com.example.multipurposeapp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multipurposeapp.dbmanager.TaskManager;
import com.example.multipurposeapp.model.Task;
import com.example.multiuseapp.R;

import java.util.ArrayList;

public class RecyclerTaskAdapter extends RecyclerView.Adapter<RecyclerTaskAdapter.MyViewHolder> implements View.OnClickListener {


    Context con;

    ArrayList<Task> data;

    int indice = -1;
    TaskManager manager;

    public RecyclerTaskAdapter() {
    }

    private OnItemClickListener listener;


    public RecyclerTaskAdapter(Context con, ArrayList<Task> data) {
        this.con = con;
        this.data = data;
        manager = new TaskManager(con);


    }

    public Task getTaskAt(int position) {
        return data.get(position);

    }

    @NonNull
    @Override
    public RecyclerTaskAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Creation d'un view
        LayoutInflater inf = LayoutInflater.from(con);
        View v = inf.inflate(R.layout.task_view, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Task task = data.get(position);


        holder.nom_task_tv.setText(task.getTask_name());
        holder.description_task.setText(task.getDescription());
        holder.priority_task.setText(task.getPriority()+"");





    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View v) {


    }


    public interface OnItemClickListener {
        void onItemClick(Task task);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nom_task_tv, description_task,priority_task;


        public MyViewHolder(@NonNull View v) {
            super(v);


            nom_task_tv = v.findViewById(R.id.text_view_title);
            description_task = v.findViewById(R.id.text_view_description);
            priority_task = v.findViewById(R.id.text_view_priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null && getLayoutPosition() != RecyclerView.NO_POSITION)
                        listener.onItemClick(data.get(getLayoutPosition()));
                }
            });


        }


    }
}




